package com.example.clientside;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button encrypt;
    Button scan;
    String public_key1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encrypt = (Button)findViewById(R.id.send_button_id);
        scan = (Button)findViewById(R.id.scan);

        //listener from Main to QRScanner activity
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rIntent = new Intent(MainActivity.this, QRScanner.class);
                startActivity(rIntent);

            }
        });
        //receive public key value from QRScanner activity
        Intent intent = getIntent();
        public_key1 = intent.getStringExtra("public");

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rIntent = new Intent(MainActivity.this, QRGenerator.class);
                rIntent.putExtra("key_user", public_key1);
                startActivity(rIntent);

            }
        });

    }

//    //send public key to QRGenerator
//    //not used
//    public void onButtonClick(View v){
//        Intent myIntent = new Intent(getBaseContext(),  QRGenerator.class);
//        myIntent.putExtra("key_user", public_key1);
//        startActivity(myIntent);
//    }
}
