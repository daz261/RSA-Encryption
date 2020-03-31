package com.example.clientside;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class QRScanner extends AppCompatActivity {
    private Button scan_btn;
    String publicKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        //scan_btn = (Button) findViewById(R.id.scan_btn);
        final Activity activity = this;
        //com.google.zxing.integration.android.IntentIntegrator class
        //Intent integrator: a utility class which helps ease integration with Barcode Scanner via Intents.
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan Public Key");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //scanned public key
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                //toast is a popup window
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                //show the result on the screen
             //   Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                //send scanned public key to Main Activity
                Intent publicKeyIntent = new Intent (QRScanner.this, MainActivity.class);
                publicKey = result.getContents().toString().trim();;
                publicKeyIntent.putExtra("public", publicKey);
                startActivity(publicKeyIntent);
            }
        }
        else {
            //if scanned result is null, scan again
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
