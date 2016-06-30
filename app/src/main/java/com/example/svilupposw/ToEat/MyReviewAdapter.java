package com.example.svilupposw.ToEat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by stage on 09/05/2016.
 */

public class MyReviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList <Review> list;
    private List<String> keys_list;
    private String localId;
    private String userId;

    private LayoutInflater mInflater;

    public MyReviewAdapter(Context context, final String localId) {

        this.context = context;
        list = new ArrayList<>();
        this.keys_list = new ArrayList<>();

        MyApplication.getReviewRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("onChildAdded", dataSnapshot.getKey());
                Review item = dataSnapshot.getValue(Review.class);
                if (item.getLocalId().equals(localId)) {
                    list.add(item);
                    keys_list.add(dataSnapshot.getKey());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("onChildChanged", dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("onChildRemoved", dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("onChildMoved", "");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i("onCancelled", firebaseError.getMessage());
            }
        });

    }

    public void addItem (Review review) {

        list.add(review);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.listview_review, parent, false);

        }

        TextView user = (TextView) convertView.findViewById(R.id.user);
        TextView comment = (TextView) convertView.findViewById(R.id.comment);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

        user.setText(((Review) getItem(position)).getUserName());
        comment.setText(((Review) getItem(position)).getComment());
        ratingBar.setRating(Float.parseFloat(((Review) getItem(position)).getRating()));

        return convertView;
    }
}
