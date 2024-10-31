package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {
    private Texture fondo;
    private float anchoFondo;
    private float altoFondo;
    
    private float posicionY1;
    private float posicionY2;
    private float velocidad;

    public Fondo(String rutaFondo, float ancho, float alto, float velocidad) {
        this.fondo = new Texture(Gdx.files.internal(rutaFondo));
        this.anchoFondo = ancho;
        this.altoFondo = alto;
        this.velocidad = velocidad;
        
        this.posicionY1 = 0;
        this.posicionY2 = altoFondo;
    }

    public void actualizar(float delta) {
        posicionY1 -= velocidad * delta;
        posicionY2 -= velocidad * delta;

        // Reseteo de la posición del fondo
        if (posicionY1 <= -altoFondo) {
            posicionY1 = posicionY2 + altoFondo;
        }
        if (posicionY2 <= -altoFondo) {
            posicionY2 = posicionY1 + altoFondo;
        }
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(fondo, 0, posicionY1, anchoFondo, altoFondo);
        batch.draw(fondo, 0, posicionY2, anchoFondo, altoFondo);
    }

    public void dispose() {
        fondo.dispose();
    }
}
