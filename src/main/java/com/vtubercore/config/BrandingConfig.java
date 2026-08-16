package com.vtubercore.config;

/**
 * Identidad visible del proyecto. Ningún módulo genérico debe hardcodear el
 * nombre, moneda o colores de una VTuber concreta.
 */
public final class BrandingConfig {
    public String projectName = "Mi Servidor Cobblemon";
    public String creatorName = "VTuber";
    public String currencyName = "Monedas";
    public String currencySymbol = "✦";
    public String primaryColor = "#D86CFF";
    public String secondaryColor = "#FFFFFF";
    public String chatPrefix = "[Servidor]";

    public void sanitize() {
        if (projectName == null || projectName.isBlank()) projectName = "Mi Servidor Cobblemon";
        if (creatorName == null || creatorName.isBlank()) creatorName = "VTuber";
        if (currencyName == null || currencyName.isBlank()) currencyName = "Monedas";
        if (currencySymbol == null || currencySymbol.isBlank()) currencySymbol = "✦";
        if (primaryColor == null || primaryColor.isBlank()) primaryColor = "#D86CFF";
        if (secondaryColor == null || secondaryColor.isBlank()) secondaryColor = "#FFFFFF";
        if (chatPrefix == null || chatPrefix.isBlank()) chatPrefix = "[Servidor]";
    }
}
