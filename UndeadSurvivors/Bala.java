import greenfoot.*;

public class Bala extends ActorMundo
{
    private static final int VELOCIDAD = 8;
    private static final int DURACION = 80;

    private final double dx;
    private final double dy;
    private final int danio;
    private int tiempoRestante = DURACION;

    public Bala(double dirX, double dirY, int danio)
    {
        dx = dirX;
        dy = dirY;
        this.danio = danio;

        setImage(new GreenfootImage(FabricaImagenes.BALA));
        setRotation((int)Math.toDegrees(Math.atan2(dy, dx)));
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        moverMundo(dx * VELOCIDAD, dy * VELOCIDAD);
        tiempoRestante--;

        if (impactarEnemigo())
        {
            return;
        }

        JuegoWorld mundo = (JuegoWorld)getWorld();

        if (tiempoRestante <= 0
            || mundoX < 0
            || mundoX > mundo.getAnchoMapa()
            || mundoY < 0
            || mundoY > mundo.getAltoMapa())
        {
            mundo.removeObject(this);
        }
    }

    private boolean impactarEnemigo()
    {
    SoldadoEnemigo enemigo =
        (SoldadoEnemigo)getOneIntersectingObject(
            SoldadoEnemigo.class
        );

    if (enemigo == null)
    {
        return false;
    }

    double textoX =
        enemigo.getMundoX();

    double textoY =
        enemigo.getMundoY() - 15;

    enemigo.recibirDanio(danio);

    if (getWorld() != null)
    {
        JuegoWorld mundoJuego =
            (JuegoWorld)getWorld();

        mundoJuego.agregarActorMundo(
            new TextoDanio(
                danio,
                textoX,
                textoY
            ),
            textoX,
            textoY
        );

        getWorld().removeObject(this);
    }

    return true;
    }
}
