package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class SpaceNav extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;    

    public void create() {
        highScore = 0;
        batch = new SpriteBatch();
        font = new BitmapFont(); // Usa Arial font por defecto
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); // Configura para pantalla completa
        //font.getData().setScale(2f);
        this.setScreen(new PantallaMenu(this)); // Cambiar aquí para iniciar en PantallaMenu
    }

    public void render() {
        super.render(); // importante!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
