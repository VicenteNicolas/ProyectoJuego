package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 implements Colisionable {
    private float x;
    private float y;
    private int xSpeed;
    private int ySpeed;
    private int maxSpeed=10;
    private Sprite spr;
    private float originalSize; // Almacena el tamaño original
    private boolean congelado; // Estado de congelación
    private int velocidadOriginalX; // Almacena la velocidad original en X
    private int velocidadOriginalY; // Almacena la velocidad original en Y

    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        this.x = x;
        this.y = y;
        this.originalSize = size; // Guardar el tamaño original
        spr.setSize(originalSize, originalSize); // Establecer tamaño original
        spr.setOriginCenter(); // Establece el origen en el centro para mejor rotación
        setPositionAndCheckBounds();
        spr.setPosition(x, y);
        setXSpeed(xSpeed);
        setySpeed(ySpeed);
        this.congelado = false; // Inicialmente no está congelado
        this.velocidadOriginalX = xSpeed; // Guardar velocidad original en X
        this.velocidadOriginalY = ySpeed; // Guardar velocidad original en Y
    }

    private void setPositionAndCheckBounds() {
        // Validar que borde de esfera no quede fuera
        if (x < 0) x = 0;
        if (x > Gdx.graphics.getWidth() - originalSize) x = Gdx.graphics.getWidth() - originalSize;
        if (y < 0) y = 0;
        if (y > Gdx.graphics.getHeight() - originalSize) y = Gdx.graphics.getHeight() - originalSize;
    }

    public void update() {
    	if (!congelado) {
            x += getXSpeed();
            y += getySpeed();
            checkBounds();
            spr.setPosition(x, y);
           
        }
    }

    private void checkBounds() {
        if (x < 0) {  // Fuera por la izquierda
            x = 0; // Reposicionar en el límite izquierdo
            setXSpeed(-getXSpeed()); // Invertir la velocidad en X
        } else if (x + spr.getWidth() > Gdx.graphics.getWidth()) {  // Fuera por la derecha
            x = Gdx.graphics.getWidth() - spr.getWidth(); // Reposicionar en el límite derecho
            setXSpeed(-getXSpeed()); // Invertir la velocidad en X
        }

        if (y < 0) {  // Fuera por abajo
            y = 0; // Reposicionar en el límite inferior
            setySpeed(-getySpeed()); // Invertir la velocidad en Y
        } else if (y + spr.getHeight() > Gdx.graphics.getHeight()) {  // Fuera por arriba
            y = Gdx.graphics.getHeight() - spr.getHeight(); // Reposicionar en el límite superior
            setySpeed(-getySpeed()); // Invertir la velocidad en Y
        }
    }

    public Rectangle getArea() {
        Rectangle boundingRectangle = spr.getBoundingRectangle();
        float padding = 10; // Ajusta este valor según lo necesites
        return new Rectangle(
            boundingRectangle.x + padding,
            boundingRectangle.y + padding,
            boundingRectangle.width - 2 * padding,
            boundingRectangle.height - 2 * padding
        );
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public void checkCollision(Ball2 b2) {
        if (getArea().overlaps(b2.getArea())) { // Usa el nuevo método
            // Calcular el vector de separación
            Rectangle rect1 = getArea();
            Rectangle rect2 = b2.getArea();

            float overlapX = Math.min(rect1.x + rect1.width - rect2.x, rect2.x + rect2.width - rect1.x);
            float overlapY = Math.min(rect1.y + rect1.height - rect2.y, rect2.y + rect2.height - rect1.y);

            // Mueve hacia el centro para que se toquen sin separarse
            if (overlapX < overlapY) {
                if (rect1.x < rect2.x) {
                    x -= overlapX; // Mueve a la izquierda
                } else {
                    x += overlapX; // Mueve a la derecha
                }
                // Invertir velocidad en X para simular un rebote
                setXSpeed(-getXSpeed());
            } else {
                if (rect1.y < rect2.y) {
                    y -= overlapY; // Mueve hacia abajo
                } else {
                    y += overlapY; // Mueve hacia arriba
                }
                // Invertir velocidad en Y para simular un rebote
                setySpeed(-getySpeed());
            }
        }
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = Math.max(-maxSpeed, Math.min(maxSpeed, xSpeed));
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = Math.max(-maxSpeed, Math.min(maxSpeed, ySpeed));
    }

    public float getOriginalSize() {
        return originalSize; // Retornar tamaño original
    }

    public void congelar() {
    	if (!congelado) {
            velocidadOriginalX = xSpeed;
            velocidadOriginalY = ySpeed;
            setXSpeed(0);
            setySpeed(0);
            this.congelado = true;
        }
    }

    public void descongelar() {
    	if (congelado) {
            // Verificamos que las velocidades originales son válidas antes de restaurarlas
            if (velocidadOriginalX != 0 && velocidadOriginalY != 0) {
                xSpeed = velocidadOriginalX;
                ySpeed = velocidadOriginalY;
            } else {
                // Asignamos valores predeterminados si las velocidades originales no son válidas
                xSpeed = 1;  // Valor por defecto
                ySpeed = 1;  // Valor por defecto
            }
            congelado = false;
        }
    }
    

    @Override
    public void procesarColision(Nave4 nave) {
        nave.herir();    
    }
}