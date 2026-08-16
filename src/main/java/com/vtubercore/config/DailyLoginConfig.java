package com.vtubercore.config;

import java.util.ArrayList;
import java.util.List;

public final class DailyLoginConfig {
    public boolean enabled = true;
    public String zoneId = "UTC";
    public String title = "Recompensas diarias";
    public String subtitle = "Vuelve cada día para mantener tu racha";
    public List<DailyReward> rewards = defaults();

    public void sanitize() {
        if (zoneId == null || zoneId.isBlank()) zoneId = "UTC";
        if (title == null || title.isBlank()) title = "Recompensas diarias";
        if (subtitle == null) subtitle = "";
        if (rewards == null || rewards.isEmpty()) rewards = defaults();
        for (int i = 0; i < rewards.size(); i++) {
            DailyReward reward = rewards.get(i);
            if (reward == null) rewards.set(i, new DailyReward(i + 1, 100L * (i + 1), "Día " + (i + 1)));
            else reward.sanitize(i + 1);
        }
    }

    private static List<DailyReward> defaults() {
        List<DailyReward> list = new ArrayList<>();
        list.add(new DailyReward(1, 100, "Día 1"));
        list.add(new DailyReward(2, 150, "Día 2"));
        list.add(new DailyReward(3, 200, "Día 3"));
        list.add(new DailyReward(4, 250, "Día 4"));
        list.add(new DailyReward(5, 350, "Día 5"));
        list.add(new DailyReward(6, 500, "Día 6"));
        list.add(new DailyReward(7, 1000, "Día 7"));
        return list;
    }

    public static final class DailyReward {
        public int day;
        public long currencyAmount;
        public String label;
        public DailyReward() {}
        public DailyReward(int day, long currencyAmount, String label) {
            this.day = day;
            this.currencyAmount = currencyAmount;
            this.label = label;
        }
        private void sanitize(int fallbackDay) {
            if (day <= 0) day = fallbackDay;
            if (currencyAmount < 0) currencyAmount = 0;
            if (label == null || label.isBlank()) label = "Día " + day;
        }
    }
}
