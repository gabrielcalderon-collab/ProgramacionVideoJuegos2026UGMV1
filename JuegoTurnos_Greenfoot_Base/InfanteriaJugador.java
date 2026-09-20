import greenfoot.*;

public class InfanteriaJugador extends Unidad
{
    private static final int ANCHO = 190;
    private static final int ALTO = 190;

    public InfanteriaJugador()
    {
        super(100);
        setFrameInicial();
    }

    public void setFrameInicial()
    {
        setFrame("jugador.png");
    }

    public void setFrameGirando()
    {
        setFrame("j_frame2.png");
    }

    public void setFrameMirando()
    {
        setFrame("j_frame3.png");
    }

    private void setFrame(String archivo)
    {
        GreenfootImage imagen = new GreenfootImage(archivo);
        imagen.scale(ANCHO, ALTO);
        setImage(imagen);
    }
}
