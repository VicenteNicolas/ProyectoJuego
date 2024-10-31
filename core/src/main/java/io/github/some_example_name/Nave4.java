package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Nave4 {
    private boolean destruida = false;
    private int vidas = 3;
    private Sprite spr;
    private Sound sonidoHerido;
    
    private float xVel = 0;
    private float yVel = 0;
    private float velocidadActual = 1;


    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private Disparo disparo;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        this.sonidoHerido = soundChoque;
        this.spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 60, 60);
        this.disparo = new Disparo(spr, soundBala, txBala);

    }
    
    public void reducirCooldownDisparo() {
        disparo.setCooldown(0.1f);
    }
    
    public Disparo getDisparo() {
        return disparo;
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
    	
    	float x = spr.getX();
        float y = spr.getY();

      
        if (!herido) {
        	// Movimiento con teclado
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                xVel = -2;
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                xVel = 2;
            else
                xVel = 0; // Detener movimiento horizontal

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                yVel = -2;
            else if (Gdx.input.isKeyPressed(Input.Keys.UP))
                yVel = 2;
            else
                yVel = 0; // Detener movimiento vertical

            // Mantener dentro de los bordes de la ventana
            if (x + xVel * velocidadActual < 0) {
                spr.setX(0);
            } else if (x + xVel * velocidadActual + spr.getWidth() > Gdx.graphics.getWidth()) {
                spr.setX(Gdx.graphics.getWidth() - spr.getWidth());
            } else {
                spr.setX(x + xVel * velocidadActual);
            }

            if (y + yVel * velocidadActual < 0) {
                spr.setY(0);
            } else if (y + yVel * velocidadActual + spr.getHeight() > Gdx.graphics.getHeight()) {
                spr.setY(Gdx.graphics.getHeight() - spr.getHeight());
            } else {
                spr.setY(y + yVel * velocidadActual);
            }

            spr.draw(batch);
        } else {
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(spr.getX());
            tiempoHerido--;
            if (tiempoHerido <= 0)
                herido = false;
        }

        // Manejo de disparo
        disparo.intentarDisparar(juego);
    }

    public Rectangle getBounds() {
        return spr.getBoundingRectangle();
    }

    public boolean checkCollision(Colisionable c) {
        if (c.getArea().overlaps(getBounds())) {
            return true;
        }
        return false;
    }

    public void herir() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
        if (vidas <= 0) {
            destruida = true;
        }
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas2) {
        vidas = vidas2;
    }
    
    public void incrementarVelocidad() {
        velocidadActual += 2; // Incremento de velocidad
    }

    public void revertirVelocidad() {
        velocidadActual = 1; // Vuelve a la velocidad base
    }

    public float getVelocidad() {
        return velocidadActual;
    }
}
