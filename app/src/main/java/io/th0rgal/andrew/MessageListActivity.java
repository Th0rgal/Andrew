package io.th0rgal.andrew;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import io.th0rgal.andrew.backend.AsyncMessageProcessing;
import io.th0rgal.andrew.chat.Author;
import io.th0rgal.andrew.chat.ChatAdapter;
import io.th0rgal.andrew.chat.ChatManager;
import io.th0rgal.andrew.chat.Message;


public class MessageListActivity extends AppCompatActivity {

    private EditText msgInputText;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerViewMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        msgInputText = findViewById(R.id.edittext_chatbox);

        recyclerViewMessageList = findViewById(R.id.reyclerview_message_list);
        recyclerViewMessageList.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();
        recyclerViewMessageList.setAdapter(chatAdapter);

    }

    public void sendMessage(View view) {
        String input = msgInputText.getText().toString();
        msgInputText.setText("");

        //my message
        ChatManager.addMessage(new Message(input, null));
        chatAdapter.notifyDataSetChanged();

        //answer
        Author andrew = new Author("Andrew", ContextCompat.getDrawable(this, R.drawable.andrew_avatar));
        new AsyncMessageProcessing(recyclerViewMessageList, chatAdapter, andrew, input).execute();

        //so that the last message appears above the keyboard and not below
        recyclerViewMessageList.scrollToPosition(ChatManager.getMessages().size() - 1);

    }
}
