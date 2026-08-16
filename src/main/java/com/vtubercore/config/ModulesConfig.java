package com.vtubercore.config;

/** Interruptores de módulos del único JAR. */
public final class ModulesConfig {
    public boolean economy = true;
    public boolean dailyLogin = true;
    public boolean guiTemplates = true;
    public boolean jobs = false;
    public boolean shop = false;
    public boolean kits = false;
    public boolean battlePass = false;
    public boolean gacha = false;
    public boolean customPokemon = false;
    public boolean npcs = false;
    public boolean dungeons = false;
    public boolean protections = false;
    public boolean toolsAndArmor = false;
    public boolean hubAndMedia = false;
    public boolean casino = false;
    public boolean pastureOptimizer = false;
    public boolean mobControl = false;
    public boolean entityCleaner = false;
    public boolean specialSpawnAlerts = false;
    public boolean campaignEngine = false;

    public void sanitize() {
        if (!economy) {
            dailyLogin = false;
            shop = false;
            casino = false;
        }
    }
}
