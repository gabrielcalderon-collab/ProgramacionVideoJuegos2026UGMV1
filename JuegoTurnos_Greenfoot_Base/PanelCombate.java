import greenfoot.*;

public class PanelCombate extends Actor
{
    public PanelCombate()
    {
        int ancho = 880;
        int alto = 145;

        GreenfootImage img = new GreenfootImage(ancho, alto);

        Color fondo = new Color(242, 240, 220);
        Color borde = new Color(55, 50, 65);
        Color bordeInterior = new Color(115, 110, 120);

        // Fondo
        img.setColor(fondo);
        img.fillRect(4, 4, ancho - 8, alto - 8);

        // Borde exterior grueso
        img.setColor(borde);

        img.fillRect(0, 6, ancho, alto - 12);
        img.fillRect(6, 0, ancho - 12, alto);

        // Esquinas pixeladas
        img.fillRect(2, 2, 4, 4);
        img.fillRect(ancho - 6, 2, 4, 4);
        img.fillRect(2, alto - 6, 4, 4);
        img.fillRect(ancho - 6, alto - 6, 4, 4);

        // Marco interior
        img.setColor(bordeInterior);

        img.drawRect(
            8,
            8,
            ancho - 17,
            alto - 17
        );

        img.drawRect(
            10,
            10,
            ancho - 21,
            alto - 21
        );

        // Línea superior
        img.setColor(borde);

        img.fillRect(
            22,
            38,
            ancho - 44,
            3
        );

        // Título
        GreenfootImage titulo = new GreenfootImage(
            "ACCIONES",
            18,
            borde,
            new Color(0, 0, 0, 0)
        );

        img.drawImage(
            titulo,
            25,
            13
        );

        // Controles
        GreenfootImage controles = new GreenfootImage(
            "WASD / FLECHAS   ENTER / ESPACIO",
            12,
            new Color(95, 90, 100),
            new Color(0, 0, 0, 0)
        );

        img.drawImage(
            controles,
            ancho - controles.getWidth() - 25,
            16
        );

        setImage(img);
    }
}