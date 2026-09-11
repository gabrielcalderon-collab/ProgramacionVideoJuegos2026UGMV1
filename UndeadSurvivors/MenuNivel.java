import greenfoot.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuNivel extends Actor
{
    private static final int CANTIDAD_OPCIONES = 3;
    private static final int ANCHO_MENU = 800;
    private static final int ALTO_MENU = 500;

    private final JuegoWorld mundo;

    private String[] opciones =
        new String[CANTIDAD_OPCIONES];

    private boolean procesandoEleccion;

    public MenuNivel(JuegoWorld mundo)
    {
        this.mundo = mundo;

        generarOpciones();
        actualizarImagen();
    }

    public void act()
    {
        if (procesandoEleccion)
        {
            return;
        }

        if (Greenfoot.isKeyDown("1"))
        {
            elegirOpcion(0);
        }
        else if (Greenfoot.isKeyDown("2"))
        {
            elegirOpcion(1);
        }
        else if (Greenfoot.isKeyDown("3"))
        {
            elegirOpcion(2);
        }
    }





    private void generarOpciones()
    {
        List<String> disponibles =
            new ArrayList<String>();

        Jugador jugador =
            mundo.getJugador();

        SistemaArmas armas =
            jugador.getSistemaArmas();


        if (jugador.puedeMejorarVelocidad())
        {
            disponibles.add("VELOCIDAD");
        }


        if (jugador.puedeMejorarVida())
        {
            disponibles.add("VIDA");
        }


        if (jugador.puedeMejorarDanioGlobal())
        {
            disponibles.add("DANIO_GLOBAL");
        }


        if (armas.getCuchillo().puedeMejorar())
        {
            disponibles.add("CUCHILLO");
        }


        if (!armas.tieneBomba())
        {
            disponibles.add("BOMBA_NUEVA");
        }
        else if (armas.puedeMejorarBomba())
        {
            disponibles.add("BOMBA");
        }


        if (!armas.tieneLaser())
        {
            disponibles.add("LASER_NUEVO");
        }
        else if (armas.puedeMejorarLaser())
        {
            disponibles.add("LASER");
        }

        Collections.shuffle(disponibles);

        for (int i = 0;
     i < CANTIDAD_OPCIONES;
     i++)
    {
    if (i < disponibles.size())
    {
        opciones[i] =
            disponibles.get(i);
    }
    else
    {
        opciones[i] =
            "SIN_MEJORA";
    }
    }
    }





    private void elegirOpcion(int indice)
    {
        if (indice < 0 ||
            indice >= CANTIDAD_OPCIONES)
        {
            return;
        }

        procesandoEleccion = true;

        aplicarMejora(
            opciones[indice]
        );

        mundo.removeObject(this);

        mundo.reanudarJuego();
    }





    private void aplicarMejora(
        String opcion
    )
    {
        Jugador jugador =
            mundo.getJugador();

        SistemaArmas armas =
            jugador.getSistemaArmas();

        if (opcion.equals("VELOCIDAD"))
        {
            jugador.mejorarVelocidad();
        }
        else if (opcion.equals("VIDA"))
        {
            jugador.mejorarVida();
        }
        else if (opcion.equals("CUCHILLO"))
        {
            armas.getCuchillo().mejorar();
        }
        else if (opcion.equals("DANIO_GLOBAL"))
        {
            jugador.mejorarDanioGlobal();
        }
        else if (opcion.equals("BOMBA_NUEVA"))
        {
            armas.desbloquearBomba();
        }
        else if (opcion.equals("LASER_NUEVO"))
        {
            armas.desbloquearLaser();
        }
        else if (opcion.equals("BOMBA"))
        {
            armas.mejorarBomba();
        }
        else if (opcion.equals("LASER"))
        {
            armas.mejorarLaser();
        }
    }





    private void actualizarImagen()
    {
        GreenfootImage imagen =
            new GreenfootImage(
                ANCHO_MENU,
                ALTO_MENU
            );

        imagen.setColor(
            new Color(
                10,
                10,
                20,
                235
            )
        );

        imagen.fill();

        imagen.setColor(Color.WHITE);

        imagen.drawRect(
            0,
            0,
            ANCHO_MENU - 1,
            ALTO_MENU - 1
        );

        imagen.setColor(Color.YELLOW);

        GreenfootImage titulo =
            new GreenfootImage(
                "LEVEL UP",
                40,
                Color.YELLOW,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            titulo,
            (ANCHO_MENU -
            titulo.getWidth()) / 2,
            35
        );

        GreenfootImage instruccion =
            new GreenfootImage(
                "Presiona 1, 2 o 3 para elegir",
                20,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            instruccion,
            (ANCHO_MENU -
            instruccion.getWidth()) / 2,
            95
        );

        for (int i = 0;
             i < CANTIDAD_OPCIONES;
             i++)
        {
            dibujarOpcion(
                imagen,
                i
            );
        }

        setImage(imagen);
    }





    private void dibujarOpcion(
        GreenfootImage imagen,
        int indice
    )
    {
        int anchoOpcion = 220;
        int altoOpcion = 260;

        int separacion = 30;

        int inicioX =
            (ANCHO_MENU -
            (
                CANTIDAD_OPCIONES
                * anchoOpcion
                +
                (
                    CANTIDAD_OPCIONES - 1
                )
                * separacion
            ))
            / 2;

        int x =
            inicioX
            + indice
            * (
                anchoOpcion
                + separacion
            );

        int y = 160;

        imagen.setColor(
            new Color(
                40,
                40,
                55
            )
        );

        imagen.fillRect(
            x,
            y,
            anchoOpcion,
            altoOpcion
        );

        imagen.setColor(Color.WHITE);

        imagen.drawRect(
            x,
            y,
            anchoOpcion,
            altoOpcion
        );

        GreenfootImage numero =
            new GreenfootImage(
                String.valueOf(
                    indice + 1
                ),
                30,
                Color.YELLOW,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            numero,
            x + 15,
            y + 15
        );

        String nombre =
            obtenerNombreVisible(
                opciones[indice]
            );

        GreenfootImage texto =
            new GreenfootImage(
                nombre,
                24,
                Color.WHITE,
                new Color(
                    0,
                    0,
                    0,
                    0
                )
            );

        imagen.drawImage(
            texto,
            x +
            (
                anchoOpcion
                - texto.getWidth()
            ) / 2,
            y + 100
        );
    }





    private String obtenerNombreVisible(
        String opcion
    )
    {
        if (opcion.equals("VELOCIDAD"))
        {
            return "VELOCIDAD";
        }

        if (opcion.equals("VIDA"))
        {
            return "VIDA";
        }

        if (opcion.equals("CUCHILLO"))
        {
            return "CUCHILLO +1";
        }

        if (opcion.equals("DANIO_GLOBAL"))
        {
            return "DANO GLOBAL +1";
        }

        if (opcion.equals("BOMBA_NUEVA"))
        {
            return "NUEVA BOMBA";
        }

        if (opcion.equals("LASER_NUEVO"))
        {
            return "NUEVO LASER";
        }

        if (opcion.equals("BOMBA"))
        {
            return "BOMBA +1";
        }

        if (opcion.equals("LASER"))
        {
            return "LASER +1";
        }

        return opcion;
    }
}
