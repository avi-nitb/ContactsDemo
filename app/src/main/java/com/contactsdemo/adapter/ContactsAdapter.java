package com.contactsdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contactsdemo.Contacts;
import com.contactsdemo.ContactsFragment;
import com.contactsdemo.R;

import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private Fragment mContext;
    private List<Contacts> list;

    public ContactsAdapter(Fragment context, List<Contacts> list) {
        this.list = list;
        mContext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_name, txt_number, txt_alpha;

        public MyViewHolder(View view) {
            super(view);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_number = (TextView) view.findViewById(R.id.txt_number);
            txt_alpha = (TextView) view.findViewById(R.id.txt_alpha);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            ((ContactsFragment)mContext).goToContactDetails(list.get(getLayoutPosition()));
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacts, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt_name.setText(list.get(position).getName());
        holder.txt_number.setText(list.get(position).getPhoneNumber());
        holder.txt_alpha.setText(list.get(position).getName().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


