public class EnemigoPesado extends SoldadoEnemigo
{
    private static final int MULTIPLICADOR_VIDA = 2;
    private static final int PENALIZACION_VELOCIDAD = 1;
    private static final int BONIFICACION_DANIO = 5;

    public EnemigoPesado(Jugador objetivo, int vida, int velocidad, int danio)
    {
        super(
            objetivo,
            vida * MULTIPLICADOR_VIDA,
            Math.max(1, velocidad - PENALIZACION_VELOCIDAD),
            danio + BONIFICACION_DANIO
        );
    }
}
