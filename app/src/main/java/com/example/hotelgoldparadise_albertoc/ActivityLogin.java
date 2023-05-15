package com.example.hotelgoldparadise_albertoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelgoldparadise_albertoc.Data.UserDbHelper;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity implements SensorEventListener {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private ProgressBar loginProgress;
    private TextView registerText, titulo;
    private ImageView logo;
    private Animation rotateAnimation, heartbeatAnimation;

    //Variables para el sensor de movimiento
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int SHAKE_LEFT = 1;
    private static final int SHAKE_RIGHT = 2;
    private int shakeState = 0;


    // Estructura de datos simple para almacenar nombres de usuario y contraseñas
    private Map<String, String> users = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Usuarios de prueba (en una aplicación real, estos deben ser almacenados de forma segura)
        users.put("ejemplo@gmail.com", "esspee");
        users.put("user2", "password2");
        ImageView backgroundImageView = findViewById(R.id.background_image);

        // Obtén el nombre de usuario desde el Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        // Cargar la animación desde el archivo XML
        //Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.background_zoom_animation);

        // Aplicar la animación a la vista de la imagen
        //backgroundImageView.startAnimation(rotateAnimation);

        titulo = findViewById(R.id.titulo);
        usernameInput = findViewById(R.id.usuario);
        passwordInput = findViewById(R.id.contraseña);
        loginButton = findViewById(R.id.login_button);
        loginProgress = findViewById(R.id.login_progress);
        logo = findViewById(R.id.logo);
        registerText = findViewById(R.id.textoAlta);

        //Sensor de movimiento del teléfono
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        // Cargo la tipografía personalizada
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Golden Ranger.ttf");
        titulo.setTypeface(face);

        // Cargar la animación desde el archivo XML
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.background_zoom_animation);
        // Aplicar la animación a la vista de la imagen
        backgroundImageView.startAnimation(rotateAnimation);

        heartbeatAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heartbeat_animation);
        logo.startAnimation(heartbeatAnimation);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndLogin();
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la vista de registro de usuario
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }


    private void validateAndLogin() {
        final String email = usernameInput.getText().toString().trim(); // Cambiado a 'email' en lugar de 'username'
        final String password = passwordInput.getText().toString().trim();

        if (!isValidEmail(email)) { // Cambiado a 'isValidEmail' en lugar de 'isValidUsername'
            usernameInput.setError("Por favor, ingrese un correo electrónico válido.");
            return;
        }

        if (!isValidPassword(password)) {
            passwordInput.setError("Por favor, ingrese una contraseña válida. Debe tener al menos 6 caracteres.");
            return;
        }

        loginProgress.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        registerText.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loginProgress.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                registerText.setVisibility(View.VISIBLE);
                if (authenticateUser(email, password)) { // Pasar 'email' en lugar de 'username'
                    Toast.makeText(ActivityLogin.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                    intent.putExtra("email", email); // Pasar 'email' en lugar de 'username'
                    startActivity(intent);
                } else {
                    Toast.makeText(ActivityLogin.this, "Correo electrónico o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    registerText.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);
    }

    private boolean isValidEmail(String email) { // Cambiado a 'isValidEmail' en lugar de 'isValidUsername'
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    private boolean authenticateUser(String email, String password) { // Cambiado a 'authenticateUser' para verificar 'email' en lugar de 'username'
        UserDbHelper dbHelper = new UserDbHelper(this);
        return dbHelper.checkUser(email, password);
    }
    //Sensor de movimiento del teléfono para cambiar de fondo
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if (x > 10 && shakeState == 0) {
            shakeState = SHAKE_LEFT;
        } else if (x < -10 && shakeState == SHAKE_LEFT) {
            shakeState = SHAKE_RIGHT;
            // Iniciar sesión después de detectar movimiento izquierdo y luego derecho
            validateAndLogin();

            // Reinicia el estado del sensor de movimiento después de 2 segundos
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    shakeState = 0;
                }
            }, 2000);
        }
    }

    // Reinicia el estado del sensor de movimiento
    private void resetShakeState() {
        shakeState = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // No se utiliza en este ejemplo pero es necesario implementar el método
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
