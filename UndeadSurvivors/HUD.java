import greenfoot.*;

public class HUD extends Actor
{
    private static final int ANCHO = 1074;
    private static final int ALTO = 1074;

    private static final int ALTURA_BARRA_SUPERIOR = 105;

    private static final int TAMANO_TEXTO = 20;
    private static final int TAMANO_TITULO = 24;
    private static final int TAMANO_PEQUENO = 16;

    private static final int ANCHO_EXP = 430;
    private static final int ALTO_EXP = 18;

    private static final int ANCHO_TARJETA_ARMA = 85;
    private static final int ALTO_TARJETA_ARMA = 70;

    private static final int INTERVALO_ACTUALIZACION = 2;

    private final JuegoWorld mundo;
    private final Jugador jugador;
    private final GestorOleadas gestor;

    private int contadorActualizacion;

    public HUD(
        JuegoWorld mundo,
        Jugador jugador,
        GestorOleadas gestor
    )
    {
        this.mundo = mundo;
        this.jugador = jugador;
        this.gestor = gestor;

        actualizarImagen();
    }

    public void act()
    {
        contadorActualizacion++;

        if (contadorActualizacion >=
            INTERVALO_ACTUALIZACION)
        {
            actualizarImagen();
            contadorActualizacion = 0;
        }
    }

