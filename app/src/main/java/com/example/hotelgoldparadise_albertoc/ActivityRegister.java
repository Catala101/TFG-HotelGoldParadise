package com.example.hotelgoldparadise_albertoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelgoldparadise_albertoc.Data.UserDbHelper;

public class ActivityRegister extends AppCompatActivity {

    EditText usernameInput, passwordInput, confirmPasswordInput, emailInput;
    Button registerButton;
    TextView titulo, usuarioText, contrasenaText, confirmContrasenaText, regresar;
    ImageView logo, backgroundImageView;
    Animation rotateAnimation, heartbeatAnimation;
    UserDbHelper userDbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDbHelper = new UserDbHelper(this);

        usernameInput = findViewById(R.id.usuario);
        passwordInput = findViewById(R.id.contraseña);
        emailInput = findViewById(R.id.email);
        confirmPasswordInput = findViewById(R.id.passwordRepite);

        registerButton = findViewById(R.id.register_button);

        titulo = findViewById(R.id.titulo);
        usuarioText = findViewById(R.id.usuarioText);
        contrasenaText = findViewById(R.id.contraseñaText);
        confirmContrasenaText = findViewById(R.id.passwordRepiteText);
        regresar = findViewById(R.id.login_text);
        logo = findViewById(R.id.logo);
        backgroundImageView = findViewById(R.id.background_image);

        // Cargo la tipografía personalizada
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Golden Ranger.ttf");
        titulo.setTypeface(face);

        // Cargar la animación desde el archivo XML
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.background_zoom_animation);
        // Aplicar la animación a la vista de la imagen
        backgroundImageView.startAnimation(rotateAnimation);

        heartbeatAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heartbeat_animation);
        logo.startAnimation(heartbeatAnimation);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    String username = usernameInput.getText().toString().trim();
                    String password = passwordInput.getText().toString().trim();
                    String email = emailInput.getText().toString().trim();

                    boolean success = userDbHelper.insertUser(username, password, email);

                    if (success) {
                        Toast.makeText(ActivityRegister.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        //Muestre el usuario completo en el registro
                        Log.d("User", userDbHelper.getUser(username).toString());
                    } else {
                        Toast.makeText(ActivityRegister.this, "Error al registrar usuario.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean validateInputs() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (username.isEmpty()) {
            usernameInput.setError("El campo de usuario no puede estar vacío.");
            return false;
        }

        if (password.isEmpty()) {
            passwordInput.setError("El campo de contraseña no puede estar vacío.");
            return false;
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.setError("Debe confirmar su contraseña.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Las contraseñas no coinciden.");
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // Navegar hacia atrás a la pantalla de inicio de sesión
        Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }
}
