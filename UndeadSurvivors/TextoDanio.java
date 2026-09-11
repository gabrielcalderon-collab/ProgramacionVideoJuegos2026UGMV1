import greenfoot.*;

public class TextoDanio extends ActorMundo
{
    private static final int DURACION = 25;
    private static final int VELOCIDAD = 1;
    private static final int TAMANO_TEXTO = 20;

    private int vidaRestante = DURACION;

    public TextoDanio(
        int danio,
        double mundoX,
        double mundoY
    )
    {
        establecerPosicionMundo(
            mundoX,
            mundoY
        );

        GreenfootImage imagen =
            new GreenfootImage(
                String.valueOf(danio),
                TAMANO_TEXTO,
                Color.YELLOW,
                new Color(0, 0, 0, 0)
            );

        setImage(imagen);
    }

    protected void addedToWorld(World mundo)
    {
        super.addedToWorld(mundo);

        actualizarPosicionPantalla();
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        moverMundo(0, -VELOCIDAD);

        vidaRestante--;

        if (vidaRestante <= 0)
        {
            getWorld().removeObject(this);
            return;
        }

        actualizarPosicionPantalla();
    }
}
