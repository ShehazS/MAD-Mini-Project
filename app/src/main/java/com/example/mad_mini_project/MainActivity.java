package com.example.mad_mini_project;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btn,sale,manageproduct,viewsale;
    FirebaseDatabase data;
    DatabaseReference dataref;

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
        viewsale = findViewById(R.id.ViewSale);

        viewsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    data = FirebaseDatabase.getInstance();
                    dataref = data.getReference("Sale");
                    Query dbqry = dataref.orderByValue();
                    dbqry.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dbtree) {
                            List<String> itemdetails = new ArrayList<>();
                            itemdetails.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            for(DataSnapshot dbts:dbtree.getChildren()){
                                Salehelper items = new Salehelper();
                                items.setType(dbts.child("type").getValue(String.class));
                                items.setPrice(dbts.child("price").getValue(String.class));
                                items.setQuantity(dbts.child("quantity").getValue(String.class));
                                items.setTotal(dbts.child("total").getValue(String.class));
                                itemdetails.add(items.toString());
                                itemdetails.add("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                            }
                            AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);
                            popup.setTitle("Available Items");
                            popup.setItems(itemdetails.toArray(new String[itemdetails.size()]),null);
                            popup.setPositiveButton("Close",null);
                            popup.show();
                            dbqry.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "An Error Have Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "An Error Have Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
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