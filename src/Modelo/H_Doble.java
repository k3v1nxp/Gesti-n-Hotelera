/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class H_Doble extends Habitacion {
     public H_Doble(int numero){
        super(numero,TipoHabitacion.DOBLE);
    }

    @Override
    public int getCapacidadMaxima() {
        return 4;
    }

    @Override
    public int getTartifa() {
        return 100000;   
    }
}
