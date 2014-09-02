package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.BitmapDrawable;

import java.io.*;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;

import java.util.*;

import com.squareup.picasso.Picasso;

import android.util.Log;
//import java.lang.reflect.Field;

public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;
	private LayoutInflater inflater;


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
  
	static File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
										Environment.DIRECTORY_PICTURES), "/ShopCo");
//	File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "ShopCo");
	static File[] listFile = mediaStorageDir.listFiles();//get list of files
	String[] Djukla = mediaStorageDir.list(null);
	static String mediaStorageDirString = mediaStorageDir.toString();
	
	//String[] listFile_temp = mediaStorageDir.list(null);
	//ArrayList<String> listFile = new ArrayList<String>(Arrays.asList(listFile_temp)); 
	
	
	String imagePath = null;
	
	public static class ViewHolder {
		        ImageView imageView;
	 }



    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }
    

    public int getCount() {
		//Drawable[] drawables;
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		mediaStorageDir.mkdir();
		Log.d("mycompany.myapp", "Smo v getCount ");
		
		if (listFile == null) {
			Log.d("mycompany.myapp", "listFile je null ");
		}
		else {
			Log.d("mycompany.myapp", "listFile je dolg " + listFile.length);
    
		if (listFile.length != 0) {
		for (int i = 0; i < listFile.length; i++)
		{
			//Log.d("mycompany.myapp", "listFile je dolg " + listFile.size());
			Log.d("mycompany.myapp", "listFile je dolg " + mediaStorageDir.listFiles().length);
			Log.d("mycompany.myapp", "Drawables index v getCount " + i);
			//String imagePath = listFile.get(i);
			//String imagePath = listFile[i].getAbsolutePath();
			
			Log.d("mycompany.myapp", "imagePath je" + imagePath);
			//BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inSampleSize = 20;
			//Bitmap bm = BitmapFactory.decodeFile(imagePath, options);

			//Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
			//Log.d("mycompany.myapp", "imag" + imagePath);
			//drawables.set(i,drawable);
			
			//drawables.add(i,drawable);
			//Log.d("mycompany.myapp", "Smo dodali drawable!");
		}
		}
		}
		//return drawables.size();
		return listFile.length;
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
		ViewHolder viewHolder = null;
		ArrayList<String> listFileString = null;
		
    	ArrayList<Drawable> drawables = new ArrayList<Drawable>();
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
											Environment.DIRECTORY_PICTURES), "ShopCo");
//		File[] listFile = mediaStorageDir.listFiles();//get list of files
		//Drawable[] drawables = new BitmapDrawable();
		
		//DownloadAsyncTask task = null;
		//PhotoBitmapTask task = null;
		
		ImageView i = new ImageView(mContext);
		ViewGroup parentView = (ViewGroup)i.getParent();
		
		//String listFile_temp = listFile.toString();
		
		//ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(listFile_temp));
		ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
		Log.d("mycompany.myapp", "listFileAL je " + listFileAL);
		
		
		for (int j = 0; j < listFileAL.size(); j++) {
			//task = new DownloadAsyncTask(mContext, parentView, listFile);
			//Log.d("mycompany.myapp", "listFile.length je dolg: " + listFile.length);
			Log.d("mycompany.myapp", "listFileAL je dolg: " + listFileAL.size());
	//		task = new PhotoBitmapTask(mContext, parentView, listFileAL);
	//		task.execute(j);
		Log.d("mycompany.myapp", "Drawables index j:" + j);
		/////	String imagePath = listFile[j].getAbsolutePath();
			//Log.d("mycompany.myapp", "getView imagePath je" + imagePath);
			
		//BitmapFactory.Options options = new BitmapFactory.Options();
		//options.inSampleSize = 20;
		//Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
		
		//	Picasso.with(mContext)
			//  .load(imagePath)
			 // .resize(50, 50)
			 // .into(i);
		
		//	Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
		//	drawable = new ScaleDrawable(drawable, 0, 20, 20).getDrawable();
		//	drawables.add(j, drawable);
		//Log.d("mycompany.myapp", "Dodan drawable stevilka " + j);
		}
		
		
		
        // TODO Auto-generated method stub
        
		
      //  i.setImageResource(mImageIds[index]);
