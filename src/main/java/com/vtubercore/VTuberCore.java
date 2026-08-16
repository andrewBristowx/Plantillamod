package com.vtubercore;

import com.vtubercore.config.BrandingConfig;
import com.vtubercore.config.CoreConfigManager;
import com.vtubercore.config.ModulesConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VTuberCore implements ModInitializer {
    public static final String MOD_ID = "vtubercore";
    public static final String VERSION = "0.1.0-alpha.1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CoreConfigManager.load();
        registerCommands();
        LOGGER.info("VTuber Cobblemon Core {} iniciado para {}", VERSION, CoreConfigManager.branding().projectName);
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                CommandManager.literal("vtubercore")
                        .then(CommandManager.literal("status")
                                .executes(context -> {
                                    BrandingConfig brand = CoreConfigManager.branding();
                                    ModulesConfig modules = CoreConfigManager.modules();
                                    var source = context.getSource();
                                    source.sendFeedback(() -> Text.literal("§d✦ VTuber Cobblemon Core §f" + VERSION), false);
                                    source.sendFeedback(() -> Text.literal("§7Proyecto: §f" + brand.projectName + " §8| §7Creador: §f" + brand.creatorName), false);
                                    source.sendFeedback(() -> Text.literal("§7Moneda: §f" + brand.currencySymbol + " " + brand.currencyName), false);
                                    source.sendFeedback(() -> Text.literal("§7Módulos configurados: §f" + enabledCount(modules) + "/14 activos"), false);
                                    source.sendFeedback(() -> Text.literal("§8Foundation: los módulos se activarán conforme sean migrados y validados."), false);
                                    return 1;
                                }))
                        .then(CommandManager.literal("reload")
                                .requires(source -> source.hasPermissionLevel(2))
                                .executes(context -> {
                                    CoreConfigManager.load();
                                    context.getSource().sendFeedback(() -> Text.literal("§aVTuberCore: configuración recargada."), true);
                                    return 1;
                                }))
        ));
    }

    private static int enabledCount(ModulesConfig m) {
        int total = 0;
        if (m.economy) total++;
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
