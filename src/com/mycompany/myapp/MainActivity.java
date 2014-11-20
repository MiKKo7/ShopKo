package com.mycompany.myapp;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

import com.mycompany.myapp.IntentIntegrator;
import com.mycompany.myapp.IntentResult;
//import com.mycompany.myapp.GalleryImageAdapter.PhotoBitmapTask;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.support.v4.app.FragmentActivity;

import java.util.*;

import android.provider.*;
import android.content.Context;
import android.content.res.Resources;

import java.io.*;
import java.text.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;



import android.location.Location;

//import com.mycompany.myapp.FancyCoverFlow;
//import com.mycompany.myapp.FancyCoverFlowAdapter;
//import com.mycompany.myapp.R;

//import com.mycompany.myapp.ViewGroupExample;



public class MainActivity extends FragmentActivity implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener 
{
	
	private Button scanBtn;
	private Button takePicBtn;
	private Button loginBtn;
	private TextView formatTxt, contentTxt;
	
	private Button btnFind;
	
	private static String logtag = "barcode";
	private Uri fileUri;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	private String storageState;
	
	//static String mediaStorageDirString = mediaStorageDir.toString();
	
	double latitude = 0;
	double longitude = 0;
	
	public BitmapFactory.Options moznosti;
	
	// Global variable to hold the current location
    Location mCurrentLocation;
    
    LocationRequest locationRequest;
    //LocationClient locationClient;
    
    LocationListener locationListener;
    
    //static LinearLayout horizontal;
    ImageView imageviewpublic;
	
	ImageView selectedImage;  
	ImageView selectedImage2;  
	
	public static Context context;
	public LinearLayout parent;
	
	//List<Integer> myIdList;
	
	GoogleMap mGoogleMap;
    Spinner mSprPlaceType;
    //Spinner mSprPlaceChoose;
 
    String[] mPlaceType=null;
    String[] mPlaceTypeStart=null;
    String[] mPlaceTypeName=null;
    List<String> GooglePlacesList = new ArrayList<String>();

    ArrayList<String> placeTypeStart = new ArrayList<String>();
    
    public static List<Integer> myIdList = new ArrayList<Integer>();
    
    public static ArrayList<Bitmap> bitmapsArray = new ArrayList<Bitmap>();
    
    public static ArrayList<View> viewArray = new ArrayList<View>();
    
   //public static ImageView imageviewpublic;
    
    public ViewGroup viewGroup;
    
    
   

 // Tuki kopiramo
	
    final static File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
			Environment.DIRECTORY_PICTURES), "ShopCo");			
 		//File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "ShopCo");
 		File[] listFile = mediaStorageDir.listFiles();//get list of files
 		String[] Djukla = mediaStorageDir.list(null);
 		static String mediaStorageDirString = mediaStorageDir.toString();
 		
 		// Tuki nehamo kopirat
    
    //List<HashMap<String,String>> GooglePlacesList;
 
    //double mLatitude=0;
    //double mLongitude=0;
	
	//private Integer[] mImageIds = {
		//R.drawable.image1,
		//R.drawable.image2,
	//	R.drawable.image3,
	//	R.drawable.image4,
	//	R.drawable.image5,
		//R.drawable.image6,
		//R.drawable.image7,
		//R.drawable.image8
	//};
	
	//private FancyCoverFlow FancyCoverFlow;
	
	//private int[] imagesShopCo;
	
	//private int[] images;
	
	LinearLayout imageGallery;
	
	// A request to connect to Location Services
//private LocationRequest mLocationRequest;

