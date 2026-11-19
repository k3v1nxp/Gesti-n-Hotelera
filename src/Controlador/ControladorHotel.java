/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.FrmHotel;
import Vista.FrmLogin;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author kevin
 */
public class ControladorHotel {

    private FrmHotel vista;
    private GestorHotel gestor;

    public ControladorHotel(FrmHotel vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstance();

        conectarEventos();
        cargarDatos();
    }

    private void conectarEventos() {

        vista.getBtnRegistrarHuesped().addActionListener(e -> registrarHuesped());

        vista.getBtnRegistrarHabitacion().addActionListener(e -> registrarHabitacion());

        vista.getBtnCalcularReserva().addActionListener(e -> calcularReserva());

        vista.getBtnRegistrarReserva().addActionListener(e -> registrarReserva());

        vista.getBtnConsultarDisp().addActionListener(e -> consultarDisponibilidad());

        vista.getBtnBuscarReserva().addActionListener(e -> buscarReserva());

        vista.getBtnProcesarCheck().addActionListener(e -> procesarCheck());

        vista.getBtnCambiarUsuario().addActionListener(e -> cambiarUsuario());

        vista.getBtnLimpiarHuesped().addActionListener(e -> limpiarHuesped());

        vista.getBtnEjecutarProcesos().addActionListener(e ->
                new ControladorProcesos(vista).ejecutarProcesos()
        );

        vista.getBtnSimular().addActionListener(e ->
                new ControladorSimulacion(vista).simular()
        );

        vista.getCmbTipoHabitacion().addActionListener(e -> actualizarTipoHabitacion());
    }

    private void cargarDatos() {

        Recepcionista recepcionista = gestor.getSesionActiva();

        if (recepcionista != null) {
            vista.getLblRecepcionista().setText(
                    "Recepcionista: " + recepcionista.getUsuario()
            );
        }

        vista.getCmbTipoHabitacion().removeAllItems();
        vista.getCmbTipoHabitacion().addItem("Individual");
        vista.getCmbTipoHabitacion().addItem("Doble");
        vista.getCmbTipoHabitacion().addItem("Familiar");

        actualizarTipoHabitacion();
        actualizarTablaHuespedes();
        actualizarTablaHabitaciones();
        actualizarTablaReservas();
        cargarCombosReserva();
        actualizarIndicadores();
    }

    // ==========================================
    // HUÉSPEDES
    // ==========================================

