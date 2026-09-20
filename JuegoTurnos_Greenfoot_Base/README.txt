JUEGO DE COMBATE POR TURNOS - GREENFOOT
========================================

VERSION CON PRESENTACION ANIMADA Y SPRITES

Estructura:
images/
    jugador.png
    j_frame2.png
    j_frame3.png
    enemigo.png
    e_frame2.png
    e_frame3.png
    escenario.png

sounds/
    caida.mp3
    rugido.mp3
    musica_combate.mp3

PRESENTACION:
- El jugador aparece solo al iniciar.
- El enemigo cae desde arriba.
- El sonido de caida comienza al iniciar la caida para sincronizar el impacto.
- El impacto visual ocurre aproximadamente 0,5 segundos despues de tocar el suelo.
- Hay 1 segundo de silencio antes del rugido.
- Durante el rugido, el enemigo cambia de sprite normal a transicion y luego a rugido.
- El jugador se gira gradualmente y queda mirando hacia la camara.
- La pantalla completa se sacude durante la parte audible del rugido.
- El enemigo y el jugador vuelven por la transicion a sus sprites normales.
- Al terminar la presentacion aparece el menu y comienza la musica de combate.

ANIMACION DEL ENEMIGO:
enemigo.png -> e_frame2.png -> e_frame3.png -> e_frame2.png -> enemigo.png

ANIMACION DEL JUGADOR:
jugador.png -> j_frame2.png -> j_frame3.png -> j_frame2.png -> jugador.png

LOGICA DE COMBATE:
- Jugador y enemigo con 100 HP.
- Ataque rapido: 8 dano / 90% acierto.
- Ataque normal: 12 dano / 75% acierto.
- Golpe fuerte: 20 dano / 50% acierto.
- Enemigo: Corte 9/85% y Estocada 14/65%.
- Estados TURNO_JUGADOR, ANIMANDO_JUGADOR, TURNO_ENEMIGO,
  ANIMANDO_ENEMIGO y FIN.
- Botones deshabilitados fuera del turno del jugador.
- Control por WASD, flechas, ENTER y ESPACIO.
- Indicador de turno.
- Animaciones de ataque.
- Barras de HP.
- Texto flotante de dano/FALLO.
- Victoria/derrota.

Las extensiones de la guia no estan incluidas en esta version:
halo de objetivo, criticos, defensa, estados temporales, 2 vs 2 y experimentos.