// Stores the current instantiation of the location client in this object
private LocationClient mLocationClient;
	
	
//	FancyCoverFlowSampleAdapter activityObj  = new FancyCoverFlowSampleAdapter(this.getApplicationContext());
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
		return Uri.fromFile(getOutputMediaFile(type));
	}


	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
											Environment.DIRECTORY_PICTURES), "ShopCo");
											
		// Create the storage directory if it does not exist
		if (! mediaStorageDir.exists()){
			if (! mediaStorageDir.mkdirs()){
				Log.d("mycompany.myapp", "failed to create directory");
				return null;
			}
		}				
		
		
		
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE){
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
								 "IMG_"+ timeStamp + ".jpg");
		} else if(type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
								 "VID_"+ timeStamp + ".mp4");
		} else {
			return null;
		}
		
		return mediaFile;
		
	}

	//public File[] listFile;
	/// ArrayList<Bitmap> bitmapImages = new ArrayList<Bitmap>(); Zakomentirau 25. julij 2014
	// Acquire a reference to the system Location Manager
	////LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	
	public static class globalAccess {
		//public static LinearLayout horizontal = null;
		public static LinearLayout horizontal = null;
		//public static ArrayList<Bitmap> bitmapsArray;
	    //public static int a;
	    //public static int b;
	}
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		PushService.setDefaultPushCallback(this, MainActivity.class);
		
		storageState = Environment.getExternalStorageState();
		Log.d("mycompany.myapp","Storidz:" + storageState);
		try
        {
		
			//InputStream caPrviInput = new BufferedInputStream(MainActivity.context.getAssets().open("ShopCo.crt"));
			String[] caPrviInput = this.getAssets().list("");
			Log.d("mycompany.myapp", "Assets: " + caPrviInput);
			//Log.d("mycompany.myapp", "ShopCo.crt je loudan na zacetku v onCreate!");
        }
		catch (Exception ex)
        {
            Log.e("mycompany.myapp", "Failed to open ShopCo.crt in MainActivity: " + ex.toString());
            
        }
		
		// TUKI KOPIRAMO
		
		//locationClient = new LocationClient(this, this, this);
	    locationRequest = new LocationRequest();
	    locationRequest.create();
	    // Use high accuracy
	    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	    // Set the update interval to 5 seconds
	    locationRequest.setInterval(2000);
	    // Set the fastest update interval to 1 second
	    locationRequest.setFastestInterval(1000);

		
		
		// TUKI NEHAMO KOPIRAT
		
		
		 mLocationClient = new LocationClient(this, this, this);
		 
		 moznosti = new BitmapFactory.Options();
        
		
		//FancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());
		
		//Parse.initialize(this, "DibF0HiaV6L8X709dfr9ogz5StQCKLGuO3F2Ph8I", "Yont4omFCs6auCQ2hSBPKyNAfvrazVymurkjN2lC");
		//PushService.setDefaultPushCallback(this, MainActivity.class);
		//PushService.subscribe(this, "majcka", MainActivity.class);
		ParseAnalytics.trackAppOpened(getIntent());
        
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		scanBtn.setOnClickListener(this);
		
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		
		takePicBtn = (Button)findViewById(R.id.takePic_button);
		
		takePicBtn.setOnClickListener(this);
		
		// Tuki dodajamo!
		
		// Array of place types
        mPlaceType = getResources().getStringArray(R.array.place_type);
 
        // Initial Array to display "...acquiring location..."
        //mPlaceTypeStart = getResources().getStringArray(R.array.place_type_start);
        
        placeTypeStart.add("...acquiring location...");
 
        // Creating an array adapter with an array of Place types
        // to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, placeTypeStart);
 
        // Getting reference to the Spinner
        mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
 
        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);
        
        loginBtn = (Button)findViewById(R.id.login_button);
		loginBtn.setOnClickListener(this);
        
 
       // Button btnFind;
 
        // Getting reference to Find Button
        //btnFind = ( Button ) findViewById(R.id.btn_find);
		
		// Tuki nehamo dodajat!
		
		//final File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
		//									Environment.DIRECTORY_PICTURES), "ShopCo");									
		// File[] listFile = mediaStorageDir.listFiles();//get list of files
		
		
//		
//		ImageView imageView1Pic = (ImageView)findViewById(R.id.imageView1);
//		
//		String imagePath = listFile[0].getAbsolutePath();
//		Bitmap bm = BitmapFactory.decodeFile(imagePath);
//		
//		imageView1Pic.setImageBitmap(bm);
		//imageView1.setImageResource(R.drawable.image1);
		
		
		//super.onCreate(savedInstanceState);
      //  setContentView(R.layout.main);

		//Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		globalAccess.horizontal = (LinearLayout) findViewById(R.id.horizontal);
        selectedImage = (ImageView)findViewById(R.id.imageView1);
        ImageView selectedImage2 = (ImageView)findViewById(R.id.imageView2);
        //gallery.setSpacing(1);
        //gallery.setAdapter(new GalleryImageAdapter(this));
        
        PhotoBitmapTask task = null;
		
		//ImageView i = new ImageView(this);
		//ViewGroup parentView = (ViewGroup)i.getParent();
        
    
        String[] Djukla = mediaStorageDir.list(null);
        //String mediaStorageDirString = mediaStorageDir.toString();
        
        ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
        
        for (int j = 0; j < listFileAL.size(); j++) {
			//task = new DownloadAsyncTask(mContext, parentView, listFile);
			//Log.d("mycompany.myapp", "listFile.length je dolg: " + listFile.length);
			Log.d("mycompany.myapp", "listFileAL je dolg: " + listFileAL.size());
			task = new PhotoBitmapTask(this, globalAccess.horizontal, listFileAL);
			task.execute(j);
			Log.d("mycompany.myapp", "Drawables index j:" + j);
			
        }
		
		
		
		//cekirajmo.servicesConnected();
		
		//Intent intent = new Intent(this, GooglePlayCheckActivity.class);
		//startActivity(intent);
		
