package com.shivamingawale.background.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shivamingawale.background.Models.Models;
import com.shivamingawale.background.R;
import com.shivamingawale.background.Repo.ImageRepo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ImagesRecyclerViewAdapter2 extends  RecyclerView.Adapter<ImagesRecyclerViewAdapter2.ViewHolder> {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
//    boolean liked = false;

    public static String m_szDevIDShort = "35" + //we make this look like a valid IMEI
            Build.BOARD.length()%10+ Build.BRAND.length()%10 +
            Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
            Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
            Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
            Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
            Build.TAGS.length()%10 + Build.TYPE.length()%10 +
            Build.USER.length()%10 ;

    ArrayList<Models> modelsArrayList;
    public ImagesRecyclerViewAdapter2(ArrayList<Models> modelsArrayList) {
        this.modelsArrayList = modelsArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImagesRecyclerViewAdapter2.ViewHolder holder, int position) {
        holder.title.setText( modelsArrayList.get(position).getTitle());
        holder.tags.setText(modelsArrayList.get(position).getTags());
        holder.tags.setSelected(true);


//        ImagesRecyclerViewAdapter2.db.collection(ImagesRecyclerViewAdapter2.m_szDevIDShort).document(String.valueOf(modelsArrayList.get(position))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                        Models newLiked = new Models();
//                        newLiked = document.toObject(Models.class);
//                        liked = true;
//                        Log.d("hello", "in");
//                }
//            }
//    });
//        Log.d("hello", " "+liked);
//        for (Models model:ImageRepo.getImageRepo().getLikedModelList()) {
//            if(modelsArrayList.get(position).getId()==model.getId()){
//                liked=true;
//            }
//        }
        if(modelsArrayList.get(position).getId().equals("0")) {
        holder.likeButton.setClickable(false);
        holder.likeButton.setAlpha((float) 0.0);
        }

        holder.likeButton.setOnClickListener(view -> {
            if(!modelsArrayList.get(position).getLiked()){
                String liked_count = modelsArrayList.get(position).getCount();
                holder.likeButton.setImageResource(R.drawable.ic_baseline_add_circle_24);
                modelsArrayList.get(position).setLiked(true);
                modelsArrayList.get(position).setCount(String.valueOf(Integer.parseInt(liked_count)+1));
                holder.count.setText((String.valueOf(Integer.parseInt(liked_count)+1)));
                db.collection(m_szDevIDShort).document(modelsArrayList.get(position).getId()).set(modelsArrayList.get(position));
            }else{
                holder.likeButton.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                modelsArrayList.get(position).setLiked(false);
                String liked_count = modelsArrayList.get(position).getCount();
                modelsArrayList.get(position).setCount(String.valueOf(Integer.parseInt(liked_count)-1));
                holder.count.setText((String.valueOf(Integer.parseInt(liked_count)-1)));
                db.collection(m_szDevIDShort).document(modelsArrayList.get(position).getId()).delete();
            }
        });
        boolean liked=false;
        for (Models model : ImageRepo.getImageRepo().getLikedModelList()) {
            if(model.getId().equals(modelsArrayList.get(position).getId())){
                liked=true;
            }
        }
//        if(db.collection(m_szDevIDShort).document(modelsArrayList.get(position).getId()).get().isSuccessful()){
//        if(modelsArrayList.get(position).getLiked()){
        if(liked){
            holder.likeButton.setImageResource(R.drawable.ic_baseline_add_circle_24);
        }else {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        }
        String liked_count = modelsArrayList.get(position).getCount();
        holder.count.setText(liked_count);
        Glide.with(holder.imageView).load(modelsArrayList.get(position).getUrl()).into(holder.imageView);
        String userUrlTrue =modelsArrayList.get(position).getUserurl();
        if(!userUrlTrue.isEmpty()){
            Glide.with(holder.user).load(userUrlTrue).into(holder.user);
        }else{
            holder.user.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
    }

    @Override
    public int getItemCount() {
        return modelsArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,tags,count;
        ImageView imageView,user;
        ImageButton likeButton;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            likeButton =itemView.findViewById(R.id.like);
            title = itemView.findViewById(R.id.title1);
            tags = itemView.findViewById(R.id.tags1);
            count = itemView.findViewById(R.id.count1);
            imageView = itemView.findViewById(R.id.imageView2);
            user = itemView.findViewById(R.id.userid);
        }
    }
}
