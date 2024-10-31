package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mejora implements Colisionable {
    private String tipo;
    private float duracion;
    private boolean activa;
    private float tiempoActivacion;
    protected float x, y;

    public Mejora(String tipo, float duracion, float x, float y) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.activa = false;
        this.x = x;
        this.y = y;
    }

    public void activarMejora() {
        this.tiempoActivacion = 0;
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }

    public boolean estaActiva(float delta) {
        if (activa) {
            tiempoActivacion += delta; // Aumentar el tiempo activación
            if (tiempoActivacion >= duracion) {
                desactivar();
                return false; // La mejora ya no está activa
            }
        }
        return activa;
    }

    public void activar(Nave4 nave) {
        if (!estaActiva(0)) { // Llamar con 0 para verificar estado
            activarMejora();
            aplicarEfecto(nave);
        }
    }

    public void verificarDesactivar(Nave4 nave, float delta) {
        if (!estaActiva(delta)) {
            removerEfecto(nave);
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void aplicarEfecto(Nave4 nave) {
        switch (tipo) {
            case "velocidad":
                nave.incrementarVelocidad();
                break;
            case "escudo":

                break;
            case "disparo y vida":
            	
            	nave.reducirCooldownDisparo();
            	nave.setVidas(5);;

                break;
        }
    }

    public void removerEfecto(Nave4 nave) {
        switch (tipo) {
            case "velocidad":
                nave.revertirVelocidad();
                break;
            case "escudo":

                break;
            case "disparo":
            	
            	nave.getDisparo().setCooldown(0.5f); // Restaurar cooldown original


                break;
        }
    }

    public static Mejora generarMejoraAleatoria(float x, float y) {
        Random random = new Random();
        int tipoAleatorio = random.nextInt(2);
        switch (tipoAleatorio) {
            case 0: return new PlanetaMejora(x, y, new Texture("planeta.png"), 7);
            case 1: return new EstrellaMejora(x, y, new Texture("estrella.png"), 7);

        }
        return null;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update(float delta);
}
