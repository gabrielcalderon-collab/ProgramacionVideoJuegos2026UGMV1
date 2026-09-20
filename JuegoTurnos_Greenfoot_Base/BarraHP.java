import greenfoot.*;

public class BarraHP extends Actor
{
    private Unidad unidad;

    public BarraHP(Unidad unidad)
    {
        this.unidad = unidad;
        actualizar();
    }

    public void actualizar()
    {
        int ancho = 210;
        int alto = 25;
        GreenfootImage img = new GreenfootImage(ancho, alto);

        img.setColor(new Color(30, 30, 30, 230));
        img.fillRect(0, 0, ancho, alto);

        int anchoVida = (int)(ancho * unidad.proporcionVida());

        img.setColor(Color.GREEN);
        img.fillRect(3, 3, Math.max(0, anchoVida - 6), alto - 6);

        img.setColor(Color.WHITE);
        img.drawString(
            "HP " + unidad.getHP() + "/" + unidad.getHPMaximo(),
            70, 18
        );

        setImage(img);
    }
}
