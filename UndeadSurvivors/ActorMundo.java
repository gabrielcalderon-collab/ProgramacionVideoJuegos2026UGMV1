import greenfoot.*;

public abstract class ActorMundo extends Actor
{
    protected double mundoX;
    protected double mundoY;
    private boolean posicionInicializada;

    protected void establecerPosicionMundo(double x, double y)
    {
        mundoX = x;
        mundoY = y;
        posicionInicializada = true;
        actualizarPosicionPantalla();
    }

    protected void moverMundo(double dx, double dy)
    {
        mundoX += dx;
        mundoY += dy;
        actualizarPosicionPantalla();
    }

    public void actualizarPosicionPantalla()
    {
        if (getWorld() == null)
        {
            return;
        }

        JuegoWorld mundo = (JuegoWorld)getWorld();
        Camara camara = mundo.getCamara();

        int pantallaX = (int)Math.round(mundoX - camara.getX());
        int pantallaY = (int)Math.round(mundoY - camara.getY());

        setLocation(pantallaX, pantallaY);
    }

    public double getMundoX()
    {
        return mundoX;
    }

    public double getMundoY()
    {
        return mundoY;
    }

    @Override
    protected void addedToWorld(World world)
    {
        if (!posicionInicializada)
        {
            mundoX = getX();
            mundoY = getY();
            posicionInicializada = true;
        }

        actualizarPosicionPantalla();
    }
}
