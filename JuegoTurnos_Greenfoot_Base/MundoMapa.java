import greenfoot.*;

public class MundoMapa extends World {
    public MundoMapa() {    
        super(900, 600, 1); 
        
        // 1. Cargar la imagen de la cueva
        GreenfootImage fondoCueva = new GreenfootImage("200px-Cueva_Cambiante_Esmeralda.png");
        
        // 2. Escalar la imagen al tamaño exacto del mundo (900x600)
        fondoCueva.scale(getWidth(), getHeight());
        
        // 3. Establecer la imagen escalada como fondo
        setBackground(fondoCueva);
        
        // 4. Agregar a los actores
        addObject(new JugadorExploracion(), 450, 300);
        addObject(new EnemigoMapa(), 750, 300);
    }
}