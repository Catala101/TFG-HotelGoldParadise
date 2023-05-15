package com.example.hotelgoldparadise_albertoc;

import java.io.Serializable;

public class Cliente {
    String nombre;
    String dni;
    String num_residentes;
    String num_dias;
    String habitacion;
    String num_habitacion;
    String precio;
    int imageId = R.drawable.perfil;

    public Cliente() {

    }

    public Cliente(String nombre, String dni, String num_residentes, String num_dias, String habitacion, String num_habitacion, String precio) {
        this.nombre = nombre;
        this.dni = dni;
        this.num_residentes = num_residentes;
        this.num_dias = num_dias;
        this.habitacion = habitacion;
        this.num_habitacion = num_habitacion;
        this.precio = precio;
    }

    public Cliente(String nombre, String dni, String num_residentes, String num_dias, String habitacion, String num_habitacion, String precio, int imageId) {
        this.nombre = nombre;
        this.dni = dni;
        this.num_residentes = num_residentes;
        this.num_dias = num_dias;
        this.habitacion = habitacion;
        this.num_habitacion = num_habitacion;
        this.precio = precio;
        this.imageId = imageId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getNum_residentes() {
        return num_residentes;
    }

    public String getNum_dias() {
        return num_dias;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public String getNum_habitacion() {
        return num_habitacion;
    }

    public String getPrecio() {
        return precio;
    }

    public int getImageId() {
        return imageId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNum_residentes(String num_residentes) {
        this.num_residentes = num_residentes;
    }

    public void setNum_dias(String num_dias) {
        this.num_dias = num_dias;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public void setNum_habitacion(String num_habitacion) {
        this.num_habitacion = num_habitacion;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
