package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlanetaMejora extends Mejora {
    private Texture texture;
    private float velocidad = 100;
    private float ancho; // Ancho del planeta
    private float alto; // Alto del planeta
    
    private int contadorImpactos = 0;
    private int maxImpactos = 0;


    public PlanetaMejora(float x, float y, Texture texture, int duracion) {
        super("velocidad", duracion, x, y); 
        this.texture = texture;
        this.ancho = texture.getWidth();
        this.alto = texture.getHeight();

    }
    
 // Verifica si la mejora ha sido destruida
    public boolean estaDestruido() {
    	Random random = new Random();
        maxImpactos = random.nextInt(20);
        return contadorImpactos >= maxImpactos;
    }

    // Recibir impacto y verificar la cantidad de impactos
    public void recibirImpacto() {
        contadorImpactos++;
    }


    @Override
    public void update(float delta) {
        y -= velocidad * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
    	batch.draw(texture, x, y, ancho/10  , alto/10 );
    }

    @Override
    public Rectangle getArea() {
    	return new Rectangle(x, y, ancho/10 , alto/10 );
    }

	@Override
	public void procesarColision(Nave4 nave) {
		nave.setVidas(0);
		
	}

}