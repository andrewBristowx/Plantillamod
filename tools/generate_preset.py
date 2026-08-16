#!/usr/bin/env python3
from __future__ import annotations
import argparse, json, shutil
from pathlib import Path

DEFAULT_MODULES = {
    "economy": True, "dailyLogin": True, "guiTemplates": True,
    "jobs": False, "shop": False, "kits": False, "battlePass": False,
    "gacha": False, "customPokemon": False, "npcs": False,
    "dungeons": False, "protections": False, "toolsAndArmor": False,
    "hubAndMedia": False, "casino": False, "pastureOptimizer": False,
    "mobControl": False, "entityCleaner": False,
    "specialSpawnAlerts": False, "campaignEngine": False
}

def copy_if_present(source: str | None, dest: Path) -> None:
    if not source: return
    src = Path(source)
    if not src.is_file(): raise SystemExit(f"Asset no encontrado: {src}")
    dest.parent.mkdir(parents=True, exist_ok=True)
    shutil.copy2(src, dest)

def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("manifest", type=Path)
    ap.add_argument("--out", type=Path, default=Path("generated-preset"))
    args = ap.parse_args()
    data = json.loads(args.manifest.read_text(encoding="utf-8"))
    cfg = args.out / "config" / "vtubercore"
    assets = args.out / "resourcepack" / "assets" / "vtubercore" / "textures" / "gui" / "generated"
    cfg.mkdir(parents=True, exist_ok=True); assets.mkdir(parents=True, exist_ok=True)

    branding = {k: data.get(k, v) for k, v in {
        "projectName":"Mi Servidor Cobblemon", "creatorName":"VTuber", "mascotName":"Mascota",
        "currencyName":"Monedas", "currencySymbol":"✦", "primaryColor":"#D86CFF",
        "secondaryColor":"#FFFFFF", "accentColor":"#FFD8FA", "chatPrefix":"[Servidor]",
        "specialGachaName":"Gasha Especial"}.items()}
    (cfg / "branding.json").write_text(json.dumps(branding, ensure_ascii=False, indent=2)+"\n", encoding="utf-8")

    modules = dict(DEFAULT_MODULES); modules.update(data.get("modules", {}))
    (cfg / "modules.json").write_text(json.dumps(modules, ensure_ascii=False, indent=2)+"\n", encoding="utf-8")

    theme_id = data.get("themeId", data.get("creatorName", "default").lower().replace(" ", "_"))
    gui_theme = {"themeId": theme_id, "font": data.get("font", "minecraft:default"), "dailyLogin": {
        "backgroundTexture":"vtubercore:textures/gui/generated/daily_login_background.png",
        "characterTexture":"vtubercore:textures/gui/generated/character.png",
        "logoTexture":"vtubercore:textures/gui/generated/logo.png",
        "mascotTexture":"vtubercore:textures/gui/generated/mascot.png",
        "characterX":18,"characterY":18,"characterWidth":112,"characterHeight":196,
        "logoX":146,"logoY":18,"rewardGridX":144,"rewardGridY":68}}
    (cfg / "gui_theme.json").write_text(json.dumps(gui_theme, ensure_ascii=False, indent=2)+"\n", encoding="utf-8")

    visuals = data.get("visuals", {})
    copy_if_present(visuals.get("dailyLoginBackground"), assets / "daily_login_background.png")
    copy_if_present(visuals.get("character"), assets / "character.png")
    copy_if_present(visuals.get("logo"), assets / "logo.png")
    copy_if_present(visuals.get("mascot"), assets / "mascot.png")
    print(f"Preset generado en: {args.out.resolve()}")

if __name__ == "__main__": main()
