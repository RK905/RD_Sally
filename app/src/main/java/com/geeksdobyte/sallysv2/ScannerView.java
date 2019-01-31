package com.geeksdobyte.sallysv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.sql.Timestamp;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class ScannerView extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;

    String scannedItem = "";
    String timeStamp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_view);

        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }


    @Override
    public void onScanned(Barcode barcode) {
        // play beep sound
        scannedItem = barcode.displayValue;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timeStamp = timestamp.toString();
        barcodeReader.playBeep();

        Intent i = new Intent(ScannerView.this,SuccessScan.class);
        i.putExtra("timestamp", timeStamp);
        i.putExtra("barcodev",scannedItem);
        startActivity(i);


    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
