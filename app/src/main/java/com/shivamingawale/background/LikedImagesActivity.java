package com.shivamingawale.background;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.shivamingawale.background.Adapters.ImagesRecyclerViewAdapter2;
import com.shivamingawale.background.Models.Models;
import com.shivamingawale.background.Repo.ImageRepo;

import java.util.Objects;

public class LikedImagesActivity extends AppCompatActivity {
    RecyclerView likedRecyclerView;
    ImageButton mainActivityButton;
    ImagesRecyclerViewAdapter2 imagesRecyclerViewAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_images2);
        mainActivityButton=findViewById(R.id.mainActivityButton);
        gettingData();
        likedRecyclerView=findViewById(R.id.likedRecyclerView);
        initRecycler();

        mainActivityButton.setOnClickListener(view -> {
            Intent intent = new Intent(LikedImagesActivity.this,MainActivity.class);
            startActivity(intent);
            LikedImagesActivity.this.finish();
        });
    }

    public void gettingData() {
        ImageRepo.getImageRepo().clearLikedImageRepo();

        ImagesRecyclerViewAdapter2.db.collection(ImagesRecyclerViewAdapter2.m_szDevIDShort).orderBy("id", Query.Direction.ASCENDING);
        ImagesRecyclerViewAdapter2.db.collection(ImagesRecyclerViewAdapter2.m_szDevIDShort).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())){
                    Models newLiked;
                    newLiked = doc.toObject(Models.class);
                    ImageRepo.getImageRepo().setLikedModelList(newLiked);
                }
                    imagesRecyclerViewAdapter2.notifyDataSetChanged();
            }
        });

    }


    void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        imagesRecyclerViewAdapter2 = new ImagesRecyclerViewAdapter2(ImageRepo.getImageRepo().getLikedModelList());
        likedRecyclerView.setLayoutManager(linearLayoutManager);
        likedRecyclerView.setAdapter(imagesRecyclerViewAdapter2);
    }
}