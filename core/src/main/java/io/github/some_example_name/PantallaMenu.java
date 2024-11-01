package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture; // Asegúrate de importar esta clase
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // Importa SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PantallaMenu implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private TextButton playButton;
    private TextButton infoButton; // Nuevo botón de información
    private TextButton exitButton; // Nuevo botón de salida
    private Viewport viewport;
    private Texture backgroundTexture; // Nueva variable para la textura de fondo
    private SpriteBatch batch; // Para dibujar la textura de fondo

    public PantallaMenu(SpaceNav game) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera); // Configuración de resolución base
        viewport.apply();

        // Inicializa el Stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage); // Procesador de entrada

        // Crea un BitmapFont básico
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2); // Escala la fuente para que sea más grande

        // Carga la imagen de fondo
        backgroundTexture = new Texture(Gdx.files.internal("fondoMenu - copia.png")); 
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Aplicar filtro a la textura

        // Crea un estilo para el botón
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font; // Establece la fuente del botón

        // Carga la imagen de fondo del botón
        Texture buttonTexture = new Texture(Gdx.files.internal("botonStart.png"));
        buttonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Aplicar filtro a la textura
        style.up = new TextureRegionDrawable(buttonTexture); // Establecer textura de fondo
        style.down = new TextureRegionDrawable(buttonTexture); // Establecer textura para el estado presionado (puedes usar otra imagen si lo deseas)

        // Crea un TextButton que simula un botón
        playButton = new TextButton("", style);
        playButton.setSize(300, 80); // Aumenta el tamaño del botón
        playButton.setPosition(viewport.getWorldWidth() / 2 - playButton.getWidth() / 2, viewport.getWorldHeight() / 2 - playButton.getHeight() / 2);

        // Añadir listener al TextButton
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Cambia a la pantalla de juego al hacer clic
                game.setScreen(new PantallaJuego(game, 1, 3, 0, 1, 1, 5, 1));
                dispose(); // Libera los recursos de la pantalla del menú
            }
        });

        // Añadir el TextButton al escenario
        stage.addActor(playButton);
        
        
     // Cargar la imagen de fondo del botón de "Info"
        Texture infoButtonTexture = new Texture(Gdx.files.internal("botonInfo.png")); // Asegúrate de que esta textura exista
        infoButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Aplicar filtro a la textura

        // Crear un estilo para el botón de información
        TextButton.TextButtonStyle infoStyle = new TextButton.TextButtonStyle();
        infoStyle.font = font; // Establece la fuente del botón de información
        infoStyle.up = new TextureRegionDrawable(infoButtonTexture); // Establecer textura de fondo para el botón de información
        infoStyle.down = new TextureRegionDrawable(infoButtonTexture); // Establecer textura para el estado presionado

        // Crear el botón de información
        infoButton = new TextButton("", infoStyle);
        infoButton.setSize(300, 80);
        infoButton.setPosition(viewport.getWorldWidth() / 2 - infoButton.getWidth() / 2, viewport.getWorldHeight() / 2 - playButton.getHeight() - 100);

        // Añadir listener al TextButton "Info"
        infoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PantallaTutorial(game)); // Cambia a la pantalla de tutorial
            }
        });

        // Añadir el botón de información al escenario
        stage.addActor(infoButton);
        
     // Crear el botón de salida
        Texture exitButtonTexture = new Texture(Gdx.files.internal("botonExit.png")); // Cargar la imagen del botón de salida
        exitButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Aplicar filtro a la textura

        // Crear un estilo para el botón de salida
        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.font = font; // Establece la fuente del botón de salida
        exitStyle.up = new TextureRegionDrawable(exitButtonTexture); // Establecer textura de fondo para el botón de salida
        exitStyle.down = new TextureRegionDrawable(exitButtonTexture); // Establecer textura para el estado presionado

        // Crear el botón de salida
        exitButton = new TextButton("", exitStyle);
        exitButton.setSize(300, 80);
        exitButton.setPosition(viewport.getWorldWidth() / 2 - exitButton.getWidth() / 2, viewport.getWorldHeight() / 2 - playButton.getHeight() - infoButton.getHeight() - 150);

        // Añadir listener al TextButton "Salir"
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Cierra la aplicación al hacer clic
            }
        });

        // Añadir el botón de salida al escenario
        stage.addActor(exitButton);

        // Crear un Label para el título
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font; // Usa la misma fuente que el botón
        Label titleLabel = new Label("", labelStyle);
        titleLabel.setColor(Color.WHITE); // Cambia el color del texto a blanco
        titleLabel.setFontScale(2); // Escalar la fuente
        titleLabel.setPosition(viewport.getWorldWidth() / 2 - titleLabel.getWidth() / 2, viewport.getWorldHeight() - 100); // Posición en la parte superior

        // Añadir el Label al escenario
        stage.addActor(titleLabel);

        batch = new SpriteBatch(); // Inicializa el SpriteBatch
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        viewport.apply();
        camera.update();

        // Dibuja la textura de fondo
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // Dibuja la textura de fondo
        batch.end();

        // Dibuja el escenario
        stage.act(delta);
        stage.draw(); // Solo dibuja el escenario, que incluye el TextButton "Jugar"
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
        viewport.update(width, height, true);
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
        stage.dispose(); // Libera los recursos del escenario
        backgroundTexture.dispose(); // Libera los recursos de la textura de fondo
        batch.dispose(); // Libera los recursos de SpriteBatch
    }
}
