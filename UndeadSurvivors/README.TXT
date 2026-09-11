------------------------------------------------------------------------
PROJECT TITLE:
Rogue Type

PURPOSE OF PROJECT:
Roguelike top-down inspirado en el genero Vampire Survivors. El jugador
sobrevive a oleadas de enemigos, ataca automaticamente y obtiene
experiencia al eliminar enemigos.

VERSION or DATE:
Base refactorizada - septiembre 2026

HOW TO START THIS PROJECT:
Abrir la carpeta del proyecto desde Greenfoot y ejecutar JuegoWorld.

AUTHORS:
Gabriel Calderón

USER INSTRUCTIONS:
Mover al jugador con W, A, S y D. El ataque actual es automatico.

ESTADO ACTUAL:
- Movimiento del jugador.
- Ataque automatico con proyectiles.
- Enemigos normales, rapidos y pesados definidos.
- Sistema base de oleadas.
- Experiencia y niveles provisionales.
- Curacion aleatoria.
- Textos de dano y curacion.
- Contador interno de enemigos eliminados preparado para el HUD final.

PROXIMAS ETAPAS:
- Camara que siga al jugador.
- Tres armas: cuchillo arrojadizo, bomba y laser.
- Level Up con menu de eleccion.
- Niveles de armas.
- HUD definitivo.
- Menu inicial, Game Over y pulido.
------------------------------------------------------------------------

CAMARA - VERSION FUNCIONAL
La pantalla de Greenfoot se mantiene en 1074x1074 y el mapa se maneja con coordenadas virtuales independientes. La clase Camara sigue al jugador y limita la vista a los bordes del mapa. Los actores que existen dentro del mapa heredan de ActorMundo, por lo que sus coordenadas reales no dependen del tamaño de la pantalla.

El mapa actual de prueba usa 2148x2148 y conserva el tamaño original de los elementos del fondo mediante repetición de la imagen base, evitando el efecto de zoom. La arquitectura queda preparada para que posteriormente cada nivel pueda definir sus propias dimensiones.


CAMARA - ARQUITECTURA
La pantalla de Greenfoot se mantiene fija en 1074x1074. El mapa del nivel se maneja con coordenadas virtuales independientes y la clase Camara centra la vista en el jugador dentro de los limites del nivel. Los actores del mapa heredan de ActorMundo y convierten coordenadas de mundo a pantalla. HUD no hereda de ActorMundo y permanece fijo.

Nivel encapsula las dimensiones y el fondo del nivel actual. Para agregar otro mapa posteriormente, se puede crear/cargar otro Nivel con sus propias dimensiones sin cambiar la logica de Jugador, enemigos, proyectiles o Camara.
