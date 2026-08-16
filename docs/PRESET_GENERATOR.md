# Generador de presets visuales

VTuberCore separa la lógica del servidor de la identidad del cliente.

## Flujo
1. Recibir personaje, logo, mascota, paleta y nombres del nuevo VTuber.
2. Generar y aprobar las imágenes usando las plantillas visuales.
3. Ejecutar `tools/generate_preset.py` para empaquetar branding y assets en rutas estables.
4. El mismo JAR consume la configuración; las GUI cliente consumen el resource pack generado.

El script no inventa ni regenera el personaje. La creación de arte ocurre antes del empaquetado para permitir revisión humana.

```bash
python tools/generate_preset.py tools/preset.example.json --out cliente-demo
```

Salida esperada:
```text
cliente-demo/
  config/vtubercore/
    branding.json
    modules.json
    gui_theme.json
  resourcepack/assets/vtubercore/textures/gui/generated/
    daily_login_background.png
    character.png
    logo.png
    mascot.png
```

La misma estrategia se extenderá a tienda, misiones, gasha, battle pass y demás interfaces.
