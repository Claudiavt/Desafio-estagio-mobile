package com.example.desafio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btEntrar;
    private TextView text_tela_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Esconde a ActionBar para tela mais limpa
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        IniciarComponentes();

        SharedPreferences prefs = getSharedPreferences("UsuarioPrefs", MODE_PRIVATE);

        // Se já estiver logado, pula login e vai direto para tela principal
        boolean logado = prefs.getBoolean("logado", false);
        if (logado) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        // Ação do botão de entrar
        btEntrar.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            // Verifica campos vazios
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Valida formato do e-mail
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Login.this, "E-mail inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            // Recupera dados salvos no cadastro
            String emailSalvo = prefs.getString("email", "");
            String senhaSalva = prefs.getString("senha", "");

            // Verifica credenciais
            if (email.equals(emailSalvo) && senha.equals(senhaSalva)) {
                prefs.edit().putBoolean("logado", true).apply(); // Salva estado logado
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(Login.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
            }
        });

        // Link para a tela de cadastro
        text_tela_cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });
    }

    // Associa variáveis aos componentes do layout
    private void IniciarComponentes() {
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btEntrar = findViewById(R.id.bt_entrar);
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
    }
}
