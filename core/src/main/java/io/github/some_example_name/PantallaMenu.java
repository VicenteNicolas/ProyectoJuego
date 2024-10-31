package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PantallaMenu implements Screen {

    private SpaceNav game;
    private OrthographicCamera camera;
    private GlyphLayout layout; // Nueva variable para GlyphLayout
    private BitmapFont font; // Objeto de fuente
    private float initialWidth; // Ancho inicial de la ventana
    private Viewport viewport;

    public PantallaMenu(SpaceNav game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera); // Configuración de resolución base
        viewport.apply();
        layout = new GlyphLayout(); // Inicializa GlyphLayout
        font = game.getFont(); // Asumiendo que este método devuelve un BitmapFont
        initialWidth = Gdx.graphics.getWidth(); // Guardar el ancho inicial
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        viewport.apply();
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

     // Coordenadas centradas para el texto
        float centerX = viewport.getWorldWidth() / 2;
        float centerY = viewport.getWorldHeight() / 2;

        // Texto a mostrar
        String welcomeText = "Bienvenido a Space Navigation !";
        String instructionsText = "Pincha en cualquier lado o presiona cualquier tecla para comenzar ...";

        // Calcular el ancho del texto para centrarlo usando GlyphLayout
        layout.setText(font, welcomeText);
        float welcomeTextWidth = layout.width;

        layout.setText(font, instructionsText);
        float instructionsTextWidth = layout.width;

        // Dibujar texto centrado
        font.draw(game.getBatch(), welcomeText, centerX - (welcomeTextWidth / 2), centerY + 50);
        font.draw(game.getBatch(), instructionsText, centerX - (instructionsTextWidth / 2), centerY - 50);

        game.getBatch().end();

        // Manejar la entrada del usuario
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 5,1);
            game.setScreen(ss);
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
        viewport.update(width, height, true);

        // Ajustar el tamaño de la fuente basado en el ancho de la ventana
        float scaleFactor = width / initialWidth; // Calcula el factor de escala
        font.getData().setScale(scaleFactor); // Cambia el tamaño de la fuente
    }

    @Override
    public void show() {
        // Método vacío
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