//		i.setImageDrawable(drawables.get(index));
		//i.setImageResource(drawables.get(index);
//        i.setLayoutParams(new Gallery.LayoutParams(200, 200));

//        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
    
 /*   
   private class DownloadAsyncTask extends AsyncTask<Integer, Void, Bitmap> {
    	
    	ArrayList<Drawable> drawables;
    	private int index;
    	private WeakReference<ViewGroup> parent;
		private Context context;
		private ArrayList<String> images;
    	
    	public DownloadAsyncTask(Context context, ViewGroup parent, ArrayList<String> images) {
    		super();
    		
    		this.context = context;
    		this.images = images;
    		this.parent = new WeakReference<ViewGroup>(parent);
    		
    	}
    	@Override
    	protected Bitmap doInBackground(Integer... params) {
    		// TODO Auto-generated method stub
    		//load image directly
    		index = params[0];
    		//index = 1;
    		File[] listFile = mediaStorageDir.listFiles();//get list of files
    		
    		
    		
    		ArrayList<Drawable> drawables = new ArrayList<Drawable>();
    		//File[] listFile = mediaStorageDir.listFiles();//get list of files
			//ArrayList<Drawable> drawables = new ArrayList<Drawable>();
			//for (int j = 0; j < listFile.length; j++)
			//{
				String imagePath = listFile[index].getAbsolutePath();
				Log.d("mycompany.myapp", "getView imagePath je" + imagePath);
				
// Tuki ustavimo
				
			//	BitmapFactory.Options options = new BitmapFactory.Options();
			//	options.inJustDecodeBounds = true;

		    //    options.inSampleSize = 8;
		        
		    //    Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
		        return getBitmapFromFile(images.get(params[0]), 600, 600);
		        
				
// Tuki nehamo ustauljat
				
	//			Drawable drawable = new BitmapDrawable(mContext.getResources(), imagePath);
		        //Drawable drawable = new BitmapDrawable(mContext.getResources(), bm);
	//			drawable = new ScaleDrawable(drawable, 0, 20, 20).getDrawable();
	//			drawables.add(j, drawable);
 		//	URL imageURL = new URL(viewHolder.imageURL);
 		//	viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
			
			//}
    		
//    		return bm;
    	}
    	
    	@Override
    	protected void onPostExecute(Bitmap bitmap) {
    		// TODO Auto-generated method stub
    		Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
   // 		ViewGroup viewGroup = parent.get();
   // 		ImageView i = new ImageView(mContext);
   // 		i.setImageBitmap(bitmap);
    		//viewGroup.addView(i);
    	}
    }
   
   public static Bitmap getBitmapFromFile(String filePath, int maxHeight,
           int maxWidth) {
       // check dimensions for sample size
       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inJustDecodeBounds = true;
       BitmapFactory.decodeFile(filePath, options);

       // calculate sample size
       options.inSampleSize = getSampleSize(options, maxHeight, maxWidth);

       // decode Bitmap with sample size
       options.inJustDecodeBounds = false;
       return BitmapFactory.decodeFile(filePath, options);
   }

   public static int getSampleSize(BitmapFactory.Options options,
           int maxHeight, int maxWidth) {
       final int height = options.outHeight;
       final int width = options.outWidth;
       int sampleSize = 1;

       if (height > maxHeight || width > maxWidth) {
           // calculate ratios of given height/width to max height/width
           final int heightRatio = Math.round((float) height / (float) maxHeight);
           final int widthRatio = Math.round((float) width / (float) maxWidth);

           // select smallest ratio as the sample size
           if (heightRatio > widthRatio)
               return heightRatio;
           else
               return widthRatio;
       } else
           return sampleSize;
   }

*/
    
    // TUKI ZDEJ COPY-PEJSTAMO 
    
   /* public static class PhotoBitmapTask extends AsyncTask<Integer, Void, Bitmap> {

        private Context context;
        private WeakReference<ViewGroup> parent;
        private ArrayList<String> images;
        private int data;
        private String fullPath = mediaStorageDirString;
        //static int[] myIdList = {};
        static List<Integer> myIdList = new ArrayList<Integer>();
        public ImageView imageviewpublic;

        public PhotoBitmapTask(Context context, ViewGroup parent, ArrayList<String> images) {
            super();

            this.context = context;
            this.parent = new WeakReference<ViewGroup>(parent);
            this.images = images;
            this.data = 0;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            
            Log.d("mycompany.myapp", "params[0] v doInBackground je " + params[0]);
            Log.d("mycompany.myapp", "images length v doInBackground je " + images.size());
            Log.d("mycompany.myapp", "images v doInBackground je " + images.get(params[0]));
            //Log.d("mycompany.myapp", "images v doInBackground je " + images.get(0));
            //return getBitmapFromFile(images.get(params[0]), 600, 600);
            fullPath = fullPath.concat("/");
            fullPath = fullPath.concat(images.get(params[0]));
            //return getBitmapFromFile(images.get(params[0]), 300, 300);
            return getBitmapFromFile(fullPath, 300, 300);
            //return getBitmapFromFile(images.get(0), 600, 600);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if (context != null && parent != null && result != null) {
            	Log.d("mycompany.myapp", "Smo v onPostExecute!");
                ViewGroup viewGroup = parent.get();
                if (viewGroup != null) {
                    ImageView imageView = PhotoBitmapTask.getImageView(context);
                    imageView.setImageBitmap(result);
                    
                    int uniqueID = data;
//                    imageView.getId();
                    
                    Log.d("mycompany.myapp", "imageView.getId pred spreminjanjem v onPostExecute je: " +imageView.getId());
                    imageView.setId(uniqueID);
                    imageView.setTag(uniqueID);
                    Log.d("mycompany.myapp", "imageView.getId po spreminjanju v onPostExecute je: " +imageView.getId());
                   // imageView.getId();
                    Log.d("mycompany.myapp", "uniqueID v onPostExecute je: " +uniqueID);
                    //myImage.setId(uniqueID);
                    //myIdList.add(uniqueID);
                    myIdList.add(uniqueID);
                    Log.d("mycompany.myapp", "myIdList v onPostExecute je: " +myIdList);
                    imageviewpublic = imageView;
                    viewGroup.addView(imageView);
                    
                    //LOCATION2
                }
            }
            else {
            	Log.d("mycompany.myapp", "Nismo v onPostExecute!");
            }
        }

        public static Bitmap getBitmapFromFile(String filePath, int maxHeight,
                int maxWidth) {
            // check dimensions for sample size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // calculate sample size
            options.inSampleSize = getSampleSize(options, maxHeight, maxWidth);

            // decode Bitmap with sample size
            options.inJustDecodeBounds = false;
            Log.d("mycompany.myapp", "filePath v getBitmapFromFile je: " + filePath);
            return BitmapFactory.decodeFile(filePath, options);
        }

        public static int getSampleSize(BitmapFactory.Options options,
                int maxHeight, int maxWidth) {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int sampleSize = 1;

            if (height > maxHeight || width > maxWidth) {
                // calculate ratios of given height/width to max height/width
                final int heightRatio = Math.round((float) height / (float) maxHeight);
                final int widthRatio = Math.round((float) width / (float) maxWidth);

                // select smallest ratio as the sample size
                if (heightRatio > widthRatio)
                    return heightRatio;
                else
                    return widthRatio;
            } else
                return sampleSize;
        }

        public int getData() {
            return this.data;
        }

        public static ImageView getImageView(Context context) {
            // width and height
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            // margins
            params.setMargins(20, 20, 20, 20);
            ImageView view = new ImageView(context);
            view.setLayoutParams(params);
            // scale type
            view.setScaleType(ScaleType.CENTER);
            return view;
        }
    }
    
    // KONEC COPY-PEJSTANJA
    */
}
