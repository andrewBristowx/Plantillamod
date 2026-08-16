package com.vtubercore.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataStore {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path ROOT = FabricLoader.getInstance().getConfigDir().resolve("vtubercore").resolve("players");
    private static final Map<UUID, PlayerData> CACHE = new HashMap<>();

    private PlayerDataStore() {}

    public static synchronized PlayerData get(UUID uuid) {
        return CACHE.computeIfAbsent(uuid, PlayerDataStore::load);
    }

    public static synchronized void save(UUID uuid) {
        PlayerData data = CACHE.get(uuid);
        if (data == null) return;
        data.sanitize();
        try {
            Files.createDirectories(ROOT);
            try (Writer writer = Files.newBufferedWriter(path(uuid))) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo guardar PlayerData de " + uuid, e);
        }
    }

    public static synchronized void clearCache() {
        CACHE.clear();
    }

    private static PlayerData load(UUID uuid) {
        try {
            Files.createDirectories(ROOT);
            Path path = path(uuid);
            if (!Files.exists(path)) return new PlayerData();
            try (Reader reader = Files.newBufferedReader(path)) {
                PlayerData data = GSON.fromJson(reader, PlayerData.class);
                if (data == null) data = new PlayerData();
                data.sanitize();
                return data;
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar PlayerData de " + uuid, e);
        }
    }

    private static Path path(UUID uuid) {
        return ROOT.resolve(uuid.toString() + ".json");
    }
}
