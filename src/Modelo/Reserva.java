/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author 09092026
 */
public class Reserva {
    private String codigo;
    private Huesped huesped;
    private Habitacion habitacion;
    private int cantPersonas;
    private LocalDate fecha_entrada;
     private LocalDate fecha_salida;
     private LocalDate fecha_salidaReal;
     private long cantNoches;
     private long totalPagar;
     private TipoEstado estado;
     private Recepcionista recepcionista;


    public Reserva(String codigo, Huesped huesped, Habitacion habitacion, int cantPersonas, 
            LocalDate fecha_entrada, LocalDate fecha_salida, 
             Recepcionista recepcionista) {
        this.codigo= codigo;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.cantPersonas = cantPersonas;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.fecha_salidaReal = null;
        this.cantNoches = calcularNoches();
        this.totalPagar = calcularTotal();
        this.estado = TipoEstado.CONFIRMADA;
        this.recepcionista = recepcionista;
    }
     
     
    
    private long calcularNoches(){
        if(fecha_entrada != null && fecha_salida != null){
            return ChronoUnit.DAYS.between(fecha_entrada, fecha_salida);
//           
        }
        return 0;
    }
    
    private long calcularTotal(){
        if (habitacion != null){
            return this.cantNoches * habitacion.getTartifa();
        }
        return 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public LocalDate getFecha_entrada() {
        return fecha_entrada;
    }

    public LocalDate getFecha_salida() {
        return fecha_salida;
    }

    public LocalDate getFecha_salidaReal() {
        return fecha_salidaReal;
    }

    public long getCantNoches() {
        return cantNoches;
    }

    public long getTotalPagar() {
        return totalPagar;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public void setId(String codigo) {
        this.codigo= codigo;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public void setFecha_entrada(LocalDate fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
        this.cantNoches = calcularNoches();
        this.totalPagar = calcularTotal();
    }

    public void setFecha_salida(LocalDate fecha_salida) {
        this.fecha_salida = fecha_salida;
        this.cantNoches = calcularNoches();
        this.totalPagar = calcularTotal();
    }

    public void setFecha_salidaReal(LocalDate fecha_salidaReal) {
        this.fecha_salidaReal = fecha_salidaReal;
    }

    public void setCantNoches(long cantNoches) {
        this.cantNoches = cantNoches;
    }

    public void setTotalPagar(long totalPagar) {
        this.totalPagar = totalPagar;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
    }
    
}
