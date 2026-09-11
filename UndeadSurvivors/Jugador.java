import greenfoot.*;

public class Jugador extends Soldado
{
    private static final int VIDA_MAXIMA = 100;
    private static final int VELOCIDAD = 4;
    private static final int EXP_INICIAL = 50;
    private static final double MULTIPLICADOR_EXP = 1.5;
    private static final int MARGEN_MOVIMIENTO = 15;
    private static final int DURACION_ANIMACION = 10;

    private static final int NIVEL_MAXIMO_DANIO_GLOBAL = 5;
    private static final double BONIFICACION_DANIO_GLOBAL = 0.05;

    private static final int NIVEL_MAXIMO_VIDA = 5;

    private SistemaArmas sistemaArmas;

    private int nivelVida = 0;

    private int nivel = 1;
    private int expActual;
    private int expSiguienteNivel = EXP_INICIAL;

    private int nivelDanioGlobal = 0;

    private int frameActual;
    private int contadorAnimacion;
    private boolean mirandoIzquierda;

    public Jugador()
    {
        super(VIDA_MAXIMA, VELOCIDAD);

        sistemaArmas = new SistemaArmas(this);

        setImage(
            new GreenfootImage(
                FabricaImagenes.JUGADOR[0]
            )
        );
    }

    public void act()
    {
        if (getWorld() == null)
        {
            return;
        }

        if (getWorld() instanceof JuegoWorld)
        {
            JuegoWorld mundo =
                (JuegoWorld)getWorld();

            if (mundo.estaPausado())
            {
                return;
            }
        }

        mover();

        sistemaArmas.actualizar();

        animar();
    }





    private void mover()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("w"))
        {
            dy -= 1;
        }

        if (Greenfoot.isKeyDown("s"))
        {
            dy += 1;
        }

        if (Greenfoot.isKeyDown("a"))
        {
            dx -= 1;
            mirandoIzquierda = true;
        }

        if (Greenfoot.isKeyDown("d"))
        {
            dx += 1;
            mirandoIzquierda = false;
        }

        if (dx == 0 && dy == 0)
        {
            return;
        }

        double longitud =
            Math.hypot(dx, dy);

        double movimientoX =
            velocidad * dx / longitud;

        double movimientoY =
            velocidad * dy / longitud;

        moverMundo(
            movimientoX,
            movimientoY
        );

        limitarPosicion();
    }

    private void limitarPosicion()
    {
        double mitadAncho =
            getImage().getWidth() / 2.0;

        double mitadAlto =
            getImage().getHeight() / 2.0;

        double minimoX =
            mitadAncho + MARGEN_MOVIMIENTO;

        double maximoX =
            getWorldAncho()
            - mitadAncho
            - MARGEN_MOVIMIENTO;

        double minimoY =
            mitadAlto + MARGEN_MOVIMIENTO;

        double maximoY =
            getWorldAlto()
            - mitadAlto
            - MARGEN_MOVIMIENTO;

        double x =
            Math.max(
                minimoX,
                Math.min(mundoX, maximoX)
            );

        double y =
            Math.max(
                minimoY,
                Math.min(mundoY, maximoY)
            );

        establecerPosicionMundo(
            x,
            y
        );
    }

    private double getWorldAncho()
    {
        if (getWorld() instanceof JuegoWorld)
        {
            return ((JuegoWorld)getWorld())
                .getAnchoMapa();
        }

        return getWorld().getWidth();
    }

    private double getWorldAlto()
    {
        if (getWorld() instanceof JuegoWorld)
        {
            return ((JuegoWorld)getWorld())
                .getAltoMapa();
        }

        return getWorld().getHeight();
    }





    public void ganarExp(int cantidad)
    {
        if (cantidad <= 0)
        {
            return;
        }

        expActual += cantidad;

        if (expActual >= expSiguienteNivel)
        {
            subirDeNivel();
        }
    }

    private void subirDeNivel()
    {
        nivel++;

        expActual -= expSiguienteNivel;

        expSiguienteNivel = Math.max(
            expSiguienteNivel + 1,
            (int)(
                expSiguienteNivel
                * MULTIPLICADOR_EXP
            )
        );

        if (getWorld() instanceof JuegoWorld)
        {
            JuegoWorld mundo =
                (JuegoWorld)getWorld();

            mundo.mostrarMenuNivel();
        }
    }

    public int getNivel()
    {
        return nivel;
    }





    public int getDanioDisparo()
    {
        return sistemaArmas.calcularDanio(
            sistemaArmas
                .getCuchillo()
                .getDanio()
        );
    }





    public int getExperienciaActual()
    {
        return expActual;
    }

    public int getExperienciaSiguienteNivel()
    {
        return expSiguienteNivel;
    }





    public int getVidaMaxima()
    {
        return VIDA_MAXIMA;
    }





    public int getCadenciaDisparo()
    {
        return sistemaArmas
            .getCuchillo()
            .getCadencia();
    }

    public SistemaArmas getSistemaArmas()
    {
        return sistemaArmas;
    }





    public void mejorarVelocidad()
    {
        if (puedeMejorarVelocidad())
        {
            velocidad += 1;
        }
    }

    public boolean puedeMejorarVelocidad()
    {
        return velocidad < 9;
    }





    public boolean puedeMejorarVida()
    {
        return nivelVida < NIVEL_MAXIMO_VIDA;
    }

    public void mejorarVida()
    {
        if (!puedeMejorarVida())
        {
            return;
        }

        nivelVida++;

        vida = Math.min(
            VIDA_MAXIMA,
            vida + 25
        );
    }





    public void mejorarDanioGlobal()
    {
        if (nivelDanioGlobal <
            NIVEL_MAXIMO_DANIO_GLOBAL)
        {
            nivelDanioGlobal++;
        }
    }

    public int getNivelDanioGlobal()
    {
        return nivelDanioGlobal;
    }

    public double getMultiplicadorDanioGlobal()
    {
        return 1.0
            + nivelDanioGlobal
            * BONIFICACION_DANIO_GLOBAL;
    }

    public boolean puedeMejorarDanioGlobal()
    {
        return nivelDanioGlobal <
            NIVEL_MAXIMO_DANIO_GLOBAL;
    }





    public void curar(int cantidad)
    {
        if (cantidad > 0)
        {
            vida = Math.min(
                VIDA_MAXIMA,
                vida + cantidad
            );
        }
    }





    protected void morir()
    {
        World mundo = getWorld();

        if (mundo instanceof JuegoWorld)
        {
            ((JuegoWorld)mundo).gameOver();
        }

        if (mundo != null)
        {
            mundo.removeObject(this);
        }
    }





    private void animar()
    {
        contadorAnimacion++;

        if (contadorAnimacion < DURACION_ANIMACION)
        {
            return;
        }

        frameActual =
            (frameActual == 0)
            ? 1
            : 0;

        GreenfootImage imagen =
            new GreenfootImage(
                FabricaImagenes.JUGADOR[
                    frameActual
                ]
            );

        if (mirandoIzquierda)
        {
            imagen.mirrorHorizontally();
        }

        setImage(imagen);

        contadorAnimacion = 0;
    }
}
