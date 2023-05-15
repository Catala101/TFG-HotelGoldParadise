package com.example.hotelgoldparadise_albertoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hotelgoldparadise_albertoc.Data.ClientDbHelper;

import java.util.ArrayList;

public class ClientAdapteer extends ArrayAdapter<Cliente> {

    private OnClientButtonClickListener mListener;
    private String nombreDevuelto;
    private String dniDevuelto;
    private String numHabitacionDevuelto;
    private String cantidadDiasString;
    private String cantidadResidentesString;
    private String precioTotal;
    private Toast vacio, existe;
    private int numHabitacion;


    public ClientAdapteer(@NonNull Context context, int resource, @NonNull ArrayList<Cliente> objects, OnClientButtonClickListener listener, EditText habitacionEditText, EditText numDiasEditText, EditText numResidentesEditText, EditText dniEditText, EditText nombreEditText, EditText precioEditText, EditText numHabitacionEditText) {
        super(context, resource, objects);
        mListener = listener;
    }

    // Añade estas variables al inicio de la clase ClientAdapteer
    private boolean isEditing = false;
    private int editingPosition = -1;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list_item = convertView;




        // Cogemos el xml con la estructura personalizada del ListView.
        if (list_item == null) {
            list_item = LayoutInflater.from(getContext()).inflate(R.layout.item_list_client, parent, false);
        }

        if (position % 2 == 1) {
            list_item.setBackgroundColor(getContext().getResources().getColor(
                    android.R.color.holo_orange_light
            ));
        } else {
            list_item.setBackgroundColor(getContext().getResources().getColor(
                    android.R.color.white
            ));
        }
        // Traemos los clientes de nuestro Array
        Cliente currentClient = getItem(position);

        final EditText primerTexto = (EditText) list_item.findViewById(R.id.nombreCliente);
        primerTexto.setText(currentClient.getNombre());

        final EditText segundoTexto = (EditText) list_item.findViewById(R.id.dniCliente);
        segundoTexto.setText(currentClient.getDni());

        final EditText tercerTexto = (EditText) list_item.findViewById(R.id.numResidentes);
        tercerTexto.setText(currentClient.getNum_residentes());

        final EditText cuartoTexto = (EditText) list_item.findViewById(R.id.numDias);
        cuartoTexto.setText(currentClient.getNum_dias());

        final EditText quintoTexto = (EditText) list_item.findViewById(R.id.habitacion);
        quintoTexto.setText(currentClient.getHabitacion());

        final EditText sextoTexto = (EditText) list_item.findViewById(R.id.numHabitacion);
        sextoTexto.setText(currentClient.getNum_habitacion());

        final EditText septimoTexto = (EditText) list_item.findViewById(R.id.precio);
        septimoTexto.setText(currentClient.getPrecio());



        ImageView imageView = (ImageView) list_item.findViewById(R.id.imagenCliente);
        imageView.setImageResource(currentClient.getImageId());

        // Indices de los datos:
        TextView primerIndice = (TextView) list_item.findViewById(R.id.indiceNombre);
        primerIndice.setText("Nombre:");

        TextView segundoIndice = (TextView) list_item.findViewById(R.id.indiceDni);
        segundoIndice.setText("DNI:");

        TextView tercerIndice = (TextView) list_item.findViewById(R.id.indiceHabitacion);
        tercerIndice.setText("Habitación:");

        TextView cuartoIndice = (TextView) list_item.findViewById(R.id.indiceNumHabitacion);
        cuartoIndice.setText("Nº Habitación:");

        TextView quintoIndice = (TextView) list_item.findViewById(R.id.indiceResidentes);
        quintoIndice.setText("Nº Residentes:");

        TextView sextoIndice = (TextView) list_item.findViewById(R.id.indiceDias);
        sextoIndice.setText("Nº Días:");

        TextView septimoIndice = (TextView) list_item.findViewById(R.id.indicePrecio);
        septimoIndice.setText("Total:");



        // Añade la referencia al botón "Guardar"
        final Button btn_save = (Button) list_item.findViewById(R.id.btn_save);

        final int currentPosition = position;
        final Button btn_edit = (Button) list_item.findViewById(R.id.btn_edit);

