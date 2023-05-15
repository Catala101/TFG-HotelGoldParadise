package com.example.hotelgoldparadise_albertoc;

import static android.system.Os.remove;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.system.ErrnoException;
import android.widget.EditText;
import android.widget.ListView;
import com.example.hotelgoldparadise_albertoc.Data.ClientDbHelper;
import java.util.ArrayList;

public class ClientList extends AppCompatActivity implements ClientAdapteer.OnClientButtonClickListener {
    private ClientDbHelper myDbHelper;
    private ClientAdapteer clientAdapter;
    private ArrayList<Cliente> datos;
    EditText numHabitacionEditText, numDiasEditText, numResidentesEditText, dniEditText, nombreEditText, precioEditText, habitacionEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        myDbHelper = new ClientDbHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        ListView list = (ListView) findViewById(R.id.listaClientes);

        // Llamada al método de consulta de datos
        ArrayList<Cliente> myPetArray = new ArrayList<Cliente>();
        myPetArray = myDbHelper.displayDataBaseInfo();

        datos = new ArrayList<Cliente>();
        for (Cliente cliente : myPetArray) {
            if (cliente != null) {
                Cliente perrosBD = new Cliente(cliente.getNombre(), cliente.getDni(), cliente.getNum_residentes(), cliente.getNum_dias(), cliente.getHabitacion(), cliente.getNum_habitacion(), cliente.getPrecio());
                datos.add(perrosBD);
            }
        }


        numHabitacionEditText = findViewById(R.id.numHabitacion);
        numDiasEditText = findViewById(R.id.numDias);
        numResidentesEditText = findViewById(R.id.numResidentes);
        dniEditText = findViewById(R.id.editDniModify);
        nombreEditText = findViewById(R.id.nombreCliente);
        precioEditText = findViewById(R.id.precio);
        habitacionEditText = findViewById(R.id.habitacion);

        // Asigna el objeto ClientAdapteer a la variable clientAdapter
        clientAdapter = new ClientAdapteer(this, 0, datos, this, numHabitacionEditText, numDiasEditText, numResidentesEditText, dniEditText, nombreEditText, precioEditText, habitacionEditText);

        // Establece el adaptador en la lista
        list.setAdapter(clientAdapter);
    }


    @Override
    public void onEditClick(int position) {
        // La lógica para editar el cliente en la posición dada se maneja en el adaptador
    }

    public void onDeleteClick(int position) {
        Cliente cliente = datos.get(position);
        // Elimina el cliente de la base de datos
        myDbHelper.eliminarCliente(cliente.getDni());
        datos.remove(position); // Elimina el cliente de la lista
        // Notifica al adaptador que se ha borrado un elemento
        clientAdapter.notifyDataSetChanged();
    }



    @Override
    public void onSaveClick(int position, Cliente updatedClient) {
        // Actualizar el cliente en la base de datos utilizando ClientDbHelper
        myDbHelper.updateClient(updatedClient);

        // Actualizar el cliente en el ArrayList datos y notificar al adaptador del cambio
        datos.set(position, updatedClient);
        clientAdapter.notifyDataSetChanged();
    }
}

