package com.bcq.oklib.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bcq.oklib.UI;
import com.squareup.picasso.Picasso;


import java.io.File;

public class ImageLoader {

    public static void loadItem(ImageView imageView,String url){
        if (TextUtils.isEmpty(url)) return;
        Picasso.with(UI.getContext())
                .load(url)
                .into(imageView);
    }
    public static void loadItem(ImageView imageView,int resouceId){
        if (resouceId < 0) return;
        Picasso.with(UI.getContext())
                .load(resouceId)
                .into(imageView);
    }
    public static void loadItem(ImageView imageView,File file){
        if (null == file) return;
        Picasso.with(UI.getContext())
                .load(file)
                .into(imageView);
    }
}
