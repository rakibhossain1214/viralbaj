package com.example.viralbaj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatepromocodeActivity extends AppCompatActivity {

    Button btnCreate;

    EditText codeId, codeStatus, startDate, endDate, discPercentage, discAmount;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpromocode);

        btnCreate = (Button) findViewById(R.id.btn_create);

        codeId = (EditText) findViewById(R.id.code_id);
        codeStatus = (EditText) findViewById(R.id.code_status);
        startDate = (EditText) findViewById(R.id.start_date);
        endDate = (EditText) findViewById(R.id.end_date);
        discPercentage = (EditText) findViewById(R.id.discount_percentage);
        discAmount = (EditText) findViewById(R.id.discount_amount);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

       btnCreate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               reference.child("promocodes").child(codeId.getText().toString()).child("codeid").setValue(codeId.getText().toString());
               reference.child("promocodes").child(codeId.getText().toString()).child("codestatus").setValue(codeStatus.getText().toString());
               reference.child("promocodes").child(codeId.getText().toString()).child("startdate").setValue(startDate.getText().toString());
               reference.child("promocodes").child(codeId.getText().toString()).child("enddate").setValue(endDate.getText().toString());
               reference.child("promocodes").child(codeId.getText().toString()).child("discountpercentage").setValue(discPercentage.getText().toString());
               reference.child("promocodes").child(codeId.getText().toString()).child("discountamount").setValue(discAmount.getText().toString());

               Toast.makeText(CreatepromocodeActivity.this, "Promo Code Created Successfully!", Toast.LENGTH_SHORT).show();
           }
       });

    }
}
