package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {

    private int xSpeed;
    private int ySpeed;
    private boolean destroyed = false;
    private Sprite spr;


    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
      
    }

    public void update() {
        spr.setPosition(spr.getX() + xSpeed, spr.getY() + ySpeed);
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    // Método de colisión genérico
    public boolean checkCollision(Colisionable c) {
        if (spr.getBoundingRectangle().overlaps(c.getArea())) {
            this.destroyed = true; // Destruir la bala al colisionar
            return true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

	public Rectangle getArea() {
		return spr.getBoundingRectangle(); // Devuelve el área de la bala
	}

}
