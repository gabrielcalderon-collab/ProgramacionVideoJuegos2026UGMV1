import greenfoot.*;

public class AccionCombate
{
    private String nombre;
    private int danio;
    private int probabilidadAcierto;

    public AccionCombate(String nombre, int danio, int probabilidadAcierto)
    {
        this.nombre = nombre;
        this.danio = danio;
        this.probabilidadAcierto = probabilidadAcierto;
    }

    public String getNombre() { return nombre; }
    public int getDanio() { return danio; }
    public int getProbabilidadAcierto() { return probabilidadAcierto; }

    public boolean acierta()
    {
        return Greenfoot.getRandomNumber(100) < probabilidadAcierto;
    }
}
