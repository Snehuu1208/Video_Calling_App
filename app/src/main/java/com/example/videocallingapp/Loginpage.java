  package com.example.videocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

  public class Loginpage extends AppCompatActivity {

    EditText emailBox,passwordBox;
    Button login,signup;

    private FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait...");

        emailBox=findViewById(R.id.editTextTextEmailAddress);
        passwordBox=findViewById(R.id.editTextTextPassword);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.create);
        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String email,password;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
              auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      dialog.dismiss();
                      if(task.isSuccessful()){
                          Toast.makeText(Loginpage.this, "Logged in...", Toast.LENGTH_SHORT).show();
                          Intent i=new Intent(Loginpage.this, Dashboard.class);
                          startActivity(i);
                      }else{
                          Toast.makeText(Loginpage.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                      }
                  }
              });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Loginpage.this,signup.class);
                startActivity(i);
            }
        });
    }
}