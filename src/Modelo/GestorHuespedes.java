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
public class GestorHuespedes {
    private List<Huesped> listaHuespedes;
    
    public GestorHuespedes(){
        this.listaHuespedes = new ArrayList<>();
    }
    
      public synchronized boolean registrarHuesped(String identificacion, String nombre, String telefono, String correo) {
        // Validaciones obligatorias (RF-02)
        if (identificacion == null || identificacion.trim().isEmpty() ||
            nombre == null || nombre.trim().isEmpty() ||
            telefono == null || telefono.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos de huésped son obligatorios.");
        }

        // Validar identificación única
        if (buscarHuesped(identificacion) != null) {
            throw new IllegalArgumentException("Ya existe un huésped registrado con la identificación: " + identificacion);
        }

        // Validar formato de correo (@ y al menos un punto posterior)
        if (!validarCorreo(correo)) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }

        Huesped Huesped = new Huesped(identificacion, nombre, telefono, correo);
        listaHuespedes.add(Huesped);
        return true;
    }

    public Huesped buscarHuesped(String identificacion) {
        for (Huesped h : listaHuespedes) {
            if (h.getId().equals(identificacion)) {
                return h;
            }
        }
        return null;
    }

    public synchronized List<Huesped> getListaHuespedes() {
        return new ArrayList<>(listaHuespedes);
    }

    private boolean validarCorreo(String correo) {
        int atIndex = correo.indexOf('@');
        if (atIndex <= 0 || correo.indexOf('@', atIndex + 1) != -1) {
            return false;
        }
        String despuesAt = correo.substring(atIndex + 1);
        return despuesAt.contains(".") && !despuesAt.startsWith(".") && !despuesAt.endsWith(".");
    } 
}
