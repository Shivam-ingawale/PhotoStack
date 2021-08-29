package com.shivamingawale.background;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shivamingawale.background.Adapters.ImagesRecyclerViewAdapter2;
import com.shivamingawale.background.Adapters.VolleySinglton;
import com.shivamingawale.background.Models.Models;
import com.shivamingawale.background.Repo.ImageRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ImageButton likedActivitybutton;
    RecyclerView recyclerView;
    EditText editText;
    TextView explore;
    String API_KEY="23020542-768062325396fe8c68933a4f9";
    ArrayList<String> queryList =new ArrayList<>();

    RequestQueue requestQueue;

    ImagesRecyclerViewAdapter2 imagesRecyclerViewAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryList.add("nature");
        queryList.add("flower");
        queryList.add("space");
        queryList.add("cool");
        queryList.add("trees");
        queryList.add("sunset");
        queryList.add("lion");
        queryList.add("king");
        queryList.add("technology");
        String query = queryList.get(new  Random().nextInt(queryList.size()));

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ImageRepo.getImageRepo().clearImageRepo();
        requestQueue= VolleySinglton.getvolleySinglton(this).getRequestQueue();
        fetchData(query);
        initRecycler();
        explore = findViewById(R.id.explore);
        editText = findViewById(R.id.edit);
        editTextFunction();

        likedActivitybutton=findViewById(R.id.likeActivityButton);
        likedActivitybutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LikedImagesActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            this.finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        imagesRecyclerViewAdapter2.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageRepo.getImageRepo().getImageModelList().clear();
        ImageRepo.getImageRepo().getLikedModelList().clear();
        deleteCache(getApplicationContext());
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        imagesRecyclerViewAdapter2.notifyDataSetChanged();
    }

    private void internalEditTextFunction() {
        String newString= editText.getText().toString();
        newString = newString.replaceAll(" ","+");
        fetchData(newString);
        closeKeyboard();
    }


    private void editTextFunction() {

        editText.setOnKeyListener((v, keyCode, event) -> {

//                if (keyEvent.getKeyCode() == EditorInfo.IME_ACTION_NEXT) {
//                    internalEditTextFunction();
//                    Log.d("fee", "onKey: send");
//                    Toast.makeText(MainActivity.this, "send", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
            if (event.getAction() == KeyEvent.ACTION_DOWN){
            if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                    (keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == EditorInfo.IME_ACTION_SEARCH)){
                internalEditTextFunction();
//                    Log.d("fee", "onKey: send");
                return true;
            }
            }

            return false;
        });
        editText.hasOnClickListeners();
//        editText.setOnClickListener(view -> internalEditTextFunction());

    }

    private void fetchData(String query) {
        String url = "https://pixabay.com/api/?key="+API_KEY+"&q="+query+"&image_type=photo";
//        ImageRepo.getImageRepo().clearImageRepo();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("hits");
                ImageRepo.getImageRepo().getImageModelList().clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String url1 = jsonObject.getString("webformatURL");
                    String user = jsonObject.getString("user");
                    String likes = String.valueOf(jsonObject.getInt("likes"));
                    String tags = jsonObject.getString("tags");
                    String userurl = jsonObject.getString("userImageURL");
                    String id = String.valueOf(jsonObject.getInt("id"));

                    ImageRepo.getImageRepo().getImageModelList().add( i , new Models(user,likes,tags, url1,userurl,false,id));
                }
                if(jsonArray.length()==0){
                    String url1 = "https://cdn.pixabay.com/photo/2021/01/10/20/03/laptop-5906264_1280.png";
                    String user = " ";
                    String likes = "";
                    String tags = " ";
                    String userurl = " ";
                    String id = "0";
                    ImageRepo.getImageRepo().clearImageRepo();
                    ImageRepo.getImageRepo().getImageModelList().add(0, new Models(user,likes,tags, url1,userurl,id));
                }

                imagesRecyclerViewAdapter2.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
//                Log.d("fee", "json error ");
            }
        }, error -> Toast.makeText(MainActivity.this, "internet issue", Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        imagesRecyclerViewAdapter2 = new ImagesRecyclerViewAdapter2(ImageRepo.getImageRepo().getImageModelList());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(imagesRecyclerViewAdapter2);
    }
    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}