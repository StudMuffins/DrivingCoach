package com.studmuffins.application;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import java.util.Locale;

/**
 * Created by Pooria on 15/05/15.
 */

public class TTSManager {
    private TextToSpeech mTts = null;
    private boolean isLoaded = false;
    private String[] speech = {"Gear Up", "Gear Down" , "Turn left" , "Turn right", "Break now"};

    public void init(Context context) {
        try {
            mTts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTts.setLanguage(Locale.US);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                }
            } else {
                Log.e("error", "Initialization Failed!");
            }
        }
    };

    public void shutDown() {
        mTts.shutdown();
    }

    public void addQueue(String text) {
        if (isLoaded)
            mTts.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            Log.e("error", "TTS Not Initialized");
    }

    public void initQueue(int i) {

        if (isLoaded)
            mTts.speak(speech[i], TextToSpeech.QUEUE_FLUSH, null);
        else
            Log.e("error", "TTS Not Initialized");
    }

}
