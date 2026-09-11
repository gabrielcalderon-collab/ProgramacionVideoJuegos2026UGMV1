import greenfoot.*;

public class Nivel
{
    private final int ancho;
    private final int alto;
    private final GreenfootImage mapa;

    public Nivel(String archivoFondo, int ancho, int alto)
    {
        if (ancho <= 0 || alto <= 0)
        {
            throw new IllegalArgumentException("Las dimensiones del nivel deben ser mayores que cero.");
        }

        this.ancho = ancho;
        this.alto = alto;
        this.mapa = construirMapa(archivoFondo);
    }

    private GreenfootImage construirMapa(String archivoFondo)
    {
        GreenfootImage original = new GreenfootImage(archivoFondo);
        GreenfootImage resultado = new GreenfootImage(ancho, alto);

        for (int y = 0; y < alto; y += original.getHeight())
        {
            for (int x = 0; x < ancho; x += original.getWidth())
            {
                resultado.drawImage(original, x, y);
            }
        }

        return resultado;
    }

    public int getAncho()
    {
        return ancho;
    }

    public int getAlto()
    {
        return alto;
    }

    public GreenfootImage getMapa()
    {
        return mapa;
    }
}
