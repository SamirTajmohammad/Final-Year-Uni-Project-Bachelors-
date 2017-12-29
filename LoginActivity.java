package com.example.sammay.loginactivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    DatabaseReference databaseRef;
    TextView tvInvalidLoginDetails;
    Button btLogin, btSignup;
    EditText etPassword, etEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseRef = FirebaseDatabase.getInstance().getReference("Student-Accounts");

        btLogin = findViewById(R.id.buttonLogin);
        btLogin.setOnClickListener(this);
        btSignup = findViewById(R.id.buttonSignup);
        btSignup.setOnClickListener(this);
        tvInvalidLoginDetails = findViewById(R.id.textViewInvalidLoginDetails);
        tvInvalidLoginDetails.setTextColor(Color.RED);
        tvInvalidLoginDetails.setVisibility(View.GONE);
        etPassword = findViewById(R.id.editTextPassword);
        etEmail = findViewById(R.id.editTextEmail);

        mAuth = FirebaseAuth.getInstance();

        //sets title on toolbar gui
        getSupportActionBar().setTitle("Smart NFC Register");
        //hide toolbar for now
       // getSupportActionBar().hide();

        //users cannot register
       // btSignup.setEnabled(false);

        retrieveStudentData();


    }

    private void retrieveStudentData(){

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //map string string because our key is a string and value is a string, map has a key and value object
                    Map<String, String> map = (Map) snapshot.getValue();
                    if (map != null) { //if the values and keys are not null
                        String message = map.get("mojtaba.tajmohammad.1@city.ac.uk");
                        String userName = map.get("fullname");

                        Log.v("E_VALUE", "username : " + userName);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


   private void loginUser(){

       String email = etEmail.getText().toString().trim();
       String password = etPassword.getText().toString().trim();

       //authorise user email and password with database to get a match
       mAuth.signInWithEmailAndPassword(email,password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       //Sign in success
                       if (task.isSuccessful()){
                           finish();
                           FirebaseUser user = mAuth.getCurrentUser();
                           startActivity(new Intent(getApplicationContext(),UserAreaActivity.class));
                       } else {
                           Log.w(TAG, "LoginActivity:failure", task.getException());
                           Toast.makeText(LoginActivity.this, "Authentication failed.",
                                   Toast.LENGTH_SHORT).show();
                       }
                   }
               });
   }

   //send user to register area
   private void signupUser(){
       startActivity(new Intent(this, RegisterActivity.class));
   }

   public void onClick(View view){ //what buttons will user click? switch to appropriate
        switch (view.getId()){

            case R.id.buttonLogin:
                loginUser();
                break;

            case R.id.buttonSignup:
                signupUser();
                break;

        }
   }
}
