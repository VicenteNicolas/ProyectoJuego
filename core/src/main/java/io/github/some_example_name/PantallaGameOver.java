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
    private BitmapFont font;
    private float initialWidth;
    private Stage stage;
    private TextButton playButton;
    private TextButton exitButton;
    private TextButton menuButton;
    private Texture backgroundTexture;
    private GameState dev;

    public PantallaGameOver(SpaceNav game) {
        this.game = game;
        dev = GameState.getInstance();
        init();
    }

    private void init() {
        // Inicializar cámara y vista
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = game.getFont();
        initialWidth = Gdx.graphics.getWidth();

        // Inicializar el Stage
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        // Cargar textura de fondo
        backgroundTexture = new Texture(Gdx.files.internal("fondoGameOver - copia.png"));

        // Inicializar botones
        initPlayButton();
        initMenuButton();
        initExitButton();
    }

    private void initPlayButton() {
        TextButton.TextButtonStyle style = createButtonStyle("botonJugar.png");
        playButton = createButton(style, Gdx.graphics.getHeight() / 2, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	dev.resetGame();
                game.setScreen(new PantallaJuego(game, 1, 3, 1, 1, 5, 1));
                dispose();
            }
        });
        stage.addActor(playButton);
    }

    private void initMenuButton() {
        TextButton.TextButtonStyle style = createButtonStyle("botonMenu.png");
        menuButton = createButton(style, Gdx.graphics.getHeight() / 2 - 100, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	dev.resetGame();
                game.setScreen(new PantallaMenu(game));
                dispose();
            }
        });
        stage.addActor(menuButton);
    }

    private void initExitButton() {
        TextButton.TextButtonStyle style = createButtonStyle("botonExit.png");
        exitButton = createButton(style, Gdx.graphics.getHeight() / 2 - 200, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    private TextButton.TextButtonStyle createButtonStyle(String textureFile) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new Texture(Gdx.files.internal(textureFile)));
        style.down = style.up;
        style.font = font;
        return style;
    }

    private TextButton createButton(TextButton.TextButtonStyle style, float yPosition, ClickListener listener) {
        TextButton button = new TextButton("", style);
        button.setSize(300, 80);
        button.setPosition(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, yPosition);
        button.addListener(listener);
        return button;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        String scoreText = "TU PUNTUACIÓN: " + GameState.getInstance().getScore();
        GlyphLayout layout = new GlyphLayout(font, scoreText);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = Gdx.graphics.getHeight() / 2 + 100;
        font.draw(game.getBatch(), scoreText, x, y);

        game.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();

        if (width > 0) {
            float scaleFactor = width / initialWidth;
            if (scaleFactor <= 0) scaleFactor = 1;
            font.getData().setScale(scaleFactor);
        }

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
