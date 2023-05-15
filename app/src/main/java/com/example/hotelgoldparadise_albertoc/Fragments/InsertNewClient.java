package com.example.hotelgoldparadise_albertoc.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hotelgoldparadise_albertoc.Cliente;
import com.example.hotelgoldparadise_albertoc.Data.ClientDbHelper;
import com.example.hotelgoldparadise_albertoc.R;


public class InsertNewClient extends Fragment implements View.OnClickListener{
    Spinner opciones;
    Toast existe, vacio;
    int cantResidentes=0;
    int cantDias=0;
    int priceIndividual=115;
    int priceDoble=200;
    int priceTriple=299;
    int priceQuad=400;
    int multi=0;
    int numHabitacion, numResidentes;
    Typeface face;
    String textoSpinner, textmulti, nombreDevuelto, dniDevuelto, numHabitacionDevuelto, cantidadDiasString, cantidadResidentesString, precioTotal;
    TextView cantidadDias, cantidadResidentes, botonResidentesMas, botonResidentesMenos, botonDiasMas, botonDiasMenos, botonCrear, textoPrecio, editNombre, editDni, editNumHabitacion, textoPrincipal;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_new_client, container, false);

        //Spinner y TextViews
        opciones = (Spinner) view.findViewById(R.id.spinner);
        textoPrecio = view.findViewById(R.id.textoPrecio);
        cantidadDias = view.findViewById(R.id.cantidadDias);
        cantidadResidentes = view.findViewById(R.id.cantidadResidentes);
        cantidadDias = view.findViewById(R.id.cantidadDias);
        textoPrincipal = view.findViewById(R.id.textoPrincipaInsert);
        //Fuente de Letra
        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Golden Ranger.ttf");
        textoPrincipal.setTypeface(face);
        //Botones:
        botonResidentesMas = view.findViewById(R.id.botonResidentesMas);
        botonResidentesMenos = view.findViewById(R.id.botonResidentesMenos);
        botonDiasMas = view.findViewById(R.id.botonDiasMas);
        botonDiasMenos = view.findViewById(R.id.botonDiasMenos);
        botonCrear = view.findViewById(R.id.botonCrear);
        //Onclick:
        botonResidentesMas.setOnClickListener(this);
        botonResidentesMenos.setOnClickListener(this);
        botonDiasMas.setOnClickListener(this);
        botonDiasMenos.setOnClickListener(this);
        botonCrear.setOnClickListener(this);
        //Edits:
        editNombre = view.findViewById(R.id.editNombre);
        editDni = view.findViewById(R.id.editDni);
        editNumHabitacion = view.findViewById(R.id.editNumHabitacion);

        ArrayAdapter<CharSequence> adaptadorSpinner = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        opciones.setAdapter(adaptadorSpinner);
        return view;

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonResidentesMas:
                    increment(view);
                break;
            case R.id.botonResidentesMenos:
                    decrease(view);
                break;
            case R.id.botonDiasMas:
                    increment(view);
                break;
            case R.id.botonDiasMenos:
                    decrease(view);
                break;
            case R.id.botonCrear:
                    verifyInformation();
                break;
        }
    }
    public void verifyInformation(){
        nombreDevuelto = editNombre.getText().toString();
        dniDevuelto = editDni.getText().toString();
        numHabitacionDevuelto = editNumHabitacion.getText().toString();
        cantidadDiasString = cantidadDias.getText().toString();
        cantidadResidentesString = cantidadResidentes.getText().toString();
        precioTotal = textoPrecio.getText().toString();
        if (nombreDevuelto.equalsIgnoreCase("")){
            vacio = Toast.makeText(getContext(), "Deberá introducir un nombre para continuar", Toast.LENGTH_SHORT);
            vacio.show();
        }else if (dniDevuelto.equalsIgnoreCase("")){
            vacio = Toast.makeText(getContext(), "Deberá introducir el dni para continuar", Toast.LENGTH_SHORT);
            vacio.show();
        }else if (numHabitacionDevuelto.equalsIgnoreCase("")){
            vacio = Toast.makeText(getContext(), "Deberá introducir el número de habitación para continuar", Toast.LENGTH_SHORT);
            vacio.show();
        }else if (cantidadResidentesString.equalsIgnoreCase("0")){
            vacio = Toast.makeText(getContext(), "Deberá introducir el número de residentes para continuar", Toast.LENGTH_SHORT);
            vacio.show();
        }else if (cantidadDiasString.equalsIgnoreCase("0")){
            vacio = Toast.makeText(getContext(), "Deberá introducir el número de días para continuar", Toast.LENGTH_SHORT);
            vacio.show();
        }else{
            busquedaDniBaseDatos();
        }
    }

    public void verifyhabitaciones(){
        numHabitacion = Integer.parseInt(numHabitacionDevuelto);
        numResidentes = Integer.parseInt(cantidadResidentesString);
        textoSpinner = opciones.getSelectedItem().toString();
        if(textoSpinner.equalsIgnoreCase("Individual (1-9)")){
            if(numResidentes>1){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (1) ", Toast.LENGTH_SHORT);
                vacio.show();
                cantidadResidentes.setText("1");
            }else{
                if (numHabitacion>=1 && numHabitacion<=9){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (1-9) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    editNumHabitacion.setText("");
                }
            }
        }else if (textoSpinner.equalsIgnoreCase("Doble (10-19)")){
            if(numResidentes>2){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (2) ", Toast.LENGTH_SHORT);
                vacio.show();
                cantidadResidentes.setText("2");
            }else{
                if (numHabitacion>=10 && numHabitacion<=19){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (10-19) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    editNumHabitacion.setText("");
                }
            }
        }else if (textoSpinner.equalsIgnoreCase("Triple (20-29)")){
            if(numResidentes>3){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (3) ", Toast.LENGTH_SHORT);
                vacio.show();
                cantidadResidentes.setText("3");
            }else{
                if (numHabitacion>=20 && numHabitacion<=29){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (20-29) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    editNumHabitacion.setText("");
                }
            }
        }else if (textoSpinner.equalsIgnoreCase("Quad (30-39)")){
            if(numResidentes>4){
                vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Residentes (4) ", Toast.LENGTH_SHORT);
                vacio.show();
                cantidadResidentes.setText("4");
            }else{
                if (numHabitacion>=30 && numHabitacion<=39){
                    busquedaHabitacionBaseDatos();
                }else{
                    vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación (30-39) ", Toast.LENGTH_SHORT);
                    vacio.show();
                    editNumHabitacion.setText("");
                }
            }
        }
    }

    public void increment(View view){
        switch (view.getId()){
            case R.id.botonResidentesMas:
                if (cantResidentes<4) {
                    cantResidentes = cantResidentes + 1;
                    String cantR = Integer.toString(cantResidentes);
                    cantidadResidentes.setText(cantR);
                }
                break;
            case R.id.botonDiasMas:
                if (cantDias<31) {
                    cantDias = cantDias + 1;
                    String cantD = Integer.toString(cantDias);
                    cantidadDias.setText(cantD);
                    //Cada vez que se pinche en el botón, actualizará el texto del Spinner almacenado
                    textoSpinner = opciones.getSelectedItem().toString();
                    calcularPorHabitacion();

                }
                break;
        }

    }
    public void decrease(View view){
        switch (view.getId()){
            case R.id.botonResidentesMenos:
                if (cantResidentes>0) {
                    cantResidentes = cantResidentes - 1;
                    String cantR = Integer.toString(cantResidentes);
                    cantidadResidentes.setText(cantR);
                }
                break;
            case R.id.botonDiasMenos:
                if (cantDias>0) {
                    cantDias = cantDias - 1;
                    String cantD = Integer.toString(cantDias);
                    cantidadDias.setText(cantD);
                    //Cada vez que se pinche en el botón, actualizará el texto del Spinner almacenado
                    textoSpinner = opciones.getSelectedItem().toString();
                    calcularPorHabitacion();
                }
                break;
        }

    }
    public void calcularPorHabitacion() {
        if(textoSpinner.equalsIgnoreCase("Individual (1-9)")){
            calculatePriceIndividual();
        }else if (textoSpinner.equalsIgnoreCase("Doble (10-19)")){
            calculatePriceDoble();
        }else if (textoSpinner.equalsIgnoreCase("Triple (20-29)")){
            calculatePriceTriple();
        }else{
            calculatePriceQuad();
        }
    }
    public void calculatePriceIndividual() {
        multi = priceIndividual * cantDias;
        textmulti = Integer.toString(multi);
        textoPrecio.setText(textmulti);
    }
    public void calculatePriceDoble() {
        multi = priceDoble * cantDias;
        textmulti = Integer.toString(multi);
        textoPrecio.setText(textmulti);
    }
    public void calculatePriceTriple() {
        multi = priceTriple * cantDias;
        textmulti = Integer.toString(multi);
        textoPrecio.setText(textmulti);
    }
    public void calculatePriceQuad() {
        multi = priceQuad * cantDias;
        textmulti = Integer.toString(multi);
        textoPrecio.setText(textmulti);
    }

    public void busquedaHabitacionBaseDatos(){
        numHabitacionDevuelto = editNumHabitacion.getText().toString();
        Cliente clientesNum=new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getActivity());
        clientDbHelper.buscarNumHabitacion(clientesNum, numHabitacionDevuelto);
        String resultadoNombre=clientesNum.getNombre();
        String resultadoDni=clientesNum.getDni();
        String resultadoNumHabitacion=clientesNum.getNum_habitacion();
        if (resultadoNumHabitacion==null){
            existe =  Toast.makeText(getContext(), "ÉXITO :El Número de habitación no pertenece a ningún cliente.", Toast.LENGTH_LONG);
            existe.show();
            callInsertNewClient();
        }else{
            existe =  Toast.makeText(getContext(), "ERROR: El Número de habitación esta registrado a nombre de: " + resultadoNombre + ", DNI: " + resultadoDni + "." , Toast.LENGTH_LONG);
            existe.show();
        }
    }
    public void busquedaDniBaseDatos(){
        Cliente clientes=new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getActivity());
        clientDbHelper.buscarCliente(clientes, dniDevuelto);
        existe =  Toast.makeText(getContext(), "Buscando DNI...", Toast.LENGTH_LONG);
        existe.show();
        String campoDni=clientes.getDni();
        String campoNombre=clientes.getNombre();
        if (campoDni==null){
            verifyhabitaciones();
        }else{
            existe =  Toast.makeText(getContext(), "ERROR: El DNI corresponde a " + campoNombre + ", DNI: " + campoDni + ".", Toast.LENGTH_LONG);
            existe.show();
        }

    }
    public void callInsertNewClient(){
        Cliente cliente =  new Cliente (nombreDevuelto, dniDevuelto, cantidadResidentesString, cantidadDiasString, textoSpinner, numHabitacionDevuelto, precioTotal);
        ClientDbHelper clientDbHelper = new ClientDbHelper(getActivity());
        double test = clientDbHelper.insertClient(cliente);
        Log.i("Este es el id de la nueva entrada: ", "" + test);
        Toast mostrarId = Toast.makeText(getContext(), "Nueva entrada en la tabla con el ID: " +  test, Toast.LENGTH_SHORT);
        mostrarId.show();
    }
}
