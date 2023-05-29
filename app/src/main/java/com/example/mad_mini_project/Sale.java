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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sale extends AppCompatActivity {


    EditText Type,Price,Quantity,Total;
    String type,price,quantity,total;;

    double tprice,tquantity,ttotal;
    ToggleButton toggler;

    Button calculate,cancel,add;

    FirebaseDatabase data;
    DatabaseReference dataref;
    long maxid=0;

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
        setContentView(R.layout.activity_sale);

        Type = findViewById(R.id.type);
        Price = findViewById(R.id.Price);
        Quantity = findViewById(R.id.quantitty);
        Total = findViewById(R.id.total);
        toggler = findViewById(R.id.WholeSale);
        calculate = findViewById(R.id.Calculate);
        cancel = findViewById(R.id.Cancel);
        add = findViewById(R.id.Add);
        getuserstatus();


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = String.valueOf(Type.getText());
                quantity = String.valueOf(Quantity.getText());
                total = String.valueOf(Total.getText());

                if(type.isEmpty()){
                    Toast.makeText(Sale.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    calculation(type, Integer.parseInt(quantity));

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Sale.this,MainActivity.class));
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                type = String.valueOf(Type.getText());
                price = String.valueOf(Price.getText());
                quantity = String.valueOf(Quantity.getText());
                total = String.valueOf(Total.getText());

                if(type.isEmpty()||price.isEmpty()||quantity.isEmpty()||total.isEmpty())
                {
                    Toast.makeText(Sale.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Salehelper helperdata = new Salehelper(type, String.valueOf(price), String.valueOf(quantity), String.valueOf(total));

                        data = FirebaseDatabase.getInstance();
                        dataref = data.getReference("Sale");
                        DatabaseReference incr = data.getReference().child("Sale");
                        incr.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    maxid=(snapshot.getChildrenCount());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dataref.child(String.valueOf(maxid+1)).setValue(helperdata);
                    } catch (Exception e) {
                        Toast.makeText(Sale.this, "Error: Something Went Wrong", Toast.LENGTH_LONG);
                    }
                    ;
                }
                Type.setText("");
                Price.setText("");
                Quantity.setText("");
                Total.setText("");
            }
        });

    }

    public void getuserstatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        }
        else {
            Toast.makeText(Sale.this, "Session Closed Logging Out.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Sale.this, Login.class));
        }
    }

    public void calculation(String type,int tquantity){


        if(type.isEmpty())
        {
            Toast.makeText(Sale.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            try{
                String togglerinf;
                data = FirebaseDatabase.getInstance();
                type = Type.getText().toString();

                boolean isOn = toggler.isChecked();
                if (!isOn) {
                    togglerinf = "retainPrice";

                } else {
                    togglerinf = "wholesalePrice";

                }

                DatabaseReference priceRef = FirebaseDatabase.getInstance().getReference("Product");
                priceRef.child(type).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){

                                DataSnapshot datafromfb = task.getResult();
                                String pricedata = String.valueOf(datafromfb.child(togglerinf).getValue());
                                Price.setText(pricedata);
                                double sumtotal = Double.valueOf(pricedata)*Double.valueOf(quantity);
                                Total.setText(String.valueOf(sumtotal));
                            }
                            else {
                                Toast.makeText(Sale.this, "Invalid Type", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(Sale.this, "Failed To Read", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
            catch (Exception e){
                Toast.makeText(Sale.this, "An Error Have Occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

}