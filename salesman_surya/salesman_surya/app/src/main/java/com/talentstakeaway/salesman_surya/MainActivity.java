package com.talentstakeaway.salesman_surya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView row_1_distributor,row_2_distributor;


// get the firebase database instance , get the rootreference. using the rootreference get the child
    private FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    private DatabaseReference rootReference=firebaseDatabase.getReference();
    private DatabaseReference Task_Allocation_Report_Reference = rootReference.child("Task_Allocation_Report");
    private DatabaseReference Records_Reference = Task_Allocation_Report_Reference.child("Records");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        row_1_distributor=(TextView)findViewById(R.id.row1);
        row_2_distributor=(TextView)findViewById(R.id.row2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Records_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CollectDistributor((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CollectDistributor(Map<String, Object> value) {

        ArrayList<String> Distributors = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : value.entrySet()){

            //Get user map
            Map singleRow = (Map) entry.getValue();
            //Get phone field and append to list
            Distributors.add((String) singleRow.get("Distributor"));
        }

        row_1_distributor.setText(Distributors.get(1).toString());
        row_2_distributor.setText(Distributors.get(0).toString());
    }
}
