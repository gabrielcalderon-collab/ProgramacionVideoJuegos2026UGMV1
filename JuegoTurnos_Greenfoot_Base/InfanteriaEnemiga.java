import greenfoot.*;

public class InfanteriaEnemiga extends Unidad
{
    private static final int ANCHO = 360;
    private static final int ALTO = 470;

    public InfanteriaEnemiga()
    {
        super(100);
        setFrameNormal();
    }

    public void setFrameNormal()
    {
        setFrame("enemigo.png");
    }

    public void setFrameTransicion()
    {
        setFrame("e_frame2.png");
    }

    public void setFrameRugido()
    {
        setFrame("e_frame3.png");
    }

    private void setFrame(String archivo)
    {
        GreenfootImage imagen = new GreenfootImage(archivo);
        imagen.scale(ANCHO, ALTO);
        setImage(imagen);
    }
}