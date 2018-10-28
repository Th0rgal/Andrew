package io.th0rgal.andrew.chat;

public class Message {

    private boolean hasAuthor;
    public Author author;
    public String content;

    public Message(String content, Author author) {
        this.hasAuthor = !(author == null);
        this.author = author;
        this.content = content;
    }

    public boolean belongsToCurrentUser() {
        return !hasAuthor;
    }


}


