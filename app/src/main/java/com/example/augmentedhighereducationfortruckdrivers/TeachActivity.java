package com.example.augmentedhighereducationfortruckdrivers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.speech.*;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.example.augmentedhighereducationfortruckdrivers.Models.Lesson;
import com.example.augmentedhighereducationfortruckdrivers.Models.Question;
import com.example.augmentedhighereducationfortruckdrivers.Models.Segment;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class TeachActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static TextToSpeech tts;
    private SpeechRecognizer sr;
    private Intent intent;
    public static ArrayList<String> heardWords;
    public static Lesson lesson;


    public static int currentState; // 0 = greeting, 1 = segmentText, 2 = question
    public static int currentSegment;
    public static int currentQuestion;
    public static int correctAnswers;
    public static String fileName;
    public static String greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        checkPermission();
        initLesson();
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new sttListener());
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setOnUtteranceProgressListener(new ttsListener());
                    startLesson();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Initialization failed",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        greetings = "Are you ready to begin the lesson?";
    }

    // Callback methods for TTS
    class ttsListener extends UtteranceProgressListener {

        @Override
        public void onDone(String utteranceId) {

            if (utteranceId.contains("greetings")){
                currentState = 0;
            }
            else if (utteranceId.contains("segmentText")) {
                currentState = 1;
            }
            else if (utteranceId.contains("question")) {

            }

            // Start listening through sttListener
            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    sr.startListening(intent);
                }
            };
            mainHandler.post(myRunnable);
        }

        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onError(String utteranceId) {

        }
    }

    // Callback methods for STT
    class sttListener implements RecognitionListener{

        @Override
        public void onResults(Bundle results) {

            String str = new String();
            ArrayList<String> words;
            words = new ArrayList<String>(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
            heardWords = words;
            for (int i = 0; i < words.size(); i++)
            {
                Log.d("tag", "result " + heardWords.get(i));
                str += words.get(i);
            }

            // Greeting and prompt for beginning of lesson
            if (currentState == 0){

                if (words.contains("yes")) {
                    currentState = 1;
                    speakSegment();
                }
                else {
                    // Send user back to MainActivity
                    finish();
                }
            }

            // End of segment text
            else if (currentState == 1) {

                if (words.contains("yes")){
                    speakSegment();
                }
                else if (words.contains("no")){
                    currentState = 2;
                    startQuestions();
                }
                else if (words.contains("exit") || words.contains("quit")) {
                    finish();
                }
                else if (words.contains("repeat")) {
                    speakSegment();
                }
                else {
                    promptAgainSegment();
                }
            }

            // Question is asked
            else if (currentState == 2) {

                Question currQuestion = lesson.segments.get(currentSegment).questions.get(currentQuestion);
                boolean answer = currQuestion.answer;
                if (words.contains("true")) {
                    if (answer) {
                        correctAnswers++;
                    }
                    currentQuestion++;
                    startQuestions();

                }
                else if (words.contains("false")){
                    if (!answer) {
                       correctAnswers++;
                    }
                    currentQuestion++;
                    startQuestions();
                }
                else if (words.contains("repeat")) {
                    startQuestions();
                }
                else if (words.contains("exit") || words.contains("quit")) {
                    finish();
                }

                // If bot does not understand
                else {
                    promptAgainQuestion();
                }
            }
        }

        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {
            switch (currentState) {
                case 0:
                    finish();
                case 1:
                    promptAgainSegment();
                    break;
                case 2:
                    promptAgainQuestion();
                    break;
                default:
                    finish();


            }


        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

    public void startLesson() {
        greet();
    }

    // Bot reads greeting
    public void greet() {
        greetings = "Are you ready to begin the lesson?";
        speak(greetings, "greeting");
    }

    // Bot reads segment text
    public void speakSegment() {
        String segmentSample = lesson.segments.get(currentSegment).segmentText;
        if (currentSegment == 0) {
            speak("We will now begin the lesson.    " + segmentSample + ".    Would you like me to repeat that?", "segmentText");
        }
        else {
            speak (segmentSample + ".    Would you like me to repeat that?", "segmentText");
        }
    }


    public void promptAgainSegment() {
        String prompt = "I did not understand, please answer with yes, no or repeat";
        speak(prompt, "segmentText");
    }

    public void promptAgainQuestion() {
        String text = "I did not understand, please answer with true, false or repeat";
        speak(text, "question");
    }



    // Bot asks question
    public void startQuestions() {
        List<Question> questions = lesson.segments.get(currentSegment).questions;
        List<Segment> segments = lesson.segments;


        if (currentQuestion != questions.size()) {
            speak(questions.get(currentQuestion).question, "question");
        }
        else if (currentSegment + 1 != segments.size()){
             currentSegment++;
             currentQuestion = 0;
             speakSegment();
        }
        else {
            int total = calculateScore(correctAnswers, lesson.segments);
            Toast toast;
            if (total >= 50) {
                toast = Toast.makeText(getApplicationContext(), "You passed with a score of " + String.valueOf(total) + "%", Toast.LENGTH_LONG);
            }
            else {
                toast = Toast.makeText(getApplicationContext(), "You failed with a score of " + String.valueOf(total) + "%. " + "Try again.", Toast.LENGTH_LONG);
            }

            updateLessonDb(total);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TeachActivity.this.finish();
                }
            }, 2000);
        }

    }

    private void updateLessonDb(int total) {

        final long lTotal = (long)total;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        final DocumentReference userRef = db.collection("users").document(uid);
        final DocumentReference lessonRef = userRef.collection("lessons").document(lesson.name);

        lessonRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot lessonSnap = task.getResult();
                long score = lessonSnap.getLong("score");

                if (score < 50 && lTotal >= 50) {
                    userRef.update("lessonsComplete", FieldValue.increment(1));

                }

                if (lTotal > score) {
                    lessonRef.update("score", lTotal);

                }
                lessonRef.update("lastUpdated", new Date());

            }
        });



    }

    public static int calculateScore(int ca, List<Segment> seg) {
        int total = 0;
        for (int i = 0; i < seg.size(); i++) {
            total += seg.get(i).questions.size();
        }
        return (ca * 100 / total);
    }

    private void speak(String text, String utterance) {
        if(text != null) {
            HashMap<String, String> myHashAlarm = new HashMap<String, String>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utterance);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }

    public void initLesson() {

        fileName = getIntent().getStringExtra("FILE_NAME");
        File dir = getFilesDir();
        File file = new File(dir, fileName);
        if (!file.exists()) {
            Log.d("tag", "json doesnt exist");
        }
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            lesson = gson.fromJson(bufferedReader, Lesson.class);
            System.out.println(lesson);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentState = 0;
        currentSegment = 0;
        currentQuestion = 0;
        correctAnswers = 0;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Toast toast = Toast.makeText(getApplicationContext(), "Please enable microphone and storage permissions", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        sr.destroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
