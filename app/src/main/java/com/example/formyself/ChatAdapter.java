package com.example.formyself;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {


        private Context context;

        private ChatList chats;


        public ChatAdapter(ChatList chats) {

            this.chats = chats;
        }

        @NonNull
        @Override
        public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
            ChatViewHolder movieViewHolder = new ChatViewHolder(view);
            return movieViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
            if(getItemCount()>0) {
                Chat chat = getItem(position);
                int p = position;
                holder.chat_LBL_title.setText(chat.getTitle());
                if(!chat.getImage().isEmpty())
                    ImageLoader.getInstance().loadImage(chat.getImage(), holder.chat_IMG_poster);
                holder.chat_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        intent.putExtra("numOfChat", getNumOfChat(p));
                        view.getContext().startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return this.chats == null ? 0 : this.chats.getChats().size();
        }



        private Chat getItem(int position) {
            return this.chats.getChats().get(position);
        }
        private String getNumOfChat(int position){return this.chats.getChats().get(position).getNumInDB();}

        public class ChatViewHolder extends RecyclerView.ViewHolder {
            private MaterialTextView chat_LBL_title;
            private ShapeableImageView chat_IMG_poster;
            private RelativeLayout chat_item;

            public ChatViewHolder(@NonNull View itemView) {
                super(itemView);
                chat_LBL_title = itemView.findViewById(R.id.chat_LBL_title);
                chat_IMG_poster = itemView.findViewById(R.id.chat_IMG_poster);
                chat_item = itemView.findViewById(R.id.chat_item);
            }
        }

}
