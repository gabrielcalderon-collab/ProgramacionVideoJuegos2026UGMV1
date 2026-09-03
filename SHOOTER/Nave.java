import greenfoot.*;

public class Nave extends Actor {

    private EstrategiaDisparo estrategia = new DisparoSimple();
    private PoolDeBalas pool;
    private int recarga = 0;

    public Nave(PoolDeBalas pool) {

        this.pool = pool;

        // Toma la imagen asignada manualmente a la Nave
        GreenfootImage img = getImage();

        // Cambia el tamaño de la imagen
        img.scale(120, 60);

        setImage(img);
    }

    public void act() {

        mover();
        cambiarArma();
        disparar();
    }

    private void mover() {

        // Mover hacia arriba
        if (Greenfoot.isKeyDown("up") && getY() > 25) {

            setLocation(
                getX(),
                getY() - 4
            );
        }

        // Mover hacia abajo
        if (
            Greenfoot.isKeyDown("down")
            && getY() < getWorld().getHeight() - 25
        ) {

            setLocation(
                getX(),
                getY() + 4
            );
        }
    }

    private void cambiarArma() {

        // Tecla 1 = disparo simple
        if (Greenfoot.isKeyDown("1")) {

            estrategia = new DisparoSimple();
        }

        // Tecla 2 = disparo triple
        if (Greenfoot.isKeyDown("2")) {

            estrategia = new DisparoTriple();
        }
    }

    private void disparar() {

        // Reduce el tiempo de recarga
        if (recarga > 0) {

            recarga--;
        }

        // Disparar con espacio
        if (
            Greenfoot.isKeyDown("space")
            && recarga == 0
        ) {

            estrategia.disparar(this, pool);

            // Evita disparar una bala cada fotograma
            recarga = 15;
        }
    }
}