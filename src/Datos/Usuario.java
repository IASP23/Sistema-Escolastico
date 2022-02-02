
package Datos;

public class Usuario {

    private String cedula;
    private String nombre;
    private String login;
    private String password;
    private String rol;

    public Usuario(String cedula, String nombre, String login, String password, String rol) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.login = login;
        this.password = password;
        this.rol = rol;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario" + "cedula=" + cedula + ", nombre=" + nombre + ", login=" + login + ", password=" + password + ", rol=" + rol;
    }

}
