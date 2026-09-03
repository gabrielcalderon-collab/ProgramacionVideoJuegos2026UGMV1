import greenfoot.*;

public class Mundo extends World
{
    public Mundo()
    {
        super(800, 500, 1);
        prepararMundo();
    }

    private void prepararMundo()
    {
        Plataforma suelo = new Plataforma(800, 40);
        addObject(suelo, 400, 470);
        
        Jugador jugador = new Jugador();
        addObject(jugador, 100, 425);

        Plataforma plataforma1 = new Plataforma();
        addObject(plataforma1, 250, 380);

        Plataforma plataforma2 = new Plataforma();
        addObject(plataforma2, 500, 320);

        Plataforma plataforma3 = new Plataforma();
        addObject(plataforma3, 700, 250);
        
        Pincho pincho = new Pincho();
        addObject(pincho, 400, 430);
        
        HUDVida hud = new HUDVida();
        addObject(hud, 80, 30);
    }
}