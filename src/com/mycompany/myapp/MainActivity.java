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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import android.widget.AdapterView.OnItemClickListener;

import com.mycompany.myapp.IntentIntegrator;
import com.mycompany.myapp.IntentResult;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
import java.util.*;
import android.provider.*;
import android.content.Context;
import android.content.res.Resources;
import java.io.*;
import java.text.*;
//import java.lang.Object;

import java.lang.reflect.Field;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.location.Location;

//import com.mycompany.myapp.FancyCoverFlow;
//import com.mycompany.myapp.FancyCoverFlowAdapter;
//import com.mycompany.myapp.R;

//import com.mycompany.myapp.ViewGroupExample;



public class MainActivity extends Activity implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener 
{
	
	private Button scanBtn;
	private Button takePicBtn;
	private TextView formatTxt, contentTxt;
	
	private static String logtag = "barcode";
	private Uri fileUri;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	private String storageState;
	
	ImageView selectedImage;  
	
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
private LocationRequest mLocationRequest;

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
	ArrayList<Bitmap> bitmapImages = new ArrayList<Bitmap>();
	// Acquire a reference to the system Location Manager
	////LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		storageState = Environment.getExternalStorageState();
		Log.d("mycompany.myapp","Storidž:" + storageState);
        
		
		//FancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());
		
		/*Parse.initialize(this, "DibF0HiaV6L8X709dfr9ogz5StQCKLGuO3F2Ph8I", "Yont4omFCs6auCQ2hSBPKyNAfvrazVymurkjN2lC");*/
		/*PushService.setDefaultPushCallback(this, MainActivity.class);*/
		/*PushService.subscribe(this, "majcka", MainActivity.class);*/
		ParseAnalytics.trackAppOpened(getIntent());
        
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		scanBtn.setOnClickListener(this);
		
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		
		takePicBtn = (Button)findViewById(R.id.takePic_button);
		
		takePicBtn.setOnClickListener(this);
		
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
											Environment.DIRECTORY_PICTURES), "ShopCo");									
		final File[] listFile = mediaStorageDir.listFiles();//get list of files
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

		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        selectedImage=(ImageView)findViewById(R.id.imageView1);
        gallery.setSpacing(1);
        gallery.setAdapter(new GalleryImageAdapter(this));
		
		
		
		//cekirajmo.servicesConnected();
		
		//Intent intent = new Intent(this, GooglePlayCheckActivity.class);
		//startActivity(intent);
		
//		scanBtn.setOnClickListener(this);
//		takePicBtn.setOnClickListener(this);
		
		checkPlayServices();
		
		mLocationClient.connect(); 
		
		// clicklistener for Gallery
		//gallery.setOnItemClickListener(new OnItemClickListener() {
        gallery.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					Toast.makeText(MainActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
					// show the selected Image
					//selectedImage.setImageResource(mImageIds[position]);
					String imagePath = listFile[position].getAbsolutePath();
					
					BitmapFactory.Options options = new BitmapFactory.Options();
			        options.inSampleSize = 6;
			        Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
					//Bitmap bm = BitmapFactory.decodeFile(imagePath);
		
					selectedImage.setImageBitmap(bm);
				}
			});
		
		
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

    }
   
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
	public void onClick(View v){

		//respond to clicks
		Log.d(logtag, "smo v onClick");
		
//		LocationListener locationListener = new LocationListener() {
//			public void onLocationChanged(Location location) {
//				// Called when a new location is found by the network location provider.
//				////////makeUseOfNewLocation(location);
//			}
//		};
		
		switch (v.getId()) {
			case R.id.scan_button:
				IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				//scan
				scanIntegrator.initiateScan();
				Log.d(logtag, "skeniramo!");
				break;
			case R.id.takePic_button:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
				// start the image capture Intent
				Log.d(logtag, "slikamo!");
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				//selectedImage.setImageURI(fileUri);
				break;	
					
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
		return true;
	} 

	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, this, 
											  REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
	
	
}


