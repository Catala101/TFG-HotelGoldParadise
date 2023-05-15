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


public class DeleteClient extends Fragment implements View.OnClickListener{
    TextView botonBuscar, textoPrincipal, botonEliminar, editBuscarDni;
    String dniDevuelto;
    Typeface face;
    Toast existente, eliminado, existe;
    ClientDbHelper clientDbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_delete_client, container, false);
        //TextView
        editBuscarDni = view.findViewById(R.id.insertDni);
        textoPrincipal = view.findViewById(R.id.textoPrincipalDelete);
        //Fuente de Letra
        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Golden Ranger.ttf");
        textoPrincipal.setTypeface(face);
        //Botones
        botonBuscar = view.findViewById(R.id.botonBuscarDel);
        botonEliminar = view.findViewById(R.id.botonEliminar);
        clientDbHelper= new ClientDbHelper(getContext());
        botonBuscar.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);


        return view;
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botonBuscarDel:
                verifyInformation();

                break;
            case R.id.botonEliminar:
                verifyInformation();
                eliminarCliente();
                break;


        }
    }
    public void busquedaDniBaseDatos() {
            Cliente clientes=new Cliente();
            clientDbHelper.buscarCliente(clientes, editBuscarDni.getText().toString());
            existe =  Toast.makeText(getContext(), "Buscando DNI...", Toast.LENGTH_LONG);
            existe.show();
            String resultadoDni=clientes.getDni();
            String resultadoNombre=clientes.getNombre();
            if (resultadoDni==null){
                existe =  Toast.makeText(getContext(), "El DNI no corresponde a ningún cliente.", Toast.LENGTH_LONG);
                existe.show();
            }else{
                existe =  Toast.makeText(getContext(), "El DNI corresponde a " + resultadoNombre + ", DNI: " + resultadoDni + ".", Toast.LENGTH_LONG);
                existe.show();
            }

    }
    public void verifyInformation(){
        dniDevuelto = editBuscarDni.getText().toString();
        if (dniDevuelto.equalsIgnoreCase("")){
            existente =  Toast.makeText(getContext(), "Introduzca un DNI existente", Toast.LENGTH_LONG);
            existente.show();
        }else{
            busquedaDniBaseDatos();
        }
    }
    public void eliminarCliente(){
        clientDbHelper.eliminarCliente(dniDevuelto);
        eliminado =  Toast.makeText(getContext(), "Cliente eliminado con Éxito", Toast.LENGTH_LONG);
        eliminado.show();
    }
}
