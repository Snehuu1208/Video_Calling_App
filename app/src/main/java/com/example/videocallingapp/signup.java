package com.example.videocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText emailBox,passwordBox,nameBox;
    Button account,createAccount;

    FirebaseFirestore database;
   private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailBox=findViewById(R.id.editTextTextEmailAddress2);
        passwordBox=findViewById(R.id.editTextTextPassword2);
        account=findViewById(R.id.account);
        createAccount=findViewById(R.id.createaccount);
        nameBox=findViewById(R.id.editTextText);

        auth= FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,pass,name;
                email=emailBox.getText().toString();
                pass=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                if(email.equals("") || pass.equals("") || name.equals("")){
                    Toast.makeText(signup.this, "INVALID!", Toast.LENGTH_SHORT).show();
                }

                Map<String,Object> user=new HashMap<>();
                user.put("Email ID ",email);
                                user.put("Password ",pass);
                user.put("Name ",name);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                             Toast.makeText(signup.this, "Account is Created Successfully...", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(signup.this,Loginpage.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                database.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(signup.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(signup.this,Loginpage.class);
                startActivity(i);
            }
        });
    }
}