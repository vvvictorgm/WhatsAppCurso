package com.barbaburguer.whatsappcurso.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barbaburguer.whatsappcurso.Helper.Permissao;
import com.barbaburguer.whatsappcurso.Helper.Preferencias;
import com.barbaburguer.whatsappcurso.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText telefone,codPais,codArea,nome;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

        nome = findViewById(R.id.editNome);
        telefone = findViewById(R.id.editTelefone);
        codArea = findViewById(R.id.edit_code_area);
        codPais = findViewById(R.id.edit_cod_pais);
        cadastrar = findViewById(R.id.buttonCadastrar);

        //colocando mascaras
        //Codigo Pais
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskPais = new MaskTextWatcher(codPais, simpleMaskPais);
        codPais.addTextChangedListener(maskPais);

        //Codigo de Area
        SimpleMaskFormatter simpleMaskArea = new SimpleMaskFormatter("NN ");
        MaskTextWatcher maskArea = new MaskTextWatcher(codArea, simpleMaskArea);
        codArea.addTextChangedListener(maskArea);

        //telefone
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener(maskTelefone);

        //adicionar evento de listener para o botão Cadastrar
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString()+
                        codArea.getText().toString()+
                        telefone.getText().toString();

                //remover o simbos gerados na mascara + e -
                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao.replace("-", "");

                // gerando token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000)+1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "Whatsapp código de confirmação: " +token;

                //salvar dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario,telefoneSemFormatacao,token);

                //Enviar SMS
                boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);
                if(enviadoSMS){
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(LoginActivity.this,"problema ao enviar SMS", Toast.LENGTH_LONG).show();
                }

                /*
                //recuperar dados
                HashMap<String,String> usuario = preferencias.getDadosUsuario();
                Log.i("DADOS DEPOIS", "token: "+ usuario.get("token")+" nome: "+ usuario.get("nome")+" telefone: "+ usuario.get("telefone"));
                 */
            }
        });
    }
    //Envio de SMS
    private boolean enviaSMS(String telefone, String mensagem){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //metodo super para caso o usuario não der permissão para envio do token (SMS), fechar  o aplicativo

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grandResults){

        super.onRequestPermissionsResult(requestCode,permissions,grandResults);

        for (int resultado : grandResults){
            if(resultado == PackageManager.PERMISSION_DENIED){
                AlertValidacaoPermicao();

            }
        }

    }
    //caso negado, irá fazer um AlertDialog avisando que não se pode funcionar sem as permissões
    private void AlertValidacaoPermicao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app é necessário utilizar as permissões");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
