package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego implements Screen {

    private SpaceNav game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Sound hurtSound;
    private Music gameMusic;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private static final int INCREMENTO_VELOCIDAD = 1;
    private int contadorRondas;
    private Nave4 nave;
    private ArrayList<Ball2> balls1 = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    private ArrayList<Mejora> mejoras = new ArrayList<>();
    private ArrayList<Mejora> mejorasActivas = new ArrayList<>();
    
    private Fondo fondo;
    
    private float tiempoEspera;  // Tiempo hasta que aparezca ls proxima mejora
    private float tiempoTranscurrido;
    private int contadorMejoras; // Contador de mejoras generadas en la ronda actual
    private GameState dev = GameState.getInstance();

    public PantallaJuego(SpaceNav game, int velXAsteroides, int velYAsteroides,
                         int cantAsteroides, int contadorRondas) {
        this.game = game;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;
        this.contadorRondas = contadorRondas;
        initialize();
    }

    private void initialize() {
    	
    	contadorMejoras = 0; // Inicializar en 0 al comenzar el juego o nueva ronda
        batch = game.getBatch();
        setupCamera();
        setupViewport();
        loadAssets();
        createAsteroids();
        resetTiempoMejora();
    }
    
    private void resetTiempoMejora() {
        Random random = new Random();
        tiempoEspera = random.nextFloat(5);
        tiempoTranscurrido = 0;
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();
    }

    private void setupViewport() {
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
    }

    private void loadAssets() {
    	hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
    	hurtSound.setVolume(1,0.5f);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("disparo.wav")));
        dev.getVidas();

        fondo = new Fondo("FondoEspacio.jpg", 1920, 1080, 50);
    }

    private void createAsteroids() {
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Ball2 bb = new Ball2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50),
                    70 + r.nextInt(10),
                    velXAsteroides + r.nextInt(2),
                    velYAsteroides + r.nextInt(2),
                    new Texture(Gdx.files.internal("aGreyMedium4.png")));
            balls1.add(bb);
        }
    }

    private void createRandomMejora() {
    	if (contadorMejoras >= 1) { // Verifica si ya se generaro 1 mejora
            return; // Si es así, no genera más mejoras
        }
        
        Random r = new Random();
        float x = r.nextInt((int) Gdx.graphics.getWidth() - 50);
        float y = Gdx.graphics.getHeight() + 50;
        Mejora mejora = Mejora.generarMejoraAleatoria(x, y, balls1);
        if (mejora != null) {
            mejoras.add(mejora);
            contadorMejoras++; 
        }
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + dev.getVidas() + " Ronda: " + dev.getRonda();
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + GameState.getInstance().getScore(), Gdx.graphics.getWidth() - 150, 30); // Usa el score de GameState
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        fondo.actualizar(delta);
        batch.begin();
        fondo.dibujar(batch);
        
        tiempoTranscurrido += delta;
        if (tiempoTranscurrido >= tiempoEspera) {
            createRandomMejora();
            resetTiempoMejora();
        }

        updateMejoras(delta);
        updateDisparo(delta);
        dibujaEncabezado();
        handleCollisions();
        drawEntities();

        batch.end();
        
        checkLevelCompleted();
        checkGameOver();
    }

    private void updateMejoras(float delta) {
        for (Mejora mejora : mejoras) {
            mejora.update(delta);
            mejora.draw(batch);
        }
        
        for (Mejora mejora : mejorasActivas) {
            mejora.verificarDesactivar(nave, delta);
        }
    }
    
    private void updateDisparo(float delta) {
    	// Verifica si la tecla de disparo está presionada
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            nave.presionarTeclaDisparo(true); // Llama al método con true
        } else {
            nave.presionarTeclaDisparo(false); // Llama al método con false
        }
    }

    private void handleCollisions() {
        if (!nave.estaHerido()) {
            checkBulletCollisions();
            updateAsteroids();
            checkAsteroidCollisions();
            checkNaveAsteroidCollisions();
            checkNaveMejoraCollisions();
            checkBulletMejoraCollisions();
        }
    }

    private void checkBulletCollisions() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < balls1.size(); j++) {
                if (b.checkCollision(balls1.get(j))) {
                    explosionSound.play(0.5f);
                    balls1.remove(j);
                    j--;
                    GameState.getInstance().addScore(10); // Usa el método addScore de GameState para agregar puntos
                    break;
                }
            }

            if (b.isDestroyed()) {
                balas.remove(i);
                i--;
            }
        }
    }

    private void updateAsteroids() {
        for (Ball2 ball : balls1) {
            ball.update();
        }
    }

    private void checkAsteroidCollisions() {
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 ball1 = balls1.get(i);
            for (int j = 0; j < balls1.size(); j++) {
                if (i < j) {
                    Ball2 ball2 = balls1.get(j);
                    ball1.checkCollision(ball2);
                }
            }
        }
    }

    private void checkNaveMejoraCollisions() {
        for (int i = 0; i < mejoras.size(); i++) {
            Mejora mejora = mejoras.get(i);
            if (nave.checkCollision(mejora)) {
            	hurtSound.play();
            	mejora.procesarColision(nave); // Procesar la colisión con la nave
                mejoras.remove(i);
                i--;
            }
        }
    }
    
    private void checkBulletMejoraCollisions() {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();

            for (int j = 0; j < mejoras.size(); j++) {
                Mejora mejora = mejoras.get(j);

                // Si la bala colisiona con la mejora
                if (b.checkCollision(mejora)) {
                    explosionSound.play(0.4f);

                    if (mejora instanceof PlanetaMejora) {
                        PlanetaMejora planeta = (PlanetaMejora) mejora;
                        planeta.recibirImpacto();

                        if (planeta.estaDestruido()) {
                            planeta.activar(nave); // Aplica el efecto de mejora del planeta
                            mejorasActivas.add(mejora);
                            mejoras.remove(j);
                            GameState.getInstance().addScore(50); // Aumenta el puntaje por destruir el planeta
                            j--;
                        }
                    } else if (mejora instanceof EstrellaMejora) {
                        EstrellaMejora estrella = (EstrellaMejora) mejora;
                        estrella.recibirImpacto();
                        
                        if (estrella.estaDestruido()) {
                            estrella.activar(nave); // Aplica el efecto de mejora de la estrella
                            mejorasActivas.add(mejora);
                            mejoras.remove(j);
                            GameState.getInstance().addScore(100); // Aumenta el puntaje por destruir la estrella
                            j--;
                        }
                    }
                    break; // Salir del bucle al procesar la mejora
                }
            }

            // Eliminar la bala si está destruida
            if (b.isDestroyed()) {
                balas.remove(i);
                i--;
            }
        }
        
    }

    private void checkNaveAsteroidCollisions() {
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 ball = balls1.get(i);
            if (nave.checkCollision(ball)) {
                hurtSound.play(); // Sonido de daño
                ball.procesarColision(nave); // Procesar la colisión con la nave
                balls1.remove(i);               
                i--;
            }
        }
    }

    private void drawEntities() {
    	nave.draw(batch, this);
        for (Ball2 ball : balls1) {
            ball.draw(batch);
        }
        for (Bullet b : balas) {
            b.draw(batch);
        }
       
    }

    private void checkGameOver() {
        if (dev.getVidas() <= 0) {
        	if (GameState.getInstance().getScore() > game.getHighScore()) {
        	    game.setHighScore(GameState.getInstance().getScore());
        	}
        	gameMusic.stop();
        	game.setScreen(new PantallaGameOver(game));
        }
    }

    private void checkLevelCompleted() {
        if (balls1.isEmpty()) {
            gameMusic.stop();
            dev.sigRonda();
            contadorMejoras = 0;
            game.setScreen(new PantallaJuego(game, velXAsteroides + INCREMENTO_VELOCIDAD,
                    velYAsteroides + INCREMENTO_VELOCIDAD, cantAsteroides + 3, contadorRondas + 1));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public boolean agregarBala(Bullet bb) {
		return balas.add(bb);
	}


    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        explosionSound.dispose();
        gameMusic.dispose();
        fondo.dispose();
    }
}
