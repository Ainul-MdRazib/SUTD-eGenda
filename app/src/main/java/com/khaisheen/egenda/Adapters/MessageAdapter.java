package com.khaisheen.egenda.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.khaisheen.egenda.Activities.SingleMessage;
import com.khaisheen.egenda.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<SingleMessage> mMessageList;

    FirebaseAuth mAuth;

    public MessageAdapter( List<SingleMessage> mMessageList){
        this.mMessageList = mMessageList;
    }


    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_message,parent,false
                );

        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;
        public TextView messageSentBy;
        public TextView messageTimestamp;

        public MessageViewHolder(View view){
            super(view);
            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            messageSentBy = (TextView) view.findViewById(R.id.message_sentBy);
            messageTimestamp = (TextView) view.findViewById(R.id.message_timestamp);

        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder viewHolder, int i) {
        mAuth = FirebaseAuth.getInstance();
        String currentName = mAuth.getCurrentUser().getDisplayName();
        SingleMessage m = mMessageList.get(i);

        if(currentName.equals(m.getSentBy())){
            viewHolder.messageText.setBackgroundColor(Color.CYAN);
        }else{

            viewHolder.messageText.setBackgroundResource(R.drawable.single_message_bg);
        }

        viewHolder.messageSentBy.setText(m.getSentBy());
        viewHolder.messageText.setText(m.getMessage());
        viewHolder.messageTimestamp.setText(m.getTime());

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
