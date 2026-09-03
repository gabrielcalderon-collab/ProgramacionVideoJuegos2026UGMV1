import greenfoot.*;

public class IndicadorCarga extends Actor
{
    private GreenfootImage cargaPequena;
    private GreenfootImage cargaMedia;
    private GreenfootImage cargaCompleta;

    private int contadorPulso = 0;
    private boolean grande = false;

    public IndicadorCarga()
    {
        cargaPequena = new GreenfootImage("small_charge.png");
        cargaMedia = new GreenfootImage("medium_charge.png");
        cargaCompleta = new GreenfootImage("full_charge.png");

        setImage(cargaPequena);
    }

    public void actualizarNivel(
        int tiempoCarga,
        int limiteCargaMedia,
        int limiteCargaCompleta)
    {
        GreenfootImage base;

        // Elegir sprite dependiendo del nivel de carga
        if (tiempoCarga >= limiteCargaCompleta)
        {
            base = new GreenfootImage(cargaCompleta);
        }
        else if (tiempoCarga >= limiteCargaMedia)
        {
            base = new GreenfootImage(cargaMedia);
        }
        else
        {
            base = new GreenfootImage(cargaPequena);
        }

        // Animación de pulso
        contadorPulso++;

        if (contadorPulso >= 5)
        {
            grande = !grande;
            contadorPulso = 0;
        }

        if (grande)
        {
            base.scale(
                base.getWidth() + 4,
                base.getHeight() + 4
            );
        }

        setImage(base);
    }
}