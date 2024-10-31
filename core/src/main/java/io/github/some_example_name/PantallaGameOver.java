package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PantallaGameOver implements Screen {

    private SpaceNav game;
    private OrthographicCamera camera;
    private GlyphLayout layout; // Nueva variable para GlyphLayout
    private BitmapFont font; // Objeto de fuente
    private float initialWidth; // Ancho inicial de la ventana

    public PantallaGameOver(SpaceNav game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        layout = new GlyphLayout(); // Inicializa GlyphLayout
        font = game.getFont(); // Asumiendo que este método devuelve un BitmapFont
        initialWidth = Gdx.graphics.getWidth(); // Guardar el ancho inicial
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

        // Obtener el tamaño de la ventana
        float centerX = Gdx.graphics.getWidth() / 2;
        float centerY = Gdx.graphics.getHeight() / 2;

        // Texto a mostrar
        String gameOverText = "Game Over !!!";
        String restartText = "Pincha en cualquier lado para reiniciar ...";

        // Calcular el ancho del texto para centrarlo usando GlyphLayout
        layout.setText(font, gameOverText);
        float gameOverTextWidth = layout.width;

        layout.setText(font, restartText);
        float restartTextWidth = layout.width;

        // Dibujar texto centrado
        font.draw(game.getBatch(), gameOverText, centerX - (gameOverTextWidth / 2), centerY + 50);
        font.draw(game.getBatch(), restartText, centerX - (restartTextWidth / 2), centerY - 50);

        game.getBatch().end();

        // Manejar la entrada del usuario
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1,3 , 0, 1, 1, 5, 1);
            game.setScreen(ss);
            dispose();
        }
    }

    @Override
    public void show() {
        // Método vacío
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

        // Ajustar el tamaño de la fuente basado en el ancho de la ventana
        if (width > 0) { // Verifica que el ancho sea mayor a 0
            float scaleFactor = width / initialWidth; // Calcula el factor de escala
            if (scaleFactor <= 0) scaleFactor = 1; // Asegura que scaleFactor no sea 0 o negativo
            font.getData().setScale(scaleFactor); // Cambia el tamaño de la fuente
        }
    }

    @Override
    public void pause() {
        // Método vacío
    }

    @Override
    public void resume() {
        // Método vacío
    }

    @Override
    public void hide() {
        // Método vacío
    }

    @Override
    public void dispose() {
        // Método vacío
    }
}
