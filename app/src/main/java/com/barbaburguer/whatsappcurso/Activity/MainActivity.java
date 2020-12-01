package com.barbaburguer.whatsappcurso.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.barbaburguer.whatsappcurso.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referencia.child("teste").setValue(100);
    }
}
