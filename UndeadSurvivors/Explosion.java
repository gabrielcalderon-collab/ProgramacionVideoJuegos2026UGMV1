import greenfoot.*;

public class Explosion extends ActorMundo
{
    private static final int DURACION = 10;

    private final int radio;
    private int tiempoRestante;

    public Explosion(int radio)
    {
        this.radio = radio;
        tiempoRestante = DURACION;

        crearImagen();
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        tiempoRestante--;

        if (tiempoRestante <= 0)
        {
            getWorld().removeObject(this);
            return;
        }

        actualizarPosicionPantalla();
    }

    private void crearImagen()
    {
        int diametro = radio * 2;

        GreenfootImage imagen =
            new GreenfootImage(
                diametro,
                diametro
            );

        imagen.setColor(
            new Color(255, 140, 0, 120)
        );

        imagen.fillOval(
            0,
            0,
            diametro - 1,
            diametro - 1
        );

        imagen.setColor(Color.RED);

        imagen.drawOval(
            0,
            0,
            diametro - 1,
            diametro - 1
        );

        setImage(imagen);
    }
}
