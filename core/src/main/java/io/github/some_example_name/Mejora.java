package io.github.some_example_name;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Mejora implements Colisionable {
    private String tipo;
    protected float duracion;
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

    // Método Template
    public final void templateMethod(Nave4 nave) {
        this.activa = true; // Marcar la mejora como activa
        tiempoActivacion = 0; // Reiniciar el tiempo de activación
        aplicarEfecto(nave);
    }

   

    public void desactivar() {
        this.activa = false;
    }

    public boolean actualizarEstado(float delta) {
        if (activa) {
            tiempoActivacion += delta; // Aumentar el tiempo de activación
            if (tiempoActivacion >= duracion) {
                desactivar();
                return false; // La mejora ya no está activa
            }
        }
        return activa;
    }

    public void activar(Nave4 nave) {
    	nave.setMejoraActiva(true); 
    	templateMethod(nave);
    }

    public void verificarDesactivar(Nave4 nave, float delta) {
        if (!actualizarEstado(delta)) {
        	nave.setMejoraActiva(false);
            removerEfecto(nave);
        }
    }

    public String getTipo() {
        return tipo;
    }

    // Métodos abstractos que las subclases deben implementar
    protected abstract void aplicarEfecto(Nave4 nave);
    protected abstract void removerEfecto(Nave4 nave);
    public abstract void draw(SpriteBatch batch);
    public abstract void update(float delta);

    public static Mejora generarMejoraAleatoria(float x, float y) {
        Random random = new Random();
        int tipoAleatorio = random.nextInt(2);
        switch (tipoAleatorio) {
            case 0: return new PlanetaMejora(x, y, new Texture("planeta.png"), 10);
            case 1: return new EstrellaMejora(x, y, new Texture("estrella.png"), 100);
        }
        return null;
    }

}
