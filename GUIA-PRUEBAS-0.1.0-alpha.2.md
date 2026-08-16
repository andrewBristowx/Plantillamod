# Pruebas VTuberCore 0.1.0-alpha.2

1. Instala el JAR con Fabric API, Cobblemon 1.7.3 y GeckoLib 4.9.2.
2. Inicia una vez y revisa `config/vtubercore/`.
3. Edita `branding.json` con un nombre y moneda de prueba; usa `/vtubercore reload`.
4. Ejecuta `/vtubercore status` y comprueba el branding.
5. Ejecuta `/balance`.
6. Ejecuta `/daily` para ver la siguiente recompensa.
7. Ejecuta `/daily claim` y verifica que aumenta el saldo.
8. Repite `/daily claim`: no debe entregar dos veces el mismo día.
9. Como OP, usa `/vtubercore daily reset <jugador>`.
10. Como OP, usa `/vtubercore economy give <jugador> <cantidad>`.

La alpha.2 todavía no muestra la GUI gráfica en cliente: deja preparado el contrato visual (`gui_theme.json`) y el empaquetador de imágenes. La pantalla gráfica se conectará después sin cambiar el formato de presets.
