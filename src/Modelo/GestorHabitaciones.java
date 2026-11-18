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
public class GestorHabitaciones {
   private List<Habitacion> listaHabitaciones;

    public GestorHabitaciones() {
        this.listaHabitaciones = new ArrayList<>();
    }

    public synchronized boolean registrarHabitacion(int nHabitacion, TipoHabitacion tipo) {
        // Validar número único
        if (buscarHabitacion(nHabitacion) != null) {
            throw new IllegalArgumentException("Ya existe una habitación registrada con el número: " + nHabitacion);
        }

        Habitacion nuevaHabitacion = HabitacionFactory.crearHabitacion(tipo, nHabitacion);
        if (nuevaHabitacion != null) {
            listaHabitaciones.add(nuevaHabitacion);
            return true;
        }
        return false;
    }

    public Habitacion buscarHabitacion(int nHabitacion) {
        for (Habitacion h : listaHabitaciones) {
            if (h.getN_habitacion() == nHabitacion) {
                return h;
            }
        }
        return null;
    }

    public synchronized List<Habitacion> getListaHabitaciones() {
        return new ArrayList<>(listaHabitaciones);
    }   
}
