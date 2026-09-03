import greenfoot.*;

public class HUDVida extends Actor
{
    private GreenfootImage corazonLleno;
    private GreenfootImage corazonVacio;

    private int vidaActual = -1;

    public HUDVida()
    {
        corazonLleno = new GreenfootImage("full_heart.png");
        corazonVacio = new GreenfootImage("empty_heart.png");

        
        corazonLleno.scale(32, 32);
        corazonVacio.scale(25, 25);

        actualizarVida(3);
    }

    public void actualizarVida(int vida)
    {
        if (vida == vidaActual)
        {
            return;
        }

        vidaActual = vida;

        int cantidadCorazones = 3;
        int separacion = 6;

        int ancho =
            cantidadCorazones * 32
            + (cantidadCorazones - 1) * separacion;

        GreenfootImage hud =
            new GreenfootImage(ancho, 32);

        for (int i = 0; i < cantidadCorazones; i++)
        {
            GreenfootImage corazon;

            if (i < vida)
            {
                corazon =
                    new GreenfootImage(corazonLleno);
            }
            else
            {
                corazon =
                    new GreenfootImage(corazonVacio);
            }

            int x =
                i * (32 + separacion);

            int posicionY = (32 - corazon.getHeight()) / 2;

            hud.drawImage(
            corazon,
            x,
            posicionY
        );
        }

        setImage(hud);
    }
}