//		scanBtn.setOnClickListener(this);
//		takePicBtn.setOnClickListener(this);
		
		checkPlayServices();
		
		

        // TUKI PEJSTAMO
		

		
 
		// TUKI NEHAMO PEJSTAT

	
		
		
		//mLocationClient.connect(); TA TUKAJ DELA KAZIN!!!
		
		// clicklistener for Gallery
		//gallery.setOnItemClickListener(new OnItemClickListener() 
		
		// TUKI SPODAJ SMO ZAKOMENTIRALI!
		
		//Tuki zdej provamo
		/*
		//String[] DjuklaBeta = mediaStorageDir.list(null);
		 //ArrayList<String> listFileALBeta = new ArrayList<String>(Arrays.asList(DjuklaBeta));
		//String imagePath = listFile[position].getAbsolutePath();
		//String imagePath = listFileAL.get(position);
		String imagePath = (mediaStorageDir.getAbsolutePath());
		
		//imagePath = imagePath.concat(mediaStorageDir.getAbsolutePath());
		imagePath = imagePath.concat("/");
		imagePath = imagePath.concat(listFileAL.get(1));
		
		
		//options2 = new BitmapFactory.Options();
		
		//moznosti.inJustDecodeBounds = true;

       moznosti.inSampleSize = 4;
       if (moznosti.toString() != null) {
    	   Log.d("mycompany.myapp", "options2.toString je: " + moznosti.toString());
       }
       else {Log.d("mycompany.myapp", "options2.toString je ena merda!");
       }
       
       Log.d("mycompany.myapp", "imagePath v onCreate je: " + imagePath);
       Bitmap slika = BitmapFactory.decodeFile(imagePath, moznosti);
       if (slika != null) {
    	   
    	   selectedImage2.setImageBitmap(slika);
    	   Log.d("mycompany.myapp", "bitmap v onCreate je uspeu! :-) ");
       }
       else {
    	   Log.d("mycompany.myapp", "bitmap v onCreate je ena merda! :-/ ");
       }
    	   
		*/
		// Tuki nehamo provavat
		
		// TUKI SPODAJ NARDIMO CUT
		/*
		
        globalAccess.horizontal.setOnClickListener(new OnClickListener() {
				//public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        	public void onClick(View viewpublic) {
        			//int vId = horizontal.getId();
        			//List<Integer> onClickIdList = PhotoBitmapTask.myIdList;
        		
        			List<Integer> onClickIdList = myIdList;
        			Log.d("mycompany.myapp", "onClickIdList v onClick je: " + onClickIdList);
        			Log.d("mycompany.myapp", "v.getId v onClick je: " + viewpublic.getId());
        			
        			int position = onClickIdList.indexOf(viewpublic.getId());
        			//int position = onClickIdList.indexOf(viewpublic.getTag());
        			//int position = 1;
        			Log.d("mycompany.myapp", "position v onClick je: " + position);
					Toast.makeText(MainActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
					// show the selected Image
					//selectedImage.setImageResource(mImageIds[position]);
					Log.d("mycompany.myapp", "indexOfChild v onClick je: " + globalAccess.horizontal.indexOfChild(viewpublic));
					
					String[] Djukla = mediaStorageDir.list(null);
					 ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
					//String imagePath = listFile[position].getAbsolutePath();
					//String imagePath = listFileAL.get(position);
					String imagePath = (mediaStorageDir.getAbsolutePath());
					
					//imagePath = imagePath.concat(mediaStorageDir.getAbsolutePath());
					imagePath = imagePath.concat("/");
					imagePath = imagePath.concat(listFileAL.get(position));
					
					
					BitmapFactory.Options options = new BitmapFactory.Options();
					//options.inJustDecodeBounds = true;

			        options.inSampleSize = 10;
			        Log.d("mycompany.myapp", "imagePath v onClick je: " + imagePath);
			        Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
					//Bitmap bm = BitmapFactory.decodeFile(imagePath);
		
					selectedImage.setImageBitmap(bm);
					
					
					//imageviewpublic.invalidate();
			        //imageviewpublic.setImageBitmap(bm);
				}
			});
		*/
		
        // TUKI NEHAMO CUTTAT
        
		//FancyCoverFlow = new FancyCoverFlow(this);
		
		//this.FancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.FancyCoverFlow);
		
		//FancyCoverFlow = (FancyCoverFlow)findViewById(R.id.FancyCoverFlow);
		
		//this.FancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
		
