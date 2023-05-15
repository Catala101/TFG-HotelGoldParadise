package com.example.hotelgoldparadise_albertoc.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.hotelgoldparadise_albertoc.ActivityLogin;
import com.example.hotelgoldparadise_albertoc.MainActivity;
import com.example.hotelgoldparadise_albertoc.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelgoldparadise_albertoc.MainActivity;
import com.example.hotelgoldparadise_albertoc.R;

public class SplashActivity extends AppCompatActivity {

    // Variables para almacenar las referencias de los elementos de la vista
    private ImageView backgroundImage;
    private ImageView rotatingWheel;
    private ImageView logo;
    private TextView changingText;
    // Array de cadenas de texto que se mostrarán de manera periódica en el TextView
    private String[] textArray = {"Accediendo a base de datos", "Cargando Usuarios", "Generando estadísticas"};
    // Índice para controlar el texto actual en el TextView
    private int textIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Asignar las referencias de los elementos de la vista a las variables
        backgroundImage = findViewById(R.id.background_image);
        rotatingWheel = findViewById(R.id.rotating_wheel);
        changingText = findViewById(R.id.changing_text);
        logo = findViewById(R.id.logo);

        // Cargar y aplicar la animación de zoom a la imagen de fondo
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out);
        backgroundImage.startAnimation(zoomAnimation);

        // Cargar y aplicar la animación de fade in al logo
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeInAnimation);
        // Cargar la animación de rotación desde el archivo XML y asignarla a la vista de la rueda
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotatingWheel.startAnimation(rotateAnimation);

        // Llamar al método para cambiar el texto periódicamente
        changeTextPeriodically();

        // Crear un nuevo Handler y programarlo para que ejecute el siguiente bloque de código después de 5000 ms (5 segundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Iniciar la actividad principal (MainActivity) y finalizar la actividad actual (SplashActivity)
                Intent intent = new Intent(SplashActivity.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    // Método para cambiar el texto en el TextView de forma periódica
    private void changeTextPeriodically() {
        final Handler handler = new Handler();
        final int delay = 1700; // Retraso en milisegundos (1 segundo)

        // Programar el Handler para que ejecute el siguiente bloque de código cada 1000 ms (1 segundo)
        handler.postDelayed(new Runnable() {
            public void run() {
                // Cambiar el texto en el TextView al siguiente texto en el array
                changingText.setText(textArray[textIndex]);
                // Incrementar el índice del texto y reiniciarlo si supera la longitud del array
                textIndex = (textIndex + 1) % textArray.length;
                // Volver a programar el Handler para ejecutar este bloque de código después de 1 segundo
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
