package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PantallaGameOver implements Screen {

    private SpaceNav game;
    private OrthographicCamera camera;
    private GlyphLayout layout; // Nueva variable para GlyphLayout
    private BitmapFont font; // Objeto de fuente
    private float initialWidth; // Ancho inicial de la ventana
    private Stage stage; // Nuevo escenario
    private TextButton playButton; // Botón para reiniciar el juego

    public PantallaGameOver(SpaceNav game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        layout = new GlyphLayout(); // Inicializa GlyphLayout
        font = game.getFont(); // Asumiendo que este método devuelve un BitmapFont
        initialWidth = Gdx.graphics.getWidth(); // Guardar el ancho inicial
        
        // Inicializa el Stage
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage); // Procesador de entrada

        // Crea un estilo para el botón
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("botonJugar.png"))); // Cambia a tu textura de botón
        style.down = style.up; // Usar la misma textura para el estado presionado

        // Asegúrate de que la fuente está asignada al estilo del botón
        style.font = font; // Asignar la fuente al estilo del botón

        // Crea el TextButton para reiniciar
        playButton = new TextButton("", style);
        playButton.setSize(300, 80);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2); // Coloca el botón

        // Añadir listener al TextButton
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambia a la pantalla de juego al hacer clic
                game.setScreen(new PantallaJuego(game, 1, 3, 0, 1, 1, 5, 1));
                dispose(); // Libera los recursos de la pantalla del Game Over
            }
        });

        // Añadir el TextButton al escenario
        stage.addActor(playButton);
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
        String instructionText = "Presiona el botón para volver a jugar";

        // Calcular el ancho del texto para centrarlo usando GlyphLayout
        layout.setText(font, instructionText);
        float instructionTextWidth = layout.width;

        // Dibujar texto centrado
        font.draw(game.getBatch(), instructionText, centerX - (instructionTextWidth / 2), centerY + 100);

        game.getBatch().end();

        // Dibuja el escenario
        stage.act(delta);
        stage.draw(); // Solo dibuja el escenario, que incluye el TextButton
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

        // Ajusta el escenario al nuevo tamaño de ventana
        stage.getViewport().update(width, height, true);
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
        stage.dispose(); // Libera los recursos del escenario
    }
}
