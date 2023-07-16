package com.example.formyself;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserActivity extends AppCompatActivity {

    private Button add_BTN_addUser;
    private EditText editText;

    String numOfChat;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        numOfChat = getIntent().getStringExtra("numOfChat");
        add_BTN_addUser = findViewById(R.id.add_BTN_addUser);
        editText = findViewById(R.id.addUser_editText);

        add_BTN_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserTOChat(numOfChat);
            }
        });
    }

    private void moveToMainActivity(String numOfChat) {
        Intent intent = new Intent(AddUserActivity.this, ChatActivity.class);
        intent.putExtra("numOfChat", numOfChat);
        startActivity(intent);
        finish();
    }

    private void addUserTOChat(String numOfChat){
        String newUserEmail = editText.getText().toString();
        if(newUserEmail.isEmpty())
            Toast.makeText(AddUserActivity.this, "Please fill the field", Toast.LENGTH_SHORT).show();
         else{
            databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.hasChild(newUserEmail))
                        Toast.makeText(AddUserActivity.this, "This user does not exist", Toast.LENGTH_SHORT).show();
                    else {
                        databaseReference.child("chats").child(numOfChat).child("Permissions").child(newUserEmail).setValue(true);
                        Toast.makeText(AddUserActivity.this, "user add successfully", Toast.LENGTH_SHORT).show();
                    }
                    moveToMainActivity(numOfChat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
