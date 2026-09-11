import greenfoot.*;

public class TextoCuracion extends ActorMundo
{
    private static final int DURACION = 35;
    private static final int VELOCIDAD = 1;
    private static final int TAMANO_TEXTO = 22;

    private int vidaRestante = DURACION;

    public TextoCuracion(int cantidad)
    {
        GreenfootImage imagen = new GreenfootImage(
            "+" + cantidad,
            TAMANO_TEXTO,
            Color.GREEN,
            new Color(0, 0, 0, 0)
        );
        setImage(imagen);
    }

    public void act()
    {
        moverMundo(0, -VELOCIDAD);
        vidaRestante--;

        if (vidaRestante <= 0 && getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
