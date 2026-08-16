package com.vtubercore.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vtubercore.data.PlayerDataStore;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CoreConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path ROOT = FabricLoader.getInstance().getConfigDir().resolve("vtubercore");
    private static final Path BRANDING = ROOT.resolve("branding.json");
    private static final Path MODULES = ROOT.resolve("modules.json");
    private static final Path DAILY_LOGIN = ROOT.resolve("daily_login.json");
    private static final Path GUI_THEME = ROOT.resolve("gui_theme.json");

    private static BrandingConfig branding = new BrandingConfig();
    private static ModulesConfig modules = new ModulesConfig();
    private static DailyLoginConfig dailyLogin = new DailyLoginConfig();
    private static GuiThemeConfig guiTheme = new GuiThemeConfig();

    private CoreConfigManager() {
    }

    public static synchronized void load() {
        try {
            Files.createDirectories(ROOT);
            branding = readOrCreate(BRANDING, BrandingConfig.class, new BrandingConfig());
            branding.sanitize();
            modules = readOrCreate(MODULES, ModulesConfig.class, new ModulesConfig());
            modules.sanitize();
            dailyLogin = readOrCreate(DAILY_LOGIN, DailyLoginConfig.class, new DailyLoginConfig());
            dailyLogin.sanitize();
            guiTheme = readOrCreate(GUI_THEME, GuiThemeConfig.class, new GuiThemeConfig());
            guiTheme.sanitize();
            write(BRANDING, branding);
            write(MODULES, modules);
            write(DAILY_LOGIN, dailyLogin);
            write(GUI_THEME, guiTheme);
            PlayerDataStore.clearCache();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar config/vtubercore", e);
        }
    }

    public static BrandingConfig branding() {
        return branding;
    }

    public static ModulesConfig modules() {
        return modules;
    }

    public static DailyLoginConfig dailyLogin() {
        return dailyLogin;
    }

    public static GuiThemeConfig guiTheme() {
        return guiTheme;
    }

    private static <T> T readOrCreate(Path path, Class<T> type, T defaults) throws IOException {
        if (!Files.exists(path)) {
            write(path, defaults);
            return defaults;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            T value = GSON.fromJson(reader, type);
            return value == null ? defaults : value;
        }
    }

    private static void write(Path path, Object value) throws IOException {
        try (Writer writer = Files.newBufferedWriter(path)) {
            GSON.toJson(value, writer);
        }
    }
}
