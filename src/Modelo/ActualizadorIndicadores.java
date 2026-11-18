/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class ActualizadorIndicadores implements Runnable{
      private volatile boolean enEjecucion;

    public ActualizadorIndicadores() {
        this.enEjecucion = true;
    }

    @Override
    public void run() {
        try {
            while (enEjecucion) {
                // dispara eventos de actualización para la interfaz
                System.out.println("[TRAZA HILO INDICADORES] Actualizando indicadores del hotel en segundo plano...");
                
                // Espera 5 segundos
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("[INDICADORES] El hilo de indicadores fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }

    public void detener() {
        this.enEjecucion = false;
    }
}
