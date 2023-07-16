package com.example.formyself.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formyself.Adapters.TextAdapter;
import com.example.formyself.Object.Text;
import com.example.formyself.Object.TextList;
import com.example.formyself.R;
import com.example.formyself.Utilities.SignalGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView main_LST_text;

    private TextList textList = new TextList();;

    private String chatNum;

    private Button addText;

    private Button addUserToChat;

    private Button addImageToChat;

    private EditText editText;

    private TextView textView;

    private String title = "";

    private Text text;

    private TextAdapter textAdapter;



    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatNum = getIntent().getStringExtra("numOfChat");
        readTextsFromDB();

        editText = findViewById(R.id.chat_editText);
        addText = findViewById(R.id.chat_BTN_addText);
        addUserToChat = findViewById(R.id.add_user_to_chat_Button);
        addImageToChat = findViewById(R.id.add_image_to_chat_Button);
        textView = findViewById(R.id.chat_text);
        getChatTitle(chatNum);


        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText();
            }
        });

        addUserToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.add_user_to_chat, null);
                builder.setView(dialogView);

                EditText editText = dialogView.findViewById(R.id.editText);

                builder.setTitle("Add user to chat")
                        .setMessage("Please enter the username you want to add");

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = editText.getText().toString();
                        addUserTOChat(chatNum, userInput);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        addImageToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);

                View dialogView = getLayoutInflater().inflate(R.layout.add_user_to_chat, null);
                builder.setView(dialogView);

                EditText editText = dialogView.findViewById(R.id.editText);

                builder.setTitle("change image to chat")
                        .setMessage("Please enter the URL of the new image");

                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = editText.getText().toString();
                        addImageTOChat(chatNum, userInput);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




    }

    private void initViews() {
        textAdapter = new TextAdapter(textList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_text.setAdapter(textAdapter);
        main_LST_text.setLayoutManager(linearLayoutManager);
    }

    private void findViews() {
        main_LST_text = findViewById(R.id.main_LST_text);
    }

    public void addText(String string){
       text = new Text(string);
       textList.addText(text);
    }

    private void readTextsFromDB(){
        databaseReference.child("chats").child(chatNum).child("chat").child("textList")
                .child("texts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                    text = new Text(childSnapshot.child("text").getValue(String.class));
                    textList.addText(text);
                }
                findViews();
                initViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addText(){
        String enteredText = editText.getText().toString();
        addText(enteredText);
        textAdapter. notifyDataSetChanged();
        databaseReference.child("chats").child(chatNum).child("chat").child("textList").setValue(textList);
        editText.setText("");
    }

    private void addUserTOChat(String numOfChat, String newUserEmail){
        if(newUserEmail.isEmpty())
            SignalGenerator.getInstance().toast("Please fill the field", Toast.LENGTH_SHORT);
        else{
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.child("users").hasChild(newUserEmail))
                        SignalGenerator.getInstance().toast("This user does not exist", Toast.LENGTH_SHORT);
                    else {
                        databaseReference.child("chats").child(numOfChat).child("Permissions").child(newUserEmail).setValue(true);
                        SignalGenerator.getInstance().toast("user add successfully", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    SignalGenerator.getInstance().toast("Error in data base", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void addImageTOChat(String numOfChat, String imageUrl){
        if(imageUrl.isEmpty())
            SignalGenerator.getInstance().toast("Please fill the field", Toast.LENGTH_SHORT);
        else{
            databaseReference.child("chats").child(numOfChat).child("chat").child("image").setValue(imageUrl);
        }
    }

    private void getChatTitle(String numOfChat){
        databaseReference.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.child(numOfChat).child("chat").child("title").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                SignalGenerator.getInstance().toast("Error in data base", Toast.LENGTH_SHORT);
            }
        });
    }


}
