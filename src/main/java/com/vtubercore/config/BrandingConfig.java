package com.vtubercore.config;

/** Identidad visible. Los módulos genéricos nunca deben hardcodear una VTuber. */
public final class BrandingConfig {
    public String projectName = "Mi Servidor Cobblemon";
    public String creatorName = "VTuber";
    public String mascotName = "Mascota";
    public String currencyName = "Monedas";
    public String currencySymbol = "✦";
    public String primaryColor = "#D86CFF";
    public String secondaryColor = "#FFFFFF";
    public String accentColor = "#FFD8FA";
    public String chatPrefix = "[Servidor]";
    public String specialGachaName = "Gasha Especial";

    public void sanitize() {
        if (projectName == null || projectName.isBlank()) projectName = "Mi Servidor Cobblemon";
        if (creatorName == null || creatorName.isBlank()) creatorName = "VTuber";
        if (mascotName == null || mascotName.isBlank()) mascotName = "Mascota";
        if (currencyName == null || currencyName.isBlank()) currencyName = "Monedas";
        if (currencySymbol == null || currencySymbol.isBlank()) currencySymbol = "✦";
        if (primaryColor == null || primaryColor.isBlank()) primaryColor = "#D86CFF";
        if (secondaryColor == null || secondaryColor.isBlank()) secondaryColor = "#FFFFFF";
        if (accentColor == null || accentColor.isBlank()) accentColor = "#FFD8FA";
        if (chatPrefix == null || chatPrefix.isBlank()) chatPrefix = "[Servidor]";
        if (specialGachaName == null || specialGachaName.isBlank()) specialGachaName = "Gasha Especial";
    }
}
