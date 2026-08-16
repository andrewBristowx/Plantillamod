package com.vtubercore.rewards;

import java.time.LocalDate;

public final class DailyStreakCalculator {
    private DailyStreakCalculator() {}

    public static int nextStreak(LocalDate lastClaim, LocalDate today, int currentStreak) {
        if (lastClaim == null) return 1;
        if (lastClaim.equals(today)) return Math.max(1, currentStreak);
        if (lastClaim.plusDays(1).equals(today)) return Math.max(0, currentStreak) + 1;
        return 1;
    }

    public static int rewardIndex(int streak, int rewardCount) {
        if (streak <= 0 || rewardCount <= 0) return 0;
        return (streak - 1) % rewardCount;
    }
}
