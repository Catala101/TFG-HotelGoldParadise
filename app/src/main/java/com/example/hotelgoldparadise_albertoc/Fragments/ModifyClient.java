package com.example.hotelgoldparadise_albertoc.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelgoldparadise_albertoc.Cliente;
import com.example.hotelgoldparadise_albertoc.Data.ClientDbHelper;
import com.example.hotelgoldparadise_albertoc.R;


public class ModifyClient extends Fragment implements View.OnClickListener{
    String textmulti, nombreDevuelto, dniDevuelto, nomHabitacionDevuelto, numHabitacionDevuelto, cantidadDiasString, cantidadResidentesString, precioTotal;
    TextView textoPrincipal, botonBuscar, botonModify, botonResidentesMasModify, botonResidentesMenosModify, botonDiasMasModify, botonDiasMenosModify, campOpciones, editBuscarDni, campoNombre, campoDni, campoNomHabitacion, campoNumHabitacion, campoPrecio, campoCantidadDias, campoCantidadResidentes;
    ClientDbHelper clientDbHelper;
    Typeface face;
    Toast vacioHabitacion, textItem, existente, modificacion, existe, vacio;
    int cantResidentes=0;
    int cantDias=0;
    int priceIndividual=115;
    int priceDoble=200;
    int priceTriple=299;
    int priceQuad=400;
    int multi=0;
    int numHabitacion, numResidentes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layaut para este fragmento
        View view = inflater.inflate(R.layout.fragment_modify_client, container, false);

        //Edits:
        editBuscarDni = view.findViewById(R.id.editDnis);
        campoNombre = view.findViewById(R.id.editNombreModify);
        campoDni = view.findViewById(R.id.editDniModify);
        campoNumHabitacion = view.findViewById(R.id.numHabitacionModify);
        campoNomHabitacion = view.findViewById(R.id.nomHabitacionModify);
        //TextViews
        campOpciones = view.findViewById(R.id.nomHabitacionModify);
        campoPrecio = view.findViewById(R.id.textoPrecioModify);
        campoCantidadDias = view.findViewById(R.id.cantidadDiasModify);
        campoCantidadResidentes = view.findViewById(R.id.cantidadResidentesModify);
        textoPrincipal = view.findViewById(R.id.textoPrincipalModify);
        //Fuente de Letra
        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Golden Ranger.ttf");
        textoPrincipal.setTypeface(face);
        //Botones
        botonBuscar = view.findViewById(R.id.botonBuscar);
        botonModify = view.findViewById(R.id.botonModify);
        botonResidentesMasModify = view.findViewById(R.id.botonResidentesMasModify);
        botonResidentesMenosModify = view.findViewById(R.id.botonResidentesMenosModify);
        botonDiasMasModify = view.findViewById(R.id.botonDiasMasModify);
        botonDiasMenosModify = view.findViewById(R.id.botonDiasMenosModify);
        botonBuscar.setOnClickListener(this);
        botonModify.setOnClickListener(this);
        botonResidentesMasModify.setOnClickListener(this);
        botonResidentesMenosModify.setOnClickListener(this);
        botonDiasMasModify.setOnClickListener(this);
        botonDiasMenosModify.setOnClickListener(this);

        clientDbHelper= new ClientDbHelper(getContext());



