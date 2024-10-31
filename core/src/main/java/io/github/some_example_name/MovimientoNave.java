package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MovimientoNave {
    private Sprite spr;
    private float xVel = 0;
    private float yVel = 0;
    private float velocidadActual = 1;

    public MovimientoNave(Sprite spr) {
        this.spr = spr;
    }

    public void actualizarMovimiento() {
        float x = spr.getX();
        float y = spr.getY();

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
    }

    public void incrementarVelocidad() {
        velocidadActual += 3; // Incremento de velocidad
    }

    public void revertirVelocidad() {
        velocidadActual = 1; // Vuelve a la velocidad base
    }

    public float getVelocidad() {
        return velocidadActual;
    }
}