    private void registrarHuesped() {

        try {

            String id = vista.getTxtIdHuesped().getText().trim();
            String nombre = vista.getTxtNombreHuesped().getText().trim();
            String telefono = vista.getTxtTelefonoHuesped().getText().trim();
            String correo = vista.getTxtCorreoHuesped().getText().trim();

            gestor.getGestorHuespedes().registrarHuesped(
                    id, nombre, telefono, correo
            );

            actualizarTablaHuespedes();
            cargarCombosReserva();
            limpiarHuesped();

            JOptionPane.showMessageDialog(vista,
                    "Huésped registrado correctamente.");

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(vista,
                    ex.getMessage(),
                    "Error al registrar huésped",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaHuespedes() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Identificación");
        modelo.addColumn("Nombre");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Correo");

        List<Huesped> lista =
                gestor.getGestorHuespedes().getListaHuespedes();

        for (Huesped h : lista) {
            modelo.addRow(new Object[]{
                h.getId(),
                h.getNombre(),
                h.getTelefono(),
                h.getGmail()
            });
        }

        vista.getTablaHuespedes().setModel(modelo);
    }

    private void limpiarHuesped() {

        vista.getTxtIdHuesped().setText("");
        vista.getTxtNombreHuesped().setText("");
        vista.getTxtTelefonoHuesped().setText("");
        vista.getTxtCorreoHuesped().setText("");
    }

    // ==========================================
    // HABITACIONES
    // ==========================================

    private void actualizarTipoHabitacion() {

        if (vista.getCmbTipoHabitacion().getSelectedItem() == null) {
            return;
        }

        String tipo = vista.getCmbTipoHabitacion()
                .getSelectedItem().toString();

        Habitacion habitacion = HabitacionFactory.crearHabitacion(
                obtenerTipoHabitacion(tipo), 0
        );

        if (habitacion != null) {

            vista.getTxtCapacidad().setText(
                    String.valueOf(habitacion.getCapacidadMaxima())
            );

            vista.getTxtTarifa().setText(
                    "₡" + String.format("%,d", habitacion.getTartifa())
            );
        }
    }

    private TipoHabitacion obtenerTipoHabitacion(String tipo) {

        switch (tipo) {

            case "Individual":
                return TipoHabitacion.INDIVIDUAL;

            case "Doble":
                return TipoHabitacion.DOBLE;

            case "Familiar":
                return TipoHabitacion.FAMILIAR;

            default:
                throw new IllegalArgumentException(
                        "Tipo de habitación no válido."
                );
        }
    }

    private void registrarHabitacion() {

        try {

            String numeroTexto = vista.getTxtNumHabitacion().getText().trim();

            if (numeroTexto.isEmpty()) {
                throw new IllegalArgumentException(
                        "Ingrese el número de habitación."
                );
            }

            int numero = Integer.parseInt(numeroTexto);

            if (numero <= 0) {
                throw new IllegalArgumentException(
                        "El número debe ser mayor que cero."
                );
            }

            String tipoTexto = vista.getCmbTipoHabitacion()
                    .getSelectedItem().toString();

            TipoHabitacion tipo = obtenerTipoHabitacion(tipoTexto);

            gestor.getGestorHabitaciones().registrarHabitacion(
                    numero, tipo
            );

            actualizarTablaHabitaciones();
            cargarCombosReserva();

            vista.getTxtNumHabitacion().setText("");

            JOptionPane.showMessageDialog(vista,
                    "Habitación registrada correctamente.");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(vista,
                    "El número de habitación debe ser numérico.");

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(vista,
                    ex.getMessage());
        }
    }

    private void actualizarTablaHabitaciones() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Número");
        modelo.addColumn("Tipo");
        modelo.addColumn("Capacidad");
        modelo.addColumn("Tarifa");

        List<Habitacion> lista =
                gestor.getGestorHabitaciones().getListaHabitaciones();

        for (Habitacion h : lista) {

            modelo.addRow(new Object[]{
                h.getN_habitacion(),
                h.getTipo().getTipo(),
                h.getCapacidadMaxima(),
                h.getTartifa()
            });
        }

        vista.getTblHabitaciones().setModel(modelo);
    }

    // ==========================================
    // COMBOS DE RESERVAS
    // ==========================================

    private void cargarCombosReserva() {

        vista.getCmbHuespedReserva().removeAllItems();

        List<Huesped> huespedes =
                gestor.getGestorHuespedes().getListaHuespedes();

        for (Huesped h : huespedes) {

            vista.getCmbHuespedReserva().addItem(
                    h.getId() + " - " + h.getNombre()
            );
        }

        vista.getCmbHabitacionReserva().removeAllItems();

        List<Habitacion> habitaciones =
                gestor.getGestorHabitaciones().getListaHabitaciones();

        for (Habitacion h : habitaciones) {

            vista.getCmbHabitacionReserva().addItem(
                    h.getN_habitacion() + " - " + h.getTipo().getTipo()
            );
        }
    }

    // ==========================================
    // RESERVAS
    // ==========================================

    private Huesped obtenerHuespedSeleccionado() {

        int indice = vista.getCmbHuespedReserva().getSelectedIndex();

        List<Huesped> lista =
                gestor.getGestorHuespedes().getListaHuespedes();

        if (indice < 0 || indice >= lista.size()) {
            throw new IllegalArgumentException(
                    "Seleccione un huésped."
            );
        }

        return lista.get(indice);
    }

    private Habitacion obtenerHabitacionSeleccionada() {

        int indice = vista.getCmbHabitacionReserva().getSelectedIndex();

        List<Habitacion> lista =
                gestor.getGestorHabitaciones().getListaHabitaciones();

        if (indice < 0 || indice >= lista.size()) {
            throw new IllegalArgumentException(
                    "Seleccione una habitación."
            );
        }

        return lista.get(indice);
    }

    private void calcularReserva() {

        try {

            Habitacion habitacion = obtenerHabitacionSeleccionada();

            LocalDate entrada = LocalDate.parse(
                    vista.getTxtFechaEntrada().getText().trim()
            );

            LocalDate salida = LocalDate.parse(
                    vista.getTxtFechaSalida().getText().trim()
            );

            int personas = Integer.parseInt(
                    vista.getTxtCantPersonas().getText().trim()
            );

            if (!salida.isAfter(entrada)) {
                throw new IllegalArgumentException(
                        "La salida debe ser posterior a la entrada."
                );
            }

            if (personas <= 0 ||
                    personas > habitacion.getCapacidadMaxima()) {
                throw new IllegalArgumentException(
                        "Cantidad de personas no válida."
                );
            }

            long noches = java.time.temporal.ChronoUnit.DAYS.between(
                    entrada, salida
            );

            long importe = noches * habitacion.getTartifa();

            vista.getTxtNoches().setText(String.valueOf(noches));

            vista.getTxtImporte().setText(
                    "₡" + String.format("%,d", importe)
            );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(vista,
                    "Utilice el formato de fecha YYYY-MM-DD.");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(vista,
                    "Revise la cantidad de personas.");

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(vista,
                    ex.getMessage());
        }
    }

    private void registrarReserva() {

        try {

            Huesped huesped = obtenerHuespedSeleccionado();

            Habitacion habitacion = obtenerHabitacionSeleccionada();

            int personas = Integer.parseInt(
                    vista.getTxtCantPersonas().getText().trim()
            );

            LocalDate entrada = LocalDate.parse(
                    vista.getTxtFechaEntrada().getText().trim()
            );

            LocalDate salida = LocalDate.parse(
                    vista.getTxtFechaSalida().getText().trim()
            );

            Recepcionista recepcionista = gestor.getSesionActiva();

            if (recepcionista == null) {
                throw new IllegalArgumentException(
                        "No existe una sesión activa."
                );
            }

            gestor.getGestorReservas().registrarReserva(
                    huesped,
                    habitacion,
                    personas,
                    entrada,
                    salida,
                    recepcionista
            );

            actualizarTablaReservas();
            actualizarIndicadores();

            JOptionPane.showMessageDialog(vista,
                    "Reserva registrada correctamente.");

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(vista,
                    "Las fechas deben tener formato YYYY-MM-DD.");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(vista,
                    "La cantidad de personas debe ser numérica.");

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(vista,
                    ex.getMessage());
        }
    }

    private void actualizarTablaReservas() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Código");
        modelo.addColumn("Habitación");
        modelo.addColumn("Entrada");
        modelo.addColumn("Salida");
        modelo.addColumn("Estado");

        List<Reserva> lista =
                gestor.getGestorReservas().getListaReservas();

        for (Reserva r : lista) {

            modelo.addRow(new Object[]{
                r.getCodigo(),
                r.getHabitacion().getN_habitacion(),
                r.getFecha_entrada(),
                r.getFecha_salida(),
                r.getEstado().getEstado()
            });
        }

        vista.getTblReservas().setModel(modelo);
    }

