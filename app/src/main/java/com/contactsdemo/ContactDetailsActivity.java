package com.contactsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SONI on 5/13/2017.
 */

public class ContactDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Contacts contact;
    private TextView txt_title, txt_alpha, txt_number, txt_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detals);
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
        txt_title = (TextView) toolbar.findViewById(R.id.txt_title);
        txt_alpha = (TextView) findViewById(R.id.txt_alpha);
        txt_send = (TextView) findViewById(R.id.txt_send);
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_title.setText(contact.getName());
        txt_number.setText(contact.getPhoneNumber());
        txt_alpha.setText(contact.getName().substring(0, 1));

        txt_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txt_send) {
            startActivity(new Intent(ContactDetailsActivity.this, SendMessageActivity.class));
        }
    }
}
