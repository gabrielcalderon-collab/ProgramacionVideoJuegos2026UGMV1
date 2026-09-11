import greenfoot.*;

public class Curacion extends ActorMundo
{
    private static final int CANTIDAD_CURA = 25;
    private static final int VIDA_UTIL = 300;

    private int vidaRestante = VIDA_UTIL;

    public Curacion()
    {
        GreenfootImage imagen = new GreenfootImage(20, 20);
        imagen.setColor(Color.GREEN);
        imagen.fill();
        imagen.setColor(Color.WHITE);
        imagen.fillRect(8, 4, 4, 12);
        imagen.fillRect(4, 8, 12, 4);
        setImage(imagen);
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        vidaRestante--;

        Jugador jugador =
            (Jugador)getOneIntersectingObject(Jugador.class);

        if (jugador != null)
        {
            jugador.curar(CANTIDAD_CURA);

            JuegoWorld mundo = (JuegoWorld)getWorld();
            mundo.agregarActorMundo(
                new TextoCuracion(CANTIDAD_CURA),
                mundoX,
                mundoY - 20
            );
            mundo.removeObject(this);
            return;
        }

        if (vidaRestante <= 0)
        {
            getWorld().removeObject(this);
        }
    }
}
