public class Camara
{
    private final JuegoWorld mundo;
    private int x;
    private int y;

    public Camara(JuegoWorld mundo)
    {
        this.mundo = mundo;
    }

    public void actualizar(double jugadorX, double jugadorY)
    {
        int objetivoX = (int)Math.round(jugadorX - mundo.getWidth() / 2.0);
        int objetivoY = (int)Math.round(jugadorY - mundo.getHeight() / 2.0);

        int maxX = Math.max(0, mundo.getAnchoMapa() - mundo.getWidth());
        int maxY = Math.max(0, mundo.getAltoMapa() - mundo.getHeight());

        x = limitar(objetivoX, 0, maxX);
        y = limitar(objetivoY, 0, maxY);
    }

    private int limitar(int valor, int minimo, int maximo)
    {
        return Math.max(minimo, Math.min(valor, maximo));
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
