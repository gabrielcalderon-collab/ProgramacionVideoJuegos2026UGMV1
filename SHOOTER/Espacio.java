import greenfoot.*;

public class Espacio extends World {

    private PoolDeBalas pool;
    private int contadorAparicion = 0;

    public Espacio() {

        super(600, 400, 1);

        GameManager.getInstancia().reiniciar();

        pool = new PoolDeBalas(20);

        addObject(
            new Nave(pool),
            60,
            getHeight() / 2
        );

        addObject(
            new Marcador(),
            80,
            20
        );
    }

    public void act() {

        contadorAparicion++;

        if (contadorAparicion >= 60) {

            addObject(
                new Enemigo(),
                getWidth() - 20,
                Greenfoot.getRandomNumber(getHeight())
            );

            contadorAparicion = 0;
        }
    }
}