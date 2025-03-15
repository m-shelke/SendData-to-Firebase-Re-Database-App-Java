package com.example.senddatatofirebasere_databaseappjava;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText dataField1;
    Button sendBtn;
    TextView demoTv;

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
        demoTv = findViewById(R.id.demoTv);

        databaseReference = firebaseDatabase.getReference("Data");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

    }

    public void sendData(){

        if (dataField1 != null){


            String dataFieldText = dataField1.getText().toString();
            String id = databaseReference.push().getKey();

            demoTv.setText(id);

            if (!TextUtils.isEmpty(dataFieldText)){
                DataModel dataModel = new DataModel(id,dataFieldText);

                databaseReference.child("Child").child("SubChild").child(id).setValue(dataModel);
            }else {
                Toast.makeText(this, "Edit Text is Empty..!!", Toast.LENGTH_SHORT).show();
            }
        }



    }

}