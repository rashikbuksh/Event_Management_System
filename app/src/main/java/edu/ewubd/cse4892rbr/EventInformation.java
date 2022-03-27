package edu.ewubd.cse4892rbr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EventInformation extends AppCompatActivity {

    private EditText etname;
    private EditText etplace;
    private RadioButton rdindoor;
    private RadioButton rdoutdoor;
    private RadioButton rdonline;
    private EditText datetime;
    private EditText budget;
    private EditText capacity;
    private EditText email;
    private EditText phone;
    private EditText description;
    private TextView eterrors;
    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);

        etname = findViewById(R.id.etname);
        etplace = findViewById(R.id.etplace);
        rdindoor = findViewById(R.id.rdindoor);
        rdoutdoor = findViewById(R.id.rdoutdoor);
        rdonline = findViewById(R.id.rdonline);
        datetime = findViewById(R.id.etdt);
        budget = findViewById(R.id.etbudget);
        capacity = findViewById(R.id.etcapacity);
        email = findViewById(R.id.etemail);
        phone = findViewById(R.id.etphone);
        description = findViewById(R.id.etdescription);
        eterrors = findViewById(R.id.showerror);

        System.out.println("EventInformation- onCreate");

        findViewById(R.id.cancel).setOnClickListener(view -> finish());
        findViewById(R.id.save).setOnClickListener(view -> save());

        Intent i = getIntent();
        existingKey = i.getStringExtra("EventKey");
        if(existingKey != null && !existingKey.isEmpty()) {
            initializeFormWithExistingData(existingKey);
        }

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void initializeFormWithExistingData(String eventKey){

        String value = Util.getInstance().getValueByKey(this, eventKey);
        System.out.println("Key: " + eventKey + "; Value: "+value);

        if(value != null) {
            String[] fieldValues = value.split("-::-");
            String name = fieldValues[0];
            String place = fieldValues[1];
            String date_time = fieldValues[2];
            String capa = fieldValues[3];
            String budgett = fieldValues[4];
            String emailadd = fieldValues[5];
            String phonenm = fieldValues[6];
            String desc = fieldValues[7];
            String eventType1 = fieldValues[8];
            String eventType2 = fieldValues[9];
            String eventType3 = fieldValues[10];

            etname.setText(name);
            etplace.setText(place);
            datetime.setText(date_time);
            capacity.setText(capa);
            budget.setText(budgett);
            email.setText(emailadd);
            phone.setText(phonenm);
            description.setText(desc);
            System.out.println(eventType1);
            System.out.println(eventType2);
            System.out.println(eventType3);

            if(eventType1.equals("true")){
                rdindoor.setChecked(true);
            }
            if (eventType2.equals("true")){
                rdoutdoor.setChecked(true);
            }
            if (eventType3.equals("true")){
                rdonline.setChecked(true);
            }
        }
    }


    private void save() {
        String error = String.valueOf(' ');
        String name = etname.getText().toString().trim();
        String place = etplace.getText().toString().trim();
        String date_time = datetime.getText().toString().trim();
        String cap = capacity.getText().toString();
        int capa = Integer.parseInt(cap);
        String budg = budget.getText().toString().trim();
        int budgett = Integer.parseInt(budg);
        String emailadd = email.getText().toString().trim();
        String phonenm = phone.getText().toString().trim();
        String desc = description.getText().toString().trim();

        boolean indoorisChecked = rdindoor.isChecked();
        boolean outdoorisChecked = rdoutdoor.isChecked();
        boolean onlineisChecked = rdonline.isChecked();
        if(name.length()>15){
            error+="Name length is too high\n";
        }
        else if(!indoorisChecked && !outdoorisChecked && !onlineisChecked){
            error+="No type selected\n";
        }
        else if(capa <= 0){
            error+="Capacity must be greater than 0\n";
        }
        else if(budgett <= 0){
            error+="Budget must be positive\n";
        }
        else if(phonenm.length() != 11){
            error+="Phone Number must be 11 digit\n";
        }
        else if(isValidEmail(emailadd) == false){
            error+="Invalid Email";
        }
        else{
            System.out.println("Event Name: " + name);
            System.out.println("Event Place: " + place);
            System.out.println("Event date time: " + date_time);
            System.out.println("Event capacity: " + capa);
            System.out.println("Event budget: " + budgett);
            System.out.println("Event email: " + emailadd);
            System.out.println("Event phone: " + phonenm);
            System.out.println("Event Description: " + desc);
            System.out.println("Indoor: "+indoorisChecked);
            System.out.println("Outdoor: "+outdoorisChecked);
            System.out.println("Online: "+onlineisChecked);
            error+="No Error Found";
        }
        System.out.println(error);
        eterrors.setText(error);
        //validation
        String key = name;
        String value = name+"-::-"+place+"-::-"+date_time+"-::-"+capa+"-::-"+budgett+"-::-"+
                emailadd+"-::-"+phonenm+"-::-"+desc+"-::-"+indoorisChecked+"-::-"+outdoorisChecked+"-::-"+onlineisChecked;
        Util.getInstance().setKeyValue(this, key, value);
        System.out.println("data saved successfully");
    }

    public void onStart() {

        super.onStart();
        System.out.println("EventInformation- onStart");
    }
    public void onResume() {

        super.onResume();
        System.out.println("EventInformation- onResume");
    }
    public void onRestart() {

        super.onRestart();
        System.out.println("EventInformation- onRestart");
    }
    public void onPause() {

        super.onPause();
        System.out.println("EventInformation- onPause");
    }
    public void onStop() {

        super.onStop();
        System.out.println("EventInformation- onStop");
    }
    public void onDestroy() {

        super.onDestroy();
        System.out.println("EventInformation- onDestroy");
    }
}