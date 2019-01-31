package com.geeksdobyte.sallysv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuccessScan extends AppCompatActivity {

    TextView scanText;
    TextView timeStamp;
    Button ScannerBtn;
    Button HistoryBtn;
    sallysModel myModel;
    Gson gson;
    List<sallysModel> mylist;
    String jsonString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_scan);
        Intent intent = getIntent();
        scanText = findViewById(R.id.textView2);
        timeStamp = findViewById(R.id.textView);

        scanText.setText(intent.getExtras().getString("barcodev"));
        timeStamp.setText(intent.getExtras().getString("timestamp"));

        ScannerBtn = findViewById(R.id.backtoScan);

        HistoryBtn= findViewById(R.id.button2);

        ScannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessScan.this, ScannerView.class);
                startActivity(i);
                finish();
            }
        });

        HistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SuccessScan.this, ScanHistory.class);
                startActivity(i);

            }
        });


        gson = new Gson();
        myModel= new sallysModel();
        mylist = new ArrayList<>();


        myModel.setBarCodev(scanText.getText().toString());
        myModel.setTimeStamp(timeStamp.getText().toString());

        mylist.add(myModel);
//        String jsonString = gson.toJson(mylist);
//        create(SuccessScan.this,"sallys.json",jsonString);

        if(isFilePresent(SuccessScan.this,"sallys.json")){
            jsonString = read(this,"sallys.json");
            List<String> vegList = new ArrayList<String>();
            sallysModel[] mcArray = gson.fromJson(jsonString, sallysModel[].class);
            List<sallysModel> mcList = new ArrayList<>(Arrays.asList(mcArray));
            mcList.add(myModel);
            String jsonStringold = gson.toJson(mcList);
            deleteFile("sallys.json");
            create(SuccessScan.this,"sallys.json",jsonStringold);

        }else{
            mylist.add(myModel);
            String jsonString = gson.toJson(mylist);
            create(SuccessScan.this,"sallys.json",jsonString);
        }


    }


    private boolean create(Context context, String fileName, String jsonString) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

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



    public boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}
