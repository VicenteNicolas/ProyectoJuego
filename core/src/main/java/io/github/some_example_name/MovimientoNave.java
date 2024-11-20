package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class MovimientoNave {
    private Sprite spr;
    private float velocidadActual;
    private float xVel;
    private float yVel;

    public MovimientoNave(Sprite spr, float velocidadInicial) {
        this.spr = spr;
        this.velocidadActual = velocidadInicial;
        this.xVel = 0;
        this.yVel = 0;
    }

    public void actualizar() {
        float x = spr.getX();
        float y = spr.getY();

        // Movimiento con teclado
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            xVel = -2;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            xVel = 2;
        else
            xVel = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            yVel = -2;
        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
            yVel = 2;
        else
            yVel = 0;

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
    }

    public void incrementarVelocidad(float incremento) {
        velocidadActual += incremento;
    }

    public void revertirVelocidad(float velocidadBase) {
        velocidadActual = velocidadBase;
    }

    public float getVelocidad() {
        return velocidadActual;
    }

    public void aplicarMovimientoHerido() {
        spr.setX(spr.getX() + MathUtils.random(-2, 2));
    }
}
