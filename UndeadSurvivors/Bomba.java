import greenfoot.*;

public class Bomba extends ActorMundo
{
    private static final double VELOCIDAD = 6.0;
    private static final double DISTANCIA_IMPACTO = 10.0;

    private static final int TIEMPO_EXPLOSION = 20;

    private static final int TAMANO_BOMBA = 16;

    private final double objetivoX;
    private final double objetivoY;

    private final int danio;
    private final int radioExplosion;

    private boolean preparadaParaExplotar;
    private int contadorExplosion;

    public Bomba(
        double mundoX,
        double mundoY,
        double objetivoX,
        double objetivoY,
        int danio,
        int radioExplosion
    )
    {
        establecerPosicionMundo(
            mundoX,
            mundoY
        );

        this.objetivoX = objetivoX;
        this.objetivoY = objetivoY;

        this.danio = danio;
        this.radioExplosion = radioExplosion;

        crearImagen();
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

        if (!preparadaParaExplotar)
        {
            avanzarHaciaObjetivo();

            if (llegoAlObjetivo())
            {
                preparadaParaExplotar = true;

                contadorExplosion =
                    TIEMPO_EXPLOSION;
            }
        }
        else
        {
            esperarExplosion();
        }

        actualizarPosicionPantalla();
    }





    private void avanzarHaciaObjetivo()
    {
        double dx =
            objetivoX - mundoX;

        double dy =
            objetivoY - mundoY;

        double distancia =
            Math.hypot(dx, dy);

        if (distancia <= 0)
        {
            return;
        }

        double direccionX =
            dx / distancia;

        double direccionY =
            dy / distancia;

        double movimiento =
            Math.min(
                VELOCIDAD,
                distancia
            );

        moverMundo(
            direccionX * movimiento,
            direccionY * movimiento
        );
    }

    private boolean llegoAlObjetivo()
    {
        double dx =
            objetivoX - mundoX;

        double dy =
            objetivoY - mundoY;

        return Math.hypot(dx, dy)
            <= DISTANCIA_IMPACTO;
    }





    private void esperarExplosion()
    {
        contadorExplosion--;

        if (contadorExplosion <= 0)
        {
            explotar();
        }
    }

    private void explotar()
    {
        if (getWorld() == null)
        {
            return;
        }

        for (SoldadoEnemigo enemigo :
            getWorld()
                .getObjects(
                    SoldadoEnemigo.class
                ))
        {
            double dx =
                enemigo.getMundoX()
                - mundoX;

            double dy =
                enemigo.getMundoY()
                - mundoY;

            double distancia =
                Math.hypot(dx, dy);

            if (distancia <= radioExplosion)
            {
                double textoX =
                    enemigo.getMundoX();

                double textoY =
                    enemigo.getMundoY()
                    - 15;

                enemigo.recibirDanio(
                    danio
                );

                if (getWorld() != null)
                {
                    JuegoWorld mundoJuego =
                        (JuegoWorld)getWorld();

                    mundoJuego.agregarActorMundo(
                        new TextoDanio(
                            danio,
                            textoX,
                            textoY
                        ),
                        textoX,
                        textoY
                    );
                }
            }
        }

        crearEfectoExplosion();

        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }





    private void crearImagen()
    {
        GreenfootImage imagen =
            new GreenfootImage(
                TAMANO_BOMBA,
                TAMANO_BOMBA
            );

        imagen.setColor(Color.BLACK);

        imagen.fillOval(
            1,
            1,
            TAMANO_BOMBA - 2,
            TAMANO_BOMBA - 2
        );

        imagen.setColor(Color.RED);

        imagen.fillOval(
            5,
            3,
            6,
            6
        );

        setImage(imagen);
    }





    private void crearEfectoExplosion()
    {
        if (getWorld() == null)
        {
            return;
        }

        Explosion explosion =
            new Explosion(
                radioExplosion
            );

        JuegoWorld mundoJuego =
            (JuegoWorld)getWorld();

        mundoJuego.agregarActorMundo(
            explosion,
            mundoX,
            mundoY
        );
    }
}
