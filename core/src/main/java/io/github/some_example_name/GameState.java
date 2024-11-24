package io.github.some_example_name;

public class GameState {
    // Instancia única de GameState
    private static GameState instance;

    // Atributos del estado global
    private int score;
    private int vidas;
    private int ronda;
    private int highScore;

    // Constructor privado para evitar creación directa
    private GameState() {
        this.score = 0;
        this.vidas = 3;
        this.ronda = 1;
        this.highScore = 0;
    }

    // Método para obtener la instancia única
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    // Métodos para acceder y modificar el estado
    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
    
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int newHighScore) {
        highScore = newHighScore;
    }

    public int getVidas() {
        return vidas;
    }
    
    public void setVidas(int vidas) {
        this.vidas = vidas;
        
    }
    
    public static void nuevasVidas(int vidas) {
        getInstance().setVidas(vidas);
    }
    

    public void reducirVida() {
        if (vidas > 0) {
            this.vidas--;
        }
    }

    public int getRonda() {
        return ronda;
    }

    public void sigRonda() {
        this.ronda++;
    }

    public void resetGame() {
        this.score = 0;
        this.vidas = 3;
        this.ronda = 1;
    }
}

