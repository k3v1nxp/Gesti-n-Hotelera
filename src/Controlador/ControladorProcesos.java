/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.FrmHotel;
import javax.swing.SwingUtilities;
import java.util.List;

/**
 *
 * @author kevin
 */
public class ControladorProcesos {
    
    private FrmHotel vista;
    private GestorHotel gestor;
    private boolean ejecutando;

    public ControladorProcesos(FrmHotel vista) {

        this.vista = vista;
        this.gestor = GestorHotel.getInstance();
        this.ejecutando = false;
    }

    public void ejecutarProcesos() {

        if (ejecutando) {
            return;
        }

        ejecutando = true;

        vista.getBtnEjecutarProcesos().setEnabled(false);
        vista.getBtnEjecutarProcesos().setText("Ejecutando...");

        System.out.println("==================================");
        System.out.println("INICIO DE PROCESOS CONCURRENTES");
        System.out.println("==================================");

        // Crear los dos Runnable
        SupervisorReservas supervisor =
                new SupervisorReservas(
                        gestor.getGestorReservas()
                );

        ActualizadorIndicadores actualizador =
                new ActualizadorIndicadores(gestor);

        // Crear los dos hilos
        Thread hiloSupervisor = new Thread(supervisor);
        Thread hiloIndicadores = new Thread(actualizador);

        // Iniciar los dos hilos sin esperar a que termine el primero
        hiloSupervisor.start();
        hiloIndicadores.start();

        // Este tercer hilo espera que terminen los dos procesos
        Thread hiloFinalizador = new Thread(() -> {

            try {

                hiloSupervisor.join();
                hiloIndicadores.join();

                // Actualizar la interfaz al terminar los dos hilos
                SwingUtilities.invokeLater(() -> {

                    actualizarIndicadores();

                    vista.getBtnEjecutarProcesos().setEnabled(true);
                    vista.getBtnEjecutarProcesos().setText(
                            "Ejecutar procesos"
                    );

                    ejecutando = false;

                    System.out.println(
                            "=================================="
                    );

                    System.out.println(
                            "LOS DOS PROCESOS HAN FINALIZADO"
                    );

                    System.out.println(
                            "=================================="
                    );
                });

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                SwingUtilities.invokeLater(() -> {

                    vista.getBtnEjecutarProcesos().setEnabled(true);
                    vista.getBtnEjecutarProcesos().setText(
                            "Ejecutar procesos"
                    );

                    ejecutando = false;
                });
            }
        });

        hiloFinalizador.start();
    }

    private void actualizarIndicadores() {

        int totalHabitaciones =
                gestor.getGestorHabitaciones()
                        .getListaHabitaciones().size();

        List<Reserva> reservas =
                gestor.getGestorReservas().getListaReservas();

        int ocupadas = 0;
        int confirmadas = 0;
        int vencidas = 0;

        for (Reserva r : reservas) {

            if (r.getEstado() == TipoEstado.HOSPEDADO) {
                ocupadas++;
            }

            if (r.getEstado() == TipoEstado.CONFIRMADA) {
                confirmadas++;
            }

            if (r.getEstado() == TipoEstado.VENCIDA) {
                vencidas++;
            }
        }

        int disponibles = totalHabitaciones - ocupadas;

        long ingresos =
                UtilidadesRecursivas.calcularIngresosEsperados(
                        reservas
                );

        vista.getLblTotalHabitaciones().setText(
                String.valueOf(totalHabitaciones)
        );

        vista.getLblOcupadas().setText(
                String.valueOf(ocupadas)
        );

        vista.getLblDisponibles().setText(
                String.valueOf(disponibles)
        );

        vista.getLblConfirmadas().setText(
                String.valueOf(confirmadas)
        );

        vista.getLblVencidas().setText(
                String.valueOf(vencidas)
        );

        vista.getLblIngresosEsperados().setText(
                "₡" + String.format("%,d", ingresos)
        );
    }
}


