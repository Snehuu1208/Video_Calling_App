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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {

    EditText emailBox,passwordBox,nameBox;
    Button account,createAccount;

    private FirebaseFirestore database;
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

                User user=new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users")
                                            .document().set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Intent i=new Intent(signup.this,Loginpage.class);
                                                    startActivity(i);
                                                }
                                            });
                            Toast.makeText(signup.this, "Account is Created Successfully...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}