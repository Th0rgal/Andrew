package io.th0rgal.andrew.backend;

public class ContextManager {

    private static ContextManager instance = new ContextManager();

    public static ContextManager get() {
        return instance;
    }

    public enum Context {
        DEFAULT,
        MSG_NOT_FOUND
    }

    private Context currentContext = Context.DEFAULT;
    private String contextMessage;

    public void setContextMessage(String message) {
        this.contextMessage = message;
    }

    public String getContextMessage() {
        return this.contextMessage;
    }

    public void updateCurrentContext(Context context) {
        this.currentContext = context;
    }

    public Context getCurrentContext() {
        return currentContext;
    }


}
