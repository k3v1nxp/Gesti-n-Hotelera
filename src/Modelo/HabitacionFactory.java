/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class HabitacionFactory {
     public static Habitacion crearHabitacion (TipoHabitacion tipo, int numero){
        if (tipo == null){
            return null;
        }
        
        switch (tipo){
            
            case INDIVIDUAL:
            return new H_Individual(numero);
            
            case DOBLE:
            return new H_Doble(numero);
            
            case FAMILIAR:
            return new H_Familiar(numero);
            
            default:
                throw new IllegalArgumentException("tipo de habitacion desconocida");
        }
    }
}
