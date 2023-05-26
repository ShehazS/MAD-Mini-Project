package com.example.mad_mini_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {

    Button add,delete,update,view,cancel;
    EditText type,wp,rp,qty;
    FirebaseDatabase data;
    DatabaseReference dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        getuserstatus();

        add = findViewById(R.id.Add);
        delete = findViewById(R.id.Delete);
        update = findViewById(R.id.Update);
        view = findViewById(R.id.View);
        cancel = findViewById(R.id.Cancel);
        type = findViewById(R.id.type);
        wp = findViewById(R.id.WPrice);
        rp = findViewById(R.id.RPrice);
        qty = findViewById(R.id.Quantity);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = FirebaseDatabase.getInstance();
                dataref = data.getReference("Product");
                String Type = String.valueOf(type.getText());
                String WholesalePrice = String.valueOf(wp.getText());
                String RetainPrice = String.valueOf(rp.getText());
                String Quantity = String.valueOf(qty.getText());
                ProductHelperClass helper = new ProductHelperClass(Type,WholesalePrice,RetainPrice,Quantity);

                dataref.setValue(helper);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add.this,MainActivity.class));
                finish();

            }
        });

    }

    public void getuserstatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        }
        else {
            Toast.makeText(Add.this, "Session Closed Logging Out.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Add.this, Login.class));
        }
    }
}