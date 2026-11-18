/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author 09092026
 */
public class Recepcionista {
      String usuario;
    String nombre;
    String contraseña;

    public Recepcionista(String usuario, String nombre, String contraseña) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public void setUsuario( String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return  usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }
    
    
}
