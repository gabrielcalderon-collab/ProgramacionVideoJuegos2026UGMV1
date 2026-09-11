import greenfoot.*;

public class GestorOleadas
{
    private static final int ENEMIGOS_BASE = 5;
    private static final int ENEMIGOS_POR_OLEADA = 3;
    private static final int MAX_ENEMIGOS_OLEADA = 80;
    private static final int MAX_SIMULTANEOS_BASE = 6;
    private static final int MAX_SIMULTANEOS_POR_OLEADA = 2;
    private static final int MAX_SIMULTANEOS = 35;
    private static final int INTERVALO_BASE = 50;
    private static final int REDUCCION_INTERVALO = 3;
    private static final int INTERVALO_MINIMO = 12;
    private static final int VIDA_BASE = 20;
    private static final int VIDA_POR_OLEADA = 4;
    private static final int VELOCIDAD_BASE = 1;
    private static final int VELOCIDAD_MAXIMA = 4;
    private static final int DANIO_BASE = 4;
    private static final int DANIO_MAXIMO = 15;
    private static final int ESPERA_OLEADA = 100;
    private static final int ESPERA_SPAWN_INICIAL = 20;
    private static final int DISTANCIA_BORDE = 10;

    private final JuegoWorld mundo;
    private final Jugador jugador;

    private int oleada = 1;
    private int generados;
    private int objetivoOleada;
    private int contadorSpawn;
    private int esperaEntreOleadas;

    public GestorOleadas(JuegoWorld mundo, Jugador jugador)
    {
        this.mundo = mundo;
        this.jugador = jugador;
        prepararOleada();
    }

    public void actualizar()
    {
        if (jugador.getWorld() == null)
        {
            return;
        }

        int enemigosActivos = mundo.getObjects(SoldadoEnemigo.class).size();

        if (generados < objetivoOleada)
        {
            actualizarSpawn(enemigosActivos);
            return;
        }

        actualizarSiguienteOleada(enemigosActivos);
    }

    private void actualizarSpawn(int enemigosActivos)
    {
        contadorSpawn--;

        if (contadorSpawn <= 0 && enemigosActivos < maxSimultaneos())
        {
            crearEnemigo();
            generados++;
            contadorSpawn = intervaloSpawn();
        }
    }

    private void actualizarSiguienteOleada(int enemigosActivos)
    {
        if (enemigosActivos != 0)
        {
            return;
        }

        if (esperaEntreOleadas == 0)
        {
            esperaEntreOleadas = ESPERA_OLEADA;
        }

        esperaEntreOleadas--;

        if (esperaEntreOleadas <= 0)
        {
            oleada++;
            prepararOleada();
        }
    }

    private void prepararOleada()
    {
        generados = 0;
        objetivoOleada = Math.min(
            ENEMIGOS_BASE + oleada * ENEMIGOS_POR_OLEADA,
            MAX_ENEMIGOS_OLEADA
        );
        contadorSpawn = ESPERA_SPAWN_INICIAL;
        esperaEntreOleadas = 0;
    }

    private int maxSimultaneos()
    {
        return Math.min(
            MAX_SIMULTANEOS_BASE + oleada * MAX_SIMULTANEOS_POR_OLEADA,
            MAX_SIMULTANEOS
        );
    }

    private int intervaloSpawn()
    {
        return Math.max(
            INTERVALO_MINIMO,
            INTERVALO_BASE - oleada * REDUCCION_INTERVALO
        );
    }

    private int vidaEnemigo()
    {
        return VIDA_BASE + oleada * VIDA_POR_OLEADA;
    }

    private int velocidadEnemigo()
    {
        return Math.min(VELOCIDAD_BASE + oleada / 4, VELOCIDAD_MAXIMA);
    }

    private int danioEnemigo()
    {
        return Math.min(DANIO_BASE + oleada, DANIO_MAXIMO);
    }

    private void crearEnemigo()
    {
        SoldadoEnemigo enemigo = new SoldadoEnemigo(
            jugador,
            vidaEnemigo(),
            velocidadEnemigo(),
            danioEnemigo()
        );

        int[] posicion = obtenerPosicionBorde();
        mundo.agregarActorMundo(enemigo, posicion[0], posicion[1]);
    }

    private int[] obtenerPosicionBorde()
    {
        int lado = Greenfoot.getRandomNumber(4);
        int x;
        int y;

        int anchoMapa = mundo.getAnchoMapa();
        int altoMapa = mundo.getAltoMapa();

        if (lado == 0)
        {
            x = DISTANCIA_BORDE;
            y = Greenfoot.getRandomNumber(altoMapa);
        }
        else if (lado == 1)
        {
            x = anchoMapa - DISTANCIA_BORDE;
            y = Greenfoot.getRandomNumber(altoMapa);
        }
        else if (lado == 2)
        {
            x = Greenfoot.getRandomNumber(anchoMapa);
            y = DISTANCIA_BORDE;
        }
        else
        {
            x = Greenfoot.getRandomNumber(anchoMapa);
            y = altoMapa - DISTANCIA_BORDE;
        }

        return new int[] {x, y};
    }

    public int getOleada()
    {
        return oleada;
    }
}
