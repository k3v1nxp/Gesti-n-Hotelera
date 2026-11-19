/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author 09092026
 */
public class ActualizadorIndicadores implements Runnable{
     
    private GestorHotel gestorHotel;

    public ActualizadorIndicadores(GestorHotel gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {

            System.out.println(
                    "[INDICADORES] Inicia ejecución (ciclo "
                    + i + " de 5)."
            );

            calcularIndicadores();

            try {
                if (i < 5) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {

                System.out.println(
                        "[INDICADORES] Hilo interrumpido."
                );

                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(
                "[INDICADORES] Finaliza ejecución."
        );
    }

    private void calcularIndicadores() {

        int totalHabitaciones =
                gestorHotel.getGestorHabitaciones()
                        .getListaHabitaciones().size();

        int ocupadas = 0;
        int confirmadas = 0;
        int vencidas = 0;

        List<Reserva> reservas =
                gestorHotel.getGestorReservas()
                        .getListaReservas();

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

        long ingresos =
                UtilidadesRecursivas.calcularIngresosEsperados(reservas);

        System.out.println(
                "[INDICADORES] Total habitaciones: "
                + totalHabitaciones
        );

        System.out.println(
                "[INDICADORES] Habitaciones ocupadas: "
                + ocupadas
        );

        System.out.println(
                "[INDICADORES] Habitaciones disponibles actualmente: "
                + (totalHabitaciones - ocupadas)
        );

        System.out.println(
                "[INDICADORES] Reservas confirmadas: "
                + confirmadas
        );

        System.out.println(
                "[INDICADORES] Reservas vencidas: "
                + vencidas
        );

        System.out.println(
                "[INDICADORES] Ingresos esperados: ₡"
                + ingresos
        );
    }
}
