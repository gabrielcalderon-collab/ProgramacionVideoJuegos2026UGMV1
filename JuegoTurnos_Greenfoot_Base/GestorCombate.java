import greenfoot.*;

public class GestorCombate
{
    public enum Estado
    {
    PRESENTACION,
    CAIDA_ENEMIGO,
    IMPACTO,
    SILENCIO,
    RUGIDO,
    TURNO_JUGADOR,
    ESPERA_TURNO_ENEMIGO,
    ANIMANDO_JUGADOR,
    TURNO_ENEMIGO,
    ANIMANDO_ENEMIGO,
    ESPERA_TURNO_JUGADOR,
    FIN
    }   

    private JuegoTurnos mundo;
    private FondoEscenario fondo;
    private GreenfootSound musica;
    private GreenfootSound sonidoCaida;
    private GreenfootSound sonidoRugido;
    private GreenfootSound sonidoVictoria;
    private GreenfootSound sonidoDerrota;

    private InfanteriaJugador jugador;
    private InfanteriaEnemiga enemigo;

    private Estado estado;

    private AccionCombate[] acciones;
    private BotonAccion[] botones;

    private AccionCombate accionActual;
    private AccionCombate accionEnemigo;

    private IndicadorTurno indicador;
    private BarraHP barraJugador;
    private BarraHP barraEnemigo;

    private int contadorAnimacion = 0;
    private int contadorPresentacion = 0;
    private int contadorEsperaTurno = 0;

    private int xJugadorOriginal;
    private int yJugadorOriginal;
    private int xEnemigoOriginal;
    private int yEnemigoOriginal;

    private int opcionSeleccionada = 0;

    private boolean teclaIzquierdaAnterior = false;
    private boolean teclaDerechaAnterior = false;
    private boolean teclaArribaAnterior = false;
    private boolean teclaAbajoAnterior = false;

    private boolean teclaEnterAnterior = false;
    private boolean teclaEspacioAnterior = false;

    private static final int VELOCIDAD_CAIDA = 3;
    private static final int ACELERACION_CAIDA = 1;
    private static final int Y_IMPACTO = 215;

    private static final int INICIO_CAIDA = 30;
    private static final int DURACION_IMPACTO = 12;
    private static final int DURACION_SILENCIO = 60;

    private static final int INICIO_SACUDIDA_RUGIDO = 70;
    private static final int FIN_SACUDIDA_RUGIDO = 174;
    private static final int FIN_PRESENTACION_RUGIDO = 220;
    private static final int DURACION_ESPERA_TURNO = 90;

    private int velocidadCaida = VELOCIDAD_CAIDA;

    public GestorCombate(
        JuegoTurnos mundo,
        FondoEscenario fondo,
        GreenfootSound musica)
    {
        this.mundo = mundo;
        this.fondo = fondo;
        this.musica = musica;

        sonidoCaida = new GreenfootSound("caida.mp3");
        sonidoRugido = new GreenfootSound("rugido.mp3");
        sonidoVictoria = new GreenfootSound("victory!.mp3");
        sonidoDerrota = new GreenfootSound("defeat!.mp3");
    }

    public void iniciar()
    {
        jugador = new InfanteriaJugador();
        enemigo = new InfanteriaEnemiga();

        mundo.addObject(jugador, 240, 450);

        xJugadorOriginal = jugador.getX();
        yJugadorOriginal = jugador.getY();

        acciones = new AccionCombate[] {
            new AccionCombate("Ataque rapido", 8, 90),
            new AccionCombate("Ataque normal", 12, 75),
            new AccionCombate("Golpe fuerte", 20, 50)
        };

        estado = Estado.PRESENTACION;
        contadorPresentacion = 0;
        contadorAnimacion = 0;

        ocultarInterfaz();
    }

