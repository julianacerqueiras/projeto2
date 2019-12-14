package br.com.projetofinal.descobreai;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Contato extends AppCompatActivity {

    DatabaseReference mDatabase;
    private EditText edtNameCont;
    private EditText edtEmailCont;
    private EditText edtMessage;
    private Button btnEnviarMsg;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        edtNameCont = (EditText) findViewById(R.id.edtNameCont);
        edtEmailCont = (EditText) findViewById(R.id.edtEmailCont);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnEnviarMsg = (Button) findViewById(R.id.btnEnviarMsg);

        //Importar as instacias do Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = mDatabase.push().getKey();


        btnEnviarMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pegar o que foi digitado nesses campos
                 String namecont = edtNameCont.getText().toString();
                 String emailcont = edtEmailCont.getText().toString();
                 String  messagecont = edtMessage.getText().toString();

                if (!namecont.isEmpty() && !emailcont.isEmpty() && !messagecont.isEmpty()) {

                    enviarmsg(namecont, emailcont ,messagecont);

                } else{
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }
    private void enviarmsg(String namecont, String emailcont, String messagecont) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", namecont);
        map.put("email", emailcont);
        map.put("message", messagecont);

        String id =  mDatabase.push().getKey();

        mDatabase.child("Users").child(id).setValue(map);
        mDatabase.child("Users").child("message").setValue(messagecont);

        Toast.makeText(getApplicationContext(), "Mensagem enviada!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        finish();



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        finish();
    }
}










