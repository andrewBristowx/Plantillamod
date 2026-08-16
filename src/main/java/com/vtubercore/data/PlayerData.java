package com.vtubercore.data;

public final class PlayerData {
    public int schemaVersion = 1;
    public long balance = 0L;
    public String lastDailyClaim = "";
    public int dailyStreak = 0;
    public int totalDailyClaims = 0;

    public void sanitize() {
        if (schemaVersion < 1) schemaVersion = 1;
        if (balance < 0) balance = 0;
        if (lastDailyClaim == null) lastDailyClaim = "";
        if (dailyStreak < 0) dailyStreak = 0;
        if (totalDailyClaims < 0) totalDailyClaims = 0;
    }
}
