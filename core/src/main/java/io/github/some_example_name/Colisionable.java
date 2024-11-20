package io.github.some_example_name;
import com.badlogic.gdx.math.Rectangle; // Asegúrate de importar Rectangle

public interface Colisionable {
	Rectangle getArea(); // Método que devuelve el área de colisión
	
	void procesarColision(Nave4 nave); // Método para procesar colisiones
	
	
}
