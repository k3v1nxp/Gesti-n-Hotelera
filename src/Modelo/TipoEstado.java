/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public enum TipoEstado {
      CONFIRMADA("CONFIRMADA"),
   HOSPEDADO("HOSPEDADO"),
   FINALIZADA("FINALIZADA"),
   VENCIDA("VENCIDA");
   
   private final String estado;

    private TipoEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
   
}
