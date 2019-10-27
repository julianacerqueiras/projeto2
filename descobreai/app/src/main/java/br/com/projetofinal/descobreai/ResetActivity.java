package br.com.projetofinal.descobreai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private EditText emailreset;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        //Importar as instacias do Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        emailreset = (EditText) findViewById(R.id.emailreset);
    }

    public void reset( View view ){
        firebaseAuth
                .sendPasswordResetEmail( emailreset.getText().toString() )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if( task.isSuccessful() ){
                            emailreset.setText("");
                            Toast.makeText(
                                    ResetActivity.this,
                                    "Recuperação de acesso iniciada. Email enviado.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        else{
                            Toast.makeText(
                                    ResetActivity.this,
                                    "Falhou! Tente novamente",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
