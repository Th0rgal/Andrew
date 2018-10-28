package io.th0rgal.andrew.chat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.th0rgal.andrew.R;

public class ChatAdapter extends RecyclerView.Adapter<MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.my_message, parent, false);
                break;
            case 1:
                view = inflater.inflate(R.layout.andrew_message, parent, false);
                break;
        }
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ChatManager.getMessages().get(position).belongsToCurrentUser() ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(ChatManager.getMessages().get(position));
    }

    @Override
    public int getItemCount() {
        return ChatManager.getMessages().size();
    }


}

class MyViewHolder extends RecyclerView.ViewHolder {

    private final TextView content;
    private TextView authorName;
    private View authorAvatar;


    public MyViewHolder(final View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.message_body);
        authorName = itemView.findViewById(R.id.name);
        authorAvatar = itemView.findViewById(R.id.avatar);
    }

    public void display(Message message) {
        if (message.belongsToCurrentUser()) {
            content.setText(message.content);
        } else {
            content.setText(message.content);
            authorName.setText(message.author.name);
             if (message.author.drawable == null) {
                 GradientDrawable drawable = (GradientDrawable) authorAvatar.getBackground();
                 drawable.setColor(Color.parseColor(message.author.color));
                 authorAvatar.setBackground(drawable);
             } else {
                 authorAvatar.setBackground(message.author.drawable);
             }

        }
    }
}