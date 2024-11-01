package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class PantallaGameOver implements Screen {

    private SpaceNav game;
    private OrthographicCamera camera;
    private BitmapFont font; // Objeto de fuente
    private float initialWidth; // Ancho inicial de la ventana
    private Stage stage; // Nuevo escenario
    private TextButton playButton; // Botón para reiniciar el juego
    private TextButton exitButton; // Botón para salir del juego
    private TextButton menuButton; // Botón para volver al menú
    private Texture backgroundTexture; // Nueva variable para la textura de fondo

    private int score; // Atributo para almacenar la puntuación

    public PantallaGameOver(SpaceNav game, int score) {
        this.game = game;
        this.score = score; // Guardar la puntuación

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        new GlyphLayout();
        font = game.getFont(); // Asumiendo que este método devuelve un BitmapFont
        initialWidth = Gdx.graphics.getWidth(); // Guardar el ancho inicial

        // Inicializa el Stage
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage); // Procesador de entrada

        // Carga la imagen de fondo
        backgroundTexture = new Texture(Gdx.files.internal("fondoGameOver - copia.png")); // Cambia a la ruta de tu textura de fondo

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
        
        // Crea un estilo para el botón de volver al menú
        TextButton.TextButtonStyle menuStyle = new TextButton.TextButtonStyle();
        menuStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("botonMenu.png"))); // Cargar textura del menú
        menuStyle.down = menuStyle.up; // Usar la misma textura para el estado presionado
        menuStyle.font = font; // Asignar la fuente al estilo del botón de menú

        // Crear el TextButton para volver al menú
        menuButton = new TextButton("", menuStyle);
        menuButton.setSize(300, 80);
        menuButton.setPosition(Gdx.graphics.getWidth() / 2 - menuButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menuButton.getHeight() - 100); // Colocar el botón de menú entre los otros dos botones

        // Añadir listener al TextButton de menú
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PantallaMenu(game)); // Cambia a la pantalla del menú
                dispose(); // Libera los recursos de la pantalla del Game Over
            }
        });

        // Añadir el TextButton de menú al escenario
        stage.addActor(menuButton);

        // Crea un estilo para el botón de salir
        TextButton.TextButtonStyle exitStyle = new TextButton.TextButtonStyle();
        exitStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("botonExit.png"))); // Cargar textura de salida
        exitStyle.down = exitStyle.up; // Usar la misma textura para el estado presionado
        exitStyle.font = font; // Asignar la fuente al estilo del botón de salida

        // Crear el TextButton para salir
        exitButton = new TextButton("", exitStyle);
        exitButton.setSize(300, 80);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() - menuButton.getHeight() - 150);

        // Añadir listener al TextButton de salir
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Cierra la aplicación
            }
        });

        // Añadir el TextButton de salida al escenario
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

        // Dibujar la textura de fondo
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Dibujar la puntuación en el centro de la pantalla
        String scoreText = "TU PUNTUACIÓN: " + score; // Texto de puntuación
        GlyphLayout layout = new GlyphLayout(font, scoreText);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2; // Centrar horizontalmente
        float y = Gdx.graphics.getHeight() / 2 + 100; // Posicionar ligeramente arriba del centro
        font.draw(game.getBatch(), scoreText, x, y); // Dibujar la puntuación

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
        backgroundTexture.dispose(); // Libera la textura de fondo
    }
}
