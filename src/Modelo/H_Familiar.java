/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class H_Familiar extends Habitacion{
     public H_Familiar(int numero){
        super(numero, TipoHabitacion.FAMILIAR);
    } 

    @Override
    public int getCapacidadMaxima() {
        return 2;
    }

    @Override
    public int getTartifa() {
        return 50000;
    }
}
