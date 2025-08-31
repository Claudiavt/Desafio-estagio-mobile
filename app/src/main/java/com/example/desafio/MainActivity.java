package com.example.desafio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textNomeUsuario, textEmailUsuario;
    private Button btDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referência aos componentes do layout
        textNomeUsuario = findViewById(R.id.textNomeUsuario);
        textEmailUsuario = findViewById(R.id.textEmailUsuario);
        btDeslogar = findViewById(R.id.bt_deslogar);

        // Recupera dados salvos do usuário
        SharedPreferences prefs = getSharedPreferences("UsuarioPrefs", MODE_PRIVATE);
        String nome = prefs.getString("nome", "Usuário");
        String email = prefs.getString("email", "email@exemplo.com");

        // Exibe informações na tela principal
        textNomeUsuario.setText(nome);
        textEmailUsuario.setText(email);

        // Ação de logout
        btDeslogar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("logado", false); // Marca como deslogado
            editor.apply();

            // Volta para tela de login e finaliza a principal
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
