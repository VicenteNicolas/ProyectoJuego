package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class EstrellaMejora extends Mejora  {
    private Texture texture;
    private float ancho; 
    private float alto;
    
    private int contadorImpactos = 0;
    private int maxImpactos = 0;
    
    public EstrellaMejora(float x, float y, Texture texture, int duracion) {
        super("disparo y vida", duracion, x, y); 
        this.texture = texture;
        this.ancho = texture.getWidth();
        this.alto = texture.getHeight();
    }
    
 // Verifica si la mejora ha sido destruida
    public boolean estaDestruido() {
    	Random random = new Random();
        maxImpactos = random.nextInt(100);
        return contadorImpactos >= maxImpactos;
    }

    // Recibir impacto y verificar la cantidad de impactos
    public void recibirImpacto() {
        contadorImpactos++;
    }

    @Override
    public void update(float delta) {
        y -= 100 * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
    	batch.draw(texture, x, y, ancho/10  , alto/10 );
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(x, y, ancho/10  , alto/10);
    }

	@Override
	public void procesarColision(Nave4 nave) {
		nave.setVidas(0);
	
	}
}