        return view;
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botonResidentesMasModify:
                increment(view);
                break;
            case R.id.botonResidentesMenosModify:
                decrease(view);
                break;
            case R.id.botonDiasMasModify:
                increment(view);
                break;
            case R.id.botonDiasMenosModify:
                decrease(view);
                break;
            case R.id.botonBuscar:
                verifyInformation2();
                break;
            case R.id.botonModify:
                verifyInformation1();
                break;

        }
    }
    public void verifyInformation1(){
        nombreDevuelto = campoNombre.getText().toString();
        dniDevuelto = campoDni.getText().toString();
        nomHabitacionDevuelto = campoNomHabitacion.getText().toString();
        numHabitacionDevuelto = campoNumHabitacion.getText().toString();
        cantidadDiasString = campoCantidadDias.getText().toString();
        cantidadResidentesString = campoCantidadResidentes.getText().toString();
        precioTotal = campoPrecio.getText().toString();
        if (nombreDevuelto.equalsIgnoreCase("")){
            Toast vacioNom = Toast.makeText(getContext(), "Deberá introducir un nombre para continuar", Toast.LENGTH_SHORT);
            vacioNom.show();
        }else if (dniDevuelto.equalsIgnoreCase("")){
            Toast vacioDni = Toast.makeText(getContext(), "Deberá introducir el dni para continuar", Toast.LENGTH_SHORT);
            vacioDni.show();
        }else if (numHabitacionDevuelto.equalsIgnoreCase("")){
            vacioHabitacion = Toast.makeText(getContext(), "Deberá introducir el número de habitación para continuar", Toast.LENGTH_SHORT);
            vacioHabitacion.show();
        }else if (cantidadDiasString.equalsIgnoreCase("0")){
            Toast vacioDias = Toast.makeText(getContext(), "Deberá introducir el número de días para continuar", Toast.LENGTH_SHORT);
            vacioDias.show();
        }else if (cantidadResidentesString.equalsIgnoreCase("0")){
            Toast vacioResidentes = Toast.makeText(getContext(), "Deberá introducir el número de residentes para continuar", Toast.LENGTH_SHORT);
            vacioResidentes.show();
        }else if (nomHabitacionDevuelto.equalsIgnoreCase("Individual (1-9)") || nomHabitacionDevuelto.equalsIgnoreCase("Double (10-19)") || nomHabitacionDevuelto.equalsIgnoreCase("Triple (20-29)") || nomHabitacionDevuelto.equalsIgnoreCase("Quad (30-39)")){
            busquedaDniRepBaseDatos();
        }else{
            vacioHabitacion = Toast.makeText(getContext(), "Deberá introducir el tipo de habitación para continuar (Individual, Double, Triple, Quad)", Toast.LENGTH_SHORT);
            vacioHabitacion.show();
        }
    }

    public void verifyhabitaciones(){
        numHabitacion = Integer.parseInt(numHabitacionDevuelto);
        numResidentes = Integer.parseInt(cantidadResidentesString);
        nomHabitacionDevuelto = campoNomHabitacion.getText().toString();


        if(nomHabitacionDevuelto.equalsIgnoreCase("Individual (1-9)")){
            if(numResidentes>1){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (1) ", Toast.LENGTH_SHORT);
                vacio.show();
            }else{
                if (numHabitacion>=1 && numHabitacion<=9){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (1-9) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    campoNumHabitacion.setText("");
                }
            }

        }else if(nomHabitacionDevuelto.equalsIgnoreCase("Doble (10-19)")){
            if(numResidentes>2){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (1-2) ", Toast.LENGTH_SHORT);
                vacio.show();
            }else{
                if (numHabitacion>=10 && numHabitacion<=19){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (10-19) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    campoNumHabitacion.setText("");
                }
            }
        }else if(nomHabitacionDevuelto.equalsIgnoreCase("Triple (20-29)")){
            if(numResidentes>3){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (1-3) ", Toast.LENGTH_SHORT);
                vacio.show();
            }else{
                if (numHabitacion>=20 && numHabitacion<=29){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (20-29) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    campoNumHabitacion.setText("");
                }
            }
        }else if(nomHabitacionDevuelto.equalsIgnoreCase("Quad (30-39)")){
            if(numResidentes>4){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (1-4) ", Toast.LENGTH_SHORT);
                vacio.show();
            }else{
                if (numHabitacion>=30 && numHabitacion<=39){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (30-39) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    campoNumHabitacion.setText("");
                }
            }
        }
    }


    public void increment(View view){
        switch (view.getId()){
            case R.id.botonResidentesMasModify:
                if (cantResidentes<4) {
                    cantResidentes = cantResidentes + 1;
                    String cantR = Integer.toString(cantResidentes);
                    campoCantidadResidentes.setText(cantR);
                }
                break;
            case R.id.botonDiasMasModify:
                if (cantDias<31) {
                    cantDias = cantDias + 1;
                    String cantD = Integer.toString(cantDias);
                    campoCantidadDias.setText(cantD);
                    nomHabitacionDevuelto = campoNomHabitacion.getText().toString();
                    calcularPorHabitacion();
                }
                break;
        }

    }
    public void decrease(View view){
        switch (view.getId()){
            case R.id.botonResidentesMenosModify:
                if (cantResidentes>0) {
                    cantResidentes = cantResidentes - 1;
                    String cantR = Integer.toString(cantResidentes);
                    campoCantidadResidentes.setText(cantR);
                }
                break;
            case R.id.botonDiasMenosModify:
                if (cantDias>0) {
                    cantDias = cantDias - 1;
                    String cantD = Integer.toString(cantDias);
                    campoCantidadDias.setText(cantD);
                    nomHabitacionDevuelto = campoNomHabitacion.getText().toString();
                    calcularPorHabitacion();
                }
                break;
        }

    }
    public void calcularPorHabitacion() {
        if(nomHabitacionDevuelto.equalsIgnoreCase("Individual (1-9)")){
            calculatePriceIndividual();

        }else if (nomHabitacionDevuelto.equalsIgnoreCase("Double (10-19)")){
            calculatePriceDoble();

        }else if (nomHabitacionDevuelto.equalsIgnoreCase("Triple (20-29)")){
            calculatePriceTriple();

        }else if (nomHabitacionDevuelto.equalsIgnoreCase("Quad (30-39)")){
            calculatePriceQuad();

        }else{
            textItem = Toast.makeText(getContext(), "Habitación no seleccionada", Toast.LENGTH_SHORT);
            textItem.show();
        }
    }
    public void calculatePriceIndividual() {
        multi = priceIndividual * cantDias;
        textmulti = Integer.toString(multi);
        campoPrecio.setText(textmulti);
    }
    public void calculatePriceDoble() {
        multi = priceDoble * cantDias;
        textmulti = Integer.toString(multi);
        campoPrecio.setText(textmulti);
    }
    public void calculatePriceTriple() {
        multi = priceTriple * cantDias;
        textmulti = Integer.toString(multi);
        campoPrecio.setText(textmulti);
    }
    public void calculatePriceQuad() {
        multi = priceQuad * cantDias;
        textmulti = Integer.toString(multi);
        campoPrecio.setText(textmulti);
    }

    public void verifyInformation2() {
        dniDevuelto = editBuscarDni.getText().toString();
        if (dniDevuelto.equalsIgnoreCase("")){
            existente =  Toast.makeText(getContext(), "Introduzca un DNI existente", Toast.LENGTH_LONG);
            existente.show();
        }else{
            busquedaDniBaseDatos();
        }
    }
    public void busquedaDniBaseDatos(){
        Cliente clientes=new Cliente();
        clientDbHelper.buscarCliente(clientes, editBuscarDni.getText().toString());
        existe =  Toast.makeText(getContext(), "Buscando DNI...", Toast.LENGTH_LONG);
        existe.show();
        campoNombre.setText(clientes.getNombre());
        campoDni.setText(clientes.getDni());
        campoCantidadResidentes.setText(clientes.getNum_residentes());
        campoCantidadDias.setText(clientes.getNum_dias());
        campOpciones.setText(clientes.getHabitacion());
        campoNumHabitacion.setText(clientes.getNum_habitacion());
        campoPrecio.setText(clientes.getPrecio());


            String campoDni=clientes.getDni();
            String campoNombre=clientes.getNombre();
            if (campoDni==null){
                existe =  Toast.makeText(getContext(), "El DNI no corresponde a ningún cliente.", Toast.LENGTH_LONG);
                existe.show();
                if (cantidadResidentesString==null || cantidadDiasString==null || precioTotal==null) {
                    cantResidentes=0;
                    cantDias=0;
                    campoCantidadResidentes.setText("0");
                    campoCantidadDias.setText("0");
                    campoPrecio.setText("0");
                }
            }else{
                existe =  Toast.makeText(getContext(), "El DNI corresponde a " + campoNombre + ", DNI: " + campoDni + ".", Toast.LENGTH_LONG);
                existe.show();
                cantidadDiasString = campoCantidadDias.getText().toString();
                cantidadResidentesString = campoCantidadResidentes.getText().toString();
                    int residentesInt = Integer.parseInt(cantidadResidentesString);
                    int diasInt = Integer.parseInt(cantidadDiasString);
                    cantResidentes=residentesInt;
                    cantDias=diasInt;


            }


    }
    public void busquedaDniRepBaseDatos(){
        Cliente clientes=new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getActivity());
        clientDbHelper.buscarCliente(clientes, dniDevuelto);
        existe =  Toast.makeText(getContext(), "Buscando DNI...", Toast.LENGTH_LONG);
        existe.show();
        String campoDni=clientes.getDni();
        String campoNombre=clientes.getNombre();
    if (campoDni!=null){
            verifyhabitaciones();
        }else{
            existe =  Toast.makeText(getContext(), "ERROR: El DNI corresponde a " + campoNombre + ", DNI: " + campoDni + ".", Toast.LENGTH_LONG);
            existe.show();
        }

    }
    public void busquedaHabitacionBaseDatos(){
        numHabitacionDevuelto = campoNumHabitacion.getText().toString();
        Cliente clientesNum=new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getActivity());
        clientDbHelper.buscarNumHabitacion(clientesNum, numHabitacionDevuelto);
        String resultadoNombre=clientesNum.getNombre();
        String resultadoDni=clientesNum.getDni();
        String resultadoNumHabitacion=clientesNum.getNum_habitacion();
        if (resultadoNumHabitacion==null){
            existe =  Toast.makeText(getContext(), "ÉXITO :El Número de habitación no pertenece a ningún cliente.", Toast.LENGTH_LONG);
            existe.show();
            modificacionBaseDatos();
        }else if (dniDevuelto.equalsIgnoreCase(resultadoDni)){
            modificacionBaseDatos();
        }else{
            existe =  Toast.makeText(getContext(), "ERROR: El Número de habitación esta registrado a nombre de: " + resultadoNombre + ", DNI: " + resultadoDni + "." , Toast.LENGTH_LONG);
            existe.show();
        }
    }
    //Modificamos dentro de la base de datos
    public void modificacionBaseDatos() {
        clientDbHelper.editarCliente(editBuscarDni.getText().toString(), campoNombre.getText().toString(), campoDni.getText().toString(), campoCantidadResidentes.getText().toString(), campoCantidadDias.getText().toString(), campOpciones.getText().toString(), campoNumHabitacion.getText().toString(), campoPrecio.getText().toString());
        modificacion = Toast.makeText(getContext(), "Modificación realizada con Éxito", Toast.LENGTH_LONG);
        modificacion.show();
    }


}
