package io.github.some_example_name;

import java.util.ArrayList;

public interface GameObjectFactory {
	
	Ball2 createAsteroid(int x, int y, int radius, int velX, int velY);
	Bullet createBullet(float x, float y, int velX, int velY);
    Mejora createMejora(float x, float y, ArrayList<Ball2> balls);

}
