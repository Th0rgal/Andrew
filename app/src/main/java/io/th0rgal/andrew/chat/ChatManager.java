package io.th0rgal.andrew.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private static List<Message> chatHistory = new ArrayList<>();

    public static void addMessage(Message message) {
        chatHistory.add(message);
    }

    public static List<Message> getMessages() {
        return chatHistory;
    }

}