//        this.FancyCoverFlow.setUnselectedAlpha(1.0f);
//        this.FancyCoverFlow.setUnselectedSaturation(0.0f);
//        this.FancyCoverFlow.setUnselectedScale(0.5f);
//        this.FancyCoverFlow.setSpacing(50);
//        this.FancyCoverFlow.setMaxRotation(0);
//        this.FancyCoverFlow.setScaleDownGravity(0.2f);
//        this.FancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
		

		
		
//		scanBtn.setOnClickListener(this);
	//	takePicBtn.setOnClickListener(this);
		
		//////imageGallery=(LinearLayout)findViewById(R.id.linearImage);
		
		//Field[] ID_Fields = com.mycompany.myapp.R.drawable.class.getFields();
		
		//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		
		
		//File sdCardRoot = Environment.getExternalStorageDirectory(Environment.DIRECTORY_PICTURES);
		
		//File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
											//Environment.DIRECTORY_PICTURES), "ShopCo");
		//File yourDir = new File(sdCardRoot, "New Folder");
		
		//File file=  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() ,"CamWay");
		
		//Resources res = getResources();

		
		
//		if (mediaStorageDir.isDirectory())
//		{
//			File[] listFile = mediaStorageDir.listFiles();//get list of files
//		
//
//			for (int i = 0; i < listFile.length; i++)
//			{
//				
//				
//				//image.setBackgroundResource();
//				
//				
//				
//				//File file = new File(Environment.getExternalStoragePath()+"/Stampii/");
//
//				//file imageList[] = file.listFiles();
//
//			//	for(int i=0;i<imageList.length;i++)
//				//{
//					Log.e("Naše slike - Image: "+i+": path", listFile[i].getAbsolutePath());
//				String imagePath = listFile[i].getAbsolutePath();
//					//Bitmap b = BitmapFactory.decodeFile(imageList[i].getAbsolutePath());
//
//					//images.add(b);
//					
//					///String imagePath = listFile[i].getAbsolutePath();
//
//
//					//ImageView image = (ImageView) findViewById(R.id.imageviewTest);
//					//image.setImageBitmap(myBitmap);
//
//					//ImageView image = new ImageView(this);
//
//
//					//image.setBackground(); //setImageURI(imageUri.fromFile(listFile[i]));
//					//image = BitmapFactory.decodeFile(imagePath);
//				//	BitmapFactory.Options options = new BitmapFactory.Options();
//				//	options.inSampleSize = 20;
//				//	Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
//				//	bitmapImages.add(bm);
//					//image.setImageBitmap(bm);
//
//
//					//Drawable drawable = new BitmapDrawable(getResources(),bm);
//					//image.setBackgroundResource(R.drawable
//					/////////image.setImageBitmap(bm);
//					//image.setImageDrawable(drawable);
//					//image.setImageURI(Uri.fromFile(listFile[i]));
//
//					/////////////imageGallery.addView(image);
//					//drawables[i] = new BitmapDrawable(getResources(),bitmap);		
//					
//			//	}
//				
//				// create a file that is really a directory
//				//File aDirectory = new File("C:/temp");
//	
//				
//				// get a listing of all files in the directory
//				//String[] filesInDir = aDirectory.list();
//				//String[] filesInDir = mediaStorageDir.list();
//
//				// sort the list of files (optional)
//				// Arrays.sort(filesInDir);
//
//				// have everything i need, just print it now
//				//for ( int j = 0; j < filesInDir.length; j++ )
//				//{
//					//System.out.println( "file: " + filesInDir[i] );
//					ImageView image = new ImageView(MainActivity.this);
//			    	//image.setBackground(); //setImageURI(imageUri.fromFile(listFile[i]));
//					//image = BitmapFactory.decodeFile(imagePath);
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inSampleSize = 10;
//				Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
//				//bitmapImages.add(bm);
//					image.setImageBitmap(bm);
//
//					
//				//////	Drawable drawableItem = new BitmapDrawable(getResources(),bm);
//					//image.setBackgroundResource(R.drawable
//					
//				///////	image.setImageDrawable(drawableItem);
//					//image.setImageURI(Uri.fromFile(listFile[i]));
//					
//					imageGallery.addView(image);
//			//	BitmapDrawable(getResources(),bm);
//						
//				//}
//				
//				//imagesShopCo[i] = res.getDrawable(R.drawable.drawableItem);
//			}	
//			
//			
//			
//			
//			
//			
//				
//			//imagesShopCo =
//			
//			//private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6};
//		}
		
		
		
	
		
	/*	for (File f : mediaStorageDir.listFiles()) {
			
			if (f.isFile())
			{
				//i++;
				//String name = f.getName();

                //Log.i("file names", name);
				
				//ImageView image = new ImageView(MainActivity.this);
				//image.setBackgroundResource(i);
				//imageGallery.addView(image);
				
			}

		}
		*/	
		
		
	    //Field[] ID_Fields = mediaStorageDir.ID_Fields;
		//drawables[i]=(BitmapDrawable)getResources().getDrawable(id);
		
     /*   int[] resArray = new int[ID_Fields.length];
        for(int i = 0; i < ID_Fields.length; i++) {
				try {
					resArray[i] = ID_Fields[i].getInt(null);
				}
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
               		e.printStackTrace();
				}
    	}
		
		for(int i=0; i< resArray.length; i++){
			ImageView image = new ImageView(MainActivity.this);
			image.setBackgroundResource(resArray[i]);
			imageGallery.addView(image);
		}*/
		
	}
	