    private void actualizarImagen()
    {
        GreenfootImage imagen =
            new GreenfootImage(
                ANCHO,
                ALTO
            );





        imagen.setColor(
            new Color(
                0,
                0,
                0,
                190
            )
        );

        imagen.fillRect(
            0,
            0,
            ANCHO,
            ALTURA_BARRA_SUPERIOR
        );





        dibujarArma(
            imagen,
            20,
            17,
            "CUCHILLO",
            jugador.getSistemaArmas()
                .getCuchillo()
                .getNivel(),
            0
        );

        if (jugador.getSistemaArmas().tieneBomba())
        {
            dibujarArma(
                imagen,
                115,
                17,
                "BOMBA",
                jugador.getSistemaArmas()
                    .getNivelBomba(),
                1
            );
        }

        if (jugador.getSistemaArmas().tieneLaser())
        {
            dibujarArma(
                imagen,
                210,
                17,
                "LASER",
                jugador.getSistemaArmas()
                    .getNivelLaser(),
                2
            );
        }





        GreenfootImage nivel =
            new GreenfootImage(
                "NIVEL " + jugador.getNivel(),
                TAMANO_TITULO,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        int centroXPantalla =
            ANCHO / 2;

        imagen.drawImage(
            nivel,
            centroXPantalla
            - nivel.getWidth() / 2,
            8
        );





        int xExp =
            centroXPantalla
            - ANCHO_EXP / 2;

        int yExp = 42;

        dibujarBarraExperiencia(
            imagen,
            xExp,
            yExp
        );





        GreenfootImage oleada =
            new GreenfootImage(
                "OLEADA " + gestor.getOleada(),
                TAMANO_TITULO,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            oleada,
            ANCHO
            - oleada.getWidth()
            - 25,
            12
        );

        GreenfootImage eliminados =
            new GreenfootImage(
                "ELIMINADOS "
                + mundo.getEnemigosEliminados(),
                TAMANO_TEXTO,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            eliminados,
            ANCHO
            - eliminados.getWidth()
            - 25,
            48
        );





        dibujarBarraVidaJugador(imagen);

        setImage(imagen);
    }





    private void dibujarBarraExperiencia(
        GreenfootImage imagen,
        int x,
        int y
    )
    {
        int experienciaActual =
            jugador.getExperienciaActual();

        int experienciaSiguiente =
            jugador.getExperienciaSiguienteNivel();

        double porcentaje =
            (double)experienciaActual
            / experienciaSiguiente;

        porcentaje =
            Math.max(
                0.0,
                Math.min(
                    porcentaje,
                    1.0
                )
            );


        imagen.setColor(
            new Color(
                35,
                35,
                45,
                255
            )
        );

        imagen.fillRect(
            x,
            y,
            ANCHO_EXP,
            ALTO_EXP
        );


        imagen.setColor(
            new Color(
                70,
                180,
                255,
                255
            )
        );

        imagen.fillRect(
            x,
            y,
            (int)(
                ANCHO_EXP
                * porcentaje
            ),
            ALTO_EXP
        );


        imagen.setColor(Color.WHITE);

        imagen.drawRect(
            x,
            y,
            ANCHO_EXP - 1,
            ALTO_EXP - 1
        );


        String texto =
            experienciaActual
            + " / "
            + experienciaSiguiente;

        GreenfootImage exp =
            new GreenfootImage(
                texto,
                TAMANO_PEQUENO,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            exp,
            x
            + (
                ANCHO_EXP
                - exp.getWidth()
            ) / 2,
            y
            + ALTO_EXP
            + 3
        );
    }





    private void dibujarArma(
        GreenfootImage imagen,
        int x,
        int y,
        String nombre,
        int nivel,
        int tipo
    )
    {

        imagen.setColor(
            new Color(
                35,
                35,
                50,
                235
            )
        );

        imagen.fillRect(
            x,
            y,
            ANCHO_TARJETA_ARMA,
            ALTO_TARJETA_ARMA
        );


        imagen.setColor(Color.WHITE);

        imagen.drawRect(
            x,
            y,
            ANCHO_TARJETA_ARMA - 1,
            ALTO_TARJETA_ARMA - 1
        );


        dibujarIconoArma(
            imagen,
            x + 42,
            y + 28,
            tipo
        );


        GreenfootImage nivelImagen =
            new GreenfootImage(
                "Lv." + nivel,
                TAMANO_PEQUENO,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            nivelImagen,
            x
            + (
                ANCHO_TARJETA_ARMA
                - nivelImagen.getWidth()
            ) / 2,
            y + 49
        );
    }

    private void dibujarIconoArma(
        GreenfootImage imagen,
        int centroX,
        int centroY,
        int tipo
    )
    {
        imagen.setColor(Color.WHITE);


        if (tipo == 0)
        {
            imagen.fillRect(
                centroX - 22,
                centroY - 3,
                28,
                6
            );

            imagen.setColor(
                new Color(
                    150,
                    150,
                    150
                )
            );

            imagen.fillRect(
                centroX - 3,
                centroY - 3,
                9,
                6
            );

            imagen.setColor(
                new Color(
                    80,
                    50,
                    30
                )
            );

            imagen.fillRect(
                centroX + 6,
                centroY - 4,
                12,
                8
            );

            return;
        }


        if (tipo == 1)
        {
            imagen.setColor(
                Color.BLACK
            );

            imagen.fillOval(
                centroX - 12,
                centroY - 12,
                24,
                24
            );

            imagen.setColor(
                Color.RED
            );

            imagen.fillOval(
                centroX - 4,
                centroY - 9,
                7,
                7
            );

            imagen.setColor(
                Color.WHITE
            );

            imagen.drawLine(
                centroX + 4,
                centroY - 10,
                centroX + 10,
                centroY - 18
            );

            return;
        }


        if (tipo == 2)
        {
            imagen.setColor(
                Color.WHITE
            );

            imagen.fillRect(
                centroX - 24,
                centroY - 4,
                48,
                8
            );

            imagen.setColor(
                Color.YELLOW
            );

            imagen.fillRect(
                centroX - 20,
                centroY - 2,
                40,
                4
            );
        }
    }





    private void dibujarBarraVidaJugador(
        GreenfootImage imagen
    )
    {
        int jugadorX =
            jugador.getX();

        int jugadorY =
            jugador.getY();

        int anchoBarra = 80;
        int altoBarra = 8;

        int x =
            jugadorX
            - anchoBarra / 2;

        int y =
            jugadorY
            + jugador.getImage().getHeight()
            / 2
            + 8;

        if (x < 0 ||
            x + anchoBarra >= ANCHO ||
            y < 0 ||
            y + altoBarra >= ALTO)
        {
            return;
        }

        double porcentaje =
            (double)jugador.getVida()
            / jugador.getVidaMaxima();

        porcentaje =
            Math.max(
                0.0,
                Math.min(
                    porcentaje,
                    1.0
                )
            );


        imagen.setColor(
            new Color(
                40,
                40,
                40
            )
        );

        imagen.fillRect(
            x,
            y,
            anchoBarra,
            altoBarra
        );


        imagen.setColor(
            new Color(
                60,
                210,
                80
            )
        );

        imagen.fillRect(
            x,
            y,
            (int)(
                anchoBarra
                * porcentaje
            ),
            altoBarra
        );


        imagen.setColor(Color.WHITE);

        imagen.drawRect(
            x,
            y,
            anchoBarra - 1,
            altoBarra - 1
        );
    }
}
