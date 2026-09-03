import greenfoot.*;

public class Plataforma extends Actor
{
    // Plataforma normal
    public Plataforma()
    {
        this(180, 20);
    }

    // Plataforma con tamaño personalizado
    public Plataforma(int ancho, int alto)
    {
        GreenfootImage imagen = new GreenfootImage(ancho, alto);
        imagen.fill();
        setImage(imagen);
    }
}