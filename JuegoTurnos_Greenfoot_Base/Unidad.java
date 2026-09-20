import greenfoot.*;

public abstract class Unidad extends Actor
{
    protected int hp;
    protected int hpMaximo;

    public Unidad(int hpMaximo)
    {
        this.hpMaximo = hpMaximo;
        this.hp = hpMaximo;
    }

    public void recibirDanio(int cantidad)
    {
        hp -= cantidad;
        if (hp < 0) hp = 0;
    }

    public boolean estaViva()
    {
        return hp > 0;
    }

    public int getHP() { return hp; }
    public int getHPMaximo() { return hpMaximo; }

    public double proporcionVida()
    {
        return (double)hp / hpMaximo;
    }
}
