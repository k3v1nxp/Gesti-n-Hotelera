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
    }

    @Override
    public void run() {
       for (int i = 1; i <= 5; i++) {

            System.out.println(
                    "[SUPERVISOR] Inicia ejecución (ciclo " + i + " de 5)."
            );

            verificarYVencerReservas();
        
        try {
            while (enEjecucion) {
                  if (i < 5) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("[SUPERVISOR] El hilo supervisor de reservas fue interrumpido.");
           Thread.currentThread().interrupt();
                return;  
        }
          System.out.println(
                "[SUPERVISOR] Finaliza ejecución."
        );
    }
    }

private void verificarYVencerReservas() {

    gestorReservas.actualizarReservasVencidas(
            LocalDate.now()
    );
}

//    public void detener() {
  //      this.enEjecucion = false;
    //} 
}
