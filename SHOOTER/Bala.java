import greenfoot.*;

public class Bala extends Actor {

    private boolean activa = false;

    public Bala() {

        // Toma la imagen asignada manualmente a la Bala
        GreenfootImage img = getImage();

        // Cambia el tamaño de la imagen
        img.scale(20, 20);

        setImage(img);
    }

    // Activa una bala del pool y la coloca en el mundo
    public void activar(World mundo, int x, int y, int angulo) {

        activa = true;

        setRotation(angulo);

        mundo.addObject(this, x, y);
    }

    public void act() {

        // Si la bala no está activa, no hace nada
        if (!activa) {
            return;
        }

        // Velocidad de la bala
        move(8);

        // Detectar colisión con enemigo
        Enemigo e =
            (Enemigo) getOneIntersectingObject(Enemigo.class);

        if (e != null) {

            e.destruir();

            desactivar();

            return;
        }

        // Si llega al borde, vuelve al pool
        if (isAtEdge()) {

            desactivar();
        }
    }

    public void desactivar() {

        activa = false;

        if (getWorld() != null) {

            getWorld().removeObject(this);
        }
    }

    public boolean estaActiva() {

        return activa;
    }
}