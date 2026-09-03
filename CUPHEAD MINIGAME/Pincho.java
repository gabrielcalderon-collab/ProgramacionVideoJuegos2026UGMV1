import greenfoot.*;

public class Pincho extends Actor
{
    public Pincho()
    {
        GreenfootImage imagen =
            new GreenfootImage(40, 30);

        imagen.setColor(Color.RED);

        int[] x = {
            0, 10, 20, 30, 40
        };

        int[] y = {
            30, 0, 30, 0, 30
        };

        imagen.fillPolygon(x, y, 5);

        setImage(imagen);
    }
}