package com.example.senddatatofirebasere_databaseappjava;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText dataField1;
    Button sendBtn,receiveBtn;
    TextView idTv,getDataTv;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sendData();

        dataField1 = findViewById(R.id.dataField);
        sendBtn = findViewById(R.id.sendBtn);
        receiveBtn = findViewById(R.id.receiveBtn);
        idTv = findViewById(R.id.idTv);
        getDataTv = findViewById(R.id.getDataTv);

        databaseReference = firebaseDatabase.getReference("Data");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        receiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    public void sendData(){

        if (dataField1 != null){


            String dataFieldText = dataField1.getText().toString();
            String id = databaseReference.push().getKey();

            idTv.setText(id);

            if (!TextUtils.isEmpty(dataFieldText)){
                DataModel dataModel = new DataModel(id,dataFieldText);

                databaseReference.child("Child").child("SubChild").child(id).setValue(dataModel);
            }else {
                Toast.makeText(this, "Edit Text is Empty..!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void getData(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                 getDataTv.setText(Objects.requireNonNull(snapshot.getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}