package com.talentstakeaway.salesman_surya;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.icu.text.AlphabeticIndex;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.focusableInTouchMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    private ListView myListView;
    private LinearLayout main_linear_layout;
    private Button datepicker_btn1,datepicker_btn2;
    private TextView fromdate,todate;
    private EditText distributor_search, salesman_search;
    private ArrayList<Report> myReportArrayList = new ArrayList<Report>();
    Calendar myCalendar = Calendar.getInstance();
    DistributorAdapter dis_adapter;
    SalesmanAdapter sal_adapter;
    String myFormat = "dd/MM/yy"; //In which you need put here
    SimpleDateFormat sdf = null;
    static int view_main;




// get the firebase database instance , get the rootreference. using the rootreference get the child
    private FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    private DatabaseReference rootReference=firebaseDatabase.getReference();
    private DatabaseReference Task_Allocation_Report_Reference = rootReference.child("Task_Allocation_Report");
    private DatabaseReference Records_Reference = Task_Allocation_Report_Reference.child("Records");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();

        distributor_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                dis_adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        salesman_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                sal_adapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Records_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CollectDistributor((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error:","cancelled in addvalueEventListerner");
            }
        });


    }

    private void Initialize() {


        myListView=(ListView)findViewById(R.id.myListView);

        datepicker_btn1=(Button)findViewById(R.id.datepicker_btn1);
        datepicker_btn2=(Button)findViewById(R.id.datepicker_btn2);

        fromdate=(TextView)findViewById(R.id.fromdate);
        todate=(TextView)findViewById(R.id.ToDate);


        distributor_search=(EditText)findViewById(R.id.distributor_search);
        salesman_search = (EditText)findViewById(R.id.salesman_search);

        main_linear_layout=(LinearLayout)findViewById(R.id.main_linear_layout);
        main_linear_layout.clearFocus();

        datepicker_btn1.setOnClickListener(this);
        datepicker_btn2.setOnClickListener(this);

        distributor_search.setOnFocusChangeListener(this);
        salesman_search.setOnFocusChangeListener(this);

    }


    private void CollectDistributor(Map<String, Object> value) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            //Get user map
            Map singleRow = (Map) entry.getValue();
            Report reportObj = new Report((String) singleRow.get("Sl"), (String) singleRow.get("Date"), (String) singleRow.get("Distributor"), (String) singleRow.get("Salesman"), (String) singleRow.get("Task Location"), (String) singleRow.get("Start Time"), (String) singleRow.get("End Time"), (String) singleRow.get("Task Description"), (String) singleRow.get("Status"));
            myReportArrayList.add(reportObj);
        }
        dis_adapter = new DistributorAdapter(MainActivity.this, myReportArrayList);
        myListView.setAdapter(dis_adapter);
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextView();
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateTextView() {
            sdf = new SimpleDateFormat(myFormat, Locale.US);
     switch(view_main) {
         case R.id.datepicker_btn1:
             fromdate.setText(sdf.format(myCalendar.getTime()));
             break;
         case R.id.datepicker_btn2:
             todate.setText(sdf.format(myCalendar.getTime()));
             break;
     }
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        view_main=id;
        switch(id){

            case R.id.datepicker_btn1:
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.datepicker_btn2:
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id=v.getId();
        switch (id){
            case R.id.distributor_search:
                if(hasFocus){
                    dis_adapter = new DistributorAdapter(MainActivity.this, myReportArrayList);
                    myListView.setAdapter(dis_adapter);
                }else {
                    distributor_search.setText("");
                    distributor_search.setCursorVisible(false);

                }
                break;
            case R.id.salesman_search:
                if(hasFocus){
                    sal_adapter=new SalesmanAdapter(MainActivity.this,myReportArrayList);
                    myListView.setAdapter(sal_adapter);
                }else {
                    salesman_search.setText("");
                    salesman_search.setCursorVisible(false);
                }
                break;
        }
    }
}
