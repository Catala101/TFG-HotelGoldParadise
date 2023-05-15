package com.example.hotelgoldparadise_albertoc;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hotelgoldparadise_albertoc.Data.RoomDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomList extends AppCompatActivity implements RoomAdapter.OnRoomButtonClickListener {

    private boolean isEditMode = false;
    private RoomDbHelper myDbHelper;
    private RoomAdapter habitacionAdapter;
    private ArrayList<Room> datos;

    private RoomAdapter roomAdapter;

    EditText numHabitacionEditText;

    Spinner tipoHabitacionSpinner;

    EditText roomNumberEditText;
    EditText capacityInput;
    EditText priceInput;
    Spinner estadoInput;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Button deleteButton = findViewById(R.id.btn_delete_habitacion);
        Button editButton = findViewById(R.id.btn_edit_habitacion);
        Button saveButton = findViewById(R.id.btn_save_habitacion);


        myDbHelper = new RoomDbHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        ListView list = findViewById(R.id.listaHabitaciones);

        ArrayList<Room> myRoomArray = new ArrayList<>();
        myRoomArray = myDbHelper.displayDataBaseInfo();

        datos = new ArrayList<>();
        for (Room habitacion : myRoomArray) {
            if (habitacion != null) {
                Room habitacionBD = new Room(habitacion.getNumeroHabitacion(), habitacion.getTipoHabitacion(), habitacion.getEstado(), habitacion.getCapacidad(), habitacion.getPrecio());
                datos.add(habitacionBD);
            }
        }






        numHabitacionEditText = findViewById(R.id.numHabitacion);
        tipoHabitacionSpinner = findViewById(R.id.tipoHabitacionSpinner);

        RoomDbHelper dbHelper = new RoomDbHelper(this);
        habitacionAdapter = new RoomAdapter(this, 0, datos, dbHelper, this, numHabitacionEditText, tipoHabitacionSpinner);

        list.setAdapter(habitacionAdapter);

        FloatingActionButton addRoomFab = findViewById(R.id.add_room_fab);
        addRoomFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddRoomDialog();
            }
        });

    }

    @Override
    public void onEditClick(int position) {
        // La lógica para editar la habitación en la posición dada se maneja en el adaptador
    }

    public void onDeleteClick(int position) {
        Room habitacion = datos.get(position);
        myDbHelper.eliminarHabitacion(habitacion.getNumeroHabitacion());
        datos.remove(position);
        habitacionAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSaveClick(int position, Room updatedRoom) {
        myDbHelper.actualizarHabitacion(updatedRoom);

        datos.set(position, updatedRoom);
        habitacionAdapter.notifyDataSetChanged();
    }

    // Este método muestra un diálogo para agregar una nueva habitación
    private void showAddRoomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_room, null);
        builder.setView(dialogView);

        final EditText roomNumberEditText = dialogView.findViewById(R.id.room_number_input);
        final Spinner roomTypeSpinner = dialogView.findViewById(R.id.room_type_input);
        final EditText capacityInput = dialogView.findViewById(R.id.capacity_input);
        final EditText priceInput = dialogView.findViewById(R.id.price_input);
        final Spinner estadoInput = dialogView.findViewById(R.id.estado_input);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomTypeSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this, R.array.estado_opciones, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoInput.setAdapter(adapterEstado);

        builder.setTitle("Añadir habitación")
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String roomNumber = roomNumberEditText.getText().toString();
                        String roomType = roomTypeSpinner.getSelectedItem().toString();
                        String roomEstado = estadoInput.getSelectedItem().toString();
                        String capacityString = capacityInput.getText().toString();
                        String priceString = priceInput.getText().toString();

                        if (!roomNumber.isEmpty() && !roomType.isEmpty() && !capacityString.isEmpty() && !priceString.isEmpty()) {
                            int capacity = Integer.parseInt(capacityString);
                            double price = Double.parseDouble(priceString);

                            if (isRoomExist(roomNumber)) {
                                Toast.makeText(RoomList.this, "La habitación ya existe en la base de datos.", Toast.LENGTH_SHORT).show();
                            } else {
                                Room newRoom = new Room(roomNumber, roomType, roomEstado, capacity, price);
                                datos.add(newRoom);
                                myDbHelper.insertarHabitacion(newRoom);
                                habitacionAdapter.notifyDataSetChanged(); // Actualizar el adaptador correcto
                            }
                        } else {
                            Toast.makeText(RoomList.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean validateRoomNumber(int roomNumber, String roomType) {
        switch (roomType) {
            case "Individual":
                return (roomNumber >= 1 && roomNumber <= 9);
            case "Doble":
                return (roomNumber >= 10 && roomNumber <= 19);
            case "Triple":
                return (roomNumber >= 20 && roomNumber <= 29);
            case "Quad":
                return (roomNumber >= 30 && roomNumber <= 39);
            default:

                // En caso de que se introduzca un tipo de habitación no reconocido.
                return false;
        }
    }

    private boolean validateRoomCapacity(int capacity, String roomType) {
        // Aquí asumimos que el tipo de habitación "Individual" tiene capacidad para 1, "Doble" para 2, etc.
        switch (roomType) {
            case "Individual":
                return capacity == 1;
            case "Doble":
                return capacity == 2;
            case "Triple":
                return capacity == 3;
            case "Quad":
                return capacity == 4;
            default:
                return false;
        }
    }

    private boolean validateRoomPrice(double price, String roomType) {
        // Aquí comprobamos si el precio se ajusta a los precios preestablecidos para cada tipo de habitación.
        switch (roomType) {
            case "Individual":
                return price == 115;
            case "Doble":
                return price == 200;
            case "Triple":
                return price == 299;
            case "Quad":
                return price == 400;
            default:
                return false;
        }
    }

    private boolean isRoomExist(String roomNumber) {
        // Usamos la función getHabitacionPorNum para obtener la habitación.
        Room existingRoom = myDbHelper.getHabitacionPorNum(roomNumber);

        // Si existingRoom no es nulo, significa que la habitación ya existe en la base de datos.
        return existingRoom != null;
    }



}

