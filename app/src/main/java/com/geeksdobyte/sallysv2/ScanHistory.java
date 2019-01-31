package com.geeksdobyte.sallysv2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScanHistory extends AppCompatActivity {

    ListView listView;
    String jsonString = "";
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);

        listView = (ListView) findViewById(R.id.list);

        jsonString = read(this,"sallys.json");

        gson = new Gson();

        List<String> vegList = new ArrayList<String>();
        sallysModel[] mcArray = gson.fromJson(jsonString, sallysModel[].class);
        List<sallysModel> mcList = Arrays.asList(mcArray);
        List<sallysModel> mcLista = new ArrayList<>(Arrays.asList(mcArray));

        for(sallysModel mymodel : mcList){
            vegList.add(mymodel.getTimeStamp() + " " + mymodel.getBarCodev());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, vegList);

        listView.setAdapter(adapter);


    }


    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
}
