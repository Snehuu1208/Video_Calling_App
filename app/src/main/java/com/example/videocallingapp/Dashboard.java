package com.example.videocallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard extends AppCompatActivity {

    EditText secretCodeBox;
    Button joinBtn,shareBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        secretCodeBox=findViewById(R.id.codeBox);
        joinBtn=findViewById(R.id.joinBtn);
        shareBtn=findViewById(R.id.shareBtn);

        URL serverURL;


        try {
            serverURL=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setFeatureFlag("welcomepage.enabled", false)
                    // Different features flags can be set
                    .setFeatureFlag("toolbox.enabled", false)
                    .setFeatureFlag("filmstrip.enabled", false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        } catch (MalformedURLException e) {
           e.printStackTrace();
        }



        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder()
                       .setRoom(secretCodeBox.getText().toString())
                       .setFeatureFlag("welcomepage.enabled", false)
                       // Settings for audio and video
                       .setAudioMuted(true)
                       .setVideoMuted(true)
                       .build();

                JitsiMeetActivity.launch(Dashboard.this,options);
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Hey! Use this code and join me on Charcha "+secretCodeBox);
                Intent chooser=Intent.createChooser(intent,"Share this code using...");
                startActivity(chooser);
            }
        });
    }
}