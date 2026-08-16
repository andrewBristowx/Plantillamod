package com.vtubercore;

import com.mojang.brigadier.arguments.LongArgumentType;
import com.vtubercore.config.BrandingConfig;
import com.vtubercore.config.CoreConfigManager;
import com.vtubercore.config.ModulesConfig;
import com.vtubercore.economy.EconomyService;
import com.vtubercore.rewards.DailyLoginService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VTuberCore implements ModInitializer {
    public static final String MOD_ID = "vtubercore";
    public static final String VERSION = "0.1.0-alpha.2";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CoreConfigManager.load();
        registerCommands();
        LOGGER.info("VTuber Cobblemon Core {} iniciado para {}", VERSION, CoreConfigManager.branding().projectName);
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("vtubercore")
                    .then(CommandManager.literal("status").executes(context -> status(context.getSource())))
                    .then(CommandManager.literal("reload")
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> {
                                CoreConfigManager.load();
                                context.getSource().sendFeedback(() -> Text.literal("§aVTuberCore: configuración recargada."), true);
                                return 1;
                            }))
                    .then(CommandManager.literal("balance").executes(context -> balance(context.getSource())))
                    .then(CommandManager.literal("economy")
                            .then(CommandManager.literal("give")
                                    .requires(source -> source.hasPermissionLevel(2))
                                    .then(CommandManager.argument("player", EntityArgumentType.player())
                                            .then(CommandManager.argument("amount", LongArgumentType.longArg(1L))
                                                    .executes(context -> {
                                                        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
                                                        long amount = LongArgumentType.getLong(context, "amount");
                                                        long total = EconomyService.add(target.getUuid(), amount);
                                                        BrandingConfig brand = CoreConfigManager.branding();
                                                        context.getSource().sendFeedback(() -> Text.literal("§aAñadido §f" + brand.currencySymbol + " " + amount + " §aa " + target.getName().getString() + ". Saldo: §f" + total), true);
                                                        return 1;
                                                    })))))
                    .then(CommandManager.literal("daily")
                            .then(CommandManager.literal("status").executes(context -> dailyStatus(context.getSource())))
                            .then(CommandManager.literal("claim").executes(context -> dailyClaim(context.getSource())))
                            .then(CommandManager.literal("reset")
                                    .requires(source -> source.hasPermissionLevel(2))
                                    .then(CommandManager.argument("player", EntityArgumentType.player())
                                            .executes(context -> {
                                                ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
                                                DailyLoginService.reset(target);
                                                context.getSource().sendFeedback(() -> Text.literal("§aDaily reset para " + target.getName().getString()), true);
                                                return 1;
                                            })))));

            dispatcher.register(CommandManager.literal("balance").executes(context -> balance(context.getSource())));
            dispatcher.register(CommandManager.literal("daily")
                    .executes(context -> dailyStatus(context.getSource()))
                    .then(CommandManager.literal("claim").executes(context -> dailyClaim(context.getSource()))));
        });
    }

    private static int status(ServerCommandSource source) {
        BrandingConfig brand = CoreConfigManager.branding();
        ModulesConfig modules = CoreConfigManager.modules();
        source.sendFeedback(() -> Text.literal("§d✦ VTuber Cobblemon Core §f" + VERSION), false);
        source.sendFeedback(() -> Text.literal("§7Proyecto: §f" + brand.projectName + " §8| §7Creador: §f" + brand.creatorName), false);
        source.sendFeedback(() -> Text.literal("§7Moneda: §f" + brand.currencySymbol + " " + brand.currencyName), false);
        source.sendFeedback(() -> Text.literal("§7Módulos activos: §f" + enabledCount(modules) + "/20"), false);
        source.sendFeedback(() -> Text.literal("§7Tema GUI: §f" + CoreConfigManager.guiTheme().themeId), false);
        return 1;
    }

    private static int balance(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("Este comando requiere un jugador."));
            return 0;
        }
        if (!CoreConfigManager.modules().economy) {
            source.sendError(Text.literal("El módulo economy está desactivado."));
            return 0;
        }
        BrandingConfig brand = CoreConfigManager.branding();
        long amount = EconomyService.balance(player.getUuid());
        source.sendFeedback(() -> Text.literal("§d" + brand.currencyName + ": §f" + brand.currencySymbol + " " + amount), false);
        return 1;
    }

    private static int dailyStatus(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("Este comando requiere un jugador."));
            return 0;
        }
        if (!CoreConfigManager.modules().dailyLogin || !CoreConfigManager.dailyLogin().enabled) {
            source.sendError(Text.literal("El login diario está desactivado."));
            return 0;
        }
        BrandingConfig brand = CoreConfigManager.branding();
        var status = DailyLoginService.status(player);
        var reward = status.nextReward();
        source.sendFeedback(() -> Text.literal("§d✦ " + CoreConfigManager.dailyLogin().title), false);
        source.sendFeedback(() -> Text.literal("§7Racha actual: §f" + status.currentStreak() + " §8| §7Reclamos totales: §f" + status.totalClaims()), false);
        if (status.claimedToday()) {
            source.sendFeedback(() -> Text.literal("§aYa reclamaste la recompensa de hoy."), false);
        } else {
            source.sendFeedback(() -> Text.literal("§7Próxima recompensa: §f" + reward.label + " §8— §d" + brand.currencySymbol + " " + reward.currencyAmount), false);
            source.sendFeedback(() -> Text.literal("§7Usa §f/daily claim §7para reclamarla."), false);
        }
        return 1;
    }

    private static int dailyClaim(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("Este comando requiere un jugador."));
            return 0;
        }
        if (!CoreConfigManager.modules().dailyLogin) {
            source.sendError(Text.literal("El login diario está desactivado."));
            return 0;
        }
        var result = DailyLoginService.claim(player);
        BrandingConfig brand = CoreConfigManager.branding();
        switch (result.status()) {
            case DISABLED -> source.sendError(Text.literal("El login diario está desactivado."));
            case ALREADY_CLAIMED -> source.sendFeedback(() -> Text.literal("§eYa reclamaste tu recompensa de hoy."), false);
            case CLAIMED -> {
                source.sendFeedback(() -> Text.literal("§d✦ Recompensa diaria reclamada"), false);
                source.sendFeedback(() -> Text.literal("§7Racha: §f" + result.streak() + " §8| §7Premio: §d" + brand.currencySymbol + " " + result.reward().currencyAmount), false);
                source.sendFeedback(() -> Text.literal("§7Saldo nuevo: §f" + brand.currencySymbol + " " + result.newBalance()), false);
            }
        }
        return result.status() == DailyLoginService.ClaimResult.Status.CLAIMED ? 1 : 0;
    }

    private static int enabledCount(ModulesConfig m) {
        int total = 0;
        if (m.economy) total++;
        if (m.dailyLogin) total++;
        if (m.guiTemplates) total++;
        if (m.jobs) total++;
        if (m.shop) total++;
        if (m.kits) total++;
        if (m.battlePass) total++;
        if (m.gacha) total++;
        if (m.customPokemon) total++;
        if (m.npcs) total++;
        if (m.dungeons) total++;
        if (m.protections) total++;
        if (m.toolsAndArmor) total++;
        if (m.hubAndMedia) total++;
        if (m.casino) total++;
        if (m.pastureOptimizer) total++;
        if (m.mobControl) total++;
        if (m.entityCleaner) total++;
        if (m.specialSpawnAlerts) total++;
        if (m.campaignEngine) total++;
        return total;
    }
}
