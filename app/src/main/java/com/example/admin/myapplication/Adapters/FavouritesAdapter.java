package com.example.admin.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.myapplication.Activities.EventActivity;
import com.example.admin.myapplication.Activities.FavouritesActivity;
import com.example.admin.myapplication.Holy.DataHelper;
import com.example.admin.myapplication.Holy.OnBottomReachedListener;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Utils.Event;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private Context context;

    private ArrayList<Event> data;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_list_item, parent, false);

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
        holder.butlis.setE(e,position);




    }


    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public FavouritesAdapter(ArrayList<Event> data, Context context){
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
        ImageButton delete;
        RecyclerView rv;
        ClickListener cvListener = new ClickListener();

        ButtonListener butlis;


        public ViewHolder(final View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.pic_ev);
            cv = itemView.findViewById(R.id.cv);
            rv = itemView.findViewById(R.id.rv);
            more = itemView.findViewById(R.id.more);
            heading = itemView.findViewById(R.id.heading);
            cv.setOnClickListener(cvListener);
            delete = itemView.findViewById(R.id.delete);
            butlis = new ButtonListener();
            delete.setOnClickListener(butlis);
        }}

        class ButtonListener implements View.OnClickListener{


        Event e;
        int pos;
            @Override
            public void onClick(View view) {
                DataHelper dh = new DataHelper(context);
                dh.delete(e.getId());
                Log.e("Delete","Done");
                Snackbar.make(view, "Удалено из избранного", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                notifyItemRemoved(pos);
                data = (new DataHelper(context)).getFavourites();



            }

            public void setE(Event e, int pos) {
                this.e = e;
                this.pos = pos;
            }
        }
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
