package com.barbaburguer.whatsappcurso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class LoginActivity extends AppCompatActivity {
    private EditText telefone,codPais,codArea,nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.editNome);
        telefone = findViewById(R.id.editTelefone);
        codArea = findViewById(R.id.edit_code_area);
        codPais = findViewById(R.id.edit_cod_pais);

        //colocando mascaras
        //Codigo Pais
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskPais = new MaskTextWatcher(codPais, simpleMaskPais);
        codPais.addTextChangedListener(maskPais);

        //Codigo de Area
        SimpleMaskFormatter simpleMaskArea = new SimpleMaskFormatter("(NN) ");
        MaskTextWatcher maskArea = new MaskTextWatcher(codArea, simpleMaskArea);
        codArea.addTextChangedListener(maskArea);

        //telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);
    }
}
