package com.contactsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contactsdemo.Contacts;
import com.contactsdemo.ContactsFragment;
import com.contactsdemo.MyApp;
import com.contactsdemo.R;

import java.util.List;


public class SentMessagesAdapter extends RecyclerView.Adapter<SentMessagesAdapter.MyViewHolder> {

    private Fragment mContext;
    private List<Contacts> list;

    public SentMessagesAdapter(Fragment context, List<Contacts> list) {
        this.list = list;
        mContext = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_name, txt_message, txt_time_stamp;

        public MyViewHolder(View view) {
            super(view);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_message = (TextView) view.findViewById(R.id.txt_message);
            txt_time_stamp = (TextView) view.findViewById(R.id.txt_time_stamp);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
//            ((Se) mContext).goToContactDetails(list.get(getLayoutPosition()));
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sent_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txt_name.setText(list.get(position).getName());
        holder.txt_time_stamp.setText(MyApp.getApplication().getTimeAgo(list.get(position).getDateTime(), mContext.getActivity()));
        holder.txt_message.setText(list.get(position).getMessageText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


