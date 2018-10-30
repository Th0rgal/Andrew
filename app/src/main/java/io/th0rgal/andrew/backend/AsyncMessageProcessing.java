package io.th0rgal.andrew.backend;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import io.th0rgal.andrew.chat.Author;
import io.th0rgal.andrew.chat.ChatAdapter;
import io.th0rgal.andrew.chat.ChatManager;
import io.th0rgal.andrew.chat.Message;
import io.th0rgal.andrew.utils.ListUtils;
import org.json.JSONException;


public class AsyncMessageProcessing extends AsyncTask<String, String, String> {

    ChatAdapter chatAdapter;
    @SuppressLint("StaticFieldLeak")
    RecyclerView recyclerViewMessageList;
    TextToSpeech vocalSynthesizer;
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

    public AsyncMessageProcessing(TextToSpeech vocalSynthesizer, Author author, String message) {
        super();
        this.vocalSynthesizer = vocalSynthesizer;
        this.author = author;
        this.input = normalizeMessage(message);
    }

    private String generateAnswer() throws JSONException {

        outputA = LayersManager.submitToLayerA(input);
        if (outputA != null)
            return outputA;

        outputB = LayersManager.submitToLayerC(input);
        if (outputB != null)
            return outputB;

        outputC = null;
        if (outputC != null)
            return outputC;

        return generateFallBackAnswer();
    }

    private String normalizeMessage(String message) {
        return message.toLowerCase()
                .replaceAll("( )?andrew", "%user%").trim();
    }

    private String generateFallBackAnswer() {
        return ListUtils.random(LayersManager.getLayerC());
    }

    @Override
    protected String doInBackground(String... params) {
        String answer = null;
        try {
            answer = generateAnswer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (chatAdapter == null)
            vocalSynthesizer.speak(answer, TextToSpeech.QUEUE_FLUSH, null);
        ChatManager.addMessage(new Message(answer, author));
        return answer;
    }

    @Override
    protected void onPostExecute(String result) {
        if (chatAdapter != null) {
            this.chatAdapter.notifyDataSetChanged();
            this.recyclerViewMessageList.scrollToPosition(ChatManager.getMessages().size() - 1);
        }
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(String... text) {

    }
}