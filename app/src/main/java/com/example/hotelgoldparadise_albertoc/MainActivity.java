package com.example.hotelgoldparadise_albertoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

//Para el Navigation Drawer
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelgoldparadise_albertoc.Fragments.InsertNewClient;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    Toast textItem;
    TextView listadoClientes, gestionClientes, titulo, subTitulo, operaciones, instrucciones;

    private boolean chatWindowOpen = false; // variable que controla el estado de la ventana de chat

    private LinearLayout chatWindow; // contenedor que muestra la ventana de chat
    private Toolbar toolbar;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView chatHistoryTextView;
    //Código del chat de la aplicación
    private EditText chatInputEditText;
    private Button sendButton;
    private String nombreUsuario;
    private HashMap<String, String> predefinedResponses;

    FloatingActionButton botonChat;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        //Creamos la nueva Toolbar.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titulo = findViewById(R.id.titulo);
        subTitulo = findViewById(R.id.subTitulo);
        instrucciones = findViewById(R.id.textoInformacion);
        operaciones = findViewById(R.id.textoOperaciones);
        listadoClientes = findViewById(R.id.listadoClientes);
        gestionClientes = findViewById(R.id.gestionClientes);

        botonChat = findViewById(R.id.botonChat);

        chatWindow = findViewById(R.id.chatWindow);
        chatWindow.setVisibility(View.GONE); // ocultar la ventana de chat al inicio


        drawerLayout = findViewById(R.id.drawer_layout); // Referencia al DrawerLayout
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view); // Referencia al NavigationView
        navigationView.setNavigationItemSelectedListener(this); // Esta es la línea que debes agregar

        // Obtén el nombre de usuario desde el Intent
        Intent intent = getIntent();
        nombreUsuario = intent.getStringExtra("email");


        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/GreekHouse.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/Golden Ranger.ttf");
        titulo.setTypeface(face1);
        subTitulo.setTypeface(face2);
        instrucciones.setTypeface(face1);
        operaciones.setTypeface(face1);
        listadoClientes.setTypeface(face2);
        gestionClientes.setTypeface(face2);

        //Botón flotante para abrir el chat
        listadoClientes.setOnClickListener(this);
        gestionClientes.setOnClickListener(this);
        botonChat.setOnClickListener(this);

        // Establecer MainActivity como el escucha de clics de menú en el Toolbar
        toolbar.setOnClickListener(this);

        //Ocultamos el título de la Toolbar.
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        chatHistoryTextView = findViewById(R.id.chat_history_textview);
        chatInputEditText = findViewById(R.id.chat_input_edittext);
        sendButton = findViewById(R.id.send_button);

        // Define las respuestas predefinidas del bot
        predefinedResponses = new HashMap<>();
        predefinedResponses.put("hola", "¡Hola! ¿En qué puedo ayudarte?");
        predefinedResponses.put("ayuda", "Claro, estoy aquí para ayudarte. ¿Cuál es tu pregunta?");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = chatInputEditText.getText().toString().toLowerCase().trim();
                if (!userInput.isEmpty()) {
                    // Agrega el mensaje del usuario al historial de chat
                    chatHistoryTextView.append("\nUsuario: " + userInput);

                    // Busca la respuesta predefinida del bot
                    String botResponse = predefinedResponses.get(userInput);

                    if (botResponse != null) {
                        // Agrega la respuesta del bot al historial de chat
                        chatHistoryTextView.append("\nBot: " + botResponse);
                    } else {
                        // Agrega una respuesta genérica si no se encuentra una respuesta predefinida
                        chatHistoryTextView.append("\nBot: No entiendo tu pregunta. Por favor, reformúlala.");
                    }

                    // Limpia el campo de entrada y enfoca el EditText
                    chatInputEditText.setText("");
                    chatInputEditText.requestFocus();
                }
            }
        });

    }
    @Override
    //Creamos la opción de menú en la Toolbar.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        // Encuentra el ítem del menú por ID
        MenuItem itemUsuario = menu.findItem(R.id.itemUsuario);

        // Establece el título del ítem del menú con el nombre del usuario
        itemUsuario.setTitle("Usuario: " + nombreUsuario);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSesion) {
            showCustomPopupMenu(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCustomPopupMenu(MenuItem item) {
        PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, popup.getMenu());
        popup.show();
    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    // Método para controlar las acciones de los elementos del menú
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_tests) {
            // Inicia la actividad principal
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.nav_manual) {
            // Inicia la actividad ClientList
            Intent intent = new Intent(this, RoomList.class);
            startActivity(intent);
            finish();
            return true;
        }else if (id == R.id.nav_profile) {
            // Inicia la actividad ClientList
            Intent intent = new Intent(this, ClientList.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            // Carga el fragmento correspondiente a la opción seleccionada
            displayFragment(id);
        }
        return false;
    }


    // Método para mostrar el fragmento correspondiente al menú seleccionado
    private void displayFragment(int fragmentId) {
        Fragment fragment = null;

        switch (fragmentId) {
            case R.id.nav_tests:
                //No
                break;
            case R.id.nav_manual:
                //fragment = new ManualFragment();
                break;
            case R.id.nav_profile:
                //fragment = new InsertNewClient();
                break;
            case R.id.nav_contact:
                //fragment = new ContactFragment();
                break;
            case R.id.nav_help:
                //fragment = new HelpFragment();
                break;
        }

        // Reemplaza el contenido del FrameLayout por el fragmento seleccionado añade transición
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.contenedorPrincipal, fragment)
                    .commit();
        }
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.listadoClientes:
                textItem = Toast.makeText(getApplicationContext(), "Accediendo a listado de Clientes", Toast.LENGTH_SHORT);
                textItem.show();
                openAnotherActivity1();
                break;

            case R.id.gestionClientes:
                textItem = Toast.makeText(getApplicationContext(), "Accediendo a Gestión de Clientes", Toast.LENGTH_SHORT);
                textItem.show();
                openAnotherActivity2();
                break;
            case R.id.botonChat:
                Toast toast = Toast.makeText(this, "Chat abierto", Toast.LENGTH_SHORT);
                toast.show();
                if (chatWindowOpen) {
                    chatWindow.setVisibility(View.GONE);
                    chatWindowOpen = false;
                     toast = Toast.makeText(this, "Chat cerrado", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    chatWindow.setVisibility(View.VISIBLE);
                    chatWindowOpen = true;
                     toast = Toast.makeText(this, "Chat abierto", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

        }
    }
    public void openAnotherActivity1(){
        Intent i = new Intent(this, ClientList.class);
        startActivity(i);
    }

    public void openAnotherActivity2(){
        Intent i = new Intent(this, ClientGestion.class);
        startActivity(i);
    }

}
