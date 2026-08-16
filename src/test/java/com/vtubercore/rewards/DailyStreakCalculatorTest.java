package com.vtubercore.rewards;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyStreakCalculatorTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 8, 16);

    @Test void firstClaimStartsAtOne() {
        assertEquals(1, DailyStreakCalculator.nextStreak(null, TODAY, 0));
    }

    @Test void consecutiveClaimIncrements() {
        assertEquals(4, DailyStreakCalculator.nextStreak(TODAY.minusDays(1), TODAY, 3));
    }

    @Test void missedDayResetsStreak() {
        assertEquals(1, DailyStreakCalculator.nextStreak(TODAY.minusDays(2), TODAY, 7));
    }

    @Test void rewardCalendarCycles() {
        assertEquals(0, DailyStreakCalculator.rewardIndex(1, 7));
        assertEquals(6, DailyStreakCalculator.rewardIndex(7, 7));
        assertEquals(0, DailyStreakCalculator.rewardIndex(8, 7));
    }
}
