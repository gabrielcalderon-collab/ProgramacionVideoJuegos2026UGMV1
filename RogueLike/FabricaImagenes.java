import greenfoot.*;

public class FabricaImagenes
{
    private static final GreenfootImage JUGADOR =
        crearSoldado(Color.GREEN);

    private static final GreenfootImage ENEMIGO =
        crearSoldado(Color.RED);

    private static final GreenfootImage BALA =
        crearBala();

    public static GreenfootImage soldadoJugador()
    {
        return JUGADOR;
    }

    public static GreenfootImage soldadoEnemigo()
    {
        return ENEMIGO;
    }

    public static GreenfootImage bala()
    {
        return BALA;
    }

    private static GreenfootImage crearBala()
    {
        GreenfootImage img = new GreenfootImage(10, 4);

        img.setColor(Color.YELLOW);
        img.fillRect(0, 0, 10, 4);

        return img;
    }

    private static GreenfootImage crearSoldado(Color color)
    {
        GreenfootImage img = new GreenfootImage(34, 34);

        img.setColor(color);

        // Cabeza
        img.fillOval(11, 1, 12, 12);

        // Cuerpo
        img.fillRect(10, 12, 14, 15);

        // Brazo izquierdo
        img.fillRect(4, 14, 7, 5);

        // Brazo derecho
        img.fillRect(23, 14, 7, 5);

        // Pierna izquierda
        img.fillRect(10, 26, 5, 8);

        // Pierna derecha
        img.fillRect(19, 26, 5, 8);

        return img;
    }
}