//	@Override
//    public int getCount() {
//        //return images.length;
//		return listFile.length;
//    }
//
//    @Override
//    public Bitmap getItem(int i) {
//		// return images[i];
//		return bitmapImages.get(i);
//		//return listFile[i];
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
//        ImageView imageView = null;
//
//        if (reuseableView != null) {
//            imageView = (ImageView) reuseableView;
//        } else {
//            imageView = new ImageView(viewGroup.getContext());
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 400));
//
//        }
//
//        //imageView.setImageResource(this.getItem(i));
//		imageView.setImageBitmap(getItem(i));
//        return imageView;
//    }	
	
	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        
        //do {} while (mLocationClient.getLastLocation() == null);
        // do {mCurrentLocation = mLocationClient.getLastLocation();}
        //while (mCurrentLocation == null); 
        
        // TUKI KOPIRAMO
        
        Location location = mLocationClient.getLastLocation();
        if (location == null)
            mLocationClient.requestLocationUpdates(locationRequest, locationListener);
        else
        {
        	Toast.makeText(this,"Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        
        // TUKI NEHAMO KOPIRAT
    
    
    // TUKI KOPIRAMO
    
    
    // TUKI NEHAMO KOPIRAT
    
    
    //    Log.i("Location Updates","getLastLocation je " +mCurrentLocation);
    //    latitude = mCurrentLocation.getLatitude();
    //    longitude = mCurrentLocation.getLongitude();    
    //    Log.i("Location", ""+latitude + " "+longitude);
        	
        	Log.i("Location Updates","getLastLocation Latitude je " + location.getLatitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();    
            Log.i("Location", ""+location.getLatitude() + " "+location.getLongitude());
            
        
        // Tu smo dodali!
        
     // Setting click event lister for the find button
       // btnFind.setOnClickListener(new OnClickListener() {

           //  @Override
           //  public void onClick(View v) {

               // int selectedPosition = mSprPlaceType.getSelectedItemPosition();
               // String type = mPlaceType[selectedPosition];

                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                sb.append("location="+latitude+","+longitude);
                sb.append("&radius=5000");
                //sb.append("&types=bus_station");
                sb.append("&types=clothing_store|shoe_store");
                sb.append("&rankBy=DISTANCE");
                //sb.append("&types="+type);
                sb.append("&sensor=true");
                sb.append("&key=AIzaSyDw084R-npWWbdq5R0JAXFZPD3lFFUocgs");

                // Creating a new non-ui thread task to download json data
                PlacesTask placesTask = new PlacesTask();

                // Invokes the "doInBackground()" method of the class PlaceTaskd
                placesTask.execute(sb.toString());

            // }
        // });

     // Tu smo nehali dodajat!
        }   
    }
    
    @Override
    public void onLocationChanged(Location location) {
        mLocationClient.removeLocationUpdates(this);
    }
        // Use the location here!!!
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
					   Toast.LENGTH_SHORT).show();
    }
   
    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
					this,
					CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		//mLocationRequest = LocationRequest.create();
		mLocationClient.connect(); //TA TUKAJ DELA KAZIN!!!
//		Log.i("Location Updates",
//			  "Smo pred Google Play services.");
//		GooglePlayCheck cekirajmo = new GooglePlayCheck(this);
//		Log.i("Location Updates",
//			  "Smo za Google Play services.");
}
			  
