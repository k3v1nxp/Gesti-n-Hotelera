/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.FrmHotel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kevin
 */
public class ControladorSimulacion {
    





    private FrmHotel vista;
    private GestorHotel gestor;

    public ControladorSimulacion(FrmHotel vista) {

        this.vista = vista;
        this.gestor = GestorHotel.getInstance();
    }

    public void simular() {

        try {

            String fechaTexto =
                    vista.getTxtFechaSimulacion()
                            .getText().trim();

            if (fechaTexto.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Ingrese una fecha para realizar la simulación."
                );

                return;
            }

            LocalDate fechaReferencia =
                    LocalDate.parse(fechaTexto);

            if (!fechaReferencia.isAfter(LocalDate.now())) {

                JOptionPane.showMessageDialog(
                        vista,
                        "La fecha de simulación debe ser futura."
                );

                return;
            }

            mostrarResultados(fechaReferencia);

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "La fecha debe tener el formato YYYY-MM-DD."
            );
        }
    }

    private void mostrarResultados(LocalDate fechaReferencia) {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Huésped");
        modelo.addColumn("Entrada");
        modelo.addColumn("Estado en la fecha");

        List<Reserva> reservas =
                gestor.getGestorReservas().getListaReservas();

        for (Reserva r : reservas) {

            String estadoSimulado =
                    r.getEstado().getEstado();

            // Simular vencimiento sin modificar la reserva real
            if (r.getEstado() == TipoEstado.CONFIRMADA
                    && r.getFecha_entrada().isBefore(fechaReferencia)) {

                estadoSimulado = "VENCIDA";
            }

            modelo.addRow(new Object[]{

                r.getCodigo(),

                r.getHuesped().getNombre(),

                r.getFecha_entrada(),

                estadoSimulado
            });
        }

        vista.getTblSimulacion().setModel(modelo);

        JOptionPane.showMessageDialog(
                vista,
                "Simulación realizada correctamente.\n"
                + "Fecha de referencia: " + fechaReferencia
                + "\nLos estados reales no fueron modificados."
        );
    }
}


