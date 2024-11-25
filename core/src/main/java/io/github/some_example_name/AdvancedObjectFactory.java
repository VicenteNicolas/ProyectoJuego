package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

public class AdvancedObjectFactory implements GameObjectFactory {
    private Texture asteroidTexture;
    private Texture bulletTexture;
    
    
    public AdvancedObjectFactory(Texture asteroidTexture, Texture bulletTexture, ArrayList<Ball2> balls1) {
        this.asteroidTexture = asteroidTexture;
        this.bulletTexture = bulletTexture;
    }

    @Override
    public Ball2 createAsteroid(int x, int y, int radius, int velX, int velY) {
        return new Ball2(x, y, radius, velX+3, velY+3, asteroidTexture);
    }

    @Override
    public Bullet createBullet(float x, float y, int velX, int velY) {
        return new Bullet(x, y, velX, velY, bulletTexture);
    }

    @Override
    public Mejora createMejora(float x, float y, ArrayList<Ball2> balls) {
        return Mejora.generarMejoraAleatoria(x, y, balls);
    }
}