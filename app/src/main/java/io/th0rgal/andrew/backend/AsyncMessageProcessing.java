package io.th0rgal.andrew.backend;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import io.th0rgal.andrew.R;
import io.th0rgal.andrew.chat.Author;
import io.th0rgal.andrew.chat.ChatAdapter;
import io.th0rgal.andrew.chat.ChatManager;
import io.th0rgal.andrew.chat.Message;
import io.th0rgal.andrew.utils.Utils;
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

        if (ContextManager.get().getCurrentContext() == ContextManager.Context.DEFAULT) {

            outputA = LayersManager.submitToLayerA(input);
            if (outputA != null)
                return outputA;

            outputB = LayersManager.submitToLayerB(input);
            if (outputB != null)
                return outputB;

            outputC = LayersManager.submitToLayerC(input);
            if (outputC != null)
                return outputC;

            ContextManager.get().setContextMessage(input);
            return notFound();

        } else {
            ContextManager.get().updateCurrentContext(ContextManager.Context.DEFAULT);
            LayersManager.learnToLayerB(input);
            return Utils.getResources().getString(R.string.just_learned);
        }

    }

    private String normalizeMessage(String message) {
        return message.toLowerCase()
                .replaceAll("( )?andrew", "%user%").trim();
    }

    private String notFound() {
        ContextManager.get().updateCurrentContext(ContextManager.Context.MSG_NOT_FOUND);
        return Utils.getResources().getString(R.string.not_found);
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