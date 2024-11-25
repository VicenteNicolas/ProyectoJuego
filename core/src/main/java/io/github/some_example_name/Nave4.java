package io.github.some_example_name;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Nave4 {
    private boolean destruida = false;
    private Sprite spr;
    private Sound sonidoHerido;

    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private Disparo disparo;

    private MovimientoNave movimientoNave;
    private EstrategiaMovimiento estrategiaMovimiento;
    private boolean mejoraActiva; // Indica si hay una mejora activa

    public Nave4(int x, int y, Texture tx, Sound soundChoque, GameObjectFactory factory, Sound soundBala) {
        this.sonidoHerido = soundChoque;
        this.spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 60, 60);
        this.disparo = new Disparo(spr, soundBala, factory);

        this.movimientoNave = new MovimientoNave(spr, 1); // Velocidad inicial
        this.estrategiaMovimiento = new MovimientoRapido();
    }

    public void reducirCooldownDisparo() {
        disparo.setCooldown(0.1f);
    }

    public Disparo getDisparo() {
        return disparo;
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
        if (!herido) {
        	estrategiaMovimiento.mover(this); 
        	movimientoNave.actualizar();
            spr.draw(batch);
        } else {
            movimientoNave.aplicarMovimientoHerido();
            spr.draw(batch);
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
        return c.getArea().overlaps(getBounds());
    }

    public void herir() {
        GameState gameState = GameState.getInstance(); // Obtener instancia única de GameState

        gameState.reducirVida();
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
        if (gameState.getVidas() <= 0) {
            destruida = true;
        }
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }
    
    public void presionarTeclaDisparo(boolean presionada) {

    	
    	if (mejoraActiva) {
    		
    		// Si hay una mejora activa, no se penaliza con el movimiento al spamear balas
    		estrategiaMovimiento = new MovimientoRapido();
            return;
        }

        if (presionada) {
            // Cambiar a movimiento lento al mantener presionada la tecla de disparo
            estrategiaMovimiento = new MovimientoLento(); // Cambia la estrategia a movimiento lento
            disparo.setCooldown(0.4f); // Cambia el cooldown para disparar más lento
        } else {
            // Cambiar a movimiento rápido al soltar la tecla de disparo
            estrategiaMovimiento = new MovimientoRapido(); // Cambia la estrategia a movimiento rápido
            disparo.setCooldown(0.5f); // Cambia el cooldown para disparar más rápido
        }
    }


    public void incrementarVelocidad() {
        movimientoNave.incrementarVelocidad(5);
    }

    public void revertirVelocidad() {
        movimientoNave.revertirVelocidad(1);
    }

    public float getVelocidad() {
        return movimientoNave.getVelocidad();
    }
    
    public MovimientoNave getMovimientoNave() {
        return movimientoNave;
    }
    
    public void setMejoraActiva(boolean activa) {
        this.mejoraActiva = activa;
    }
    public boolean isMejoraActiva() {
        return mejoraActiva;
    }
}
