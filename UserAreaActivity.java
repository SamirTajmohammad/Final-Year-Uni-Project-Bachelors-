package com.example.sammay.loginactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAreaActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    DatabaseReference databaseReference;

    TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);



        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //required to set title for toolbar
        getSupportActionBar().setTitle("NFC Attendance");

        onButtonClickListener();
        checkNFCEnabled();

        //displays user ID and others if edited

        //disable button for now
        findViewById(R.id.buttonNFCRegister).setEnabled(false);

        tvLogin = findViewById(R.id.textViewLogin);


    }

    public void whenNFCTriggered(){
//        if(nfcAdapter.EXTRA_TAG)){
//            nfcRegister.performClick(); // needed for executing of nfc tagging.
//        }


    }


    public void onButtonClickListener() {
        findViewById(R.id.buttonNFCRegister).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        AlertDialog.Builder alertBox = new AlertDialog.Builder(UserAreaActivity.this);
                        alertBox.setMessage("You are registered!").setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //finish();
                                        //do something
                                    }
                                });
                        AlertDialog alert = alertBox.create();
                        alert.setTitle("Smart Attendance Register");
                        alert.show();

                    }
                }
        );
    }

//    protected void onNewIntent(Intent intent){
//        super.onNewIntent(intent);
//
//        if(intent.hasExtra(nfcAdapter.EXTRA_TAG)){
//            nfcRegister.performClick(); // needed for executing of nfc tagging.
//        }
//    }

    public void checkNFCEnabled(){

        NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC has been turned on!", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please turn on your NFC", Toast.LENGTH_LONG).show();
        }

    }

}
