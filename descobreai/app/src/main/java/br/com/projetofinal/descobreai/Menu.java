package br.com.projetofinal.descobreai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    private Button btnLogout;
    private ImageView btnMap;
    private ImageView btnSobre;
    private ImageView btnContato;
    private ImageView btnQrCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //Botão de Mapa
        btnMap = (ImageView) findViewById(R.id.mapsIcon);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
                finish();
            }
        });

        //Botão de Sobre
        btnSobre = (ImageView) findViewById(R.id.sobreIcon);
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sobre.class);
                startActivity(intent);
                finish();
            }
        });

        //Botão de Contato
        btnContato = (ImageView) findViewById(R.id.contatoIcon);
        btnContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contato.class);
                startActivity(intent);
                finish();
            }
        });

        //Botão QrCode
        btnQrCode = (ImageView) findViewById(R.id.qrCodeIcon);
        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QrCode.class);
                startActivity(intent);
                finish();

            }
        });


        //Botão de Logout
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });


    }

    private void disconnect() {
        FirebaseAuth.getInstance().signOut();
        closeMenu();
    }

    private void closeMenu(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
