import greenfoot.*;

public class IndicadorTurno extends Actor
{
    private String texto = "";

    public void act()
    {
        actualizar();
    }

    public void mostrar(String texto, Color color)
    {
        this.texto = texto;
        actualizar();
    }

    private void actualizar()
    {
        int ancho = 230;
        int alto = 46;

        Color fondo = new Color(242, 240, 220);
        Color borde = new Color(55, 50, 65);
        Color bordeInterior = new Color(115, 110, 120);

        GreenfootImage img =
            new GreenfootImage(ancho, alto);

        img.setColor(borde);
        img.fillRect(0, 5, ancho, alto - 10);
        img.fillRect(5, 0, ancho - 10, alto);

        img.setColor(fondo);
        img.fillRect(8, 8, ancho - 16, alto - 16);

        img.setColor(bordeInterior);
        img.drawRect(
            10,
            10,
            ancho - 21,
            alto - 21
        );

        GreenfootImage textoImagen =
            new GreenfootImage(
                texto,
                20,
                borde,
                new Color(0, 0, 0, 0)
            );

        int x =
            (ancho - textoImagen.getWidth()) / 2;

        int y =
            (alto - textoImagen.getHeight()) / 2;

        img.drawImage(
            textoImagen,
            x,
            y
        );

        setImage(img);
    }
}