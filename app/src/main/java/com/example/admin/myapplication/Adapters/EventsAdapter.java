package com.example.admin.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.myapplication.Activities.EventActivity;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;
import com.example.admin.myapplication.Utils.PostValue;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private Context context;

    private ArrayList<Event> data;

    OnBottomReachedListener onBottomReachedListener;

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);

       ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Event e = data.get(position);
        holder.more.setText(e.getPlace() + " \n" + e.getDates() + " \n" + e.getPrice());
        holder.heading.setText(e.getTitle() + " " + e.getRating());
        holder.picture.setImageURI(e.getImagelink());
        holder.cvListener.setRecord(e,position);

        if (position == data.size() - 3){

            onBottomReachedListener.onBottomReached(position);

        }



    }


    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public EventsAdapter(ArrayList<Event> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView more,heading;
        SimpleDraweeView picture;
        ClickListener cvListener = new ClickListener();

        public ViewHolder(final View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.pic_ev);
            cv = itemView.findViewById(R.id.cv);
            more = itemView.findViewById(R.id.more);
            heading = itemView.findViewById(R.id.heading);
            cv.setOnClickListener(cvListener);

        }}
    class ClickListener implements View.OnClickListener{

        Event e;
        int pos;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,EventActivity.class);
            intent.putExtra("name",e.getTitle());
            intent.putExtra("place",e.getPlace());
            intent.putExtra("price",e.getPrice());
            intent.putExtra("image",e.getImagelink());
            intent.putExtra("rating",e.getRating());
            intent.putExtra("types",e.getTypes());
            intent.putExtra("address",e.getAddress());
            intent.putExtra("imagePlace",e.getPlaceImgUrl());
            intent.putExtra("UserRating",e.getUserRating());
            intent.putExtra("dates",e.getDates());
            intent.putExtra("longitude",e.getLongitude());
            intent.putExtra("latitude",e.getLatitude());
            intent.putExtra("buy",e.getToBuy());
            intent.putExtra("id",e.getId());
            context.startActivity(intent);


        }

        void setRecord(Event e,int pos){
            this.e = e;
            this.pos = pos;
        }

    }
}
