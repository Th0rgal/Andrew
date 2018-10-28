package io.th0rgal.andrew;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import io.th0rgal.andrew.utils.GestureListener;
import io.th0rgal.andrew.utils.GestureType;
import io.th0rgal.andrew.utils.Utils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    // This is the gesture detector compat instance.
    private GestureDetectorCompat gestureDetectorCompat = null;
    private SpeechRecognizer speechRecognizer;
    RecognitionProgressView recognitionProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Initialization of Utils
        Utils.setDataDirectory(getApplicationInfo().dataDir);
        Utils.setAssetManager(getAssets());

        //Message displayed updated
        updateTexts();

        // Create a common gesture listener object.
        GestureListener gestureListener = new GestureListener(this);
        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
        // Setup the speech recognizer
        setupSpeechRecognizer();


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

    public void onWriteButtonClicked(View view) {
        Intent chatIntent = new Intent(this, MessageListActivity.class);
        startActivity(chatIntent);
    }

    public void onListenButtonClicked(View view) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            startRecognition();
            recognitionProgressView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startRecognition();
                }
            }, 50);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }
    
    public void setupSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognitionProgressView = findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results);
            }
        });
        recognitionProgressView.setColors(new int[]{
                ContextCompat.getColor(this, R.color.google_blue),
                ContextCompat.getColor(this, R.color.google_green),
                ContextCompat.getColor(this, R.color.google_yellow),
                ContextCompat.getColor(this, R.color.google_red),
                ContextCompat.getColor(this, R.color.clouds)
        });
        recognitionProgressView.setBarMaxHeightsInDp(new int[]{100, 120, 90, 115, 80});
        recognitionProgressView.setCircleRadiusInDp(10);
        recognitionProgressView.setSpacingInDp(10);
        recognitionProgressView.setIdleStateAmplitudeInDp(2);
        recognitionProgressView.setRotationRadiusInDp(50);
        recognitionProgressView.play();
    }

    private void updateTexts() {
        TextView helloTextView = findViewById(R.id.hello_message_textView);

        helloTextView.setText(helloTextView.getText().toString()
                .replace("%username%",
                        "Thomas"));
    }

    @Override
    protected void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Toast.makeText(this, matches.get(0), Toast.LENGTH_LONG).show();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    //REQUEST_RECORD_AUDIO_PERMISSION_CODE
                    1);
        }
    }

}
