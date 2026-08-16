# Plan de migración desde el ecosistema Emi

La plantilla no reemplaza ni modifica los proyectos Emi originales. Se toma cada última versión validada como referencia, se extrae la lógica genérica y se neutraliza antes de incorporarla.

## Módulos y origen

| Módulo VTuberCore | Proyecto fuente principal | Qué se reutiliza | Qué se vuelve configurable |
|---|---|---|---|
| economy | Emipokemon | wallet, jobs, tienda, daily, kits, progresión | moneda, límites, recompensas, catálogo |
| gacha | Emipokemon | backend, pity, tiers, rotación, máquinas, displays | banners, pesos, tickets, nombres, efectos, pools |
| custom-pokemon | Emipokemon | registro de especies/resolvers y soporte normal/shiny | especies, modelos, texturas y evolución por cliente |
| npc | Emipokemon | NPC, diálogo, vendedores, enfermería, desafíos | nombres, skins, textos, roles y recompensas |
| dungeons | Emipokemon + EmiDungeonLoot | entrenadores adaptativos, guardianes, level sync y loot tiers | temas, pools, dificultad, estructuras, premios |
| protections | emiProteciones | cores, propietario, guests, permisos, preview, Flan/LuckPerms | tiers, tamaños, texturas, permisos por rango |
| tools-armor | Emipokemon | habilidades de herramientas y set de armadura | stats, efectos, modelos, texturas y nombres |
| hub-media | Emipokemon | hub/spawn, hologramas, paneles, media, displays | textos, imágenes, ubicaciones y temas |
| casino | Emipokemon | ruleta/mesa, sesiones y economía | apuestas, límites, UI y assets |
| pasture-optimizer | CobblePasture-Optimizer | virtualización/optimización de drops | límites, intervalos y comportamiento |
| mob-control | EmiMobControl | control de granjas/spawners | límites y reglas |
| entity-cleaner | CobbleEntity-Cleaner | limpieza segura, votación, diagnostics/hotspots | intervalos, radio, protecciones y voto |
| special-spawn-alerts | CobbleEvent-Alerts | shiny/legendary alerts, tracking y resolución de captura | radios, sonidos, textos y categorías |
| campaign-engine | EmiProgresion | objetivos, NPC historia, gates, terminales, PokéStops | campaña completa como datos/preset |

## Qué NO se migra literalmente

- `Emi`, `Emipokemon`, `Michicoins` u otros nombres de marca como lógica fija.
- Assets visuales de Emi dentro del core genérico.
- Coordenadas de un mapa concreto.
- Catálogos exclusivos de una campaña concreta.
- Pokémon custom de un cliente como contenido obligatorio.

## Orden de implementación

### Fase 0 — Foundation
- build Fabric/Java 21;
- `vtubercore` estable;
- branding;
- module switches;
- CI;
- comandos status/reload.

### Fase 1 — Datos, economía y permisos
- PlayerData genérico versionado;
- wallet;
- jobs;
- tienda;
- daily/kits;
- LuckPerms bridge.

### Fase 2 — Hub/NPC
- `/hub` y `/spawn`;
- NPC base;
- diálogo;
- vendedor/enfermería;
- hologramas/media.

### Fase 3 — Gasha
- backend/pity;
- estándar/especial/tesoro;
- máquinas y UI;
- catálogo externo JSON;
- soporte custom Pokémon.

### Fase 4 — Herramientas/armadura
- habilidades reutilizables;
- stats configurables;
- skins/assets por preset.

### Fase 5 — Dungeons
- detector de estructuras;
- loot tiers;
- entrenadores adaptativos;
- guardián;
- Level Sync;
- progreso por jugador.

### Fase 6 — Protecciones
- cores por tier;
- Flan/LuckPerms;
- gestión visual;
- límites por rango.

### Fase 7 — Optimización y alertas
- pasture optimizer;
- mob control;
- entity cleaner;
- shiny/legendary alerts.

### Fase 8 — Casino/social
- ruleta;
- mesas;
- límites y configuración.

### Fase 9 — Campaign engine
- objetivos declarativos;
- gates;
- NPC/terminal/PokéStop;
- campañas externas como paquetes de contenido.

## Criterio para considerar un módulo migrado

Un módulo solo se marca como disponible cuando:

- compila dentro del único JAR;
- no contiene branding específico de Emi;
- puede deshabilitarse sin romper los demás módulos;
- conserva la funcionalidad de su build fuente validada;
- tiene config y diagnóstico;
- pasa pruebas/regresiones;
- tiene guía de prueba en servidor.
