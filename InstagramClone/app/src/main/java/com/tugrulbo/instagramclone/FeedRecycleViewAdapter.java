package com.tugrulbo.instagramclone;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecycleViewAdapter extends RecyclerView.Adapter<FeedRecycleViewAdapter.PostHolder> {

    private ArrayList<String> userEmailFromFB;
    private ArrayList<String> userCommentFromFB;
    private ArrayList<String> userImageFromFB;

    public FeedRecycleViewAdapter(ArrayList<String> userEmailFromFB, ArrayList<String> userCommentFromFB, ArrayList<String> userImageFromFB) {
        this.userEmailFromFB = userEmailFromFB;
        this.userCommentFromFB = userCommentFromFB;
        this.userImageFromFB = userImageFromFB;
    }


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_item,parent,false);


        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
            holder.userEmail.setText(userEmailFromFB.get(position));
            holder.comment.setText(userCommentFromFB.get(position));
            Picasso.get().load(userImageFromFB.get(position)).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return userEmailFromFB.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        TextView userEmail, comment;
        ImageView userImage;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.reycycle_image);
            userEmail = itemView.findViewById(R.id.reycycle_user);
            comment = itemView.findViewById(R.id.reycycle_textView);

        }
    }
}
