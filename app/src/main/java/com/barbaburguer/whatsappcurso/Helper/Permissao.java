package com.barbaburguer.whatsappcurso.Helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){
        if(Build.VERSION.SDK_INT >= 23){

            List<String> listaPermissoes = new ArrayList<String>();
            //percorrers as permissões passadas, verificar uma a uma se já está liberada
            for(String permissao : permissoes) {
                Boolean validaPermisao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermisao) listaPermissoes.add(permissao);
                }
            //caso a lista não esteja vazia, não é necessário pedir nenhuma permissão
            if(listaPermissoes.isEmpty()) return true;

            //converter a Lista para Array
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //solicetar permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            }
        return true;
    }
}