    // ==========================================
    // DISPONIBILIDAD
    // ==========================================

    private void consultarDisponibilidad() {

        try {

            LocalDate fecha = LocalDate.parse(
                    vista.getTxtFechaConsulta().getText().trim()
            );

            List<Habitacion> habitaciones =
                    gestor.getGestorHabitaciones().getListaHabitaciones();

            List<Reserva> reservas =
                    gestor.getGestorReservas().getListaReservas();

            int cantidad = UtilidadesRecursivas
                    .contarHabitacionesDisponibles(
                            habitaciones, reservas, fecha
                    );

            vista.getLblCantDisponibles().setText(
                    "Habitaciones disponibles: " + cantidad
            );

            String texto = "";

            for (Habitacion h : habitaciones) {

                boolean disponible = true;

                for (Reserva r : reservas) {

                    if (r.getHabitacion().getN_habitacion()
                            == h.getN_habitacion()) {

                        if (r.getEstado() == TipoEstado.CONFIRMADA ||
                                r.getEstado() == TipoEstado.HOSPEDADO) {

                            if (!fecha.isBefore(r.getFecha_entrada()) &&
                                    fecha.isBefore(r.getFecha_salida())) {

                                disponible = false;
                            }
                        }
                    }
                }

                if (disponible) {
                    texto += h.getN_habitacion() + "\n";
                }
            }

            vista.getTxtListaDisponibles().setText(texto);

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(vista,
                    "Ingrese una fecha válida: YYYY-MM-DD.");
        }
    }

