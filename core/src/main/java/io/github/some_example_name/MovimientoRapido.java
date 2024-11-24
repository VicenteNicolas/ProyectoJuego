package io.github.some_example_name;

public class MovimientoRapido implements EstrategiaMovimiento {
	
    @Override
    public void mover(Nave4 nave) {
    	nave.getMovimientoNave().setVelocidad(1.5f); // Establece una velocidad más alta
    }

}
