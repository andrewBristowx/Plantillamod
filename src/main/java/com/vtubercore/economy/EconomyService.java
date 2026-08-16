package com.vtubercore.economy;

import com.vtubercore.data.PlayerData;
import com.vtubercore.data.PlayerDataStore;

import java.util.UUID;

public final class EconomyService {
    private EconomyService() {}

    public static long balance(UUID uuid) {
        return PlayerDataStore.get(uuid).balance;
    }

    public static long add(UUID uuid, long amount) {
        if (amount <= 0) return balance(uuid);
        PlayerData data = PlayerDataStore.get(uuid);
        data.balance = Math.addExact(data.balance, amount);
        PlayerDataStore.save(uuid);
        return data.balance;
    }

    public static boolean spend(UUID uuid, long amount) {
        if (amount < 0) return false;
        PlayerData data = PlayerDataStore.get(uuid);
        if (data.balance < amount) return false;
        data.balance -= amount;
        PlayerDataStore.save(uuid);
        return true;
    }

    public static long set(UUID uuid, long amount) {
        PlayerData data = PlayerDataStore.get(uuid);
        data.balance = Math.max(0L, amount);
        PlayerDataStore.save(uuid);
        return data.balance;
    }
}
