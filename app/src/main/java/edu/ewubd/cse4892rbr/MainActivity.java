package edu.ewubd.cse4892rbr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnCreate, btnExit, btnAttendence;

    // Reference objects for handling event lists
    private ListView lvEvents;
    private ArrayList<Event> events;
    private CustomEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnCreateNew = findViewById(R.id.cnew);
        btnCreateNew.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this,EventInformation.class);
            startActivity(i);
        });
        Button btnAttendence = findViewById(R.id.attendence);
        btnAttendence.setOnClickListener(view -> {
            System.out.println("Attendence");
            Intent i = new Intent(this,myattendence.class);
            startActivity(i);
        });
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> finish());

        lvEvents = findViewById(R.id.lvEvents);
        // load events from database if there is any
        loadData();
    }
    private void loadData(){
        events = new ArrayList<>();
        KeyValueDB db = new KeyValueDB(this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        //events = new Event[rows.getCount()];
        while (rows.moveToNext()) {
            String key = rows.getString(0);
            String eventData = rows.getString(1);

            String[] fieldValues = eventData.split("-::-");
            String name = fieldValues[0];
            String place = fieldValues[1];
            String date_time = fieldValues[2];
            String capa = fieldValues[3];
            String budgett = fieldValues[4];
            String emailadd = fieldValues[5];
            String phonenm = fieldValues[6];
            String desc = fieldValues[7];
            String eventType = null;
            String eventType1 = fieldValues[8];
            String eventType2 = fieldValues[9];
            String eventType3 = fieldValues[10];
            if(eventType1.equals("Indoor")){
                eventType = "Indoor";
            }
            if(eventType2.equals("Outdoor")){
                eventType = "Outdoor";
            }
            if(eventType3.equals("Online")){
                eventType = "Online";
            }
            Event e = new Event(key, name, place, date_time, capa, budgett, emailadd, phonenm, desc, eventType);
            events.add(e);
        }
        db.close();
        adapter = new CustomEventAdapter(this, events);
        lvEvents.setAdapter(adapter);

        // handle the click on an event-list item
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // String item = (String) parent.getItemAtPosition(position);
                System.out.println(position);
                Intent i = new Intent(MainActivity.this, EventInformation.class);
                i.putExtra("EventKey", events.get(position).key);
                startActivity(i);
            }
        });
        // handle the long-click on an event-list item
        lvEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //String message = "Do you want to delete event - "+events[position].name +" ?";
                String message = "Do you want to delete event - "+events.get(position).name +" ?";
                System.out.println(message);
                showDialog(message, "Delete Event", events.get(position).key);
                return true;
            }
        });
    }
    public void showDialog(String message, String title, String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);

        builder.setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Util.getInstance().deleteByKey(MainActivity.this,key);
                dialog.cancel();
                loadData();
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();

        alert.show();
    }
    public void onStart() {

        super.onStart();
    }
    public void onResume() {

        super.onResume();
    }
    public void onRestart() {

        super.onRestart();
        loadData();
    }
    public void onPause() {

        super.onPause();
    }
    public void onStop() {

        super.onStop();
        events.clear();
    }
    public void onDestroy() {

        super.onDestroy();
    }
}