//	@Override
//    public void onConnected(Bundle dataBundle) {
//        // Display the connection status
//        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
//		Log.i("Location Updates",
//			  "Connected!");
//			  
//		Log.i("Location Updates",
//			  "Smo pred Google Play services.");
//		GooglePlayCheck cekirajmo = new GooglePlayCheck(this);
//		Log.i("Location Updates",
//			  "Smo za Google Play services.");
//     
//	if (cekirajmo.servicesConnected() == true)
//	//if (checkPlayServices() == true)
//	//if (true == true)
//		{
//			Log.i("Location Updates",
//				  "Google Play services is available.");	
//		}
//		else
//		{
//			Log.i("Location Updates",
//				  "Google Play services is unavailable.");
//		}
//		}
//    
	
	// * Called when the Activity is no longer visible.
    //  */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
	
	
	public void onClick(View v){

		//respond to clicks
		Log.d(logtag, "smo v onClick");
		
				
		
		
//		LocationListener locationListener = new LocationListener() {
//			public void onLocationChanged(Location location) {
//				// Called when a new location is found by the network location provider.
//				////////makeUseOfNewLocation(location);
//			}
//		};
		
		int id = v.getId();
		if (id == R.id.scan_button) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//scan
			scanIntegrator.initiateScan();
			Log.d(logtag, "skeniramo!");
		} else if (id == R.id.takePic_button) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			// start the image capture Intent
			Log.d(logtag, "slikamo!");
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		} else if (id == R.id.login_button) { 
			Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
			MainActivity.this.startActivity(loginIntent);
		
	} else if (id == R.id.spr_place_type) {
			String spinnerText = mSprPlaceType.getSelectedItem().toString();
		}
		
		/*if(v.getId()==R.id.scan_button){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//scan
			scanIntegrator.initiateScan();
		}*/
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        Log.d(logtag, "able to set the text");

		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Log.d(logtag, "smo slikali!");
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved :-)", Toast.LENGTH_LONG).show();
				selectedImage.setImageURI(fileUri);
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				
			} else {
				// Image capture failed, advise user
				Toast.makeText(MainActivity.this, "image capture failed", Toast.LENGTH_SHORT).show();
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Video saved to:\n" +
							   intent.getData(), Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
				Toast.makeText(MainActivity.this, "video capture failed", Toast.LENGTH_SHORT).show();
			}
		}
		
        if(scanResult != null) {
            Log.d(logtag, "able to set the text");

            String scanContent = "Scan Content: ";
            String scanFormat = "Scan Format: ";
			String scanPrefix = "ShopCo_";

            scanContent += scanResult.getContents();
            scanFormat += scanResult.getFormatName();

            Log.d(logtag, "able to set the text");

            // put the results to the text
            contentTxt.setText(scanContent);
            formatTxt.setText(scanFormat);
			
			//formatTxt.setText("FORMAT: " + scanFormat);
			//contentTxt.setText("CONTENT: " + scanContent);
			
			Log.d(logtag, scanResult.getContents());
		    PushService.subscribe(this, scanPrefix + scanResult.getContents(), MainActivity.class);

        } else {
         //   Toast.makeText(MainActivity.this, "we didn't get anything back from the scan", Toast.LENGTH_SHORT).show();
        }
    }
	
	void googlePlacesList(List<HashMap<String,String>> hmPlace) {
		GooglePlacesList.clear();
		//ArrayAdapter<String> arrayAdapter;
    	for(int i=0;i<hmPlace.size();i++){
    		HashMap<String, String> hmGooglePlace = hmPlace.get(i);
    		String Google_place_name = hmGooglePlace.get("place_name");
    		Log.i("Kraj v googlePlacesList", Google_place_name);
    		GooglePlacesList.add(Google_place_name);
    	}
    	Log.i("Kraji v googlePlacesList", GooglePlacesList.toString());
    	
    	 mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
    	
    	placeTypeStart.clear();
    	Log.i("PlaceTypeStart pred clear", placeTypeStart.toString());
    	placeTypeStart.addAll(GooglePlacesList);
    	Log.i("PlaceTypeStart po addAll", placeTypeStart.toString());
    	 //arrayAdapter = mSprPlaceType.getAdapter();
    	 //SpinnerAdapter adapter = mSprPlaceType.getAdapter();
    	//arrayAdapter.notifyDataSetChanged();
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, placeTypeStart);
    	// Getting reference to the Spinner
        mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
 
        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);
    	
    	//adapter.notifyDataSetChanged(); /* Inform the adapter  we've changed items, which should force a refresh */

    }
	
	private boolean checkPlayServices() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(status);
			} else {
				Toast.makeText(this, "This device is not supported.", 
							   Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		 // In debug mode, log the status
        Log.d("Location Updates",
                "Google Play services is available.");
        // Continue
		return true;
	} 

	
	 /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
 
        return data;
    }
	
	
	 /** A class, to download Google Places */
    private class PlacesTask extends AsyncTask<String, Integer, String>{
 
        String data = null;
 
        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
                Log.i("Data od downloadUrl", data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();
 
            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
 
    }
	
	
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        JSONObject jObject;
        // List<String> GooglePlacesList;
 
        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){
 
            // Clears all the existing markers
            //mGoogleMap.clear();
        	Log.i("List size", Integer.toString(list.size()));
 
            for(int i=0;i<list.size();i++){
 
                // Creating a marker
                // MarkerOptions markerOptions = new MarkerOptions();
 
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                //String Google_place_name = hmPlace.get("place_name");
                //GooglePlacesList.add(Google_place_name);
                
 
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
 
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
 
                // Getting name
                String name = hmPlace.get("place_name");
                Log.i("Kraj", name);
                //List<String> GooglePlacesList.add(name);
               
 
                // Getting vicinity
                //String vicinity = hmPlace.get("vicinity");
 
                //LatLng latLng = new LatLng(lat, lng);
 
                // Setting the position for the marker
               // markerOptions.position(latLng);
 
                // Setting the title for the marker.
                //This will be displayed on taping the marker
              //  markerOptions.title(name + " : " + vicinity);
 
                // Placing a marker on the touched position
              //  mGoogleMap.addMarker(markerOptions);
            }
            googlePlacesList(list);
        }
    }
    
    
	
	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this, 
											  REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}


