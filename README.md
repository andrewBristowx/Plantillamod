# VTuber Cobblemon Core Template

Plantilla reutilizable para crear servidores Cobblemon personalizados para VTubers, streamers y comunidades sin rehacer cada sistema desde cero.

## Objetivo

Este repositorio concentra en **un solo mod Fabric** los sistemas genéricos desarrollados originalmente para el ecosistema de Emipokemon, pero eliminando nombres, colores, personajes y assets específicos de Emi.

La personalización de cada cliente se realizará mediante:

- configuración de branding;
- módulos activables/desactivables;
- assets sustituibles;
- catálogos JSON de recompensas, tiendas, gashas, misiones y NPC;
- perfiles de Pokémon custom;
- presets por proyecto.

El `mod id` base debe permanecer estable para evitar migraciones innecesarias de datos entre clientes. El nombre visible, moneda, colores y contenido sí pueden cambiar por configuración.

## Stack objetivo

- Minecraft 1.21.1
- Fabric
- Java 21
- Cobblemon 1.7.3
- GeckoLib 4.9.x
- Compatibilidad opcional con Cobbleverse / Radical Cobblemon Trainers

## Módulos previstos

### Core y branding
- nombre del servidor/proyecto;
- nombre y símbolo de moneda;
- paleta de colores;
- textos globales;
- logo e iconos;
- nombre/personaje principal del creador;
- presets independientes por VTuber.

### Gasha
- gasha estándar;
- gasha especial del creador;
- gasha de tesoro;
- pity y rotaciones;
- tiers/pesos;
- Pokémon de evento/custom;
- display 3D y efectos configurables.

### Pokémon custom
- especies/formas personalizadas;
- normal + shiny;
- resolvers/modelos/texturas/animaciones;
- líneas evolutivas;
- integración opcional con gasha.

### Economía y progresión
- moneda virtual;
- jobs;
- tienda;
- recompensas diarias;
- kits;
- misiones;
- límites por rango/permisos;
- persistencia por jugador.

### NPC, diálogos y desafíos
- NPC visuales;
- diálogos estilo novela visual;
- vendedores/enfermería;
- entrenadores;
- desafíos regionales;
- soporte RCT;
- level cap / level sync configurable.

### Dungeons
- entrenadores adaptativos;
- roles Explorador/Veterano/Élite/Guardián;
- progreso independiente por jugador;
- equipos temáticos;
- level sync temporal;
- loot Pokémon por tiers;
- compatibilidad con estructuras de otros mods.

### Protecciones
- bloques de protección por tiers;
- propietario/invitados;
- permisos;
- preview del área;
- integración opcional con Flan y LuckPerms.

### Herramientas y armadura custom
- espada;
- pico 3x3;
- hacha de árbol;
- pala 3x3;
- azada con recompensas configurables;
- armadura temática;
- modelos/texturas sustituibles por proyecto.

### Hub y presentación
- `/hub` y `/spawn`;
- NPC del hub;
- hologramas;
- paneles multimedia;
- displays 3D;
- mensajes y elementos visuales configurables.

### Casino / minijuegos sociales
- ruleta;
- póker/mesa;
- apuestas con moneda virtual;
- límites configurables;
- UI y assets tematizables.

### Optimización y utilidades de servidor
- optimización de Pasture Loot;
- control de mobs/spawners;
- limpieza segura de Pokémon salvajes;
- diagnósticos/hotspots;
- alertas de shiny/legendario;
- seguimiento del Pokémon anunciado.

### Motor de campaña opcional
- objetivos por jugador;
- NPC de historia;
- puertas/gates;
- viajes/terminales;
- PokéStops;
- secuencias y recompensas;
- mapas/campañas externas como contenido separado del core.

## Regla principal de la plantilla

**El código genérico vive aquí. El contenido de cada VTuber no.**

No deben quedar hardcodeados nombres como `Emi`, colores rosados, Michicoins, personajes, logos ni Pokémon particulares. Cada proyecto nuevo tendrá su propio preset y paquete de assets.

Ejemplo conceptual:

```text
config/vtubercore/
├── branding.json
├── modules.json
├── economy.json
├── gacha.json
├── dungeons.json
└── permissions.json

config/vtubercore/presets/
├── ejemplo_vtuber.json
└── otro_proyecto.json
```

## Estrategia de migración desde los proyectos Emi

No se copiarán todos los repositorios a ciegas. Cada sistema se trasladará desde su última versión validada, se neutralizará, se probará aislado y luego se activará como módulo del core.

Orden recomendado:

1. Core + branding + módulos.
2. Economía/progresión.
3. NPC/Hub.
4. Gasha.
5. Herramientas/armadura.
6. Dungeons + loot.
7. Protecciones.
8. Alertas/optimización.
9. Casino/UI.
10. Motor de campaña.
11. Pokémon custom como contenido de proyecto.

## Estado

**Fase 0 — arquitectura de plantilla.**

El repositorio comienza limpio para no arrastrar dependencias, assets o nombres específicos de Emipokemon.
