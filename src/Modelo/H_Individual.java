/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class H_Individual extends Habitacion {
     public H_Individual(int numero){
        super(numero,TipoHabitacion.INDIVIDUAL);
    }
    
    @Override
    public int getCapacidadMaxima() {
        return 1;
    }

    @Override
    public int getTartifa() {
        return 25000;
    }

}
