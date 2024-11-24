package io.github.some_example_name;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlanetaMejora extends Mejora {
    private Texture texture;
    private float ancho; // Ancho del planeta
    private float alto; // Alto del planeta
    
    private int contadorImpactos = 0;
    private int maxImpactos = 0;

    //referencia a la lista de asteroides
    private List<Ball2> asteroides;

    public PlanetaMejora(float x, float y, Texture texture, int duracion, List<Ball2> asteroides) {
        super("velocidad", duracion, x, y); 
        this.texture = texture;
        this.ancho = texture.getWidth();
        this.alto = texture.getHeight();
        this.asteroides = asteroides; // Inicializa la lista de asteroides
    }
    
    // Verifica si la mejora ha sido destruida
    public boolean estaDestruido() {
        Random random = new Random();
        maxImpactos = random.nextInt(10);
        return contadorImpactos >= maxImpactos;
    }

    // Recibir impacto y verificar la cantidad de impactos
    public void recibirImpacto() {
        contadorImpactos++;
    }

    @Override
    public void update(float delta) {
        y -= 100 * delta; // Mantén la velocidad de caída del planeta
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
        // Congela la velocidad de los asteroides
        for (Ball2 asteroide : asteroides) {
            asteroide.congelar(); // Congela el asteroide
        }
    }

    @Override
    protected void removerEfecto(Nave4 nave) {
        // Restaura la velocidad de los asteroides
        for (Ball2 asteroide : asteroides) {
            asteroide.descongelar(); // Descongela el asteroide
        }
    }
}