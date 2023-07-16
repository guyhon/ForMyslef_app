package com.example.formyself.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formyself.Object.Text;
import com.example.formyself.Object.TextList;
import com.example.formyself.R;
import com.google.android.material.textview.MaterialTextView;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder>{

    private Context context;

    private TextList texts;


    public TextAdapter(TextList texts) {
        this.texts = texts;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false);
        TextAdapter.TextViewHolder textViewHolder = new TextAdapter.TextViewHolder(view);
        return textViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        if(getItemCount()>0) {
            Text text = getItem(position);
            holder.text_LBL_text.setText(text.getText());
        }
    }

    @Override
    public int getItemCount() {
        return this.texts == null ? 0 : this.texts.getTexts().size();
    }

//    public void addText(String string){
//        Text text = new Text(string);
//        texts.addText(text);
//        notifyDataSetChanged();
//    }


    private Text getItem(int position) {
        return this.texts.getTexts().get(position);
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView text_LBL_text;
        private RelativeLayout text_item;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            text_LBL_text = itemView.findViewById(R.id.text_LBL_text);
            text_item = itemView.findViewById(R.id.text_item);
        }
    }
}
