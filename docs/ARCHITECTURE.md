# Arquitectura de VTuber Cobblemon Core

## Principio

El producto final se distribuye como **un único JAR**, pero internamente se divide en módulos desacoplados. La configuración decide qué módulos se habilitan para cada cliente.

## Capas

### 1. Core
Responsable de configuración, persistencia común, permisos, comandos administrativos, networking y registro de módulos.

### 2. Branding
Nunca contiene lógica de juego. Solo resuelve identidad visual y textual del proyecto:
- nombre del servidor;
- nombre del creador;
- moneda/símbolo;
- colores;
- prefijos;
- rutas de assets/presets.

### 3. Gameplay modules
- economy
- gacha
- custom-pokemon
- npc
- dungeons
- protections
- tools-armor
- hub-media
- casino
- campaign

### 4. Server utility modules
- pasture-optimizer
- mob-control
- entity-cleaner
- special-spawn-alerts

### 5. Integrations
Puentes opcionales con mods externos. Un módulo genérico no debe asumir que Flan, LuckPerms o RCT están instalados salvo que su integración esté activada.

## Regla de mundo persistente

El mod ID base es `vtubercore` y debe permanecer estable. Cambiar la marca de una VTuber no debe obligar a migrar IDs de datos guardados.

Cuando un contenido necesite IDs propios se usarán claves internas neutrales, por ejemplo:

```text
vtubercore:gacha_special
vtubercore:creator_currency
vtubercore:protection_core_basic
```

El nombre mostrado al jugador se obtiene del branding/preset.

## Presets de cliente

Cada trabajo comercial podrá guardar un preset separado. Ejemplo:

```text
presets/
└── cliente_x/
    ├── branding.json
    ├── economy.json
    ├── gacha.json
    ├── quests/
    ├── pokemon/
    └── assets/
```

Los presets privados o con assets licenciados no tienen por qué publicarse junto con el core.

## Contenido que NO debe vivir en el core

- modelos/texturas personales de una VTuber;
- logos de clientes;
- nombres de monedas concretos;
- diálogos personales;
- Pokémon custom exclusivos de un cliente;
- mapas de terceros redistribuidos sin la licencia correspondiente;
- credenciales/tokens/URLs privadas.

## Estrategia de compatibilidad

Cada módulo migrado debe tener:

1. interfaz/servicio neutral;
2. configuración propia;
3. persistencia versionada;
4. comandos de diagnóstico;
5. pruebas de regresión;
6. migrador desde la estructura anterior cuando sea necesario;
7. comprobación de dependencias opcionales.

## Versionado

- `foundation/*`: arquitectura/base.
- `migration/*`: traslado de un módulo desde un proyecto anterior.
- `release/*`: builds validadas para reutilización.

Nunca se sobrescribe una release estable.
