package com.example.hotelgoldparadise_albertoc;
public class Room {
    private String numeroHabitacion;
    private String tipoHabitacion;
    private String estado;
    private int capacidad;
    private double precio; // Nuevo atributo

    public Room(String numeroHabitacion, String tipoHabitacion, String estado, int capacidad, double precio) { // Actualizar el constructor
        this.numeroHabitacion = numeroHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.estado = estado;
        this.capacidad = capacidad;
        this.precio = precio; // Nueva línea
    }

    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecio() { // Nuevo método getter
        return precio;
    }

    public void setPrecio(double precio) { // Nuevo método setter
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numeroHabitacion='" + numeroHabitacion + '\'' +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                ", estado='" + estado + '\'' +
                ", capacidad=" + capacidad +
                ", precio=" + precio + // Nueva línea
                '}';
    }
}

