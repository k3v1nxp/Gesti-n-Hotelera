/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public abstract class Habitacion {
    int N_habitacion;
    TipoHabitacion tipo;

    public Habitacion(int N_habitacion, TipoHabitacion tipo) {
        this.N_habitacion = N_habitacion;
        this.tipo = tipo;
    }

    public int getN_habitacion() {
        return N_habitacion;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setN_habitacion(int N_habitacion) {
        this.N_habitacion = N_habitacion;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }
    
    
    
    public abstract int getCapacidadMaxima(); 
    public abstract int getTartifa(); 
    
}
