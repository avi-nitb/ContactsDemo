package com.contactsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contactsdemo.adapter.ContactsAdapter;
import com.contactsdemo.adapter.SentMessagesAdapter;

/**
 * Created by SONI on 5/13/2017.
 */

public class MessageSentFragment extends Fragment{

    private RecyclerView rv_messages;
    private SentMessagesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tab_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_messages = (RecyclerView) view.findViewById(R.id.rv_contacts);
//        txt_status = (TextView) view.findViewById(R.id.txt_status);


    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new SentMessagesAdapter(MessageSentFragment.this, MyApp.getApplication().readMessages());
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_messages.setLayoutManager(horizontalLayoutManager);
        rv_messages.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}