    // ==========================================
    // CHECK-IN / CHECK-OUT
    // ==========================================

    private void buscarReserva() {

        String codigo = vista.getTxtBuscarCodigo()
                .getText().trim();

        Reserva reserva =
                gestor.getGestorReservas().buscarReserva(codigo);

        if (reserva == null) {

            JOptionPane.showMessageDialog(vista,
                    "No se encontró la reserva.");

            return;
        }

        String detalles =
                "Huésped: " + reserva.getHuesped().getNombre() +
                "\nHabitación: " + reserva.getHabitacion().getN_habitacion() +
                "\nEntrada: " + reserva.getFecha_entrada() +
                "\nSalida: " + reserva.getFecha_salida() +
                "\nEstado: " + reserva.getEstado().getEstado();

        JOptionPane.showMessageDialog(vista, detalles);
    }

    private void procesarCheck() {

        try {

            String codigo = vista.getTxtBuscarCodigo()
                    .getText().trim();

            if (vista.getRbCheckIn().isSelected()) {

                gestor.getGestorReservas().realizarCheckIn(codigo);

                JOptionPane.showMessageDialog(vista,
                        "Check-in realizado correctamente.");

            } else if (vista.getRbCheckOut().isSelected()) {

                gestor.getGestorReservas().realizarCheckOut(codigo);

                JOptionPane.showMessageDialog(vista,
                        "Check-out realizado correctamente.");

            } else {

                JOptionPane.showMessageDialog(vista,
                        "Seleccione Check-in o Check-out.");

                return;
            }

            actualizarTablaReservas();
            actualizarIndicadores();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(vista,
                    ex.getMessage());
        }
    }

    // ==========================================
    // INDICADORES
    // ==========================================

    public void actualizarIndicadores() {

        List<Habitacion> habitaciones =
                gestor.getGestorHabitaciones().getListaHabitaciones();

        List<Reserva> reservas =
                gestor.getGestorReservas().getListaReservas();

        int total = habitaciones.size();
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

        long ingresos =
                UtilidadesRecursivas.calcularIngresosEsperados(reservas);

        vista.getLblTotalHabitaciones().setText(String.valueOf(total));
        vista.getLblOcupadas().setText(String.valueOf(ocupadas));
        vista.getLblDisponibles().setText(
                String.valueOf(total - ocupadas)
        );
        vista.getLblConfirmadas().setText(String.valueOf(confirmadas));
        vista.getLblVencidas().setText(String.valueOf(vencidas));

        vista.getLblIngresosEsperados().setText(
                "₡" + String.format("%,d", ingresos)
        );
    }

    // ==========================================
    // CAMBIO DE USUARIO
    // ==========================================

    private void cambiarUsuario() {

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea cerrar la sesión actual y cambiar de usuario?",
                "Cambiar usuario",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            gestor.cerrarSesion();

            FrmLogin login = new FrmLogin();

            new ControladorLogin(login);

            login.setLocationRelativeTo(null);
            login.setVisible(true);

            vista.dispose();
        }
    }
}
    
 
