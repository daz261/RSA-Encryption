package com.example.clientside;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class QRGenerator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView receiver_msg;
    TextView ocr_view;
    Button receive_button;
    Button ocr;
    String text2Qr;
    String key;
    ImageView image;
    EditText text;
    final String ENCRYPTED_TEXT = "Generate Encrypted Text";
    final String QR = "Generate QR of Encrypted Text";
    final String SELECT = "Select";
    String selectedModel = ENCRYPTED_TEXT;
    String hex = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
       // receive_button = (Button) findViewById(R.id.receive_button_id2);
        image = (ImageView) findViewById(R.id.image);
        text = (EditText) findViewById(R.id.editText_field);
       // ocr = (Button) findViewById(R.id.button_ocr);
        ocr_view = (TextView)findViewById(R.id.ocr_textview);

        //receive public key from Main activity
        Intent intent = getIntent();
        key = intent.getStringExtra("key_user").toString().trim();

        //listener that corresponds to QR encryption
//        receive_button.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String encryptedString = null;
//            //retrieve text from user (ie. text to encrypt)
//            text2Qr = text.getText().toString().trim();
//            if (text2Qr.isEmpty()) {
//                receiver_msg.setError("enter text");
//                return;
//            }
//            //encrypt user text
//            encryptedString = RSA.encryptData(text2Qr, key);
//            //encrypted text in QR format
//            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//            try {
//                BitMatrix bitMatrix = multiFormatWriter.encode(encryptedString, BarcodeFormat.QR_CODE, 200, 200);
//                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                //display qr
//                image.setImageBitmap(bitmap);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//   });
//        //listener that corresponds to text encryption hex format (for OCR)
//        ocr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String encryptedString2 = null;
//                //retrieve text from user (ie. text to encrypt)
//                text2Qr = text.getText().toString().trim();
//                if (text2Qr.isEmpty()) {
//                    receiver_msg.setError("enter text");
//                    return;
//                }
//                //encrypt user text in hex format
//                encryptedString2 = RSA.encryptData(text2Qr, key);
//                String hex = RSA.key_hex(encryptedString2);
//                //display text
//                ocr_view.setText(hex);
//            }
//
//
//        });
//

        Spinner spinner = findViewById(R.id.spinner);
        List<String> options = new ArrayList<>();
        options.add(SELECT);
        options.add(ENCRYPTED_TEXT);
        options.add(QR);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_style,
                options);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //retrieve selected item
//        ocr_view.setText("");
//        image.setImageResource(0);
        if (position == 0){
            ocr_view.setText("");
            image.setImageResource(0);
        }
        if (position == 1){
            ocr_view.setText("");
            image.setImageResource(0);
            String encryptedString2 = null;
           //retrieve text from user (ie. text to encrypt)
            text2Qr = text.getText().toString().trim();
            if (text2Qr.isEmpty()) {
                ocr_view.setText("enter text");

            }
            else {
//            //encrypt user text in hex format
                encryptedString2 = RSA.encryptData(text2Qr, key);
                hex = RSA.key_hex(encryptedString2);
            }
            //display text
            ocr_view.setText(hex);

        }
        else if (position ==2 ){
            ocr_view.setText("");
            image.setImageResource(0);
            String encryptedString = null;
            //retrieve text from user (ie. text to encrypt)
            text2Qr = text.getText().toString().trim();
            if (text2Qr.isEmpty()) {
                image.setImageBitmap(null);
                //return;

            }
            else {
                //encrypt user text
                encryptedString = RSA.encryptData(text2Qr, key);
                //encrypted text in QR format
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(encryptedString, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    //display qr
                    image.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            //nothing
        ocr_view.setText("enter text");
        image.setImageBitmap(null);
    }
}
