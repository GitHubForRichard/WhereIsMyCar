package academy.learnprogramming.whereismycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //get reference of widgets from xml layout
        Spinner garageSpinner=(Spinner)findViewById(R.id.garageSpinner);
        Spinner searchSpinner=(Spinner)findViewById(R.id.searchSpinner);
        //initializing a string array for garage spinner
        String[] garages=new String[]{
                "South Garage",
                "North Garage",
                "West Garage"
        };
        //initializing a string array for search spinner
        String[] searchOption=new String[]{
                "Search By Car Model",
                "Search By Plate"
        };
        //intializeing ArrayAdapter
        ArrayAdapter<String> mySpinnerAdpt=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,garages);
        //change default spinner text size
        mySpinnerAdpt.setDropDownViewResource(R.layout.spinner_layout);
        garageSpinner.setAdapter(mySpinnerAdpt);

        //ArrayAdapter for search option
        //intializeing ArrayAdapter
        ArrayAdapter<String> mySpinnerAdpt2=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,searchOption);
        //change default spinner text size
        mySpinnerAdpt2.setDropDownViewResource(R.layout.spinner_layout);
        searchSpinner.setAdapter(mySpinnerAdpt2);

        //user click search
        
    }
}
