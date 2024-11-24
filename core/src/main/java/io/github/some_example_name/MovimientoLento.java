package io.github.some_example_name;

public class MovimientoLento implements EstrategiaMovimiento {
    @Override
    public void mover(Nave4 nave) {
    	nave.getMovimientoNave().setVelocidad(0.5f); // Establece una velocidad más baja
    } 
}