    public void actualizar()
{
    if (estado == Estado.PRESENTACION)
    {
        actualizarPresentacion();
    }
    else if (estado == Estado.CAIDA_ENEMIGO)
    {
        actualizarCaidaEnemigo();
    }
    else if (estado == Estado.IMPACTO)
    {
        actualizarImpacto();
    }
    else if (estado == Estado.SILENCIO)
    {
        actualizarSilencio();
    }
    else if (estado == Estado.RUGIDO)
    {
        actualizarRugido();
    }
    else if (estado == Estado.TURNO_JUGADOR)
    {
        actualizarBarrasVida();
        controlarMenu();
    }
    else if (estado == Estado.ESPERA_TURNO_ENEMIGO)
    {
        actualizarBarrasVida();
        actualizarEsperaTurnoEnemigo();
    }
    else if (estado == Estado.ANIMANDO_JUGADOR)
    {
        actualizarBarrasVida();
        animarAtaqueJugador();
    }
    else if (estado == Estado.TURNO_ENEMIGO)
    {
        actualizarBarrasVida();
        iniciarTurnoEnemigo();
    }
    else if (estado == Estado.ANIMANDO_ENEMIGO)
    {
        actualizarBarrasVida();
        animarAtaqueEnemigo();
    }
    else if (estado == Estado.ESPERA_TURNO_JUGADOR)
    {
        actualizarBarrasVida();
        actualizarEsperaTurnoJugador();
    }
}

    private void actualizarEsperaTurnoEnemigo()
    {
    contadorEsperaTurno++;

    if (contadorEsperaTurno >= DURACION_ESPERA_TURNO)
    {
        estado = Estado.TURNO_ENEMIGO;
        contadorEsperaTurno = 0;
        actualizarInterfaz();
    }
    }

    private void actualizarEsperaTurnoJugador()
    {
    contadorEsperaTurno++;

    if (contadorEsperaTurno >= DURACION_ESPERA_TURNO)
    {
        estado = Estado.TURNO_JUGADOR;
        contadorEsperaTurno = 0;
        actualizarInterfaz();
    }
    }
    
    private void actualizarPresentacion()
    {
    contadorPresentacion++;

    if (contadorPresentacion == INICIO_CAIDA - 18)
    {
        sonidoCaida.play();
    }

    if (contadorPresentacion >= INICIO_CAIDA)
    {
        comenzarCaidaEnemigo();
    }
    }

    private void comenzarCaidaEnemigo()
    {
    estado = Estado.CAIDA_ENEMIGO;
    contadorPresentacion = 0;
    velocidadCaida = VELOCIDAD_CAIDA;

    mundo.addObject(enemigo, 690, -140);

    xEnemigoOriginal = 690;
    yEnemigoOriginal = Y_IMPACTO;
    }

    private void actualizarCaidaEnemigo()
    {
        int nuevaY = enemigo.getY() + velocidadCaida;

        if (nuevaY >= Y_IMPACTO)
        {
            enemigo.setLocation(
                xEnemigoOriginal,
                Y_IMPACTO
            );

            comenzarImpacto();
            return;
        }

        enemigo.setLocation(
            xEnemigoOriginal,
            nuevaY
        );

        velocidadCaida += ACELERACION_CAIDA;

        if (velocidadCaida > 18)
        {
            velocidadCaida = 18;
        }
    }

    private void comenzarImpacto()
    {
        estado = Estado.IMPACTO;
        contadorAnimacion = 0;
    }

    private void actualizarImpacto()
    {
    contadorAnimacion++;

    if (contadorAnimacion >= 9 &&
        contadorAnimacion < 21)
    {
        sacudirPantalla(8);
    }

    if (contadorAnimacion >= 21)
    {
        restaurarPantalla();

        estado = Estado.SILENCIO;
        contadorAnimacion = 0;
    }
    }

    private void actualizarSilencio()
    {
        contadorAnimacion++;

        if (contadorAnimacion >= DURACION_SILENCIO)
        {
            comenzarRugido();
        }
    }

    private void comenzarRugido()
    {
        estado = Estado.RUGIDO;
        contadorAnimacion = 0;

        sonidoRugido.play();

        enemigo.setFrameNormal();
        jugador.setFrameInicial();
    }

