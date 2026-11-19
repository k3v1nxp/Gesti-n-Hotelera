/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 09092026
 */
public class GestorReservas {
     private List<Reserva> listaReservas;
    private int contadorReservas = 1;

    public GestorReservas() {
        this.listaReservas = new ArrayList<>();
    }

    public synchronized boolean registrarReserva(Huesped huesped, Habitacion habitacion, int cantPersonas, 
                                               LocalDate fechaEntrada, LocalDate fechaSalida, Recepcionista recepcionista) {
        LocalDate hoy = LocalDate.now();
        if (fechaEntrada.isBefore(hoy)) {
            throw new IllegalArgumentException("La fecha de entrada debe ser igual o posterior a la fecha actual.");
        }
        if (!fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }
        if (cantPersonas <= 0 || cantPersonas > habitacion.getCapacidadMaxima()) {
            throw new IllegalArgumentException("La cantidad de personas no es válida para la capacidad de la habitación (" + habitacion.getCapacidadMaxima() + ").");
        }

        // Verificar superposición de fechas (RF-04)
        if (existeSuperposicion(habitacion.getN_habitacion(), fechaEntrada, fechaSalida)) {
            throw new IllegalArgumentException("La habitación ya tiene una reserva confirmada u hospedada que se superpone con las fechas solicitadas.");
        }

        String codigo = String.format("R%03d", contadorReservas++);
        Reserva nuevaReserva = new Reserva( codigo, huesped, habitacion, cantPersonas, fechaEntrada, fechaSalida, recepcionista);
        listaReservas.add(nuevaReserva);
        return true;
    }

    private boolean existeSuperposicion(int nHabitacion, LocalDate nuevaEntrada, LocalDate nuevaSalida) {
        for (Reserva r : listaReservas) {
            // Solo bloquean las Confirmadas y Hospedado (RF-04 / RF-05)
            if (r.getHabitacion().getN_habitacion() == nHabitacion && 
                (r.getEstado() == TipoEstado.CONFIRMADA || r.getEstado() == TipoEstado.HOSPEDADO)) {
                
                LocalDate existenteEntrada = r.getFecha_entrada();
                LocalDate existenteSalida = r.getFecha_salida();

                // Regla de superposición: nueva entrada < salida existente Y nueva salida > entrada existente
                boolean seSuperpone = nuevaEntrada.isBefore(existenteSalida) && nuevaSalida.isAfter(existenteEntrada);
                if (seSuperpone) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean realizarCheckIn(String codigoReserva) {
        Reserva r = buscarReserva(codigoReserva);
        if (r == null) throw new IllegalArgumentException("La reserva no existe.");
        if (r.getEstado() != TipoEstado.CONFIRMADA) throw new IllegalArgumentException("Solo se permite check-in a reservas Confirmadas.");
        if (!LocalDate.now().isEqual(r.getFecha_entrada())) throw new IllegalArgumentException("La fecha actual debe coincidir con la fecha de entrada programada.");

        r.setEstado(TipoEstado.HOSPEDADO);
        return true;
    }

    public synchronized boolean realizarCheckOut(String codigoReserva) {
        Reserva r = buscarReserva(codigoReserva);
        if (r == null) throw new IllegalArgumentException("La reserva no existe.");
        if (r.getEstado() != TipoEstado.HOSPEDADO) throw new IllegalArgumentException("Solo se permite check-out a reservas en estado Hospedado.");

        r.setFecha_salidaReal(LocalDate.now());
        r.setEstado(TipoEstado.FINALIZADA);
        return true;
    }

    public Reserva buscarReserva(String codigo) {
        for (Reserva r : listaReservas) {
            if (r.getCodigo().equalsIgnoreCase(codigo)) return r;
        }
        return null;
    }

    public synchronized List<Reserva> getListaReservas() {
        return new ArrayList<>(listaReservas);
    }
    
    
public synchronized void actualizarReservasVencidas(LocalDate hoy) {

    for (Reserva r : listaReservas) {

        if (r.getEstado() == TipoEstado.CONFIRMADA
                && r.getFecha_entrada().isBefore(hoy)) {

            r.setEstado(TipoEstado.VENCIDA);

            System.out.println(
                    "[REVISION INICIAL] Reserva "
                    + r.getCodigo()
                    + " actualizada a VENCIDA."
            );
        }
    }
}

}
