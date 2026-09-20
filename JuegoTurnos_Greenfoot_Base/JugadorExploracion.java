import greenfoot.*;

public class JugadorExploracion extends Actor {
    // Arreglos para guardar las animaciones de cada dirección
    private GreenfootImage[] caminarAbajo = new GreenfootImage[4];
    private GreenfootImage[] caminarArriba = new GreenfootImage[4];
    private GreenfootImage[] caminarIzquierda = new GreenfootImage[4];
    private GreenfootImage[] caminarDerecha = new GreenfootImage[4];
    
    // Variables para controlar la animación actual
    private GreenfootImage[] animacionActual;
    private int frameActual = 0;
    private int temporizadorAnimacion = 0;

    public JugadorExploracion() {
        // Cargar las imágenes desde la carpeta del proyecto
        for (int i = 0; i < 4; i++) {
            caminarAbajo[i] = new GreenfootImage("abajo" + i + ".png");
            caminarArriba[i] = new GreenfootImage("arriba" + i + ".png");
            caminarIzquierda[i] = new GreenfootImage("izq" + i + ".png");
            caminarDerecha[i] = new GreenfootImage("der" + i + ".png");
        }
        
        // Estado inicial del jugador (mirando hacia abajo, quieto)
        animacionActual = caminarAbajo;
        setImage(animacionActual[0]);
    }

    public void act() {
        moverYAnimar();
        revisarEncuentro();
    }
    
    private void moverYAnimar() {
        int velocidad = 4;
        boolean seMovio = false;
        
        // Detección de movimiento y asignación de la animación correspondiente
        if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - velocidad);
            animacionActual = caminarArriba;
            seMovio = true;
        } 
        else if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + velocidad);
            animacionActual = caminarAbajo;
            seMovio = true;
        } 
        else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            setLocation(getX() - velocidad, getY());
            animacionActual = caminarIzquierda;
            seMovio = true;
        } 
        else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            setLocation(getX() + velocidad, getY());
            animacionActual = caminarDerecha;
            seMovio = true;
        }

        // Si el jugador está presionando una tecla, avanza los fotogramas
        if (seMovio) {
            temporizadorAnimacion++;
            // Cambia de fotograma cada 6 ciclos de ejecución (puedes ajustar este número para cambiar la velocidad de la animación)
            if (temporizadorAnimacion % 6 == 0) { 
                frameActual = (frameActual + 1) % 4; // Cicla entre 0, 1, 2, 3
                setImage(animacionActual[frameActual]);
            }
        } else {
            // Si no se mueve, vuelve al fotograma 0 (postura de pie)
            frameActual = 0;
            setImage(animacionActual[0]);
        }
    }
    
    private void revisarEncuentro() {
        if (isTouching(EnemigoMapa.class)) {
            World mapaActual = getWorld();
            Greenfoot.setWorld(new JuegoTurnos(mapaActual));
            removeTouching(EnemigoMapa.class); 
        }
    }
}