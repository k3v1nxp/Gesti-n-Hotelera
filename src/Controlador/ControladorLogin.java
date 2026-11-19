/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Vista.FrmHotel;
import Vista.FrmLogin;
import javax.swing.JOptionPane;
import java.time.LocalDate;
/**
 *
 * @author kevin
 */
public class ControladorLogin {


    private FrmLogin vista;
    private GestorHotel gestor;

    public ControladorLogin(FrmLogin vista) {
        this.vista = vista;
        this.gestor = GestorHotel.getInstance();

        // Conectar el botón de iniciar sesión
        this.vista.getBtnIniciarSesion().addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {

        String usuario = vista.getTxtUsuario().getText().trim();

        String contrasena = new String(
                vista.getTxtContrasena().getPassword()
        );

        if (usuario.isEmpty() || contrasena.trim().isEmpty()) {
            vista.getLblMensaje().setText("Complete todos los campos.");
            return;
        }

        if (gestor.iniciarSesion(usuario, contrasena)) {
            gestor.getGestorReservas().actualizarReservasVencidas(
        LocalDate.now()
);
            vista.getLblMensaje().setText("");

            // Revisar las reservas vencidas al iniciar sesión
            // Esta revisión debe utilizar la fecha actual.

            FrmHotel ventanaHotel = new FrmHotel();

            ControladorHotel controladorHotel =
                    new ControladorHotel(ventanaHotel);

            ventanaHotel.setLocationRelativeTo(null);
            ventanaHotel.setVisible(true);

            vista.dispose();

            JOptionPane.showMessageDialog(
                    ventanaHotel,
                    "La revisión inicial de reservas vencidas se realizó correctamente.",
                    "Inicio de sesión",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            vista.getLblMensaje().setText(
                    "Usuario o contraseña incorrectos."
            );

            vista.getTxtContrasena().setText("");
        }
    }
}


