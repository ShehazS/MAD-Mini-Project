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


public class Add extends AppCompatActivity {

    Button add,delete,update,view,cancel;
    EditText type,wp,rp,qty;
    FirebaseDatabase data;
    DatabaseReference dataref;

    String Type,WholesalePrice,RetainPrice,Quantity;

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
        setContentView(R.layout.activity_add);

        add = findViewById(R.id.Add);
        delete = findViewById(R.id.Delete);
        update = findViewById(R.id.Update);
        view = findViewById(R.id.View);
        cancel = findViewById(R.id.Cancel);
        type = findViewById(R.id.type);
        wp = findViewById(R.id.Price);
        rp = findViewById(R.id.quantitty);
        qty = findViewById(R.id.total);

        getuserstatus();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type = String.valueOf(type.getText());
                WholesalePrice = String.valueOf(wp.getText());
                RetainPrice = String.valueOf(rp.getText());
                Quantity = String.valueOf(qty.getText());
                try{
                    if(Type.isEmpty()){
                        Toast.makeText(Add.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        data = FirebaseDatabase.getInstance();
                        dataref = data.getReference("Product");
                        dataref.child(Type).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){

                                        Toast.makeText(Add.this, "Item Already Exist", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        ProductHelperClass helperdata = new ProductHelperClass(Type,WholesalePrice,RetainPrice,Quantity);

                                        data = FirebaseDatabase.getInstance();
                                        dataref = data.getReference("Product");

                                        dataref.child(Type).setValue(helperdata);
                                        Toast.makeText(Add.this, "Item Added To Database", Toast.LENGTH_SHORT).show();
                                        type.setText("");
                                        wp.setText("");
                                        rp.setText("");
                                        qty.setText("");
                                    }
                                }
                                else {
                                    Toast.makeText(Add.this, "Failed To Connect to Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch (Exception e){
                    Toast.makeText(Add.this,"Error: Something Went Wrong",Toast.LENGTH_LONG);
                };
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Type = String.valueOf(type.getText());
                if(Type.isEmpty())
                {
                    Toast.makeText(Add.this, "Please Provide Valid Item Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    try{


                        data = FirebaseDatabase.getInstance();
                        dataref = data.getReference("Product");
                        dataref.child(Type).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){

                                        dataref.removeValue();
                                        Toast.makeText(Add.this, "Deleted Item", Toast.LENGTH_SHORT).show();
                                        type.setText("");
                                        wp.setText("");
                                        rp.setText("");
                                        qty.setText("");
                                    }
                                    else {
                                        Toast.makeText(Add.this, "Item Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(Add.this, "Failed To Read", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                    }catch (Exception e){
                        Toast.makeText(Add.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Type = String.valueOf(type.getText());
                WholesalePrice = String.valueOf(wp.getText());
                RetainPrice = String.valueOf(rp.getText());
                Quantity = String.valueOf(qty.getText());
                try{
                    if(Type.isEmpty()){
                        Toast.makeText(Add.this, "Please Check Fields", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        data = FirebaseDatabase.getInstance();
                        dataref = data.getReference("Product");
                        dataref.child(Type).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){

                                        ProductHelperClass helperdata = new ProductHelperClass(Type,WholesalePrice,RetainPrice,Quantity);

                                        data = FirebaseDatabase.getInstance();
                                        dataref = data.getReference("Product");

                                        dataref.child(Type).setValue(helperdata);
                                        Toast.makeText(Add.this, "Item Updated in Database", Toast.LENGTH_SHORT).show();
                                        type.setText("");
                                        wp.setText("");
                                        rp.setText("");
                                        qty.setText("");

                                    }
                                    else {
                                        Toast.makeText(Add.this, "Item Does not Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(Add.this, "Failed To Connect to Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch (Exception e){
                    Toast.makeText(Add.this,"Error: Something Went Wrong",Toast.LENGTH_LONG);
                };
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    data = FirebaseDatabase.getInstance();
                    dataref = data.getReference("Product");
                    Query dbqry = dataref.orderByValue();
                    dbqry.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dbtree) {
                            List<String> itemdetails = new ArrayList<>();
                            itemdetails.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            for(DataSnapshot dbts:dbtree.getChildren()){
                                ItemsHelperDb items = new ItemsHelperDb();
                                items.setType(dbts.child("type").getValue(String.class));
                                items.setQuantity(dbts.child("quantity").getValue(String.class));
                                items.setRPrice(dbts.child("retainPrice").getValue(String.class));
                                items.setWPrice(dbts.child("wholesalePrice").getValue(String.class));
                                itemdetails.add(items.toString());
                                itemdetails.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                            }
                            AlertDialog.Builder popup = new AlertDialog.Builder(Add.this);
                            popup.setTitle("Available Items");
                            popup.setItems(itemdetails.toArray(new String[itemdetails.size()]),null);
                            popup.setPositiveButton("Close",null);
                            popup.show();
                            dbqry.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Add.this, "An Error Have Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (Exception e){
                    Toast.makeText(Add.this, "An Error Have Occurred", Toast.LENGTH_SHORT).show();
                }
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