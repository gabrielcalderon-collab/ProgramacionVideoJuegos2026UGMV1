import greenfoot.*;
import java.util.List;

public class Cuchillo
{
    private static final int DANIO_INICIAL = 10;
    private static final int CADENCIA_INICIAL = 15;

    private static final int DANIO_MAXIMO = 30;

    private static final int LIMITE_BALAS = 60;

    private final Jugador jugador;

    private int cooldown;
    private int danio = DANIO_INICIAL;
    private int cadencia = CADENCIA_INICIAL;

    public Cuchillo(Jugador jugador)
    {
        this.jugador = jugador;
    }

    public void actualizar()
    {
        if (jugador == null ||
            jugador.getWorld() == null)
        {
            return;
        }

        if (cooldown > 0)
        {
            cooldown--;
        }

        if (cooldown == 0)
        {
            atacar();
        }
    }

    private void atacar()
    {
        SoldadoEnemigo objetivo =
            obtenerEnemigoMasCercano();

        if (objetivo == null)
        {
            return;
        }

        if (dispararContra(objetivo))
        {
            cooldown = cadencia;
        }
    }

    private boolean dispararContra(
        SoldadoEnemigo objetivo
    )
    {
        double dx =
            objetivo.getMundoX()
            - jugador.getMundoX();

        double dy =
            objetivo.getMundoY()
            - jugador.getMundoY();

        double distancia =
            Math.hypot(dx, dy);

        if (distancia <= 0)
        {
            return false;
        }

        int cantidadBalas =
            jugador.getWorld()
                .getObjects(Bala.class)
                .size();

        if (cantidadBalas >= LIMITE_BALAS)
        {
            return false;
        }

        double dirX =
            dx / distancia;

        double dirY =
            dy / distancia;

        int danioFinal =
            jugador.getSistemaArmas()
                .calcularDanio(danio);

        Bala bala =
            new Bala(
                dirX,
                dirY,
                danioFinal
            );

        JuegoWorld mundo =
            (JuegoWorld)jugador.getWorld();

        mundo.agregarActorMundo(
            bala,
            jugador.getMundoX(),
            jugador.getMundoY()
        );

        return true;
    }

    private SoldadoEnemigo obtenerEnemigoMasCercano()
    {
        List<SoldadoEnemigo> enemigos =
            jugador.getWorld()
                .getObjects(
                    SoldadoEnemigo.class
                );

        SoldadoEnemigo masCercano = null;

        double menorDistancia =
            Double.MAX_VALUE;

        for (SoldadoEnemigo enemigo :
            enemigos)
        {
            double dx =
                enemigo.getMundoX()
                - jugador.getMundoX();

            double dy =
                enemigo.getMundoY()
                - jugador.getMundoY();

            double distancia =
                Math.hypot(dx, dy);

            if (distancia < menorDistancia)
            {
                menorDistancia = distancia;
                masCercano = enemigo;
            }
        }

        return masCercano;
    }

    public int getDanio()
    {
        return danio;
    }

    public int getCadencia()
    {
        return cadencia;
    }

    public int getNivel()
    {
    return ((danio - DANIO_INICIAL) / 5) + 1;
    }

    public boolean puedeMejorar()
    {
        return danio < DANIO_MAXIMO;
    }

    public void mejorar()
    {
        if (!puedeMejorar())
        {
            return;
        }

        danio += 5;

        cadencia =
            Math.max(
                5,
                cadencia - 1
            );
    }
}
