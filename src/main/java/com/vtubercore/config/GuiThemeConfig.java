package com.vtubercore.config;

public final class GuiThemeConfig {
    public String themeId = "default";
    public String font = "minecraft:default";
    public DailyLoginTheme dailyLogin = new DailyLoginTheme();

    public void sanitize() {
        if (themeId == null || themeId.isBlank()) themeId = "default";
        if (font == null || font.isBlank()) font = "minecraft:default";
        if (dailyLogin == null) dailyLogin = new DailyLoginTheme();
        dailyLogin.sanitize();
    }

    public static final class DailyLoginTheme {
        public String backgroundTexture = "vtubercore:textures/gui/generated/daily_login_background.png";
        public String characterTexture = "vtubercore:textures/gui/generated/character.png";
        public String logoTexture = "vtubercore:textures/gui/generated/logo.png";
        public String mascotTexture = "vtubercore:textures/gui/generated/mascot.png";
        public int characterX = 18;
        public int characterY = 18;
        public int characterWidth = 112;
        public int characterHeight = 196;
        public int logoX = 146;
        public int logoY = 18;
        public int rewardGridX = 144;
        public int rewardGridY = 68;

        private void sanitize() {
            if (backgroundTexture == null || backgroundTexture.isBlank()) backgroundTexture = "vtubercore:textures/gui/generated/daily_login_background.png";
            if (characterTexture == null || characterTexture.isBlank()) characterTexture = "vtubercore:textures/gui/generated/character.png";
            if (logoTexture == null || logoTexture.isBlank()) logoTexture = "vtubercore:textures/gui/generated/logo.png";
            if (mascotTexture == null || mascotTexture.isBlank()) mascotTexture = "vtubercore:textures/gui/generated/mascot.png";
            if (characterWidth <= 0) characterWidth = 112;
            if (characterHeight <= 0) characterHeight = 196;
        }
    }
}
