package com.example.sammay.loginactivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity"; //required for log TAG
    DatabaseReference databaseRef;

    EditText
            etStudentID,
            etLoginID,
            etEmail,
            etFullname,
            etPassword,
            etModule,
            etDegree,
            etRoom;

    Button btSubmit;

    private FirebaseAuth mAuth;

    //These are all the variables from the registration form
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseRef = FirebaseDatabase.getInstance().getReference("Student-Accounts");

        btSubmit = findViewById(R.id.buttonSubmit);
        btSubmit.setOnClickListener(this);
        etStudentID = findViewById(R.id.editTextStudentID);
        etLoginID = findViewById(R.id.editTextLoginID);
        etEmail = findViewById(R.id.editTextEmail);
        etFullname = findViewById(R.id.editTextFullname);
        etModule = findViewById(R.id.editTextModule);
        etDegree = findViewById(R.id.editTextDegree);
        etRoom = findViewById(R.id.editTextRoom);
        etPassword = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Register Your New Account");



    }
    private void registerUserAccounts(){
        //string variables created from activity register
        String studentID = etStudentID.getText().toString().trim();
        String loginID = etLoginID.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String fullname = etFullname.getText().toString().trim();
        String module = etModule.getText().toString().trim();
        String degree = etDegree.getText().toString().trim();
        String room = etRoom.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //pattern matches if user entered email is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter a valid Email");
            return;
        }

        //checks if user inputs first and surname
        if(!fullname.contains(" ") && (!fullname.matches("^(?=.*[A-Z].*[A-Z])(?=.*[a-z].*[a-z].*[a-z])"))){
            etFullname.setError("Please enter first name and surname");
            return;
        }

        //Strong password -- regex taken from https://stackoverflow.com/questions/37454110/how-to-create-a-java-program-to-check-if-your-password-is-strong-or-not
        //if password does not meet the critera of a strong password
        if (!(password.length() > 6) &&
            !(password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z])"))){
            etPassword.setError ("Please enter a strong password containing at least:" +
                                 " 1 capital letter, 1 number " +
                                 "and password length must be over 6 characters long.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                           // Sign up success
                        if (task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(), "Your account has been successful", Toast.LENGTH_LONG).show();
                        }else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "RegisterActivity:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "User Register Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //finally check if all textfields are not empty
        if (!(TextUtils.isEmpty(studentID)  && TextUtils.isEmpty(loginID)  &&
              TextUtils.isEmpty(email)      && TextUtils.isEmpty(fullname) &&
              TextUtils.isEmpty(module)     && TextUtils.isEmpty(degree)   &&
              TextUtils.isEmpty(room)))
        {
            //firebase autogenerated uniqueKey
        String uniqueKey = databaseRef.push().getKey();

        NewStudentAccounts sam = new NewStudentAccounts
        (studentID, loginID, email, fullname, module, degree, room);

        databaseRef.child(uniqueKey).setValue(sam);

        Toast.makeText(this, "Your account registration has been successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
        Toast.makeText(this, "Invalid Student Credentials Entered!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonSubmit:
                registerUserAccounts();
                break;
        }
    }
}







