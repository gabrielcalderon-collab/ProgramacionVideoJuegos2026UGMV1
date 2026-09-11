import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

public class Laser extends ActorMundo
{
    private final double longitud;
    private final int grosor;
    private final int duracion;

    private final double origenX;
    private final double origenY;
    private final double direccionX;
    private final double direccionY;

    private final int danio;

    private boolean danoAplicado;
    private int tiempoRestante;

    public Laser(
        double origenX,
        double origenY,
        double direccionX,
        double direccionY,
        int danio,
        double longitud,
        int duracion,
        int grosor
    )
    {
        establecerPosicionMundo(
            origenX,
            origenY
        );

        this.origenX = origenX;
        this.origenY = origenY;

        this.danio = danio;
        this.longitud = longitud;
        this.duracion = duracion;
        this.grosor = grosor;

        this.tiempoRestante =
            duracion;

        double distancia =
            Math.hypot(
                direccionX,
                direccionY
            );

        if (distancia == 0)
        {
            this.direccionX = 1;
            this.direccionY = 0;
        }
        else
        {
            this.direccionX =
                direccionX / distancia;

            this.direccionY =
                direccionY / distancia;
        }

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

        if (!danoAplicado)
        {
            aplicarDanio();

            danoAplicado = true;
        }

        tiempoRestante--;

        if (tiempoRestante <= 0)
        {
            getWorld().removeObject(this);

            return;
        }

        actualizarPosicionPantalla();
    }





    private void aplicarDanio()
    {
        double finalX =
            origenX
            + direccionX
            * longitud;

        double finalY =
            origenY
            + direccionY
            * longitud;

        List<SoldadoEnemigo> enemigos =
            new ArrayList<SoldadoEnemigo>(
                getWorld()
                    .getObjects(
                        SoldadoEnemigo.class
                    )
            );

        for (SoldadoEnemigo enemigo :
            enemigos)
        {
            double distancia =
                distanciaPuntoSegmento(
                    enemigo.getMundoX(),
                    enemigo.getMundoY(),
                    origenX,
                    origenY,
                    finalX,
                    finalY
                );

            if (distancia <= grosor + 10)
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
    }





    private double distanciaPuntoSegmento(
        double puntoX,
        double puntoY,
        double inicioX,
        double inicioY,
        double finalX,
        double finalY
    )
    {
        double segmentoX =
            finalX - inicioX;

        double segmentoY =
            finalY - inicioY;

        double puntoXRelativo =
            puntoX - inicioX;

        double puntoYRelativo =
            puntoY - inicioY;

        double longitudCuadrada =
            segmentoX * segmentoX
            + segmentoY * segmentoY;

        if (longitudCuadrada == 0)
        {
            return Math.hypot(
                puntoXRelativo,
                puntoYRelativo
            );
        }

        double posicion =
            (
                puntoXRelativo
                * segmentoX
                +
                puntoYRelativo
                * segmentoY
            )
            / longitudCuadrada;

        posicion =
            Math.max(
                0,
                Math.min(
                    1,
                    posicion
                )
            );

        double puntoMasCercanoX =
            inicioX
            + posicion
            * segmentoX;

        double puntoMasCercanoY =
            inicioY
            + posicion
            * segmentoY;

        return Math.hypot(
            puntoX
            - puntoMasCercanoX,

            puntoY
            - puntoMasCercanoY
        );
    }





    private void crearImagen()
    {
        int longitudImagen =
            (int)longitud;

        GreenfootImage imagen =
            new GreenfootImage(
                longitudImagen,
                grosor * 2 + 2
            );

        imagen.setColor(Color.WHITE);

        int centroY =
            imagen.getHeight() / 2;

        imagen.fillRect(
            longitudImagen / 2,
            centroY - grosor / 2,
            longitudImagen / 2,
            grosor
        );

        imagen.setColor(Color.YELLOW);

        imagen.fillRect(
            longitudImagen / 2,
            centroY - 2,
            longitudImagen / 2,
            4
        );

        setImage(imagen);

        setRotation(
            (int)Math.toDegrees(
                Math.atan2(
                    direccionY,
                    direccionX
                )
            )
        );
    }
}
