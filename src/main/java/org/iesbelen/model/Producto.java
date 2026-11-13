package org.iesbelen.model;

public class Producto {

    private int codigo;
    private String nombre;
    private int codigoFabricante;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getCodigoFabricante() {
        return codigoFabricante;
    }
    public void setCodigoFabricante(int codigoFabricante) {
        this.codigoFabricante = codigoFabricante;
    }
    @Override
    public String toString() {
        return "Producto [codigo=" + codigo + ", nombre=" + nombre
                + ", codigoFabricante=" + codigoFabricante + "]";
    }
}
