public class EnemigoRapido extends SoldadoEnemigo
{
    private static final double MULTIPLICADOR_VIDA = 0.6;
    private static final int BONIFICACION_VELOCIDAD = 2;

    public EnemigoRapido(Jugador objetivo, int vida, int velocidad, int danio)
    {
        super(
            objetivo,
            (int)(vida * MULTIPLICADOR_VIDA),
            velocidad + BONIFICACION_VELOCIDAD,
            danio
        );
    }
}
