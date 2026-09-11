public class SistemaArmas
{
    private final Jugador jugador;

    private final Cuchillo cuchillo;

    private boolean bombaDesbloqueada;
    private boolean laserDesbloqueado;





    private static final boolean PRUEBA_CUCHILLO = true;
    private static final boolean PRUEBA_BOMBA = false;
    private static final boolean PRUEBA_LASER = false;





    private static final int NIVEL_MAXIMO_BOMBA = 5;
    private static final int NIVEL_MAXIMO_LASER = 5;

    private int nivelBomba = 0;
    private int nivelLaser = 0;





    private static final double ALCANCE_BOMBA = 400.0;





    private int cooldownBomba;
    private int cooldownLaser;

    public SistemaArmas(Jugador jugador)
    {
        this.jugador = jugador;

        cuchillo = new Cuchillo(jugador);

        bombaDesbloqueada = PRUEBA_BOMBA;
        laserDesbloqueado = PRUEBA_LASER;

        if (PRUEBA_BOMBA)
        {
            nivelBomba = 1;
        }

        if (PRUEBA_LASER)
        {
            nivelLaser = 1;
        }
    }

    public void actualizar()
    {
        if (PRUEBA_CUCHILLO)
        {
            cuchillo.actualizar();
        }

        if (bombaDesbloqueada)
        {
            actualizarBomba();
        }

        if (laserDesbloqueado)
        {
            actualizarLaser();
        }
    }





    public int calcularDanio(int danioBase)
    {
        double danioFinal =
            danioBase
            * jugador.getMultiplicadorDanioGlobal();

        return Math.max(
            1,
            (int)Math.round(danioFinal)
        );
    }





    private void actualizarBomba()
    {
        if (cooldownBomba > 0)
        {
            cooldownBomba--;
        }

        if (cooldownBomba > 0)
        {
            return;
        }

        SoldadoEnemigo objetivo =
            obtenerEnemigoMasCercano();

        if (objetivo == null)
        {
            return;
        }

        lanzarBomba(objetivo);

        cooldownBomba =
            getCadenciaBomba();
    }

    private void lanzarBomba(
        SoldadoEnemigo objetivo
    )
    {
        JuegoWorld mundo =
            (JuegoWorld)jugador.getWorld();

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
            return;
        }

        double distanciaObjetivo =
            Math.min(
                distancia,
                ALCANCE_BOMBA
            );

        double direccionX =
            dx / distancia;

        double direccionY =
            dy / distancia;

        double objetivoX =
            jugador.getMundoX()
            + direccionX
            * distanciaObjetivo;

        double objetivoY =
            jugador.getMundoY()
            + direccionY
            * distanciaObjetivo;

        int danio =
            calcularDanio(
                getDanioBaseBomba()
            );

        Bomba bomba =
            new Bomba(
                jugador.getMundoX(),
                jugador.getMundoY(),
                objetivoX,
                objetivoY,
                danio,
                getRadioBomba()
            );

        mundo.agregarActorMundo(
            bomba,
            jugador.getMundoX(),
            jugador.getMundoY()
        );
    }

    private int getDanioBaseBomba()
    {
        if (nivelBomba == 1)
        {
            return 35;
        }

        if (nivelBomba == 2)
        {
            return 45;
        }

        if (nivelBomba == 3)
        {
            return 55;
        }

        if (nivelBomba == 4)
        {
            return 70;
        }

        return 85;
    }

    public int getRadioBomba()
    {
        if (nivelBomba == 1)
        {
            return 80;
        }

        if (nivelBomba == 2)
        {
            return 90;
        }

        if (nivelBomba == 3)
        {
            return 100;
        }

        if (nivelBomba == 4)
        {
            return 110;
        }

        return 120;
    }

    public int getCadenciaBomba()
    {
        if (nivelBomba == 1)
        {
            return 120;
        }

        if (nivelBomba == 2)
        {
            return 115;
        }

        if (nivelBomba == 3)
        {
            return 110;
        }

        if (nivelBomba == 4)
        {
            return 105;
        }

        return 100;
    }

    public int getNivelBomba()
    {
        return nivelBomba;
    }

    public boolean tieneBomba()
    {
        return bombaDesbloqueada;
    }

    public boolean puedeMejorarBomba()
    {
        return bombaDesbloqueada
            && nivelBomba < NIVEL_MAXIMO_BOMBA;
    }

    public void desbloquearBomba()
    {
        bombaDesbloqueada = true;

        if (nivelBomba == 0)
        {
            nivelBomba = 1;
        }

        cooldownBomba = 0;
    }

    public void mejorarBomba()
    {
        if (!puedeMejorarBomba())
        {
            return;
        }

        nivelBomba++;
    }





    private void actualizarLaser()
    {
        if (cooldownLaser > 0)
        {
            cooldownLaser--;
        }

        if (cooldownLaser > 0)
        {
            return;
        }

        SoldadoEnemigo objetivo =
            obtenerEnemigoMasCercano();

        if (objetivo == null)
        {
            return;
        }

        dispararLaser(objetivo);

        cooldownLaser =
            getCadenciaLaser();
    }

    private void dispararLaser(
        SoldadoEnemigo objetivo
    )
    {
        JuegoWorld mundo =
            (JuegoWorld)jugador.getWorld();

        double dx =
            objetivo.getMundoX()
            - jugador.getMundoX();

        double dy =
            objetivo.getMundoY()
            - jugador.getMundoY();

        int danio =
            calcularDanio(
                getDanioBaseLaser()
            );

        Laser laser =
            new Laser(
                jugador.getMundoX(),
                jugador.getMundoY(),
                dx,
                dy,
                danio,
                getLongitudLaser(),
                getDuracionLaser(),
                getGrosorLaser()
            );

        mundo.agregarActorMundo(
            laser,
            jugador.getMundoX(),
            jugador.getMundoY()
        );
    }

    private int getDanioBaseLaser()
    {
        if (nivelLaser == 1)
        {
            return 20;
        }

        if (nivelLaser == 2)
        {
            return 27;
        }

        if (nivelLaser == 3)
        {
            return 35;
        }

        if (nivelLaser == 4)
        {
            return 45;
        }

        return 60;
    }

    public double getLongitudLaser()
    {
        if (nivelLaser == 1)
        {
            return 1200.0;
        }

        if (nivelLaser == 2)
        {
            return 1300.0;
        }

        if (nivelLaser == 3)
        {
            return 1400.0;
        }

        if (nivelLaser == 4)
        {
            return 1500.0;
        }

        return 1600.0;
    }

    public int getDuracionLaser()
    {
        if (nivelLaser <= 2)
        {
            return 8;
        }

        if (nivelLaser <= 4)
        {
            return 9;
        }

        return 10;
    }

    public int getGrosorLaser()
    {
        if (nivelLaser == 1)
        {
            return 6;
        }

        if (nivelLaser == 2)
        {
            return 7;
        }

        if (nivelLaser == 3)
        {
            return 8;
        }

        if (nivelLaser == 4)
        {
            return 9;
        }

        return 10;
    }

    public int getCadenciaLaser()
    {
        if (nivelLaser == 1)
        {
            return 60;
        }

        if (nivelLaser == 2)
        {
            return 58;
        }

        if (nivelLaser == 3)
        {
            return 56;
        }

        if (nivelLaser == 4)
        {
            return 54;
        }

        return 52;
    }

    public int getNivelLaser()
    {
        return nivelLaser;
    }

    public boolean tieneLaser()
    {
        return laserDesbloqueado;
    }

    public boolean puedeMejorarLaser()
    {
        return laserDesbloqueado
            && nivelLaser < NIVEL_MAXIMO_LASER;
    }

    public void desbloquearLaser()
    {
        laserDesbloqueado = true;

        if (nivelLaser == 0)
        {
            nivelLaser = 1;
        }

        cooldownLaser = 0;
    }

    public void mejorarLaser()
    {
        if (!puedeMejorarLaser())
        {
            return;
        }

        nivelLaser++;
    }





    private SoldadoEnemigo obtenerEnemigoMasCercano()
    {
        SoldadoEnemigo masCercano = null;

        double menorDistancia =
            Double.MAX_VALUE;

        for (SoldadoEnemigo enemigo :
            jugador.getWorld()
                .getObjects(
                    SoldadoEnemigo.class
                ))
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





    public Cuchillo getCuchillo()
    {
        return cuchillo;
    }
}
