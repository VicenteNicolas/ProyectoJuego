package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class EstrellaMejora extends Mejora {
    private Texture texture;
    private float ancho; 
    private float alto;
    
    private int contadorImpactos = 0;
    private int maxImpactos;
    
    public EstrellaMejora(float x, float y, Texture texture, int duracion) {
        super("disparo y vida", duracion, x, y); 
        this.texture = texture;
        this.ancho = texture.getWidth();
        this.alto = texture.getHeight();
        Random random = new Random();
        this.maxImpactos = random.nextInt(10); 
    }
    
    // Verifica si la mejora ha sido destruida
    public boolean estaDestruido() {
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
        batch.draw(texture, x, y, ancho / 10, alto / 10);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(x, y, ancho / 10, alto / 10);
    }

    @Override
    public void procesarColision(Nave4 nave) {
        GameState.nuevasVidas(0);
    }

    @Override
    protected void aplicarEfecto(Nave4 nave) {	 
    	
        	nave.reducirCooldownDisparo();
        	GameState.nuevasVidas(5);
    }

    @Override
    protected void removerEfecto(Nave4 nave) {
    	nave.getDisparo().setCooldown(0.5f); // Restaurar cooldown original
    }
}