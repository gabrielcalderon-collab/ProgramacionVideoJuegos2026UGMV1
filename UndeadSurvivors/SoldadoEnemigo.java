import greenfoot.*;
import java.util.List;

public class SoldadoEnemigo extends Soldado
{
    private static final int CADENCIA_ATAQUE = 30;
    private static final int EXPERIENCIA = 15;
    private static final int PROBABILIDAD_CURACION = 5;
    private static final int DURACION_ANIMACION = 10;

    private final Jugador objetivo;
    private final int danio;
    private int cooldownAtaque;
    private int frameActual;
    private int contadorAnimacion;

    public SoldadoEnemigo(Jugador objetivo, int vida, int velocidad, int danio)
    {
        super(vida, velocidad);
        this.objetivo = objetivo;
        this.danio = danio;
        setImage(new GreenfootImage(FabricaImagenes.ENEMIGO[0]));
    }

    public void act()
    {
    if (objetivo == null ||
        objetivo.getWorld() == null ||
        getWorld() == null)
    {
        return;
    }

    if (getWorld() instanceof JuegoWorld)
    {
        JuegoWorld mundo =
            (JuegoWorld)getWorld();

        if (mundo.estaPausado())
        {
            return;
        }
    }

    perseguir();
    actualizarAtaque();
    atacar();
    animar();
    }

    private void perseguir()
    {
        double dx = objetivo.getMundoX() - mundoX;
        double dy = objetivo.getMundoY() - mundoY;
        double distancia = Math.hypot(dx, dy);

        if (distancia <= 0)
        {
            return;
        }

        double movX = velocidad * dx / distancia;
        double movY = velocidad * dy / distancia;

        moverMundo(movX, movY);
        actualizarOrientacion(dx);
    }

    private void actualizarAtaque()
    {
        if (cooldownAtaque > 0)
        {
            cooldownAtaque--;
        }
    }

    private void atacar()
    {
        if (cooldownAtaque == 0 && isTouching(Jugador.class))
        {
            objetivo.recibirDanio(danio);
            cooldownAtaque = CADENCIA_ATAQUE;
        }
    }

    private void actualizarOrientacion(double dx)
    {
        GreenfootImage imagen =
            new GreenfootImage(FabricaImagenes.ENEMIGO[frameActual]);

        if (dx < 0)
        {
            imagen.mirrorHorizontally();
        }

        setImage(imagen);
    }

    protected void morir()
    {
        World mundo = getWorld();

        if (mundo != null)
        {
            soltarCuracion(mundo);
            otorgarExperiencia(mundo);
            registrarEliminacion(mundo);
        }

        super.morir();
    }

    private void soltarCuracion(World mundo)
    {
        if (Greenfoot.getRandomNumber(100) < PROBABILIDAD_CURACION)
        {
            Curacion curacion = new Curacion();
            ((JuegoWorld)mundo).agregarActorMundo(
                curacion,
                mundoX,
                mundoY
            );
        }
    }

    private void otorgarExperiencia(World mundo)
    {
        List<Jugador> jugadores = mundo.getObjects(Jugador.class);

        if (!jugadores.isEmpty())
        {
            jugadores.get(0).ganarExp(EXPERIENCIA);
        }
    }

    private void registrarEliminacion(World mundo)
    {
        if (mundo instanceof JuegoWorld)
        {
            ((JuegoWorld)mundo).registrarEnemigoEliminado();
        }
    }

    private void animar()
    {
        contadorAnimacion++;

        if (contadorAnimacion < DURACION_ANIMACION)
        {
            return;
        }

        frameActual = (frameActual == 0) ? 1 : 0;
        actualizarOrientacion(objetivo.getMundoX() - mundoX);
        contadorAnimacion = 0;
    }
}
