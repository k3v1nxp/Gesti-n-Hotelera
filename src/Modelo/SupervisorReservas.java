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
public class SupervisorReservas implements Runnable {
     private GestorReservas gestorReservas;
    private volatile boolean enEjecucion;

    public SupervisorReservas(GestorReservas gestorReservas) {
        this.gestorReservas = gestorReservas;
        this.enEjecucion = true;
    }

    @Override
    public void run() {
        try {
            while (enEjecucion) {
                verificarYVencerReservas();
                
                // El hilo espera 10 segundos antes de la siguiente revisión
                Thread.sleep(10000); 
            }
        } catch (InterruptedException e) {
            System.out.println("[SUPERVISOR] El hilo supervisor de reservas fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void verificarYVencerReservas() {
        LocalDate hoy = LocalDate.now();
        List<Reserva> reservas = gestorReservas.getListaReservas();

        for (Reserva r : reservas) {
            // Si la reserva está Confirmada y la fecha de entrada es anterior a hoy
            if (r.getEstado() == TipoEstado.CONFIRMADA) {
                if (r.getFecha_entrada().isBefore(hoy)) {
                    r.setEstado(TipoEstado.VENCIDA);
                    // Trazas en consola requeridas
                    System.out.println("[TRAZA HILO SUPERVISOR] La reserva con código " + r.getCodigo() + 
                                       " ha cambiado a estado VENCIDA. Fecha de entrada era: " + r.getFecha_entrada());
                }
            }
        }
    }

    public void detener() {
        this.enEjecucion = false;
    } 
}
