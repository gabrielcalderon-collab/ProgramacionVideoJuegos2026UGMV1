public abstract class Soldado extends ActorMundo
{
    protected int vida;
    protected int velocidad;

    protected Soldado(int vida, int velocidad)
    {
        this.vida = vida;
        this.velocidad = velocidad;
    }

    public void recibirDanio(int cantidad)
    {
        if (cantidad <= 0 || vida <= 0)
        {
            return;
        }

        vida -= cantidad;

        if (vida <= 0)
        {
            vida = 0;
            morir();
        }
    }

    public int getVida()
    {
        return vida;
    }

    protected void morir()
    {
        if (getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
}
