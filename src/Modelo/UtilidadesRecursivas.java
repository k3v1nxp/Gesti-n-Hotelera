/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author 09092026
 */
public class UtilidadesRecursivas {
    public static long calcularIngresosEsperados(List <Reserva> reservas, int indice) {
        // Caso base: si la lista está vacía, es nula o el índice llegó al final creo jajaj
        if (reservas == null || reservas.isEmpty() || indice >= reservas.size()) {
            return 0L;
        }

        Reserva actual = reservas.get(indice);
        long importeActual = 0L;

        // Se suman únicamente las reservas Confirmadas (se excluyen Hospedado, Finalizada y Vencida)[span_1](start_span)[span_1](end_span)
        if (actual.getEstado() == TipoEstado.CONFIRMADA) {
            importeActual = actual.getTotalPagar();
        }

        // Llamada recursiva avanzando a la siguiente posición (índice + 1)
        return importeActual + calcularIngresosEsperados(reservas, indice + 1);
    }

    // Sobrecarga para facilitar la llamada desde el controlador o el hilo ActualizadorIndicadores
    public static long calcularIngresosEsperados(List<Reserva> reservas) {
        return calcularIngresosEsperados(reservas, 0);
    }


    // =========================================================================
    // MÉTODO RECURSIVO 2: Contar habitaciones disponibles (RF-10 & RF-05)
    // Recorre recursivamente las habitaciones y cuenta las que están libres en la fecha.
    // =========================================================================
    public static int contarHabitacionesDisponibles(List<Habitacion> habitaciones, List<Reserva> reservas, LocalDate fechaConsulta, int indice) {
        // Caso base: si la lista de habitaciones está vacía, es nula o el índice llegó al final
        if (habitaciones == null || habitaciones.isEmpty() || indice >= habitaciones.size()) {
            return 0;
        }

        Habitacion habActual = habitaciones.get(indice);
        int esDisponible = 0;

        // Comprobación mediante método auxiliar si la habitación está libre en la fecha
        if (estaHabitacionDisponibleEnFecha(habActual, reservas, fechaConsulta)) {
            esDisponible = 1;
        }

        // Llamada recursiva avanzando a la siguiente habitación (índice + 1)
        return esDisponible + contarHabitacionesDisponibles(habitaciones, reservas, fechaConsulta, indice + 1);
    }

    // Sobrecarga para facilitar la llamada desde la interfaz de consulta de disponibilidad
    public static int contarHabitacionesDisponibles(List<Habitacion> habitaciones, List<Reserva> reservas, LocalDate fechaConsulta) {
        return contarHabitacionesDisponibles(habitaciones, reservas, fechaConsulta, 0);
    }


    // =========================================================================
    // MÉTODO AUXILIAR ITERATIVO: Validación de conflictos de fechas (RF-05)
    // La rúbrica permite usar ciclos dentro de métodos auxiliares para verificar esto.
    // =========================================================================
    private static boolean estaHabitacionDisponibleEnFecha(Habitacion habitacion, List<Reserva> reservas, LocalDate fechaConsulta) {
        if (reservas == null || fechaConsulta == null) {
            return true;
        }

        for (Reserva reserva : reservas) {
            // Validar que la reserva pertenezca a esta habitación
            if (reserva.getHabitacion().getN_habitacion() == habitacion.getN_habitacion()) {
                
                // Solo se consideran bloqueantes las reservas Confirmadas y Hospedado (RF-05)
                if (reserva.getEstado() == TipoEstado.CONFIRMADA || reserva.getEstado() == TipoEstado.HOSPEDADO) {
                    
                    LocalDate entrada = reserva.getFecha_entrada();
                    LocalDate salida = reserva.getFecha_salida();
                    
                    // Reglas de fechas del hotel (RF-05):
                    // - La fecha de entrada se considera ocupada.
                    // - La fecha de salida NO se considera ocupada.
                    // Hay conflicto si: fechaConsulta >= entrada  Y  fechaConsulta < salida
                    boolean incluyeFecha = !fechaConsulta.isBefore(entrada) && fechaConsulta.isBefore(salida);
                    
                    if (incluyeFecha) {
                        return false; // Encontró un bloqueo, la habitación NO está disponible
                    }
                }
            }
        }
        return true;
    } 
}
