import greenfoot.*;

public class Bala extends Actor
{
    private int velocidad;
    private int direccion;
    private int dano;

    public Bala(TipoBala tipo, int direccion)
    {
        this.direccion = direccion;

        GreenfootImage imagen;
        
        GreenfootImage referenciaMedia =
            new GreenfootImage("bullet_medium.png");

        int anchoMedio =
            referenciaMedia.getWidth() * 2;

        int altoMedio =
            referenciaMedia.getHeight() * 2;

        if (tipo == TipoBala.PEQUENA)
        {
            imagen = new GreenfootImage("bullet_small.png");


            imagen.scale(
                anchoMedio / 2,
                altoMedio / 2
            );

            velocidad = 10;
            dano = 5;
        }

        else if (tipo == TipoBala.MEDIA)
        {
            imagen = new GreenfootImage("bullet_medium.png");

            imagen.scale(
                anchoMedio,
                altoMedio
            );

            velocidad = 9;
            dano = 10;
        }

        else
        {
            imagen = new GreenfootImage("bullet_charge.png");

            imagen.scale(
            (int)(anchoMedio * 1.5),
            (int)(altoMedio * 1.5)
            );

            velocidad = 8;
            dano =20;
        }

        if (direccion < 0)
        {
            imagen.mirrorHorizontally();
        }

        setImage(imagen);
    }

    public void act()
    {
        mover();
        comprobarBorde();
    }

    private void mover()
    {
        setLocation(
            getX() + velocidad * direccion,
            getY()
        );
    }

    private void comprobarBorde()
    {
        if (isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }

    public int getDano()
    {
        return dano;
    }
}