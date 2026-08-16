package com.vtubercore.rewards;

import com.vtubercore.config.CoreConfigManager;
import com.vtubercore.config.DailyLoginConfig;
import com.vtubercore.data.PlayerData;
import com.vtubercore.data.PlayerDataStore;
import com.vtubercore.economy.EconomyService;
import net.minecraft.server.network.ServerPlayerEntity;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

public final class DailyLoginService {
    private DailyLoginService() {}

    public static DailyStatus status(ServerPlayerEntity player) {
        DailyLoginConfig config = CoreConfigManager.dailyLogin();
        PlayerData data = PlayerDataStore.get(player.getUuid());
        LocalDate today = today(config);
        LocalDate last = parse(data.lastDailyClaim);
        boolean claimedToday = today.equals(last);
        int nextStreak = claimedToday ? Math.max(1, data.dailyStreak) : DailyStreakCalculator.nextStreak(last, today, data.dailyStreak);
        DailyLoginConfig.DailyReward reward = rewardFor(config, nextStreak);
        return new DailyStatus(claimedToday, data.dailyStreak, nextStreak, reward, data.totalDailyClaims);
    }

    public static ClaimResult claim(ServerPlayerEntity player) {
        DailyLoginConfig config = CoreConfigManager.dailyLogin();
        if (!config.enabled) return ClaimResult.disabled();

        PlayerData data = PlayerDataStore.get(player.getUuid());
        LocalDate today = today(config);
        LocalDate last = parse(data.lastDailyClaim);
        if (today.equals(last)) {
            return ClaimResult.alreadyClaimed(data.dailyStreak, rewardFor(config, Math.max(1, data.dailyStreak)));
        }

        int streak = DailyStreakCalculator.nextStreak(last, today, data.dailyStreak);
        DailyLoginConfig.DailyReward reward = rewardFor(config, streak);
        data.dailyStreak = streak;
        data.lastDailyClaim = today.toString();
        data.totalDailyClaims++;
        PlayerDataStore.save(player.getUuid());
        long balance = EconomyService.add(player.getUuid(), reward.currencyAmount);
        return ClaimResult.claimed(streak, reward, balance);
    }

    public static void reset(ServerPlayerEntity player) {
        PlayerData data = PlayerDataStore.get(player.getUuid());
        data.lastDailyClaim = "";
        data.dailyStreak = 0;
        PlayerDataStore.save(player.getUuid());
    }

    private static DailyLoginConfig.DailyReward rewardFor(DailyLoginConfig config, int streak) {
        int index = DailyStreakCalculator.rewardIndex(streak, config.rewards.size());
        return config.rewards.get(index);
    }

    private static LocalDate today(DailyLoginConfig config) {
        try {
            return LocalDate.now(ZoneId.of(config.zoneId));
        } catch (DateTimeException ignored) {
            return LocalDate.now(ZoneId.of("UTC"));
        }
    }

    private static LocalDate parse(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return LocalDate.parse(value);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

    public record DailyStatus(boolean claimedToday, int currentStreak, int nextStreak,
                              DailyLoginConfig.DailyReward nextReward, int totalClaims) {}

    public record ClaimResult(Status status, int streak, DailyLoginConfig.DailyReward reward, long newBalance) {
        public enum Status { CLAIMED, ALREADY_CLAIMED, DISABLED }
        static ClaimResult claimed(int streak, DailyLoginConfig.DailyReward reward, long balance) {
            return new ClaimResult(Status.CLAIMED, streak, reward, balance);
        }
        static ClaimResult alreadyClaimed(int streak, DailyLoginConfig.DailyReward reward) {
            return new ClaimResult(Status.ALREADY_CLAIMED, streak, reward, -1);
        }
        static ClaimResult disabled() {
            return new ClaimResult(Status.DISABLED, 0, null, -1);
        }
    }
}
