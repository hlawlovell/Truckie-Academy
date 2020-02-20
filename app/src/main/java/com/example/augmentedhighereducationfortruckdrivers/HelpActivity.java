package com.example.augmentedhighereducationfortruckdrivers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;





public class HelpActivity extends AppCompatActivity {

    private Button quit;
    private TextToSpeech quitSpeech;
    private TextView text;
    public static String text1;

    //Create help page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //The button of home page
        quit = findViewById(R.id.node_name_view1);

        //Click the home button and jump to home page
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View section) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //Marquee effect
        text = findViewById(R.id.text_1);
        text.setSelected(true);
        text1 = "Welcome back.";
    }



    @Override
    protected void onDestroy() {
        if (quitSpeech != null) {
            quitSpeech.stop();
            quitSpeech.shutdown();
        }

        super.onDestroy();
    }


}
