package io.th0rgal.andrew.backend;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import io.th0rgal.andrew.chat.Author;
import io.th0rgal.andrew.chat.ChatAdapter;
import io.th0rgal.andrew.chat.ChatManager;
import io.th0rgal.andrew.chat.Message;
import io.th0rgal.andrew.utils.ListUtils;


public class AsyncMessageProcessing extends AsyncTask<String, String, String> {

    ChatAdapter chatAdapter;
    RecyclerView recyclerViewMessageList;
    Author author;
    String input;
    String outputA;
    String outputB;
    String outputC;

    public AsyncMessageProcessing(RecyclerView recyclerViewMessageList, ChatAdapter chatAdapter, Author author, String message) {
        super();
        this.recyclerViewMessageList = recyclerViewMessageList;
        this.chatAdapter = chatAdapter;
        this.author = author;
        this.input = normalizeMessage(message);
    }

    private String generateAnswer() {

        outputA = null;
        if (outputA != null)
            return outputA;

        outputB = Analyzer.searchForSimilar(input, LayersManager.getLayerB());
        if (outputB != null)
            return outputB;

        outputC = null;
        if (outputC != null)
            return outputC;

        return generateFallBackAnswer();
    }

    private String normalizeMessage(String message) {
        return message.replaceAll("<@\\d+>( )?", "%user%").trim().toLowerCase();
    }

    private String generateFallBackAnswer() {
        return ListUtils.random(LayersManager.getLayerB());
    }


    @Override
    protected String doInBackground(String... params) {
        String answer = generateAnswer().replaceAll("<@\\d+>( )?","");
        ChatManager.addMessage(new Message(answer, author));
        return answer;
    }

    @Override
    protected void onPostExecute(String result) {
        this.chatAdapter.notifyDataSetChanged();
        this.recyclerViewMessageList.scrollToPosition(ChatManager.getMessages().size() - 1);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(String... text) {

    }
}