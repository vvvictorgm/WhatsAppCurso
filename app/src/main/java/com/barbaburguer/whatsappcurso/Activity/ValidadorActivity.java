package com.barbaburguer.whatsappcurso.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barbaburguer.whatsappcurso.Helper.Preferencias;
import com.barbaburguer.whatsappcurso.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {
    private EditText codigoValidar;
    private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidar = findViewById(R.id.edit_code_validacao);
        validar = findViewById(R.id.ButtonValidar);

        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher codValidar = new MaskTextWatcher(codigoValidar, simpleMaskPais);
        codigoValidar.addTextChangedListener(codValidar);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recuperar dados do usuario
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String,String> usuario= preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidar.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "token validado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "token não validado", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

}
