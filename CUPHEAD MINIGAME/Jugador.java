import greenfoot.*;
import java.util.List;

public class Jugador extends Actor
{
    private enum Estado
    {
        QUIETO,
        CAMINANDO_DERECHA,
        CAMINANDO_IZQUIERDA,
        SALTANDO,
        CAYENDO
    }

    private Estado estado = Estado.QUIETO;

    private GreenfootImage imagenQuieto;
    private GreenfootImage[] caminar;
    private GreenfootImage[] imagenesSalto;

    private GreenfootImage imagenDisparoQuieto;
    private GreenfootImage[] caminarDisparando;

    private int frameActual = 0;
    private int contadorAnimacion = 0;
    
    private boolean mirandoIzquierda = false;

    private boolean saltando = false;
    private int tiempoSalto = 0;
    private int yInicial;

    private double velocidadInicial = 12.0;
    private double velocidadSalto = 12.0;
    private double gravedad = 0.7;
    private int vida = 3;
    private boolean invulnerable = false;
    private int tiempoInvulnerable = 0;

    private int duracionInvulnerabilidad = 90;

    private boolean cargandoDisparo = false;
    private int tiempoCarga = 0;

    private int cargaMedia = 66;
    private int cargaCompleta = 126;
    private int cargaMaxima = 180;

    private boolean teclaDisparoAnterior = false;

    private boolean mostrandoDisparo = false;
    private int tiempoSpriteDisparo = 0;

    private IndicadorCarga indicadorCarga;
    
    
    public Jugador()
    {
        cargarImagenes();
    }

    private void escalarAlDoble(GreenfootImage imagen)
    {
        imagen.scale(
            imagen.getWidth() * 2,
            imagen.getHeight() * 2
        );
    }

    private void cargarImagenes()
    {
        imagenQuieto = new GreenfootImage("idle.png");
        escalarAlDoble(imagenQuieto);

        caminar = new GreenfootImage[4];

        caminar[0] = new GreenfootImage("walk1.png");
        caminar[1] = new GreenfootImage("walk2.png");
        caminar[2] = new GreenfootImage("walk3.png");
        caminar[3] = new GreenfootImage("walk4.png");

        for (int i = 0; i < caminar.length; i++)
        {
            escalarAlDoble(caminar[i]);
        }

        imagenesSalto = new GreenfootImage[2];

        imagenesSalto[0] = new GreenfootImage("jump1.png");
        imagenesSalto[1] = new GreenfootImage("jump2.png");

        for (int i = 0; i < imagenesSalto.length; i++)
        {
            escalarAlDoble(imagenesSalto[i]);
        }

        imagenDisparoQuieto =
            new GreenfootImage("shoot_idle.png");

        escalarAlDoble(imagenDisparoQuieto);

        caminarDisparando = new GreenfootImage[4];

        caminarDisparando[0] =
            new GreenfootImage("shoot_walk1.png");

        caminarDisparando[1] =
            new GreenfootImage("shoot_walk2.png");

        caminarDisparando[2] =
            new GreenfootImage("shoot_walk3.png");

        caminarDisparando[3] =
            new GreenfootImage("shoot_walk4.png");

        for (int i = 0; i < caminarDisparando.length; i++)
        {
            escalarAlDoble(caminarDisparando[i]);
        }

        setImage(imagenQuieto);
    }

    public void act()
    {
        controlarMovimientoHorizontal();
        controlarSalto();
        controlarDisparo();

        comprobarCaida();
        actualizarSalto();

        actualizarTiempoDisparo();
        actualizarAnimacion();
        
        comprobarDano();
        actualizarInvulnerabilidad();
    }

    private void controlarMovimientoHorizontal()
    {
        if (Greenfoot.isKeyDown("right"))
        {
            setLocation(
                getX() + 4,
                getY()
            );

            mirandoIzquierda = false;

            if (!saltando)
            {
                estado = Estado.CAMINANDO_DERECHA;
            }
        }
        else if (Greenfoot.isKeyDown("left"))
        {
            setLocation(
                getX() - 4,
                getY()
            );

            mirandoIzquierda = true;

            if (!saltando)
            {
                estado = Estado.CAMINANDO_IZQUIERDA;
            }
        }
        else
        {
            if (!saltando)
            {
                estado = Estado.QUIETO;
            }
        }
    }

    private void controlarSalto()
    {
        if (Greenfoot.isKeyDown("space") && !saltando)
        {
            iniciarSalto();
        }
    }

    private void iniciarSalto()
    {
        saltando = true;

        tiempoSalto = 0;
        yInicial = getY();

        velocidadInicial = velocidadSalto;

        estado = Estado.SALTANDO;
    }

