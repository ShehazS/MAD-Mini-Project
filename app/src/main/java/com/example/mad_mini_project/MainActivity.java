package com.example.mad_mini_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btn,sale,manageproduct;

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
        setContentView(R.layout.activity_main);

        getuserstatus();

        btn = findViewById(R.id.button2);
        sale = findViewById(R.id.sale);
        manageproduct = findViewById(R.id.ManageProducts);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getuserstatus();
            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Sale.class));
                finish();
            }
        });

        manageproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Add.class));
                finish();
            }
        });

    }

    public void getuserstatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        }
        else {
            Toast.makeText(MainActivity.this, "Session Closed Logging Out.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
    }
}