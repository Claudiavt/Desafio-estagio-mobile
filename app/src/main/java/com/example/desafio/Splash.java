package com.example.desafio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    Handler handler = new Handler(); // Controla o tempo de exibição da splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove título e deixa tela em fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // Recupera se o usuário já está logado no app
        SharedPreferences prefs = getSharedPreferences("UsuarioPrefs", MODE_PRIVATE);
        boolean logado = prefs.getBoolean("logado", false);

        // Define próxima tela: principal se logado, login se não logado
        Class proximaTela = logado ? MainActivity.class : Login.class;

        // Aguarda 3 segundos e abre a próxima tela
        handler.postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), proximaTela);
            startActivity(intent);
            finish(); // Fecha a splash para não retornar nela
        }, 3000);
    }
}
