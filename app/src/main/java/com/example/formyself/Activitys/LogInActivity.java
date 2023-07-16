package com.example.formyself.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.formyself.R;
import com.example.formyself.Utilities.SignalGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    Button logIn;
    Button registered;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.email_editText);
        EditText password = findViewById(R.id.password_editText);
        logIn = findViewById(R.id.login_BTN_login);
        registered = findViewById(R.id.login_BTN_registered);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();
                LogIn(emailTxt, passwordTxt);
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();
                register(emailTxt, passwordTxt);
            }
        });

    }


    private void register(String emailTxt, String passwordTxt) {
        if(emailTxt.isEmpty()||passwordTxt.isEmpty())
            SignalGenerator.getInstance().toast("Please fill all fields", Toast.LENGTH_SHORT);
        else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(emailTxt)){
                        SignalGenerator.getInstance().toast("This username is already registered", Toast.LENGTH_SHORT);
                    }
                    else {
                        databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt);
                        SignalGenerator.getInstance().toast("User registered successfully", Toast.LENGTH_SHORT);
                        moveToMainActivity(emailTxt);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    SignalGenerator.getInstance().toast("Error in data base", Toast.LENGTH_SHORT);
                }
            });

        }
    }

    private void LogIn(String emailTxt, String passwordTxt){
        if(emailTxt.isEmpty()||passwordTxt.isEmpty())
            SignalGenerator.getInstance().toast("Please fill all fields", Toast.LENGTH_SHORT);
        else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(emailTxt)) {
                        final String getPassword = snapshot.child(emailTxt).child("password").getValue(String.class);
                        if (getPassword.equals(passwordTxt)) {
                            SignalGenerator.getInstance().toast("Successfully logged in", Toast.LENGTH_SHORT);
                            moveToMainActivity(emailTxt);
                        } else {
                            SignalGenerator.getInstance().toast("Wrong Password", Toast.LENGTH_SHORT);
                        }
                    } else {
                        SignalGenerator.getInstance().toast("Wrong username", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    SignalGenerator.getInstance().toast("Error in data base", Toast.LENGTH_SHORT);
                }
            });
        }
    }


    private void moveToMainActivity(String email) {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }




}
