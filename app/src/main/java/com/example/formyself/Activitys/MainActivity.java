package com.example.formyself.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.formyself.Adapters.ChatAdapter;
import com.example.formyself.Utilities.ImageLoader;
import com.example.formyself.Object.Chat;
import com.example.formyself.Object.ChatList;
import com.example.formyself.Object.Text;
import com.example.formyself.Object.TextList;
import com.example.formyself.R;
import com.example.formyself.Utilities.SignalGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_LST_chats;

    private ChatList chatList = new ChatList();;

    private Button addChat;

    private Button logOut;

    String email;

    private TextView textView;

    private Chat chat;

    private TextList textList;

    private Text text;

    private ChatAdapter chatAdapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader.initImageLoader(this);
        email = getIntent().getStringExtra("email");

        readChatsFromDB();



//        findViews();
//        initViews();
        addChat = findViewById(R.id.main_BTN_addChat);
        logOut = findViewById(R.id.log_out_button);
        textView = findViewById(R.id.email_text);

        textView.setText(email);

        addChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.add_chat, null);
                builder.setView(dialogView);

                EditText editText = dialogView.findViewById(R.id.addChat_editText);
                EditText editTextImage = dialogView.findViewById(R.id.addImage_editText);
                builder.setTitle("Add chat")
                        .setMessage("Please enter chat name, if you want you can add a image URL");

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = editText.getText().toString();
                        String userInputImage = editTextImage.getText().toString();
                        addChat(email, userInput, userInputImage);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });


    }

    private void initViews() {
        chatAdapter = new ChatAdapter(chatList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_chats.setAdapter(chatAdapter);
        main_LST_chats.setLayoutManager(linearLayoutManager);
    }

    private void findViews() {
        main_LST_chats = findViewById(R.id.main_LST_chats);
    }


    private void readChatsFromDB(){
        databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int numOfChats = snapshot.child("numOfChats").getValue(Integer.class);
                for(int i =1; i <=numOfChats; i++ ){
                    String numChat = Integer.toString(i);
                    if(snapshot.child(numChat).child("Permissions").hasChild(email)) {
                        chat = new Chat(snapshot.child(numChat).child("chat").child("title").getValue(String.class));
                        chat.setNumInDB(numChat);
                        if(snapshot.child(numChat).child("chat").hasChild("image"))
                            chat.setImage(snapshot.child(numChat).child("chat").child("image").getValue(String.class));
                        chatList.addChat(chat);
                    }
                }
                findViews();
                initViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addChat(String email, String title, String imageUrl){
        if(title.isEmpty())
            SignalGenerator.getInstance().toast("Please fill the field", Toast.LENGTH_SHORT);
        else{
            databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final int numOfChats = snapshot.child("numOfChats").getValue(Integer.class);
                    int NewNumOfChats = numOfChats+1;
                    String numChat = Integer.toString(NewNumOfChats);
                    Chat chatToAdd = new Chat(title);
                    chatToAdd.setImage(imageUrl);
                    chatToAdd.setNumInDB(numChat);
                    databaseReference.child("chats").child(numChat).child("Permissions").child(email).setValue(true);
                    databaseReference.child("chats").child(numChat).child("chat").setValue(chatToAdd);
                    databaseReference.child("chats").child("numOfChats").setValue(NewNumOfChats);
                    chatList.addChat(chatToAdd);
                    chatAdapter. notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    SignalGenerator.getInstance().toast("Error in data base", Toast.LENGTH_SHORT);
                }
            });



        }
    }

    private void logOut(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }






}