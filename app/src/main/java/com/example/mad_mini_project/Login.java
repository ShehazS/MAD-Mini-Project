package com.example.mad_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText un,pwd;
    Button btn;
    ProgressBar br;
    String email,password;
    private FirebaseAuth mAuth;
    TextView gotoreg;

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Exit Application?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
        return;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        un = findViewById(R.id.username);
        pwd = findViewById(R.id.editTextTextPassword);
        br = findViewById(R.id.progressBar);
        btn = findViewById(R.id.button);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        gotoreg = findViewById(R.id.textView);

        gotoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br.setVisibility(View.VISIBLE);
                email = String.valueOf(un.getText());
                password = String.valueOf(pwd.getText());

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    br.setVisibility(View.GONE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Login.this, "Welcome "+user,
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();
                                } else {
                                    br.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Invalid Username Or Passwword.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}