/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public enum TipoHabitacion {
    INDIVIDUAL("Individual"),
   DOBLE("Doble"),
   FAMILIAR("Familiar");
   
   private final String tipo;

    private TipoHabitacion(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
   
}
