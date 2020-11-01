package com.tugrulbo.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    FeedRecycleViewAdapter feedRecycleViewAdapter;
    RecyclerView recyclerView;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    ArrayList<String> userEmailFromFB;
    ArrayList<String> userCommentFromFB;
    ArrayList<String> userImageFromFB;

    //connect menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insta_options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.add_post){
            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
            finish();
        }else if (item.getItemId()==R.id.sign_out){
            mAuth.signOut();
            Intent intent = new Intent(FeedActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        userCommentFromFB = new ArrayList<>();
        userEmailFromFB = new ArrayList<>();
        userImageFromFB = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getDataFromFireStore();

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecycleViewAdapter = new FeedRecycleViewAdapter(userEmailFromFB,userCommentFromFB,userImageFromFB);
        recyclerView.setAdapter(feedRecycleViewAdapter);
    }

    public void getDataFromFireStore(){

           CollectionReference collectionReference = firebaseFirestore.collection("posts");
           collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
               @Override
               public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error !=null){
                            Toast.makeText(FeedActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                       if (value != null){
                           for (DocumentSnapshot snapshot:value.getDocuments()){
                               Map<String,Object> data= snapshot.getData();
                               //Casting
                               String comment = (String) data.get("comment");
                               String email =(String) data.get("useremail");
                               String downloadUrl =(String) data.get("downloadurl");

                               userEmailFromFB.add(email);
                               userCommentFromFB.add(comment);
                               userImageFromFB.add(downloadUrl);
                               feedRecycleViewAdapter.notifyDataSetChanged();
                           }
                       }
               }
           });
    }
}