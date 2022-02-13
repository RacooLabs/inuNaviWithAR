package com.maru.inunavi.ui.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.maru.inunavi.R;

public class FindImage {


    int mainImageId = 0;

    public int getPlaceImageId(String placeCode){

        switch(placeCode){

            case "FOODXIA" :

                mainImageId = R.drawable.place_foodxia_main;

                break;

            case "FOODSTU0" :

                mainImageId = R.drawable.place_foodstu0_main;

                break;

            case "FOODFHO" :

                mainImageId = R.drawable.place_foodfho_main;

                break;

            case "FOODSAL" :

                mainImageId = R.drawable.place_foodsal_main;

                break;

            case "FOODGYO" :

                mainImageId = R.drawable.place_foodgyo_main;

                break;

            case "FOODGON" :

                mainImageId = R.drawable.place_foodgon_main;

                break;

            case "FOODTOM" :

                mainImageId = R.drawable.place_foodtom_main;

                break;

            case "FOODMEL" :

                mainImageId = R.drawable.place_foodmel_main;

                break;

            case "FOODGOG" :

                mainImageId = R.drawable.place_foodgog_main;

                break;

            case "FOODSTU2" :

                mainImageId = R.drawable.place_foodstu2_main;

                break;

            case "FOODMOM" :

                mainImageId = R.drawable.place_foodmom_main;

                break;

            case "FOODBUN" :

                mainImageId = R.drawable.place_foodbun_main;

                break;

            case "FOODPAL" :

                mainImageId = R.drawable.place_foodpal_main;

                break;

            case "FOODPRO" :

                mainImageId = R.drawable.place_foodpro_main;

                break;

            default:

                mainImageId = R.drawable.place_none;

                break;


        }

        return mainImageId;

    }

}
