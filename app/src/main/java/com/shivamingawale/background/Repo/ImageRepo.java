package com.shivamingawale.background.Repo;

import com.shivamingawale.background.Models.Models;

import java.util.ArrayList;

public class ImageRepo {
    private static ImageRepo imageRepo;
    ArrayList<Models> imageModelList = new ArrayList<>();
    ArrayList<Models> likedModelList = new ArrayList<>();




    public ImageRepo() {
//        imageModelList.add(new Models("first","100","hello","https://cdn.pixabay.com/photo/2020/07/06/01/33/forest-5375005__480.jpg"));
//        imageModelList.add(new Models("set","150","couple","https://cdn.pixabay.com/photo/2021/05/08/09/08/sunset-6237951_1280.jpg"));
//        imageModelList.add(new Models("sec","420","walls","https://cdn.pixabay.com/photo/2021/04/07/03/24/walls-6157947_1280.jpg"));
//        imageModelList.add(new Models("third","69","clouds","https://cdn.pixabay.com/photo/2021/01/24/20/21/cloud-5946381__480.jpg"));
//        imageModelList.add(new Models("four","36","leaves","https://cdn.pixabay.com/photo/2020/09/28/16/29/leaves-5610361__480.png"));
//        imageModelList.add(new Models("five","52","man","https://cdn.pixabay.com/photo/2020/10/09/13/12/man-5640540__480.jpg"));
//        imageModelList.add(new Models("six","203","green","https://cdn.pixabay.com/photo/2021/01/15/17/01/green-5919790__480.jpg"));
    }
    public static ImageRepo getImageRepo(){
        if(imageRepo==null){
            imageRepo= new ImageRepo();
        }
        return imageRepo;
    }

    public void clearImageRepo(){
        imageRepo.imageModelList.clear();
    }
    public void clearLikedImageRepo(){
        imageRepo.likedModelList.clear();
    }

    public ArrayList<Models> getImageModelList() {

        return imageRepo.imageModelList;
    }

    public ArrayList<Models> getLikedModelList() {
        return imageRepo.likedModelList;
    }

    public void setLikedModelList(Models best) {
        imageRepo.likedModelList.add(best);
    }
}
