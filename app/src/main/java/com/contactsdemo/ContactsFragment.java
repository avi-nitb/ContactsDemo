package com.contactsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contactsdemo.adapter.ContactsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment {

    private RecyclerView rv_contacts;
    private ContactsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tab_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_contacts = (RecyclerView) view.findViewById(R.id.rv_contacts);
        try {
//            JSONObject o = new JSONObject(getString(R.string.contacts));
            List<Contacts> cList = new ArrayList<>();
            JSONArray arr = new JSONArray(getString(R.string.contacts));
            for (int i = 0; i < arr.length(); i++) {
                Contacts c = new Contacts();
                JSONObject o = arr.getJSONObject(i);
                c.setName(o.optString("firstName") + " " + o.optString("lastName"));
                c.setPhoneNumber(o.optString("phone"));
                cList.add(c);
            }

            adapter = new ContactsAdapter(ContactsFragment.this, cList);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_contacts.setLayoutManager(horizontalLayoutManager);
            rv_contacts.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void goToContactDetails(Contacts contacts) {
        MyApp.getApplication().setCurrentContact(contacts);
        startActivity(new Intent(getActivity(), ContactDetailsActivity.class));
    }
}