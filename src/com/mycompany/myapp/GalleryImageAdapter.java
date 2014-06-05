package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.BitmapDrawable;
import java.io.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.content.Context;
import java.util.*;
import android.util.Log;
//import java.lang.reflect.Field;

public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;

   // private Integer[] mImageIds;// = {
	//	R.drawable.image1,
	//	R.drawable.image2,
		//R.drawable.image3,
		//R.drawable.image4,
		//R.drawable.image5,
	//	R.drawable.image6,
	//	R.drawable.image7,
		//R.drawable.image8
  //  };
  
	File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
										Environment.DIRECTORY_PICTURES), "ShopCo");
	File[] listFile = mediaStorageDir.listFiles();//get list of files
	
  

    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }

    public int getCount() {
		//Drawable[] drawables;
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		for (int i = 0; i < listFile.length; i++)
		{
			Log.d("mycompany.myapp", "listFile je dolg " + listFile.length);
			Log.d("mycompany.myapp", "Drawables index v getCount " + i);
			String imagePath = listFile[i].getAbsolutePath();
			Log.d("mycompany.myapp", "imagePath je" + imagePath);
			//BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inSampleSize = 20;
			//Bitmap bm = BitmapFactory.decodeFile(imagePath, options);

			Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
			Log.d("mycompany.myapp", "imag" + imagePath);
			//drawables.set(i,drawable);
			
			drawables.add(i,drawable);
			Log.d("mycompany.myapp", "Smo dodali drawable!");
		}
		return drawables.size();
		//return 3;
        //return mImageIds.length;
    }
	
//	public Drawable getDrawable4ImageView(int position) {
//		//Drawable[] drawables;
//		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
//		for (int i = 0; i < listFile.length; i++)
//		{
//			//Log.d("mycompany.myapp", "listFile je dolg " + listFile.length);
//			//Log.d("mycompany.myapp", "Drawables index v getCount " + i);
//			String imagePath = listFile[i].getAbsolutePath();
//			//Log.d("mycompany.myapp", "imagePath je" + imagePath);
//			//BitmapFactory.Options options = new BitmapFactory.Options();
//			//options.inSampleSize = 20;
//			//Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
//
//			Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
//			//Log.d("mycompany.myapp", "imag" + imagePath);
//			//drawables.set(i,drawable);
//
//			drawables.add(i,drawable);
//			//Log.d("mycompany.myapp", "Smo dodali drawable!");
//		}
//		return drawables.get(position);
//		//return 3;
//        //return mImageIds.length;
//    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
											Environment.DIRECTORY_PICTURES), "ShopCo");
		File[] listFile = mediaStorageDir.listFiles();//get list of files
		//Drawable[] drawables = new BitmapDrawable();
		
		
		for (int j = 0; j < listFile.length; j++)
		{
		Log.d("mycompany.myapp", "Drawables index" + j);
		String imagePath = listFile[j].getAbsolutePath();
			Log.d("mycompany.myapp", "getView imagePath je" + imagePath);
			
		//BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inSampleSize = 20;
		//Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
		
		
		Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
		drawable = new ScaleDrawable(drawable, 0, 20, 20).getDrawable();
		drawables.add(j, drawable);
		Log.d("mycompany.myapp", "Dodan drawable številka " + j);
		}
		
		
		
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

      //  i.setImageResource(mImageIds[index]);
		i.setImageDrawable(drawables.get(index));
		//i.setImageResource(drawables.get(index);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));

        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}
