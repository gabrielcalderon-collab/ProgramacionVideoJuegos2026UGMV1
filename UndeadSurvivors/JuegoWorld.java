import greenfoot.*;

public class JuegoWorld extends World
{
    private static final int ANCHO = 1074;
    private static final int ALTO = 1074;
    private static final int CELDA = 1;

    private static final int ANCHO_MAPA = 2148;
    private static final int ALTO_MAPA = 2148;

    private Jugador jugador;
    private GestorOleadas gestor;
    private HUD hud;
    private Camara camara;

    private int enemigosEliminados;

    private boolean juegoPausado;

    public JuegoWorld()
    {
        super(ANCHO, ALTO, CELDA);

        juegoPausado = false;

        camara = new Camara(this);

        prepararEscenario();
        crearEntidades();
    }

    public void act()
    {
        if (!juegoPausado)
        {
            gestor.actualizar();
        }

        actualizarCamara();
    }

    private void actualizarCamara()
    {
        if (jugador == null)
        {
            return;
        }

        camara.actualizar(
            jugador.getMundoX(),
            jugador.getMundoY()
        );

        actualizarActoresMundo();
    }

    private void actualizarActoresMundo()
    {
        for (ActorMundo actor :
            getObjects(ActorMundo.class))
        {
            actor.actualizarPosicionPantalla();
        }
    }

    public void gameOver()
    {
        showText(
            "GAME OVER",
            getWidth() / 2,
            getHeight() / 2
        );

        Greenfoot.stop();
    }

    public Jugador getJugador()
    {
        return jugador;
    }

    public GestorOleadas getGestorOleadas()
    {
        return gestor;
    }

    public int getEnemigosEliminados()
    {
        return enemigosEliminados;
    }

    public void registrarEnemigoEliminado()
    {
        enemigosEliminados++;
    }

    public int getAnchoMapa()
    {
        return ANCHO_MAPA;
    }

    public int getAltoMapa()
    {
        return ALTO_MAPA;
    }

    public Camara getCamara()
    {
        return camara;
    }

    public boolean estaPausado()
    {
        return juegoPausado;
    }

    public void pausarJuego()
    {
        juegoPausado = true;
    }

    public void reanudarJuego()
    {
        juegoPausado = false;
    }

    public void mostrarMenuNivel()
    {
        pausarJuego();

        MenuNivel menu =
            new MenuNivel(this);

        addObject(
            menu,
            getWidth() / 2,
            getHeight() / 2
        );
    }

    public void agregarActorMundo(
        ActorMundo actor,
        double mundoX,
        double mundoY
    )
    {
        addObject(
            actor,
            (int)mundoX,
            (int)mundoY
        );

        actor.establecerPosicionMundo(
            mundoX,
            mundoY
        );

        actor.actualizarPosicionPantalla();
    }

    private void prepararEscenario()
    {
        FondoMundo fondo =
            new FondoMundo(
                "fondo.png",
                ANCHO_MAPA,
                ALTO_MAPA
            );

        agregarActorMundo(
            fondo,
            ANCHO_MAPA / 2.0,
            ALTO_MAPA / 2.0
        );
    }

    private void crearEntidades()
    {
        jugador = new Jugador();

        agregarActorMundo(
            jugador,
            ANCHO_MAPA / 2.0,
            ALTO_MAPA / 2.0
        );

        gestor =
            new GestorOleadas(
                this,
                jugador
            );

        hud =
            new HUD(
                this,
                jugador,
                gestor
            );

        addObject(
            hud,
            getWidth() / 2,
            getHeight() / 2
        );
    }
}
