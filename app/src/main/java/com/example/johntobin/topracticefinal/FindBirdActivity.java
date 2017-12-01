package com.example.johntobin.topracticefinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class FindBirdActivity extends AppCompatActivity {

    private EditText editTextZip;
    private Button buttonSubmit;
    private TextView resultText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bird);

        buttonSubmit = (Button) findViewById(R.id.LookupButton);
        editTextZip = (EditText) findViewById(R.id.ZipLookupInput);
        resultText = (TextView) findViewById(R.id.ResultText);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String zip = editTextZip.getText().toString();
                if(!zip.isEmpty()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(zip);

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount() == 0){
                                resultText.setText("No birds found in " + zip);
                            }
                            else{
                                for(DataSnapshot birdSighting : dataSnapshot.getChildren()){
                                    Bird sighting = birdSighting.getValue(Bird.class);
                                    String texttodisplay = "Last reported sighting in zipcode " + zip + "\n";
                                    texttodisplay += "Birdname: " + sighting.birdname + "\n";
                                    texttodisplay += "Reporter name: " + sighting.obsName + "\n";
                                    texttodisplay += "Date: " + sighting.dateObs + "\n";
                                    texttodisplay += "Thank you for using the birdwatcher.";
                                    resultText.setText(texttodisplay);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
