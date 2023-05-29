package com.example.mad_mini_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView gotologin;
    EditText em,psw;
    String email,password;
    Button btn;

    ProgressBar br;

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
        setContentView(R.layout.activity_registration);

        em = findViewById(R.id.username);
        psw = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        gotologin = findViewById(R.id.textView);

        br = findViewById(R.id.progressBar);
        btn = findViewById(R.id.button);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = String.valueOf(em.getText());
                password = String.valueOf(psw.getText());
                br.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Registration.this, "User Added.",
                                            Toast.LENGTH_SHORT).show();
                                    br.setVisibility(View.GONE);
                                    startActivity(new Intent(Registration.this,Login.class));
                                    finish();

                                } else {
                                    Toast.makeText(Registration.this, "User Already Exist.",
                                            Toast.LENGTH_SHORT).show();
                                    br.setVisibility(View.GONE);

                                }
                            }
                        });
            }
        });

    }
}