    private void actualizarSalto()
    {
        if (saltando)
        {
            tiempoSalto++;

            int yAnterior = getY();

            double yCalculada =
                yInicial
                - velocidadInicial * tiempoSalto
                + 0.5 * gravedad
                * tiempoSalto * tiempoSalto;

            int nuevaY = (int)yCalculada;

            if (
                velocidadInicial > 0 &&
                tiempoSalto < velocidadInicial / gravedad
            )
            {
                estado = Estado.SALTANDO;
            }
            else
            {
                estado = Estado.CAYENDO;
            }

            setLocation(
                getX(),
                nuevaY
            );

            if (estado == Estado.CAYENDO)
            {
                comprobarAterrizaje(
                    yAnterior,
                    nuevaY
                );
            }
        }
    }
    
    private void comprobarAterrizaje(
        int yAnterior,
        int nuevaY
    )
    {
        List<Plataforma> plataformas =
            getWorld().getObjects(
                Plataforma.class
            );

        int mitadJugador =
            getImage().getHeight() / 2;

        int piesAntes =
            yAnterior + mitadJugador;

        int piesAhora =
            nuevaY + mitadJugador;

        for (Plataforma plataforma : plataformas)
        {
            int mitadPlataforma =
                plataforma.getImage().getHeight() / 2;

            int parteSuperior =
                plataforma.getY()
                - mitadPlataforma;

            int mitadAnchoPlataforma =
                plataforma.getImage().getWidth() / 2;

            int mitadAnchoJugador =
                getImage().getWidth() / 2;

            int izquierdaJugador =
                getX()
                - mitadAnchoJugador;

            int derechaJugador =
                getX()
                + mitadAnchoJugador;

            int izquierdaPlataforma =
                plataforma.getX()
                - mitadAnchoPlataforma;

            int derechaPlataforma =
                plataforma.getX()
                + mitadAnchoPlataforma;

            boolean coincideHorizontalmente =
                derechaJugador > izquierdaPlataforma &&
                izquierdaJugador < derechaPlataforma;

            boolean cruzoLaSuperficie =
                piesAntes <= parteSuperior &&
                piesAhora >= parteSuperior;

            if (
                coincideHorizontalmente &&
                cruzoLaSuperficie
            )
            {
                int nuevaPosicionY =
                    parteSuperior
                    - mitadJugador;

                setLocation(
                    getX(),
                    nuevaPosicionY
                );

                saltando = false;
                tiempoSalto = 0;
                velocidadInicial = 0;

                estado = Estado.QUIETO;

                return;
            }
        }
    }

    private void comprobarCaida()
    {
        if (!saltando)
        {
            int mitadJugador =
                getImage().getHeight() / 2;

            Actor plataformaDebajo =
                getOneObjectAtOffset(
                    0,
                    mitadJugador + 2,
                    Plataforma.class
                );

            if (plataformaDebajo == null)
            {
                saltando = true;

                tiempoSalto = 0;
                yInicial = getY();

                velocidadInicial = 0;

                estado = Estado.CAYENDO;
            }
        }
    }


    private void actualizarAnimacion()
    {
        if (estado == Estado.QUIETO)
        {
            if (
                mostrandoDisparo ||
                cargandoDisparo
            )
            {
                GreenfootImage imagen =
                    new GreenfootImage(
                        imagenDisparoQuieto
                    );

                if (mirandoIzquierda)
                {
                    imagen.mirrorHorizontally();
                }

                setImage(imagen);
            }
            else
            {
                GreenfootImage imagen =
                    new GreenfootImage(
                        imagenQuieto
                    );

                if (mirandoIzquierda)
                {
                    imagen.mirrorHorizontally();
                }

                setImage(imagen);
            }
        }

        else if (
            estado == Estado.CAMINANDO_DERECHA
        )
        {
            if (
                mostrandoDisparo ||
                cargandoDisparo
            )
            {
                animarCaminataDisparando(false);
            }
            else
            {
                animarCaminata(false);
            }
        }

        else if (
            estado == Estado.CAMINANDO_IZQUIERDA
        )
        {
            if (
                mostrandoDisparo ||
                cargandoDisparo
            )
            {
                animarCaminataDisparando(true);
            }
            else
            {
                animarCaminata(true);
            }
        }

        else if (
            estado == Estado.SALTANDO ||
            estado == Estado.CAYENDO
        )
        {
            animarSalto();
        }
    }

    private void animarCaminata(boolean izquierda)
    {
        contadorAnimacion++;

        if (contadorAnimacion >= 6)
        {
            frameActual++;

            if (frameActual >= caminar.length)
            {
                frameActual = 0;
            }

            GreenfootImage imagen =
                new GreenfootImage(
                    caminar[frameActual]
                );

            if (izquierda)
            {
                imagen.mirrorHorizontally();
            }

            setImage(imagen);

            contadorAnimacion = 0;
        }
    }

