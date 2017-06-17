package com.contactsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;

import java.util.List;
import java.util.Random;

/**
 * Created by SONI on 5/13/2017.
 */

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Contacts contact;
    private TextView txt_title;
    private ImageButton btn_send;
    private EditText edt_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        inIt();
    }

    private void inIt() {
        contact = MyApp.getApplication().getCurrentContact();
        edt_message = (EditText) findViewById(R.id.edt_message);
        txt_title = (TextView) toolbar.findViewById(R.id.txt_title);
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        Random rnd = new Random();
        int otp = 100000 + rnd.nextInt(900000);
        edt_message.setText("Hi, Your OTP is: " + otp + "");
        btn_send.setOnClickListener(this);

//        try {
//            NexmoClient nexmoClient = new NexmoClient.NexmoClientBuilder()
//                    .context(SendMessageActivity.this)
//                    .applicationId("e139f480") //your App key
//                    .sharedSecretKey("22728fa222472ce0") //your App secret
//                    .build();
//        } catch (ClientBuilderException e) {
//            e.printStackTrace();
//        }
//
//        verifyClient = new VerifyClient(nexmoClient);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_send) {
            List<Contacts> list = MyApp.getApplication().readMessages();
            contact.setDateTime(System.currentTimeMillis());
            contact.setMessageText(edt_message.getText().toString());

            list.add(0, contact);
            MyApp.getApplication().writeMessages(list);
            MyApp.showMassage(SendMessageActivity.this, "Message Sent successfully");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
