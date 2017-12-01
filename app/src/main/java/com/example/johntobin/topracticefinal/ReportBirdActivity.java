package com.example.johntobin.topracticefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;
import java.util.HashMap;

public class ReportBirdActivity extends AppCompatActivity {

    private EditText editTextBirdName, editTextZip, editTextDate, editTextBirdPerson;
    private Button buttonSubmit;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bird);

        buttonSubmit = (Button) findViewById(R.id.submit);
        editTextBirdName = (EditText) findViewById(R.id.birdnameinput);
        editTextZip = (EditText) findViewById(R.id.zipcodeinput);
        editTextDate = (EditText) findViewById(R.id.dateinput);
        editTextBirdPerson = (EditText) findViewById(R.id.birdpersoninput);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String birdname = editTextBirdName.getText().toString();
                String zip = editTextZip.getText().toString();
                String date = editTextDate.getText().toString();
                String birdperson = editTextBirdPerson.getText().toString();

                if(birdname.isEmpty() || zip.isEmpty() || date.isEmpty() || birdperson.isEmpty()){
                    // Do something here to tell the user they didn't input something.
                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(zip).push();



                    Bird inputBird = new Bird(birdname, birdperson, zip, date);
                    myRef.setValue(inputBird);

                    //Map<String, Bird> sighting = new HashMap<>();
                    //sighting.put(birdname, inputBird);
                    //myRef.setValue(sighting);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.find_bird){
            Intent i = new Intent(getApplicationContext(), FindBirdActivity.class);
            startActivity(i);
            finish();
        }
        if(item.getItemId() == R.id.report_bird){
            Intent i = new Intent(getApplicationContext(), ReportBirdActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
