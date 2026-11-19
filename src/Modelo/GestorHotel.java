/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 09092026
 */
public class GestorHotel {
  private static GestorHotel instancia;

    private GestorHuespedes gestorHuespedes;
    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;

    private List<Recepcionista> recepcionistas;
    private Recepcionista sesionActiva;

    private GestorHotel() {
        this.gestorHuespedes = new GestorHuespedes();
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorReservas = new GestorReservas();

        // Inicializar usuarios predefinidos (RF-01)
        this.recepcionistas = new ArrayList<>();
       this.recepcionistas.add(new Recepcionista(
    "recepcion1", "Recepcionista 1", "Hotel123"));

this.recepcionistas.add(new Recepcionista(
    "recepcion2", "Recepcionista 2", "Hotel456"));
        this.sesionActiva = null;
    }

    public static synchronized GestorHotel getInstance() {
        if (instancia == null) {
            instancia = new GestorHotel();
        }
        return instancia;
    }

public synchronized boolean iniciarSesion(String usuario, String contrasena) {

    if (sesionActiva != null) {
        return false;
    }

    for (Recepcionista r : recepcionistas) {

        if (r.getUsuario().equals(usuario)
                && r.getContraseña().equals(contrasena)) {

            sesionActiva = r;
            return true;
        }
    }

    return false;
}//para evitar que se inicie otra sesión mientras existe una activa

    public synchronized void cerrarSesion() {
        this.sesionActiva = null;
    }

    public Recepcionista getSesionActiva() {
        return sesionActiva;
    }

    public GestorHuespedes getGestorHuespedes() {
        return gestorHuespedes;
    }

    public GestorHabitaciones getGestorHabitaciones() {
        return gestorHabitaciones;
    }

    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }   
}
