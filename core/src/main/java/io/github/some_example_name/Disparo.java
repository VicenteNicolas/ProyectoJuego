package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.audio.Sound;

public class Disparo {
    private Sprite spr;
    private Sound soundBala;
    private float cooldown = 0; // Cooldown actual
    private float cooldownBase = 0.5f; // Cooldown base
    private GameObjectFactory factory;

    public Disparo(Sprite spr, Sound soundBala, GameObjectFactory factory) {
        this.spr = spr;
        this.soundBala = soundBala;
        this.factory = factory; // Inicializa la fábrica
    }

    public void intentarDisparar(PantallaJuego juego) {
        cooldown -= Gdx.graphics.getDeltaTime(); // Resta el tiempo entre fotogramas
        
        // Usa cooldownBase como el tiempo de espera entre disparos
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && cooldown <= 0) {
        	Bullet bala = factory.createBullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 4);
            juego.agregarBala(bala);
            soundBala.play();
            cooldown = cooldownBase; // Aquí se utiliza el cooldownBase
        }
    }
    
    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float nuevoCooldown) {
        cooldownBase = nuevoCooldown; // Cambia el cooldown base
    }
}