        Button btn_delete = (Button) list_item.findViewById(R.id.btn_delete);
        // Configura el OnClickListener para el botón "Borrar"
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDeleteClick(position);
                }
            }
        });

        // Configura el OnClickListener para el botón "Editar"
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEditing) {
                    isEditing = true;
                    editingPosition = currentPosition;

                    // Cambia la visibilidad de los botones "Editar" y "Guardar"
                    btn_edit.setVisibility(View.GONE);
                    btn_save.setVisibility(View.VISIBLE);

                    // Habilita la edición de los EditText
                    primerTexto.setEnabled(true);
                    segundoTexto.setEnabled(false);
                    tercerTexto.setEnabled(true);
                    cuartoTexto.setEnabled(true);
                    quintoTexto.setEnabled(true);
                    sextoTexto.setEnabled(true);
                    septimoTexto.setEnabled(true);
                }
            }
        });

        // Configura el OnClickListener para el botón "Guardar"
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditing && editingPosition == currentPosition) {
                    isEditing = false;

                    verifyInformations(getContext(), btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);

                    // Cambia la visibilidad de los botones "Editar" y "Guardar"
                    btn_edit.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.GONE);

                    // Configura los EditText como no editables
                    primerTexto.setEnabled(false);
                    segundoTexto.setEnabled(false);
                    tercerTexto.setEnabled(false);
                    cuartoTexto.setEnabled(false);
                    quintoTexto.setEnabled(false);
                    sextoTexto.setEnabled(false);
                    septimoTexto.setEnabled(false);

                    // Actualizar la información del cliente en la base de datos
                    Cliente updatedClient = new Cliente(
                            primerTexto.getText().toString(),
                            segundoTexto.getText().toString(),
                            tercerTexto.getText().toString(),
                            cuartoTexto.getText().toString(),
                            quintoTexto.getText().toString(),
                            sextoTexto.getText().toString(),
                            septimoTexto.getText().toString()
                    );
                    mListener.onSaveClick(editingPosition, updatedClient);
                }
            }
        });




        return list_item;
    }
    // Modificar el método verifyInformation() para incluir la lógica de guardar después de las validaciones
    private void verifyInformations(Context context, Button btn_save, Button btn_edit, EditText primerTexto, EditText segundoTexto, EditText tercerTexto, EditText cuartoTexto, EditText quintoTexto, EditText sextoTexto, EditText septimoTexto) {

        nombreDevuelto = primerTexto.getText().toString();
        dniDevuelto = segundoTexto.getText().toString();
        numHabitacionDevuelto = tercerTexto.getText().toString();
        cantidadDiasString = cuartoTexto.getText().toString();
        cantidadResidentesString = quintoTexto.getText().toString();
        precioTotal = sextoTexto.getText().toString();
        String habitacionTipo = septimoTexto.getText().toString();

        boolean validacionesExitosas = true;

        if (nombreDevuelto.equalsIgnoreCase("")) {
            vacio = Toast.makeText(context, "Deberá introducir un nombre para continuar", Toast.LENGTH_SHORT);
            vacio.show();
            validacionesExitosas = false;
        } else if (dniDevuelto.equalsIgnoreCase("")) {
            vacio = Toast.makeText(context, "Deberá introducir el dni para continuar", Toast.LENGTH_SHORT);
            vacio.show();
            validacionesExitosas = false;
        } else if (numHabitacionDevuelto.equalsIgnoreCase("")) {
            vacio = Toast.makeText(context, "Deberá introducir el número de habitación para continuar", Toast.LENGTH_SHORT);
            vacio.show();
            validacionesExitosas = false;
        } else if (cantidadResidentesString.equalsIgnoreCase("0")) {
            vacio = Toast.makeText(context, "Deberá introducir el número de residentes para continuar", Toast.LENGTH_SHORT);
            vacio.show();
            validacionesExitosas = false;
        } else if (cantidadDiasString.equalsIgnoreCase("0")) {
            vacio = Toast.makeText(context, "Deberá introducir el número de días para continuar", Toast.LENGTH_SHORT);
            vacio.show();
            validacionesExitosas = false;
        } else {
            //Puedes agregar aquí la lógica para las validaciones de habitaciones y DNI
            habitacionTipo = quintoTexto.getText().toString();
            verifyhabitaciones(habitacionTipo, btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);

            busquedaDniBaseDatos( dniDevuelto, btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);
        }

        if (validacionesExitosas) {
            isEditing = false;

            // Cambia la visibilidad de los botones "Editar" y "Guardar"
            btn_edit.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.GONE);

            // Configura los EditText como no editables
            primerTexto.setEnabled(false);
            segundoTexto.setEnabled(false);
            tercerTexto.setEnabled(false);
            cuartoTexto.setEnabled(false);
            quintoTexto.setEnabled(false);
            sextoTexto.setEnabled(false);
            septimoTexto.setEnabled(false);

            // Actualizar la información del cliente en la base de datos
            Cliente updatedClient = new Cliente(
                    primerTexto.getText().toString(),
                    segundoTexto.getText().toString(),
                    tercerTexto.getText().toString(),
                    cuartoTexto.getText().toString(),
                    quintoTexto.getText().toString(),
                    sextoTexto.getText().toString(),
                    septimoTexto.getText().toString()
            );
            mListener.onSaveClick(editingPosition, updatedClient);
        }
    }

    // Modificar el método verifyhabitaciones() para incluir la lógica de guardar después de las validaciones
    public void verifyhabitaciones(String tipoHabitacion, Button btn_save, Button btn_edit, EditText primerTexto, EditText segundoTexto, EditText tercerTexto, EditText cuartoTexto, EditText quintoTexto, EditText sextoTexto, EditText septimoTexto) {
        numHabitacion = Integer.parseInt(numHabitacionDevuelto);

        if (tipoHabitacion.equalsIgnoreCase("Individual (1-9)") && numHabitacion >= 1 && numHabitacion <= 9) {
            busquedaHabitacionBaseDatos( btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto );
        } else if (tipoHabitacion.equalsIgnoreCase("Doble (10-19)") && numHabitacion >= 10 && numHabitacion <= 19) {
            busquedaHabitacionBaseDatos( btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);
        } else if (tipoHabitacion.equalsIgnoreCase("Triple (20-29)") && numHabitacion >= 20 && numHabitacion <= 29) {
            busquedaHabitacionBaseDatos( btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);
        } else if (tipoHabitacion.equalsIgnoreCase("Quad (30-39)") && numHabitacion >= 30 && numHabitacion <= 39) {
            busquedaHabitacionBaseDatos( btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);
        } else {
            vacio = Toast.makeText(getContext(), "Vuelva a introducir un Número de Habitación válido según el tipo de habitación", Toast.LENGTH_SHORT);
            vacio.show();

            // Limpiar el EditText de número de habitación
            sextoTexto.setText("");
        }
    }
    // Modificar el método busquedaDniBaseDatos() para incluir la lógica de guardar después de las validaciones
    public void busquedaHabitacionBaseDatos(Button btn_save, Button btn_edit, EditText primerTexto, EditText segundoTexto, EditText tercerTexto, EditText cuartoTexto, EditText quintoTexto, EditText sextoTexto, EditText septimoTexto) {
        numHabitacionDevuelto = sextoTexto.getText().toString();
        Cliente clientesNum = new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getContext());
        clientDbHelper.buscarNumHabitacion(clientesNum, numHabitacionDevuelto);
        String resultadoNombre = clientesNum.getNombre();
        String resultadoDni = clientesNum.getDni();
        String resultadoNumHabitacion = clientesNum.getNum_habitacion();

        String dniActual = segundoTexto.getText().toString();

        if (resultadoDni != null && resultadoDni.equals(dniActual)) {
            existe = Toast.makeText(getContext(), "ÉXITO: El Número de habitación pertenece al cliente con DNI: " + resultadoDni + ".", Toast.LENGTH_LONG);
            existe.show();

            // Actualizar la información del cliente en la base de datos
            Cliente updatedClient = new Cliente(
                    primerTexto.getText().toString(),
                    segundoTexto.getText().toString(),
                    tercerTexto.getText().toString(),
                    cuartoTexto.getText().toString(),
                    quintoTexto.getText().toString(),
                    sextoTexto.getText().toString(),
                    septimoTexto.getText().toString()
            );
            mListener.onSaveClick(editingPosition, updatedClient);
        } else {
            existe = Toast.makeText(getContext(), "ERROR: El Número de habitación está registrado a nombre de: " + resultadoNombre + ", DNI: " + resultadoDni + ".", Toast.LENGTH_LONG);
            existe.show();
        }
    }



    // Modificar el método busquedaDniBaseDatos() para incluir la lógica de guardar después de las validaciones
    public void busquedaDniBaseDatos(String habitacionTipo, Button btn_save, Button btn_edit, EditText primerTexto, EditText segundoTexto, EditText tercerTexto, EditText cuartoTexto, EditText quintoTexto, EditText sextoTexto, EditText septimoTexto) {
        Cliente clientes=new Cliente();
        ClientDbHelper clientDbHelper = new ClientDbHelper(getContext());
        clientDbHelper.buscarCliente(clientes, dniDevuelto);
        existe =  Toast.makeText(getContext(), "Buscando DNI...", Toast.LENGTH_LONG);
        existe.show();
        String campoDni=clientes.getDni();
        String campoNombre=clientes.getNombre();
        String clientRoomType = clientes.getHabitacion();
        if (campoDni==null){
            verifyhabitaciones(habitacionTipo, btn_save, btn_edit, primerTexto, segundoTexto, tercerTexto, cuartoTexto, quintoTexto, sextoTexto, septimoTexto);
        }else{
            existe =  Toast.makeText(getContext(), "ERROR: El DNI corresponde a " + campoNombre + ", DNI: " + campoDni + ".", Toast.LENGTH_LONG);
            existe.show();
        }

    }

    // Interfaz para los botones de editar y borrar.
    public interface OnClientButtonClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onSaveClick(int position, Cliente updatedClient);
    }
    // Este es el método que notifica al adaptador de los cambios en los datos
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}



