/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestionhotelera;

import Vista.FrmLogin;
import Controlador.ControladorLogin;
import javax.swing.SwingUtilities;

/**
 *
 * @author kevin
 */
public class GestionHotelera {

    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) {

        // Iniciar la interfaz gráfica
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                FrmLogin login = new FrmLogin();

                // Conectar la ventana con su controlador
                ControladorLogin controlador =
                        new ControladorLogin(login);

                login.setLocationRelativeTo(null);
                login.setVisible(true);
            }
        });
    }
}


