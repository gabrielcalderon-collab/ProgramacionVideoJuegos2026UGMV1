import greenfoot.*;

public class BotonAccion extends Actor
{
    private AccionCombate accion;
    private GestorCombate gestor;

    private boolean habilitado = true;
    private boolean seleccionado = false;

    public BotonAccion(
        AccionCombate accion,
        GestorCombate gestor
    )
    {
        this.accion = accion;
        this.gestor = gestor;

        actualizarImagen();
    }

    public void act()
    {
        // Selección mediante teclado.
    }

    public void setHabilitado(boolean valor)
    {
        habilitado = valor;

        if (!habilitado)
            seleccionado = false;

        actualizarImagen();
    }

    public void setSeleccionado(boolean valor)
    {
        seleccionado = valor;
        actualizarImagen();
    }

    public boolean estaSeleccionado()
    {
        return seleccionado;
    }

    private void actualizarImagen()
    {
        int ancho = 250;
        int alto = 65;

        Color fondo =
            new Color(242, 240, 220);

        Color texto =
            new Color(45, 42, 52);

        Color rojo =
            new Color(190, 65, 65);

        GreenfootImage img =
            new GreenfootImage(ancho, alto);

        // Fondo
        img.setColor(fondo);
        img.fill();

        if (seleccionado)
        {
            // Marco rojo pixelado
            img.setColor(rojo);

            img.fillRect(0, 0, ancho, 4);
            img.fillRect(0, alto - 4, ancho, 4);
            img.fillRect(0, 0, 4, alto);
            img.fillRect(ancho - 4, 0, 4, alto);

            // Pequeño segundo marco
            img.setColor(
                new Color(230, 110, 90)
            );

            img.drawRect(
                5,
                5,
                ancho - 11,
                alto - 11
            );
        }
        else
        {
            // Línea inferior sutil
            img.setColor(
                new Color(180, 175, 165)
            );

            img.fillRect(
                10,
                alto - 3,
                ancho - 20,
                2
            );
        }

        // Flecha de selección
        if (seleccionado)
        {
            GreenfootImage flecha =
                new GreenfootImage(
                    "▶",
                    18,
                    rojo,
                    new Color(0, 0, 0, 0)
                );

            img.drawImage(
                flecha,
                10,
                10
            );
        }

        // Nombre
        GreenfootImage nombre =
            new GreenfootImage(
                accion.getNombre(),
                18,
                texto,
                new Color(0, 0, 0, 0)
            );

        int xNombre =
            (ancho - nombre.getWidth()) / 2;

        img.drawImage(
            nombre,
            xNombre,
            8
        );

        // Información
        String info =
            accion.getDanio()
            + " DMG       "
            + accion.getProbabilidadAcierto()
            + "%";

        GreenfootImage datos =
            new GreenfootImage(
                info,
                14,
                new Color(90, 85, 90),
                new Color(0, 0, 0, 0)
            );

        int xDatos =
            (ancho - datos.getWidth()) / 2;

        img.drawImage(
            datos,
            xDatos,
            36
        );

        // Deshabilitado
        if (!habilitado)
        {
            img.setTransparency(120);
        }

        setImage(img);
    }
}