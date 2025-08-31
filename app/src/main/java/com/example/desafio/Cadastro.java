package com.example.desafio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {

    private EditText editNome, editEmail, editSenha;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        // Esconde ActionBar para interface mais limpa
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        iniciarComponentes();

        // Ação do botão de cadastro
        btCadastrar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString().trim();

            // Validações básicas dos campos
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
                return;
            }
            if (senha.length() < 6) {
                Toast.makeText(this, "Senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("UsuarioPrefs", MODE_PRIVATE);

            // Verifica se já existe cadastro com o mesmo e-mail
            String emailSalvo = prefs.getString("email", "");
            if (email.equals(emailSalvo)) {
                Toast.makeText(this, "Usuário já cadastrado", Toast.LENGTH_SHORT).show();
                return;
            }

            // Salva novo usuário localmente
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nome", nome);
            editor.putString("email", email);
            editor.putString("senha", senha);
            editor.putBoolean("logado", false); // será definido no login
            editor.apply();

            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

            // Após cadastro, volta para tela de login
            startActivity(new Intent(Cadastro.this, Login.class));
            finish();
        });
    }

    // Associa variáveis aos componentes do layout
    private void iniciarComponentes() {
        editNome = findViewById(R.id.edit_nome);
        editEmail = findViewById(R.id.edit_email);
        editSenha = findViewById(R.id.edit_senha);
        btCadastrar = findViewById(R.id.bt_cadastrar);
    }
}
