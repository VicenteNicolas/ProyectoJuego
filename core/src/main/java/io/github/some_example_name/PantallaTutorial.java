package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PantallaTutorial implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Texture tutorialImage; // Imagen del tutorial
    private TextButton backButton;
    private SpriteBatch batch;

    public PantallaTutorial(SpaceNav game) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera); // Configuración base
        viewport.apply();

        // Inicializa el Stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Cargar la imagen del tutorial
        tutorialImage = new Texture(Gdx.files.internal("tutorial-copia.png"));
        tutorialImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Aplicar filtro a la textura

        // Crear el botón de "Atrás"
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        Texture backButtonTexture = new Texture(Gdx.files.internal("botonBack.png")); // Ruta de tu textura de botón
        style.up = new TextureRegionDrawable(backButtonTexture);
        style.down = new TextureRegionDrawable(backButtonTexture);
        style.font = game.getFont(); // Asigna una fuente al botón

        backButton = new TextButton("", style);
        backButton.setSize(300, 80);
        backButton.setPosition(20, 20); // Coloca el botón en la esquina inferior izquierda

        // Añadir listener al botón de "Atrás"
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PantallaMenu(game)); // Regresa a la pantalla del menú
                dispose();
            }
        });

        // Añadir el botón al escenario
        stage.addActor(backButton);

        // Inicializa el SpriteBatch
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Dibuja la imagen de fondo ajustada a pantalla completa
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(tutorialImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        // Dibuja el escenario (incluye el botón de "Atrás")
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.setToOrtho(false, width, height);
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
        stage.dispose();
        tutorialImage.dispose();
        batch.dispose();
    }
}