    private void actualizarRugido()
    {
        contadorAnimacion++;

        if (contadorAnimacion >= INICIO_SACUDIDA_RUGIDO &&
            contadorAnimacion < INICIO_SACUDIDA_RUGIDO + 6)
        {
            enemigo.setFrameTransicion();
            jugador.setFrameGirando();
        }

        if (contadorAnimacion >= INICIO_SACUDIDA_RUGIDO + 6 &&
            contadorAnimacion <= FIN_SACUDIDA_RUGIDO)
        {
            enemigo.setFrameRugido();
            jugador.setFrameMirando();
            sacudirPantalla(7);
        }

        if (contadorAnimacion > FIN_SACUDIDA_RUGIDO &&
            contadorAnimacion <= FIN_SACUDIDA_RUGIDO + 20)
        {
            enemigo.setFrameRugido();
            jugador.setFrameMirando();
        }

        if (contadorAnimacion > FIN_SACUDIDA_RUGIDO + 20 &&
            contadorAnimacion <= FIN_SACUDIDA_RUGIDO + 28)
        {
            enemigo.setFrameTransicion();
            jugador.setFrameGirando();
        }

        if (contadorAnimacion > FIN_SACUDIDA_RUGIDO + 28 &&
            contadorAnimacion < FIN_PRESENTACION_RUGIDO)
        {
            enemigo.setFrameNormal();
            jugador.setFrameInicial();
        }

        if (contadorAnimacion >= FIN_PRESENTACION_RUGIDO)
        {
            restaurarPantalla();
            sonidoRugido.stop();
            enemigo.setFrameNormal();
            jugador.setFrameInicial();
            terminarPresentacion();
        }
    }

    private void sacudirPantalla(int intensidad)
    {
        int desplazamientoX =
            Greenfoot.getRandomNumber(intensidad * 2 + 1)
            - intensidad;

        int desplazamientoY =
            Greenfoot.getRandomNumber(intensidad * 2 + 1)
            - intensidad;

        fondo.sacudir(
            desplazamientoX,
            desplazamientoY
        );

        jugador.setLocation(
            xJugadorOriginal + desplazamientoX,
            yJugadorOriginal + desplazamientoY
        );

        if (enemigo != null &&
            enemigo.getWorld() != null)
        {
            enemigo.setLocation(
                xEnemigoOriginal + desplazamientoX,
                yEnemigoOriginal + desplazamientoY
            );
        }
    }

    private void restaurarPantalla()
    {
        fondo.restaurar();

        jugador.setLocation(
            xJugadorOriginal,
            yJugadorOriginal
        );

        if (enemigo != null &&
            enemigo.getWorld() != null)
        {
            enemigo.setLocation(
                xEnemigoOriginal,
                yEnemigoOriginal
            );
        }
    }

    private void terminarPresentacion()
    {
        restaurarPantalla();

        crearBotones();
        crearIndicador();
        crearBarrasVida();

        estado = Estado.TURNO_JUGADOR;

        actualizarInterfaz();

        musica.playLoop();
    }

    private void ocultarInterfaz()
    {
        indicador = null;
        barraJugador = null;
        barraEnemigo = null;
        botones = null;
    }

    private void crearBotones()
    {
        PanelCombate panel = new PanelCombate();
        mundo.addObject(panel, 450, 530);

        botones = new BotonAccion[acciones.length];

        int[] posicionesX = {
            180,
            450,
            720
        };

        for (int i = 0; i < acciones.length; i++)
        {
            botones[i] = new BotonAccion(
                acciones[i],
                this
            );

            mundo.addObject(
                botones[i],
                posicionesX[i],
                550
            );
        }

        opcionSeleccionada = 0;
        actualizarSeleccionVisual();
    }

    private void crearIndicador()
    {
        indicador = new IndicadorTurno();
        mundo.addObject(indicador, 450, 45);
    }

    private void crearBarrasVida()
    {
        barraJugador = new BarraHP(jugador);
        barraEnemigo = new BarraHP(enemigo);

        mundo.addObject(
            barraJugador,
            jugador.getX(),
            jugador.getY() - 100
        );

        mundo.addObject(
            barraEnemigo,
            enemigo.getX(),
            enemigo.getY() - 190
        );
    }

    private void actualizarBarrasVida()
    {
    if (jugador == null ||
        enemigo == null ||
        jugador.getWorld() == null ||
        enemigo.getWorld() == null)
    {
        return;
    }

    if (barraJugador == null ||
        barraEnemigo == null)
    {
        return;
    }

    barraJugador.actualizar();
    barraEnemigo.actualizar();

    barraJugador.setLocation(
        jugador.getX(),
        jugador.getY() - 100
    );

    barraEnemigo.setLocation(
        enemigo.getX(),
        enemigo.getY() - 155
    );
    }   

