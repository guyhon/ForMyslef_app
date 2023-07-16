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

public class AddChatActivity extends AppCompatActivity {


    private Button add_BTN_addChat;
    private EditText editText;

    private EditText editTextImage;

    String email;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chat);

        email = getIntent().getStringExtra("email");
//        add_BTN_addChat = findViewById(R.id.add_BTN_addChat);
        editText = findViewById(R.id.addChat_editText);
        editTextImage = findViewById(R.id.addImage_editText);

//        add_BTN_addChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addChat(email);
////                databaseReference.child("chats").add
////                editText.setEnabled(true);
////                editText.requestFocus();
//            }
//        });
    }

    private void moveToMainActivity(String email) {
        Intent intent = new Intent(AddChatActivity.this, MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }

    private void addChat(String email){
        //databaseReference.child("chats").child("numOfChats").setValue(0);
        String title = editText.getText().toString();
        String imageUrl = editTextImage.getText().toString();
        if(title.isEmpty())
            Toast.makeText(AddChatActivity.this, "Please fill the field.", Toast.LENGTH_SHORT).show();
        else{
            databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final int numOfChats = snapshot.child("numOfChats").getValue(Integer.class);
                    int NewNumOfChats = numOfChats+1;
                    String numChat = Integer.toString(NewNumOfChats);
                    Chat chat = new Chat(title);
                    //chat.setImage(imageUrl);
                    Text text = new Text("aaa");
                    chat.getTextList().addText(text);chat.setImage("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/vdVab7yNvgYEMd8shCfy2D6nTMu.jpg");
                    databaseReference.child("chats").child(numChat).child("Permissions").child(email).setValue(true);
                    databaseReference.child("chats").child(numChat).child("chat").setValue(chat);
                    databaseReference.child("chats").child("numOfChats").setValue(NewNumOfChats);
                    moveToMainActivity(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }
    }



}
