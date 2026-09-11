import greenfoot.*;

public class FondoMundo extends ActorMundo
{
    private final int anchoMapa;
    private final int altoMapa;

    public FondoMundo(
        String nombreArchivo,
        int anchoMapa,
        int altoMapa
    )
    {
        this.anchoMapa = anchoMapa;
        this.altoMapa = altoMapa;

        crearFondo(nombreArchivo);
    }

    protected void addedToWorld(World mundo)
    {
        super.addedToWorld(mundo);

        actualizarPosicionPantalla();
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        actualizarPosicionPantalla();
    }

    private void crearFondo(String nombreArchivo)
    {
        GreenfootImage original =
            new GreenfootImage(nombreArchivo);

        int anchoCelda =
            1074;

        int altoCelda =
            1074;

        original.scale(
            anchoCelda,
            altoCelda
        );

        GreenfootImage fondo =
            new GreenfootImage(
                anchoMapa,
                altoMapa
            );

        for (int y = 0;
             y < altoMapa;
             y += altoCelda)
        {
            for (int x = 0;
                 x < anchoMapa;
                 x += anchoCelda)
            {
                fondo.drawImage(
                    original,
                    x,
                    y
                );
            }
        }

        setImage(fondo);
    }
}
