package com.example.hotelgoldparadise_albertoc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hotelgoldparadise_albertoc.Data.RoomDbHelper;

import java.util.ArrayList;

public class RoomAdapter extends ArrayAdapter<Room> implements View.OnClickListener {

    private ArrayList<Room> rooms;
    private Context context;
    private OnRoomButtonClickListener mListener;
    private int editingPosition = -1;

    private RoomDbHelper dbHelper;
    public RoomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Room> rooms, RoomDbHelper dbHelper, RoomList roomList, EditText numHabitacionEditText, Spinner tipoHabitacionSpinner) {
        super(context, resource, rooms);
        this.context = context;
        this.rooms = rooms;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list_item = convertView;
        if (list_item == null) {
            list_item = LayoutInflater.from(context).inflate(R.layout.item_list_habitacion, parent, false);
        }

        Room currentRoom = rooms.get(position);

        // Asigna los TextView y otros elementos de la vista con sus respectivos datos del objeto Room
        Spinner estadoSpinner = list_item.findViewById(R.id.estadoSpinner);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(context, R.array.estado_opciones, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSpinner.setAdapter(adapterEstado);
        estadoSpinner.setSelection(adapterEstado.getPosition(currentRoom.getEstado()));

        EditText roomNumberTextView = list_item.findViewById(R.id.numeroHabitacion);
        Spinner roomTypeSpinner = list_item.findViewById(R.id.tipoHabitacionSpinner);


        EditText capacidadTextView = list_item.findViewById(R.id.capacidad);
        EditText precioTextView = list_item.findViewById(R.id.precioHabitacion);

        capacidadTextView.setText(String.valueOf(currentRoom.getCapacidad()));
        precioTextView.setText(String.valueOf(currentRoom.getPrecio()));

        roomNumberTextView.setText(currentRoom.getNumeroHabitacion());
        // Configura el Spinner para que muestre el tipo de habitación correcto
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomTypeSpinner.setAdapter(adapter);
        roomTypeSpinner.setSelection(adapter.getPosition(currentRoom.getTipoHabitacion()));

        Button deleteButton = list_item.findViewById(R.id.btn_delete_habitacion);
        deleteButton.setTag(position); // Establece la posición como tag para recuperarla en el evento onClick
        deleteButton.setOnClickListener(this);

        Button editButton = list_item.findViewById(R.id.btn_edit_habitacion);
        Button saveButton = list_item.findViewById(R.id.btn_save_habitacion);

        // Set the position as tag to retrieve it in onClick event
        editButton.setTag(position);
        saveButton.setTag(position);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingPosition = position;
                notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un diálogo de alerta para confirmar la eliminación de la habitación
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Estás seguro de que quieres eliminar esta habitación?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Elimina la habitación de la base de datos
                        dbHelper.deleteRoom(currentRoom);
                        // Elimina la habitación de la lista
                        rooms.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene el número de habitación directamente del campo de texto
                String roomNumber = roomNumberTextView.getText().toString();
                String roomType = roomTypeSpinner.getSelectedItem().toString();
                String roomEstado = estadoSpinner.getSelectedItem().toString();
                String capacidad = capacidadTextView.getText().toString();
                String precio = precioTextView.getText().toString();



                Room updatedRoom = rooms.get(position);
                updatedRoom.setNumeroHabitacion(roomNumber);
                updatedRoom.setTipoHabitacion(roomType);
                updatedRoom.setEstado(roomEstado);
                updatedRoom.setCapacidad(Integer.parseInt(capacidad));
                updatedRoom.setPrecio(Double.parseDouble(precio));

                // Actualiza la habitación en la base de datos
                dbHelper.updateRoom(updatedRoom);

                // Recarga los datos de la habitación y actualiza la vista
                Room updatedRoomFromDb = dbHelper.getHabitacionPorNum(roomNumber);
                rooms.set(position, updatedRoomFromDb);  // Asegúrate de que 'position' es el índice correcto de la habitación en la lista
                editingPosition = -1;
                notifyDataSetChanged();
            }
        });

        if (editingPosition == position) {
            editButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            roomNumberTextView.setEnabled(true);
            roomTypeSpinner.setEnabled(true);
            capacidadTextView.setEnabled(true);
            estadoSpinner.setEnabled(true);
            precioTextView.setEnabled(true);
        } else {
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
            roomNumberTextView.setEnabled(false);
            roomTypeSpinner.setEnabled(false);
            capacidadTextView.setEnabled(false);
            precioTextView.setEnabled(false);
            estadoSpinner.setEnabled(false);
        }

        return list_item;
    }

    public interface OnRoomButtonClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onSaveClick(int position, Room updatedRoom);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Room room = getItem(position);

        switch (v.getId()) {
            case R.id.btn_delete_habitacion:
                if (mListener != null) {
                    //Mostrar un cuadro de diálogo de confirmación
                    new AlertDialog.Builder(context)
                            .setTitle("Eliminar habitación")
                            .setMessage("¿Estás seguro de que quieres eliminar esta habitación?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continúa con la eliminación
                                    dbHelper.deleteRoom(room);
                                    // Remueve la habitación de la lista de habitaciones
                                    rooms.remove(position);
                                    // Notifica al adaptador que los datos han cambiado
                                    notifyDataSetChanged();
                                    // Notifica a cualquier escuchador que la habitación ha sido eliminada
                                    mListener.onDeleteClick(position);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                break;
            case R.id.btn_edit_habitacion:
                editingPosition = position;
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.onEditClick(position);
                }
                break;
            case R.id.btn_save_habitacion:
                if (mListener != null) {
                    // Get the updated room details from the fields
                    Room updatedRoom = new Room( room.getNumeroHabitacion(), room.getTipoHabitacion(), room.getEstado(), room.getCapacidad(), room.getPrecio());
                    // Set the updated fields to the updatedRoom object
                    // ...
                    mListener.onSaveClick(position, updatedRoom);
                }
                editingPosition = -1;
                notifyDataSetChanged();
                break;
        }
    }
}


