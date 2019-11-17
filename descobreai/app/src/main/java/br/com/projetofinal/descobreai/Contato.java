package br.com.projetofinal.descobreai;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contato extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private EditText edtNameCont;
    private EditText edtEmailCont;
    private EditText edtMessage;
    private Button btnEnviarMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNameCont = (EditText) findViewById(R.id.edtNameCont);
        edtEmailCont = (EditText) findViewById(R.id.edtEmailCont);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnEnviarMsg = (Button) findViewById(R.id.btnEnviarMsg);

          btnEnviarMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Pegar o que foi digitado nesses campos
                final String name = edtNameCont.getText().toString();
                final String email = edtEmailCont.getText().toString();
                final String message = edtMessage.getText().toString();


                mDatabase.child("message").child("name").setValue(name);
                mDatabase.child("message").child("email").setValue(email);
                mDatabase.child("message").child("message").setValue(message);
                ;


            }
        });

    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        finish();
    }

}









