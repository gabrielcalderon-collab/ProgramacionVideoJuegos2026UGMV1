import greenfoot.*;

public class FondoEscenario extends Actor
{
    private int xOriginal;
    private int yOriginal;

    public FondoEscenario()
    {
        GreenfootImage fondo =
            new GreenfootImage("escenario.png");

        fondo.scale(900, 600);

        setImage(fondo);
    }

    public void guardarPosicion()
    {
        xOriginal = getX();
        yOriginal = getY();
    }

    public void sacudir(int x, int y)
    {
        setLocation(
            xOriginal + x,
            yOriginal + y
        );
    }

    public void restaurar()
    {
        setLocation(
            xOriginal,
            yOriginal
        );
    }
}