    private void actualizarInterfaz()
    {
        actualizarBotones();

        if (indicador == null)
        {
            return;
        }

        if (estado == Estado.TURNO_JUGADOR)
        {
            indicador.mostrar(
            "TU TURNO",
            Color.WHITE
            );
        }
        else if (estado == Estado.ESPERA_TURNO_ENEMIGO ||
             estado == Estado.TURNO_ENEMIGO ||
             estado == Estado.ANIMANDO_ENEMIGO)
        {
            indicador.mostrar(
            "TURNO ENEMIGO",
            Color.WHITE
            );
        }
        else if (estado == Estado.ESPERA_TURNO_JUGADOR)
        {
            indicador.mostrar(
            "TU TURNO",
            Color.WHITE
            );
        }
    }

    private void actualizarBotones()
    {
        if (botones == null)
        {
            return;
        }

        boolean habilitar =
            estado == Estado.TURNO_JUGADOR;

        for (BotonAccion boton : botones)
        {
            boton.setHabilitado(habilitar);
        }
    }

    public void seleccionarAccion(AccionCombate accion)
    {
        if (estado != Estado.TURNO_JUGADOR)
        {
            return;
        }

        accionActual = accion;
        estado = Estado.ANIMANDO_JUGADOR;
        contadorAnimacion = 0;

        actualizarInterfaz();
    }

    private void animarAtaqueJugador()
    {
        contadorAnimacion++;

        if (contadorAnimacion <= 5)
        {
            jugador.setLocation(
                jugador.getX() - 3,
                jugador.getY() + 1
            );
        }
        else if (contadorAnimacion <= 8)
        {
            jugador.setLocation(
                jugador.getX() + 12,
                jugador.getY() - 3
            );
        }
        else if (contadorAnimacion <= 13)
        {
            jugador.setLocation(
                jugador.getX() - 7,
                jugador.getY() + 2
            );
        }
        else
        {
            jugador.setLocation(
                xJugadorOriginal,
                yJugadorOriginal
            );

            resolverAtaqueJugador();
        }
    }

    private void resolverAtaqueJugador()
    {
        if (accionActual.acierta())
        {
            int danio = accionActual.getDanio();

            enemigo.recibirDanio(danio);

            mostrarResultado(
                "-" + danio + " HP",
                enemigo.getX(),
                enemigo.getY() - 65,
                Color.YELLOW
            );
        }
        else
        {
            mostrarResultado(
                "FALLO",
                enemigo.getX(),
                enemigo.getY() - 65,
                Color.WHITE
            );
        }

        actualizarBarrasVida();

        if (!enemigo.estaViva())
        {
            terminarCombate(true);
            return;
        }

            estado = Estado.ESPERA_TURNO_ENEMIGO;
            contadorEsperaTurno = 0;
            actualizarInterfaz();
        }

    private void iniciarTurnoEnemigo()
    {
    if (enemigo == null || enemigo.getWorld() == null)
    {
        estado = Estado.FIN;
        return;
    }

    AccionCombate[] opciones = {
        new AccionCombate("Corte", 9, 85),
        new AccionCombate("Estocada", 14, 65)
    };

    accionEnemigo =
        opciones[
            Greenfoot.getRandomNumber(opciones.length)
        ];

    estado = Estado.ANIMANDO_ENEMIGO;
    contadorAnimacion = 0;

    actualizarInterfaz();
    }

    private void animarAtaqueEnemigo()
    {
    if (enemigo == null || enemigo.getWorld() == null)
    {
        estado = Estado.FIN;
        return;
    }

    contadorAnimacion++;

    if (contadorAnimacion <= 5)
    {
        enemigo.setLocation(
            enemigo.getX() + 3,
            enemigo.getY() - 1
        );
    }
    else if (contadorAnimacion <= 8)
    {
        enemigo.setLocation(
            enemigo.getX() - 12,
            enemigo.getY() + 3
        );
    }
    else if (contadorAnimacion <= 13)
    {
        enemigo.setLocation(
            enemigo.getX() + 7,
            enemigo.getY() - 2
        );
    }
    else
    {
        enemigo.setLocation(
            xEnemigoOriginal,
            yEnemigoOriginal
        );

        resolverAtaqueEnemigo();
    }
    }
    