//	@Override
//	public void onLocationChanged(Location arg0) {
//		// TODO Auto-generated method stub
//		
//	}
	public class PhotoBitmapTask extends AsyncTask<Integer, Void, Bitmap> {

        //private Context context;
		public Context context;
       // private WeakReference<ViewGroup> parent;
        //public ViewGroup parent;
        //public LinearLayout parent;
		public LinearLayout horizontal;
        private ArrayList<String> images;
        private int data;
        private String fullPath = mediaStorageDirString;
        //static int[] myIdList = {};
        // List<Integer> myIdList = new ArrayList<Integer>();
        //myIdList = new ArrayList<Integer>();
        //public ImageView imageviewpublic;

        //public PhotoBitmapTask(Context context, ViewGroup parent, ArrayList<String> images) {
        //public PhotoBitmapTask(Context context, LinearLayout parent, ArrayList<String> images) {
        public PhotoBitmapTask(Context context, LinearLayout horizontal, ArrayList<String> images) {
            super();

            this.context = context;
            //this.parent = new WeakReference<ViewGroup>(parent);
            //this.parent = parent;
            this.horizontal = horizontal;
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
            

            //if (context != null && parent != null && result != null) {
            if (context != null && horizontal != null && result != null) {
            	Log.d("mycompany.myapp", "Smo v onPostExecute!");
                //ViewGroup viewGroup = parent.get();
            	//ViewGroup viewGroup = parent.get();
            	//ViewGroup viewGroup = parent;
            	//LinearLayout viewGroup = parent;
            	LinearLayout viewGroup = horizontal;
            	//ViewGroup viewGroup = horizontal;
                if (viewGroup != null) {
                   // ImageView imageView = PhotoBitmapTask.getImageView(context);
                	 //imageviewpublic = PhotoBitmapTask.getImageView(context);
                	//imageviewpublic = PhotoBitmapTask.getImageView(context);
                	imageviewpublic = getImageView(context);
                    //imageView.setImageBitmap(result);
                	 imageviewpublic.setImageBitmap(result);
                    
                    int uniqueID = data;
//                    imageView.getId();
                    
                    //Log.d("mycompany.myapp", "imageView.getId pred spreminjanjem v onPostExecute je: " +imageView.getId());
                    Log.d("mycompany.myapp", "imageView.getId pred spreminjanjem v onPostExecute je: " +imageviewpublic.getId());
                    //imageView.setId(uniqueID);
                    imageviewpublic.setId(uniqueID);
                    //imageView.setTag(uniqueID);
                    imageviewpublic.setTag(uniqueID);
                    //Log.d("mycompany.myapp", "imageView.getId po spreminjanju v onPostExecute je: " +imageView.getId());
                    Log.d("mycompany.myapp", "imageView.getId po spreminjanju v onPostExecute je: " +imageviewpublic.getId());
                   // imageView.getId();
                    Log.d("mycompany.myapp", "uniqueID v onPostExecute je: " +uniqueID);
                    //myImage.setId(uniqueID);
                    //myIdList.add(uniqueID);
                    myIdList.add(uniqueID);
                    //globalAccess.bitmapsArray.add(result);
                    bitmapsArray.add(result);
                    Log.d("mycompany.myapp", "myIdList v onPostExecute je: " +myIdList);
                    //imageviewpublic = imageView;
                   // viewGroup.addView(imageView);
                    //viewGroup.addView(imageviewpublic);
                    //horizontal = viewGroup;
                    
                    
                    // TUKI PEJSTAMO!
                    
            		final int index = data;
                    //globalAccess.horizontal.setOnClickListener(new OnClickListener() {
            		imageviewpublic.setOnClickListener(new OnClickListener() {
            				//public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    	//@Override
                    	public void onClick(View v) {
                    		
                    			//int vId = horizontal.getId();
                    			//List<Integer> onClickIdList = PhotoBitmapTask.myIdList;
                    		ArrayList<View> clickView;
                    		ArrayList<View> clickViewID;
                    			List<Integer> onClickIdList = myIdList;
                    			Log.d("mycompany.myapp", "onClickIdList v onClick je: " + onClickIdList);
                    			Log.d("mycompany.myapp", "v.getId v onClick je: " + v.getId());
                    			
                    			Log.d("mycompany.myapp", "Childcount v onClick je: " +globalAccess.horizontal.getChildCount());
                    			
                    			
                    			//Log.d("mycompany.myapp", "viewById(0) v onClick je: " +globalAccess.horizontal.findViewById(0));
                    			//viewArray.add(0, globalAccess.horizontal.findViewById(0));
                    			
                				//Log.d("mycompany.myapp", "viewArray v onClick je: " +globalAccess.horizontal.findViewById(0));
                    			
                    			//globalAccess.bitmapsArray.indexOf(object)
                    			//for (int i = 0;  i < globalAccess.horizontal.getChildCount(); i++) {
                    				Log.d("mycompany.myapp", "i v onClick je: " +index);
                    				//viewArray.add(index, globalAccess.horizontal.findViewById(index));
                    				
                    				//Log.d("mycompany.myapp", "viewArray v onClick je: " +globalAccess.horizontal.findViewById(index));
                    				//int position = globalAccess.horizontal.indexOfChild(viewArray.get(index));
                    				//Log.d("mycompany.myapp", "index of selected position v onClick je: " + position);
                    		//	}
                    			//int position = globalAccess.horizontal.indexOfChild(viewArray.get(1));
                    			//int position = globalAccess.horizontal.indexOfChild(viewArray.get(i));
                    			
                    			
                    			
                    			
                    			//clickView = globalAccess.horizontal.findViewWithTag(6);
                    			//clickViewID = globalAccess.horizontal.findViewById(6);
                    			//Log.d("mycompany.myapp", "findViewWithTag v onClick je: " +clickView);
                    			//Log.d("mycompany.myapp", "findViewWithID v onClick je: " +clickViewID);
                    			
                    			
                    			//int viewID = onClickIdList.indexOf(v.getId());
                    			int viewID = v.getId();
                    			//int position = onClickIdList.indexOf(viewpublic.getTag());
                    			//int position = viewArray.indexOf(v);
                    			//Log.d("mycompany.myapp", "position v onClick je: " + position);
            					Toast.makeText(MainActivity.this, "Your selected position = " + index, Toast.LENGTH_SHORT).show();
            					// show the selected Image
            					//selectedImage.setImageResource(mImageIds[position]);
            					//Log.d("mycompany.myapp", "indexOfChild v onClick je: " + globalAccess.horizontal.indexOfChild(v));
            					Log.d("mycompany.myapp", "viewID of selected position v onClick je: " + viewID);
            					
            					String[] Djukla = mediaStorageDir.list(null);
            					 ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
            					//String imagePath = listFile[position].getAbsolutePath();
            					//String imagePath = listFileAL.get(position);
            					String imagePath = (mediaStorageDir.getAbsolutePath());
            					
            					//imagePath = imagePath.concat(mediaStorageDir.getAbsolutePath());
            					imagePath = imagePath.concat("/");
            					imagePath = imagePath.concat(listFileAL.get(index));
            				
            					
            					
            					BitmapFactory.Options options = new BitmapFactory.Options();
            					//options.inJustDecodeBounds = true;

            			        options.inSampleSize = 10;
            			        Log.d("mycompany.myapp", "imagePath v onClick je: " + imagePath);
            			        Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
            					//Bitmap bm = BitmapFactory.decodeFile(imagePath);
            		
            					selectedImage.setImageBitmap(bm);
            					
            					
            					//imageviewpublic.invalidate();
            			        //imageviewpublic.setImageBitmap(bm);
            				}
            			});
                    
                    // TUKI NEHAMO PEJSTAT!
                    
                    
                    
                    
                    globalAccess.horizontal.addView(imageviewpublic);
                    //horizontal.addView(imageviewpublic);
                    Log.d("mycompany.myapp", "indexOfChild v onPostExecute je: " + horizontal.indexOfChild(imageviewpublic));
                    //viewGroup.addView(selectedImage);
                    
                    
                    //LOCATION2
                    
                  }
            }
            else {
            	Log.d("mycompany.myapp", "Nismo v onPostExecute!");
            }
        }

        public Bitmap getBitmapFromFile(String filePath, int maxHeight,
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

        public int getSampleSize(BitmapFactory.Options options,
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

        public ImageView getImageView(Context context) {
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
    
	
	
}


