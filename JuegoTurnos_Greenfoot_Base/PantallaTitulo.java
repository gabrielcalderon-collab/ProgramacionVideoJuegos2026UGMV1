import greenfoot.*;

public class PantallaTitulo extends World {
    public PantallaTitulo() {    
        super(900, 600, 1); 
        
        GreenfootImage fondoTitulo = new GreenfootImage("maxresdefault.jpg");
        fondoTitulo.scale(getWidth(), getHeight());
        
        // 1. Crear una fuente nueva (Nombre, negrita, cursiva, tamaño)
        Font fuenteGrande = new Font("Arial", true, false, 40); 
        
        // 2. Asignar la fuente y el color a la imagen
        fondoTitulo.setFont(fuenteGrande);
        fondoTitulo.setColor(Color.WHITE); 
        
        fondoTitulo.drawString("Edicion Greenfoot Demake", 200, 220);
        
        // 3. Dibujar el texto sobre el fondo 
        // Nota: X e Y marcan la esquina inferior izquierda del texto, ajusta la X si no queda centrado
        fondoTitulo.drawString("Presiona ENTER para empezar", 180, 400);
        
        // 4. Establecer la imagen como fondo
        setBackground(fondoTitulo);
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("enter")) {
            Greenfoot.setWorld(new MundoMapa());
        }
    }
}