# VTuberCore 0.1.0-alpha.2

Primera fase funcional del mod plantilla.

- PlayerData genérico y persistente por UUID.
- Wallet/economía neutral basada en `branding.json`.
- Daily Login configurable con calendario de 7 recompensas y rachas.
- `/balance`, `/daily`, `/daily claim` y comandos administrativos dentro de `/vtubercore`.
- `daily_login.json` y `gui_theme.json` generados automáticamente.
- Sistema de módulos ampliado para daily, jobs, shop, kits y battle pass.
- Especificación de GUI desacoplada de la marca.
- Generador de presets que empaqueta branding + imágenes aprobadas en rutas estables.
- Test unitario de rachas y rotación del calendario.
- Sin assets ni nombres exclusivos de un cliente dentro del core.