    private void resolverAtaqueEnemigo()
    {
        if (accionEnemigo.acierta())
        {
            int danio = accionEnemigo.getDanio();

            jugador.recibirDanio(danio);

            mostrarResultado(
                "-" + danio + " HP",
                jugador.getX(),
                jugador.getY() - 65,
                Color.RED
            );
        }
        else
        {
            mostrarResultado(
                "FALLO",
                jugador.getX(),
                jugador.getY() - 65,
                Color.WHITE
            );
        }

        actualizarBarrasVida();

        if (!jugador.estaViva())
        {
            terminarCombate(false);
            return;
        }

        estado = Estado.ESPERA_TURNO_JUGADOR;
        contadorEsperaTurno = 0;
        actualizarInterfaz();
    }

    private void mostrarResultado(
        String texto,
        int x,
        int y,
        Color color)
    {
        mundo.addObject(
            new TextoFlotante(texto, color),
            x,
            y
        );
    }

    private void terminarCombate(boolean victoria)
    {
    estado = Estado.FIN;

    actualizarBotones();

    String mensaje =
        victoria ? "VICTORIA" : "DERROTA";

    indicador.mostrar(
        mensaje,
        Color.WHITE
    );

    mundo.showText(
        mensaje,
        450,
        300
    );

    musica.stop();

    if (victoria)
    {
        sonidoVictoria.play();
    }
    else
    {
        sonidoDerrota.play();
    }
    }

    private void controlarMenu()
    {
        if (estado != Estado.TURNO_JUGADOR)
        {
            return;
        }

        boolean izquierda =
            Greenfoot.isKeyDown("left") ||
            Greenfoot.isKeyDown("a");

        boolean derecha =
            Greenfoot.isKeyDown("right") ||
            Greenfoot.isKeyDown("d");

        boolean arriba =
            Greenfoot.isKeyDown("up") ||
            Greenfoot.isKeyDown("w");

        boolean abajo =
            Greenfoot.isKeyDown("down") ||
            Greenfoot.isKeyDown("s");

        boolean enter =
            Greenfoot.isKeyDown("enter");

        boolean espacio =
            Greenfoot.isKeyDown("space");

        if ((izquierda || arriba) &&
            !(teclaIzquierdaAnterior ||
              teclaArribaAnterior))
        {
            moverSeleccion(-1);
        }

        if ((derecha || abajo) &&
            !(teclaDerechaAnterior ||
              teclaAbajoAnterior))
        {
            moverSeleccion(1);
        }

        if (enter && !teclaEnterAnterior)
        {
            seleccionarOpcionActual();
        }

        if (espacio && !teclaEspacioAnterior)
        {
            seleccionarOpcionActual();
        }

        teclaIzquierdaAnterior = izquierda;
        teclaDerechaAnterior = derecha;
        teclaArribaAnterior = arriba;
        teclaAbajoAnterior = abajo;

        teclaEnterAnterior = enter;
        teclaEspacioAnterior = espacio;
    }

    private void moverSeleccion(int direccion)
    {
        opcionSeleccionada += direccion;

        if (opcionSeleccionada < 0)
        {
            opcionSeleccionada = botones.length - 1;
        }

        if (opcionSeleccionada >= botones.length)
        {
            opcionSeleccionada = 0;
        }

        actualizarSeleccionVisual();
    }

    private void actualizarSeleccionVisual()
    {
        if (botones == null)
        {
            return;
        }

        for (int i = 0; i < botones.length; i++)
        {
            botones[i].setSeleccionado(
                i == opcionSeleccionada
            );
        }
    }

    private void seleccionarOpcionActual()
    {
        if (botones == null)
        {
            return;
        }

        if (opcionSeleccionada < 0 ||
            opcionSeleccionada >= botones.length)
        {
            return;
        }

        seleccionarAccion(
            acciones[opcionSeleccionada]
        );
    }
}