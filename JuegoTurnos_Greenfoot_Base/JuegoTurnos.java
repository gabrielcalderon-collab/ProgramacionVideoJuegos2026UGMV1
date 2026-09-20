import greenfoot.*;

public class JuegoTurnos extends World
{
    private GestorCombate combate;
    private GreenfootSound musica;
    
    // 1. Variable para guardar el mapa de exploración
    private World mundoAnterior;

    // 2. EL NUEVO CONSTRUCTOR: Recibe el mapa de exploración
    public JuegoTurnos(World mapaAnterior)
    {
        // Llama al constructor de abajo para cargar todo el combate normalmente
        this(); 
        
        // Guarda el mapa en la variable
        this.mundoAnterior = mapaAnterior;
    }

    // 3. TU CONSTRUCTOR ORIGINAL (se mantiene igual)
    public JuegoTurnos()
    {
        super(900, 600, 1);

        FondoEscenario fondo = new FondoEscenario();
        addObject(fondo, getWidth() / 2, getHeight() / 2);
        fondo.guardarPosicion();

        musica = new GreenfootSound("musica_combate.mp3");
        musica.setVolume(50);

        combate = new GestorCombate(this, fondo, musica);
        combate.iniciar();
    }

    public void act()
    {
        combate.actualizar();
    }
    
    // 4. Método para usar cuando quieras regresar al mapa (por ejemplo, al ganar)
    public World getMundoAnterior()
    {
        return mundoAnterior;
    }
}