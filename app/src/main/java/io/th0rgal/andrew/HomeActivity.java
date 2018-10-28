package io.th0rgal.andrew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import io.th0rgal.andrew.utils.GestureListener;
import io.th0rgal.andrew.utils.GestureType;
import io.th0rgal.andrew.utils.Utils;

public class HomeActivity extends AppCompatActivity {

    // This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateTexts();

        // Create a common gesture listener object.
        GestureListener gestureListener = new GestureListener(this);
        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        Utils.setDataDirectory(getApplicationInfo().dataDir);
        Utils.setAssetManager(getAssets());
    }


    private void updateTexts() {
        TextView helloTextView = findViewById(R.id.hello_message_textView);

        helloTextView.setText(helloTextView.getText().toString()
                .replace("%username%",
                        "Thomas"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }

    public void onGestureMade(GestureType gesture) {
        switch (gesture) {
            //Open andrew
            case UP_SWIPE:
                Intent chatIntent = new Intent(this, MessageListActivity.class);
                startActivity(chatIntent);
                break;

            //Close andrew
            case DOWN_SWIPE:
                finishAndRemoveTask();
                break;
        }
    }

    public void onListenButtonClicked(View view) {
        Toast.makeText(this,"Cette fonctionnalité n'est pas encore disponible ¯\\_(ツ)_/¯", Toast.LENGTH_LONG).show();
    }

    public void onWriteButtonClicked(View view) {
        Intent chatIntent = new Intent(this, MessageListActivity.class);
        startActivity(chatIntent);
    }
}