    private void animarSalto()
    {
    int frame;

    if (estado == Estado.SALTANDO)
    {
        frame = 0; 
    }
    else
    {
        frame = 1; 
    }

    GreenfootImage imagen =
        new GreenfootImage(imagenesSalto[frame]);

    if (mirandoIzquierda)
    {
        imagen.mirrorHorizontally();
    }

    setImage(imagen);
    }
    
    private void controlarDisparo()
    {
        boolean teclaDisparo =
            Greenfoot.isKeyDown("z");

        if (
            teclaDisparo &&
            !teclaDisparoAnterior
        )
        {
            cargandoDisparo = true;
            tiempoCarga = 0;

            crearIndicadorCarga();
        }

        if (
            teclaDisparo &&
            cargandoDisparo
        )
        {
            if (tiempoCarga < cargaMaxima)
            {
                tiempoCarga++;
            }

            actualizarIndicadorCarga();
        }

        if (
            !teclaDisparo &&
            teclaDisparoAnterior &&
            cargandoDisparo
        )
        {
            dispararSegunCarga();

            cargandoDisparo = false;
            tiempoCarga = 0;

            eliminarIndicadorCarga();
        }

        teclaDisparoAnterior =
            teclaDisparo;
    }

    private void dispararBala(TipoBala tipo)
    {
        int direccion;

        if (mirandoIzquierda)
        {
            direccion = -1;
        }
        else
        {
            direccion = 1;
        }

        Bala bala =
            new Bala(
                tipo,
                direccion
            );

        int offsetX =
            32 * direccion;

        int offsetY = 0;

        if (tipo == TipoBala.CARGADA)
        {
            offsetY = -8;
        }

        getWorld().addObject(
            bala,
            getX() + offsetX,
            getY() + offsetY
        );
    }

    private void dispararSegunCarga()
    {
        if (tiempoCarga >= cargaCompleta)
        {
            dispararBala(
                TipoBala.CARGADA
            );
        }
        else if (tiempoCarga >= cargaMedia)
        {
            dispararBala(
                TipoBala.MEDIA
            );
        }
        else
        {
            dispararBala(
                TipoBala.PEQUENA
            );
        }

        mostrandoDisparo = true;
        tiempoSpriteDisparo = 8;
    }

    private void actualizarTiempoDisparo()
    {
        if (tiempoSpriteDisparo > 0)
        {
            tiempoSpriteDisparo--;
        }
        else
        {
            mostrandoDisparo = false;
        }
    }

    private void animarCaminataDisparando(
        boolean izquierda
    )
    {
        contadorAnimacion++;

        if (contadorAnimacion >= 6)
        {
            frameActual++;

            if (
                frameActual >=
                caminarDisparando.length
            )
            {
                frameActual = 0;
            }

            GreenfootImage imagen =
                new GreenfootImage(
                    caminarDisparando[frameActual]
                );

            if (izquierda)
            {
                imagen.mirrorHorizontally();
            }

            setImage(imagen);

            contadorAnimacion = 0;
        }
    }

    private void crearIndicadorCarga()
    {
        if (indicadorCarga == null)
        {
            indicadorCarga =
                new IndicadorCarga();

            getWorld().addObject(
                indicadorCarga,
                getX(),
                getY()
            );
        }
    }

    private void actualizarIndicadorCarga()
    {
        if (indicadorCarga != null)
        {
            int direccion =
                mirandoIzquierda ? -1 : 1;

            int offsetX =
                30 * direccion;

            int offsetY = 0;

            indicadorCarga.setLocation(
                getX() + offsetX,
                getY() + offsetY
            );

            indicadorCarga.actualizarNivel(
                tiempoCarga,
                cargaMedia,
                cargaCompleta
            );
        }
    }

    private void eliminarIndicadorCarga()
    {
        if (indicadorCarga != null)
        {
            getWorld().removeObject(
                indicadorCarga
            );

            indicadorCarga = null;
        }
    }
    private void comprobarDano()
    {
    if (!invulnerable)
    {
        Pincho pincho =
            (Pincho)getOneIntersectingObject(
                Pincho.class
            );

        if (pincho != null)
        {
            recibirDano();
        }
    }
    }   
    private void recibirDano()
    {
    vida--;

    invulnerable = true;
    tiempoInvulnerable =
        duracionInvulnerabilidad;

    List<HUDVida> huds =
        getWorld().getObjects(HUDVida.class);

    if (!huds.isEmpty())
    {
        huds.get(0).actualizarVida(vida);
    }

    if (vida <= 0)
    {
        morir();
    }
    }
    private void actualizarInvulnerabilidad()
    {
    if (invulnerable)
    {
        tiempoInvulnerable--;

        if (tiempoInvulnerable <= 0)
        {
            invulnerable = false;
        }
    }
    }
    private void morir()
    {
    System.out.println("GAME OVER");

    Greenfoot.stop();
    }
    public int getVida()
    {
    return vida;
    }
}