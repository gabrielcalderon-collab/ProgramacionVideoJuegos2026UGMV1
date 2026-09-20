import greenfoot.*;

public class TextoFlotante extends Actor
{
    private int vida = 45;

    public TextoFlotante(String texto, Color color)
    {
        setImage(new GreenfootImage(
            texto, 26, color, new Color(0, 0, 0, 0)
        ));
    }

    public void act()
    {
        setLocation(getX(), getY() - 1);
        vida--;

        if (vida <= 0)
            getWorld().removeObject(this);
    }
}
