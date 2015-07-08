package com.mycompany.myapp;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.*;
import android.os.*;
import android.os.AsyncTask.Status;
import android.view.*;
import android.widget.*;
import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

import com.mycompany.myapp.IntentIntegrator;
import com.mycompany.myapp.IntentResult;
import com.mycompany.myapp.app.Config;
import com.mycompany.myapp.helper.SQLiteHandler;
import com.mycompany.myapp.helper.SessionManager;
//import com.parse.GcmBroadcastReceiver;
//import com.mycompany.myapp.GalleryImageAdapter.PhotoBitmapTask;
//import com.parse.Parse;
//import com.parse.ParseAnalytics;
//import com.parse.ParseInstallation;
//import com.parse.PushService;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalService;
import com.suredigit.inappfeedback.FeedbackDialog;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.ActionBarActivity;

import java.util.*;

import android.provider.*;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.DatabaseUtils;

import java.io.*;
import java.text.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.location.LocationClient;
// Zaradi novih knjiznic:
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.plus.Plus;

import android.location.Location;

//import com.mycompany.myapp.FancyCoverFlow;
//import com.mycompany.myapp.FancyCoverFlowAdapter;
//import com.mycompany.myapp.R;

//import com.mycompany.myapp.ViewGroupExample;



//public class MainActivity extends FragmentActivity implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks,
public class MainActivity extends Activity implements OnClickListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, ConnectionCallbacks, OnConnectionFailedListener 
{
	
	private Button scanBtn;
	private Button takePicBtn;
	private Button followBtn;
	
	private Button loginBtn;
	private Button checkoutBtn;
	private TextView formatTxt, contentTxt;
	private TextView userLoggedTxt;
	public TextView itemDescriptionTxt;
	
	private Button btnFind;
	
	private static String logtag = "barcode";
	public Uri fileUri;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int SCAN_ACTIVITY_REQUEST_CODE = 0x0000c0de;  // to je kot v IntentIntegratorju
	private static final int LOGIN_ACTIVITY_REQUEST_CODE = 300;

	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	
	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	 private static final String KEY_IMAGE_PATH_URI = "image_path_uri";
	
	private String storageState;
	
	public boolean onlineMode = false;
	
	private SessionManager session;
	public SQLiteHandler db;
	
	GoogleCloudMessaging gcm;
	
	String regid;
	
	//private String serverURL = "https://192.168.1.100";
	// private String serverURL = "https://10.10.0.148";
	private String serverURL = Config.URL_MYSQL_SERVER;
	
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	static final String TAG = "mycompany.myapp";
	
	//static String mediaStorageDirString = mediaStorageDir.toString();
	
	double latitude = 0;
	double longitude = 0;
	
	static boolean scanPressed = false;
	static boolean takePicPressed = false;
	static boolean placeSelected = false;
	
	public BitmapFactory.Options moznosti;
	
	//Editor editor;
	
	// Global variable to hold the current location
    Location mCurrentLocation;
    
    LocationRequest locationRequest;
    //LocationClient locationClient;
    
    LocationListener locationListener;
    
    static LinearLayout horizontal;
    ImageView imageviewpublic;
	
	ImageView selectedImage;  
	ImageView selectedImage2;  
	ImageView itemPic;
	
	
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
   // List<List<String>> GooglePlacesList = new ArrayList<List<String>>();

    ArrayList<String> placeTypeStart = new ArrayList<String>();
    
    public static List<Integer> myIdList = new ArrayList<Integer>();
    
    public static ArrayList<Bitmap> bitmapsArray = new ArrayList<Bitmap>();
    
    public static ArrayList<View> viewArray = new ArrayList<View>();
    
    // PROVA
    
    private String personName;
    private String email;
    public AccountManager accMgr;
    public static final String PARAM_AUTHTOKEN_TYPE = "auth.token";
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;
    
    // PROVA
    
   //public static ImageView imageviewpublic;
    
    public ViewGroup viewGroup;
    
   // public boolean internetConn = false;
    
    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "440103715165";

	
   public  List<HashMap<String, String>> places = null;
   
   static public IntentResult scanResult;
   
   public String uniquePlaceId;
   
   public String imagePath;
   
   private View cellItem;  
   
   RelativeLayout relLayout;
   LinearLayout item;
   
   private LinearLayout parentLayout;  
   
   public PlacesTask placesTask;
   
// ItemFollow table name
   private static final String TABLE_ITEM_FOLLOW = "item_follow";
   
   public PhotoBitmapTask task = null;
   

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
	ItemFollow follow = new ItemFollow(this);
	UpdateLocalDb updLocalDb = new UpdateLocalDb(this);
	
	// A request to connect to Location Services
//private LocationRequest mLocationRequest;

// Stores the current instantiation of the location client in this object
//private LocationClient mLocationClient;
private GoogleApiClient mGoogleApiClient;
private GoogleApiClient mGoogleApiPlusClient;
	
private FeedbackDialog feedBack;

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
	//	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
	//	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmsssss").format(new Date());
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").toString();
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
	//	public static LinearLayout horizontal = null;
		public static LinearLayout horizontal = null;
		public static Context kontext = MainActivity.context;
	//	kontext 
	//	public static boolean databasesInSync = false;
		//public static ArrayList<Bitmap> bitmapsArray;
	    //public static int a;
	    //public static int b;
	}
	
	 // PayPal configuration
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(Config.PAYPAL_ENVIRONMENT).clientId(
                    Config.PAYPAL_CLIENT_ID);
 
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ArrayList<String> dbItems = null;
		
		feedBack = new FeedbackDialog(this,"AF-EB857D40E417-A6");
		
		// Session manager
        session = new SessionManager(getApplicationContext());
    //    session.setLogin(false);
		if (!session.isLoggedIn()) {
	//	if (db.getUserDetails().isEmpty()) {
			Intent loginIntent = new Intent(this, LoginActivity.class);
		//	MainActivity.this.startActivity(loginIntent);

			startActivityForResult(loginIntent, LOGIN_ACTIVITY_REQUEST_CODE);
		}
		setContentView(R.layout.main);
		
		/* mAccountAuthenticatorResponse =
	                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

	        if (mAccountAuthenticatorResponse != null) {
	            mAccountAuthenticatorResponse.onRequestContinued();
	        }*/
	        
	        
		// Starting PayPal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);
        
	        // PROVA
	        
	        
	       /* Account account = new Account("mitja_placer@yahoo.com", "com.mycompany.myapp");
	        accMgr = AccountManager.get(this);
            
            accMgr.addAccountExplicitly(account, "ernuvolo", null);  
            
            Log.d("AuthenticatorActivity", "Smo za addAccountExplicitly!");
  
            // Now we tell our caller, could be the Andreoid Account Manager or even our own application  
            // that the process was successful  
  
            final Intent intent = new Intent();  
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, "mitja_placer@yahoo.com");  
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, "com.mycompany.myapp");  
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, "com.mycompany.myapp");  
            setAccountAuthenticatorResult(intent.getExtras());  
            setResult(RESULT_OK, intent); */ 
	        
	        // PROVA KONEC
		
	//	 mAccountAuthenticatorResponse =
	//                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
		
		//PushService.setDefaultPushCallback(this, MainActivity.class);
		
		// Initializing google plus api client
        mGoogleApiPlusClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
		
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
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();
		
		
		//locationClient = new LocationClient(this, this, this);
	    locationRequest = new LocationRequest();
	    
	    LocationRequest.create();
	    // Use high accuracy
	    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	    // Set the update interval to 5 seconds
	    locationRequest.setInterval(2000);
	    // Set the fastest update interval to 1 second
	    locationRequest.setFastestInterval(1000);

	    // Session manager
  //      session = new SessionManager(getApplicationContext());
        
        // SQLite database handler
     //   db = new SQLiteHandler(getApplicationContext());
        db = SQLiteHandler.getInstance(getApplicationContext());
		// TUKI NEHAMO KOPIRAT
        
        
   //     db.deleteItems();  // TO JE SAMO ZA DEVELOPMENT!!!
		
	//	 mLocationClient = new LocationClient(this, this, this);
		 
		 moznosti = new BitmapFactory.Options();
		 
		 relLayout = new RelativeLayout(this);
		 item = new LinearLayout(this);
		 
		 parentLayout = (LinearLayout) findViewById(R.id.horizontal);  
		
		//FancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());
		
		//Parse.initialize(this, "DibF0HiaV6L8X709dfr9ogz5StQCKLGuO3F2Ph8I", "Yont4omFCs6auCQ2hSBPKyNAfvrazVymurkjN2lC");
		//PushService.setDefaultPushCallback(this, MainActivity.class);
		//PushService.subscribe(this, "majcka", MainActivity.class);
		////ParseAnalytics.trackAppOpened(getIntent());
		 
		// Initializing google plus api client
//	        mGoogleApiClient = new GoogleApiClient.Builder(this)
//	                .addConnectionCallbacks(this)
//	                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
//	                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
		 
		
		scanBtn = (Button)findViewById(R.id.scan_button);
		scanBtn.setOnClickListener(this);
		
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		
		takePicBtn = (Button)findViewById(R.id.takePic_button);
		
		takePicBtn.setOnClickListener(this);
		
		followBtn = (Button)findViewById(R.id.follow_button);
		
		followBtn.setOnClickListener(this);
		
		userLoggedTxt = (TextView)findViewById(R.id.user_logged_in);
		//userLoggedTxt.setText("Mitja Placer");
		
	//	itemDescriptionTxt = (TextView) cellItem.findViewById(R.id.item_description);
		
		
		
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
		
		checkoutBtn = (Button)findViewById(R.id.checkout_button);
		checkoutBtn.setOnClickListener(this);
        
		
 
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
	//	globalAccess.horizontal = (LinearLayout) findViewById(R.id.horizontal);
		globalAccess.horizontal = (LinearLayout) findViewById(R.id.horizontal);
        selectedImage = (ImageView)findViewById(R.id.imageView1);
   //     ImageView selectedImage2 = (ImageView)findViewById(R.id.imageView2);
        //gallery.setSpacing(1);
        //     selecimtedImagegallery.setAdapter(new GalleryImageAdapter(this));
   
   
    //    PhotoBitmapTask task = null;
		
		//ImageView i = new ImageView(this);
		//ViewGroup parentView = (ViewGroup)i.getParent();
        
    
    //    String[] Djukla = mediaStorageDir.list(null);
        //String mediaStorageDirString = mediaStorageDir.toString();
        
        ///// Gremo izpolnit places spinner pred updejtanjem baze, ker ce ne dela probleme
        //
        
 Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        
        // TUKI KOPIRAMO
        
       // Location location = mLocationClient.getLastLocation();
        if (location == null)
        	Toast.makeText(this,"Location unavailable :(",Toast.LENGTH_SHORT).show();
           // mLocationClient.requestLocationUpdates(locationRequest, locationListener);
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
                placesTask = new PlacesTask();
               
                // Invokes the "doInBackground()" method of the class PlaceTaskd
                placesTask.execute(sb.toString());
               
            // }
        // });

     // Tu smo nehali dodajat!
        }   
        
    //    db.deleteUsers();		// TO JE SAMO ZA DEVELOPMENT!!!
        
        
        //
        //// KONEC dodajanja za izpolnit places spinner
        
        // Gremo zamenjat to, da gledamo use fajle u direktoriju s tem, da gledamo use
        // itemse u SQLite lokalni bazi
        
        
        /*
         *  Updejtajmo lokalno bazo z itemsi iz serverja
         */
        if (follow.setUpHttpsConnection(serverURL) != null && (session.isLoggedIn()) && db.getRowCount() != 0) {
			onlineMode = true;
			final ArrayList<NameValuePair> params_upd = new ArrayList<NameValuePair>();
			params_upd.add(new BasicNameValuePair("tag", "updateLocalDb"));
			Log.d("OnCreate", "getUserDetails je: " + db.getUserDetails().get("uid").toString());
			params_upd.add(new BasicNameValuePair("uid", db.getUserDetails().get("uid").toString()));
			dbItems = db.getAllItems();
			Log.d("mycompany.myapp", "dbItems v onCreate je: " + dbItems);
			int i = 0;
			//while (i  < dbItems.size()) {
			//	params_upd.add(new BasicNameValuePair(String.valueOf(i), dbItems.get(i)));
			//	String query = "item_unique_id[" + String.valueOf(i) + "]";
			//	params_upd.add(new BasicNameValuePair("item_unique_id[]", dbItems.get(i)));
			if (!dbItems.isEmpty()) {
				for(String value: dbItems){
					// nameValuePairs.add(new BasicNameValuePair("items[]",value));
					params_upd.add(new BasicNameValuePair("item_unique_id[]", value));
				}
			} else {
				params_upd.add(new BasicNameValuePair("item_unique_id", "empty"));
			}
			//params_upd.add(new BasicNameValuePair("item_unique_id", dbItems.toString()));
				//follow.Follow(db.getUserDetails().get("uid"), scanResult.getContents(), uniquePlaceId, fileUri, item_followed_at);
			//	follow.Follow(db.getUserDetails().get("uid"), db.getItemDetails(i).get("barcode"), db.getItemDetails(i).get("unique_place_id"), Uri.parse(db.getItemDetails(i).get("image_path_uri")), db.getItemDetails(i).get("created_at"));
				i++; 
			//}
			updLocalDb.Update(params_upd);
			//updLocalDb.closeConnection();
		}
		else {
			onlineMode = false;
		}
        
       //redrawGallery();
        
      /*  Integer NumberOfItems = db.getItemRowCount();
        for (int j = 0; j < NumberOfItems; j++) {
        	Log.d("mycompany.myapp", "Stevilo itemsov v lokalni bazi je : " + NumberOfItems);
        	task = new PhotoBitmapTask(this, globalAccess.horizontal);
			task.execute(j);
			Log.d("mycompany.myapp", "Drawables index j:" + j);
        }*/
        
        
       /* ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
        
        for (int j = 0; j < listFileAL.size(); j++) {
			//task = new DownloadAsyncTask(mContext, parentView, listFile);
			//Log.d("mycompany.myapp", "listFile.length je dolg: " + listFile.length);
			Log.d("mycompany.myapp", "listFileAL je dolg: " + listFileAL.size());
			task = new PhotoBitmapTask(this, globalAccess.horizontal, listFileAL);
			task.execute(j);
			Log.d("mycompany.myapp", "Drawables index j:" + j);
			
        }*/
		
		
		
		//cekirajmo.servicesConnected();
		
		//Intent intent = new Intent(this, GooglePlayCheckActivity.class);
		//startActivity(intent);
		
//		scanBtn.setOnClickListener(this);
//		takePicBtn.setOnClickListener(this);
		
        if (checkPlayServices()) {
		
        	gcm = GoogleCloudMessaging.getInstance(this);
        	//regid = getRegistrationId(context);
        	regid = getRegistrationId(getApplicationContext());
        	Log.d("mycompany.myapp", "CGM Registration ID je: " + regid);

        	if (regid.isEmpty()) {
        		registerInBackground();
        	}
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
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
//					Log.e("NaÅ¡e slike - Image: "+i+": path", listFile[i].getAbsolutePath());
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
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
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
    	 
    	if (session.isLoggedIn()) {
    		 // Display the login status
            Toast.makeText(this, "Logged in.", Toast.LENGTH_SHORT).show();
    		//db.getUserDetails();
    		loginBtn.setVisibility(View.GONE);
    //		logoutBtn.setVisibility(View.VISIBLE);
    		if (!db.getUserDetails().isEmpty()) {
    			userLoggedTxt.setText("Logged in as: ".concat(db.getUserDetails().get("name")).toString());
        		Log.d("mycompany.myapp", "db.getUserDetails() je: " + db.getUserDetails().get("name").toString());
    		}
    	} else {
    		loginBtn.setVisibility(View.VISIBLE);
    //		logoutBtn.setVisibility(View.GONE);
    	}
    	
    	// Nardimo postopek za updejtanje lokalne in server baze, ce smo online:
    	
    	// KONEC:  Nardimo postopek za updejtanje lokalne in server baze, ce smo online
        
        //do {} while (mLocationClient.getLastLocation() == null);
        // do {mCurrentLocation = mLocationClient.getLastLocation();}
        //while (mCurrentLocation == null);
        
        // Tuki provamo z novimi ukazi:
        
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        
        // TUKI KOPIRAMO
        
       // Location location = mLocationClient.getLastLocation();
        if (location == null)
        	Toast.makeText(this,"Location unavailable :(",Toast.LENGTH_SHORT).show();
           // mLocationClient.requestLocationUpdates(locationRequest, locationListener);
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
                placesTask = new PlacesTask();
               
                // Invokes the "doInBackground()" method of the class PlaceTaskd
                placesTask.execute(sb.toString());
               
            // }
        // });

     // Tu smo nehali dodajat!
        }   
    }
    
    @Override
    public void onLocationChanged(Location location) {
      //  mLocationClient.removeLocationUpdates(this);
    	// TO ZGORAJ SMO ZAKOMENTIRALI Z ZAMENJAVO STAREGA APIja!  ...lahko bo treba kej zamenjat
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
	
   /* protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }*/
    
	@Override
	protected void onResume() {
	//	scanPressed = false;
		super.onResume();
		ArrayList<String> NotSyncedItems = null;
		
		checkPlayServices();
		if (scanPressed == true && takePicPressed == true && session.isLoggedIn()) {
			followBtn.setEnabled(true);
		}
		//mLocationRequest = LocationRequest.create();
		//mLocationClient.connect(); //TA TUKAJ DELA KAZIN!!!
	//	mGoogleApiClient.connect();
		mGoogleApiClient.reconnect();
		
		if (follow.setUpHttpsConnection(serverURL) != null) {
			onlineMode = true;
			NotSyncedItems = db.getNotSyncedItems();
			int i = 0;
			while (i  < NotSyncedItems.size()) {
			//	uid_string = db.getItemDetails(i).get("uid");
				//follow.Follow(db.getUserDetails().get("uid"), scanResult.getContents(), uniquePlaceId, fileUri, item_followed_at);
				follow.Follow(db.getUserDetails().get("uid"), db.getItemDetails(i).get("barcode"), db.getItemDetails(i).get("unique_place_id"), Uri.parse(db.getItemDetails(i).get("image_path_uri")), db.getItemDetails(i).get("created_at"));
				i++; 
			}
			follow.closeConnection();
		}
		else {
			onlineMode = false;
		}
		parentLayout.removeAllViews();
		redrawGallery();
		Log.d(TAG, "Smo u MainActivity u onResume!");
		if (session.isLoggedIn()) {
   		 // Display the login status
           Toast.makeText(this, "Logged in.", Toast.LENGTH_SHORT).show();
   		//db.getUserDetails();
   		loginBtn.setVisibility(View.GONE);
   	//	logoutBtn.setVisibility(View.VISIBLE);
   		if (!db.getUserDetails().isEmpty()) {
   			userLoggedTxt.setText("Logged in as: ".concat(db.getUserDetails().get("name")).toString());
       		Log.d("mycompany.myapp", "db.getUserDetails() je: " + db.getUserDetails().get("name").toString());
   		}
		} else {
			loginBtn.setVisibility(View.VISIBLE);
			//logoutBtn.setVisibility(View.GONE);
		}
	  //  Integer NumberOfItems = db.getItemRowCount();
	    
	    
	//    NumberOfItems = 0;
    /*    for (int j = 0; j < NumberOfItems; j++) {
        	Log.d("mycompany.myapp", "Stevilo itemsov v lokalni bazi je : " + NumberOfItems);
        	if (NumberOfItems == 0) {
      			break;
      		}
        	task = new PhotoBitmapTask(this, globalAccess.horizontal);
			task.execute(j);
			Log.d("mycompany.myapp", "Drawables index j:" + j);
        }*/
	
	// Postopek za updejtanje baz, ce smo online:
/////	if (follow.setUpHttpsConnection(serverURL) != null && globalAccess.databasesInSync == false) {
		
				
/////	}
	
	// KONEC: Postopek za updejtanje baz, ce smo online
	
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
// * Called when the Activity is in pause.
//  */
	public void onPause() {
	    super.onPause();
	    feedBack.dismiss();
	    mGoogleApiClient.disconnect();
	}
	// * Called when the Activity is no longer visible.
    //  */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
      //  mLocationClient.disconnect();
    	super.onStop();
    	 if (mGoogleApiClient.isConnected()) {
        mGoogleApiClient.disconnect();    
    	 }
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
			scanPressed = true;
			if (scanPressed == true && takePicPressed == true) {
				followBtn.setEnabled(true);
			}
			
		} else if (id == R.id.takePic_button) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			// start the image capture Intent
			Log.d(logtag, "slikamo!");
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			takePicPressed = true;
			if (scanPressed == true && takePicPressed == true) {
				followBtn.setEnabled(true);
			}
		} else if (id == R.id.follow_button) { 
			  placesTask.getStatus();
			  if (Status.FINISHED != null) {
				  Log.d(logtag, "Follow item!");
				  Log.d(logtag, "scanResult.getContents() je: " + scanResult.getContents());
				  Log.d(logtag, "uniquePlaceId je: " + uniquePlaceId);

				  //  PlacesTask placesTask = new PlacesTask();

				  if (!placeSelected) {
					  Log.d(logtag, "places pri follow_button je:" + places.get(0).toString());
					  uniquePlaceId = places.get(0).get("place_id");
				  }
				 // Log.d(logtag, "imagePath v MainActivity je: " + imagePath);

				  //	barcode, shop_unique_id, local_image_path in created_at,
				  //	(String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id) {
				  // TA VRSTICA SPODAJ JE SAMO ZA DEVELOPMENT - JE TREBA ZAKOMENTIRAT NA KONCU!
				//  db.deleteItems();

				  // dodamo item v lokalno bazo, ce ga nimamo ze notri
				  switch (db.getItemRowCount(scanResult.getContents(), uniquePlaceId)) {
				  	case 0:
						String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").toString();
				//	  int index = (int) db.addItem(scanResult.getContents(), "NO NAME", "NO NAME", "NO NAME", "NOW()", 0.0, 0, "NO NAME", uniquePlaceId, fileUri.getEncodedPath(), 1);
				  	  int index = (int) db.addItem(scanResult.getContents(), "NO NAME", "NO NAME", "NO NAME", 0.0, "NO NAME", 0, "NO NAME", uniquePlaceId, timeStamp, fileUri.getEncodedPath(), 1);
					  db.getItemRowCount();
					  Log.d(logtag, "index v db je: " + index);
					  String item_followed_at = db.getItemDetails(index).get("created_at");
					  Log.d("R.id.follow_button", "item_followed_at v R.id.follow_button je: " + item_followed_at);
					  String fileUri_string = db.getItemDetails(index).get("image_path_uri");
					  Log.d("R.id.follow_button", "image_path_uri v R.id.follow_button je: " + fileUri_string);
					//  Log.d("R.id.follow_button", "fileUri.getEncodedPath() v R.id.follow_button je: " + fileUri.getEncodedPath());
					  // ce smo online, shranimo item na server
				//	  globalAccess.databasesInSync = false;
					  
					  if (follow.setUpHttpsConnection(serverURL) != null) {
						  onlineMode = true;
						  follow.Follow(db.getUserDetails().get("uid"), scanResult.getContents(), uniquePlaceId, fileUri, item_followed_at);
				//		  follow.Follow(db.getUserDetails().get("uid"), scanResult.getContents(), uniquePlaceId, fileUri);
						  // "globalAccess.databasesInSync = true" naredimo v zgornji vrstici v follow.Follow()
						 follow.closeConnection(); 
					  }
					  followBtn.setEnabled(false);
					  break;
				  	case 1:
				  	  Toast.makeText(this, "Item has been followed already!", Toast.LENGTH_SHORT).show();
				  	  break;
				  } 
			  } else {
				  Toast.makeText(this, "Please wait a moment. Looking for shop location...", Toast.LENGTH_SHORT).show();
			  }
			 
		} else if (id == R.id.login_button) { 
			Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
			MainActivity.this.startActivity(loginIntent);
	//	} else if (id == R.id.spr_place_type) {
	//		mSprPlaceType.
	//		String spinnerText = mSprPlaceType.getSelectedItem().toString();
	//		Log.d(logtag, "Spinner rext je:" +spinnerText);
		} else if (id == R.id.checkout_button) { 
			/*session.setLogin(false);
			// Clearing all data from Shared Preferences
	       // editor.clear();
	        //editor.commit();
	        db.deleteUsers();
	    //    signOutFromGplus();
			Intent loginIntent = new Intent(this, LoginActivity.class);
			
			
			String trueVar = "true";
			loginIntent.putExtra("logoutFlag", trueVar);
			//MainActivity.this.startActivity(loginIntent);
			
			// PROVA
			
			startActivity(loginIntent);*/
			//startActivityForResult(loginIntent, LOGIN_ACTIVITY_REQUEST_CODE);
			//finish();
			
		/*	
			 mAccountAuthenticatorResponse =
		                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

		        if (mAccountAuthenticatorResponse != null) {
		            mAccountAuthenticatorResponse.onRequestContinued();
		        }
			
			 String accountType = "com.mycompany.myapp";
         	// String KEmail = "bla@hoho.com";
         	 
         	 Log.d("LoginActivity", "getApplicationInfo je: " +getApplicationInfo().toString());
              final Account account = new Account(mEmail, accountType);
              
         	// Account account = new Account(KEmail, accountType);
              accMgr = AccountManager.get(getApplicationContext());
             
              accMgr.addAccountExplicitly(account, mPassword, null);  
              Log.d("AuthenticatorActivity", "Smo za addAccountExplicitly!");
    
              // Now we tell our caller, could be the Andreoid Account Manager or even our own application  
              // that the process was successful  
    
              final Intent intent = new Intent();  
              intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mEmail);  
              intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);  
              intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);  
              setAccountAuthenticatorResult(intent.getExtras());  
              setResult(RESULT_OK, intent);  
              finishAuth(); 
			*/
			// PROVA KONEC
			
		}
		
		
		/*if(v.getId()==R.id.scan_button){
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//scan
			scanIntegrator.initiateScan();
		}*/
	}
	
	 /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        
        case R.id.action_about:
        	Intent aboutIntent = new Intent(this, AboutActivity.class);
        	
       // 	 mGoogleApiClient.disconnect();    
				  startActivity(aboutIntent);
			 
        	return true;
        case R.id.action_logout:
            // logout action
        	session.setLogin(false);
			// Clearing all data from Shared Preferences
	       // editor.clear();
	        //editor.commit();
	        db.deleteUsers();
	    //    signOutFromGplus();
			Intent loginIntent = new Intent(this, LoginActivity.class);
			
			
			String trueVar = "true";
			loginIntent.putExtra("logoutFlag", trueVar);
			//MainActivity.this.startActivity(loginIntent);
			
			// PROVA
			
			startActivity(loginIntent);
            return true;
        case R.id.action_feedback:
            // leave feedback
        	feedBack.show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
       // scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        
        Log.d("onActivityResult", "requestCode je: " +requestCode);
        
        switch(requestCode) {     
        //if (requestCode == SCAN_ACTIVITY_REQUEST_CODE) {
        case SCAN_ACTIVITY_REQUEST_CODE: {
        	scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        	if(scanResult != null) {
        		Log.d(logtag, "able to set the text");

        		String scanContent = "Scan Content: ";
        		String scanFormat = "Scan Format: ";
        		//	String scanPrefix = "ShopCo_";

        		scanContent += scanResult.getContents();
        		scanFormat += scanResult.getFormatName();

        		// Log.d(logtag, "able to set the text");

        		// put the results to the text
        		contentTxt.setText(scanContent);
        		formatTxt.setText(scanFormat);

        		//formatTxt.setText("FORMAT: " + scanFormat);
        		//contentTxt.setText("CONTENT: " + scanContent);

        		Log.d(logtag, scanResult.getContents());
        		// PushService.subscribe(this, scanPrefix + scanResult.getContents(), MainActivity.class);

        	} else {
        		//   Toast.makeText(MainActivity.this, "we didn't get anything back from the scan", Toast.LENGTH_SHORT).show();
        	}
        	break;
        }
        // if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE: {
        	if (resultCode == RESULT_OK) {
        		Log.d(logtag, "Smo slikali!");
        		// Image captured and saved to fileUri specified in the Intent
        		Toast.makeText(this, "Image saved :-)", Toast.LENGTH_LONG).show();
        		selectedImage.setImageURI(fileUri);
        	} else if (resultCode == RESULT_CANCELED) {
        		// User cancelled the image capture

        	} else {
        		// Image capture failed, advise user
        		Toast.makeText(MainActivity.this, "image capture failed", Toast.LENGTH_SHORT).show();
        	}
        break;
        }
        case LOGIN_ACTIVITY_REQUEST_CODE: {
        	
        	Log.d(logtag, "Smo prisli iz LoginActivity v MainActivity izven if!");
        	if (resultCode == RESULT_OK) {
        		//finishActivity(LOGIN_ACTIVITY_REQUEST_CODE);
        		/// Login with Google+
        		Log.d(logtag, "Smo prisli iz LoginActivity v MainActivity: brez Google+!");
        		
        		// PROVA
        		 String mEmail = intent.getStringExtra("mEmail");
        		 String mPassword = intent.getStringExtra("mPassword");
        		
   			/* mAccountAuthenticatorResponse =
   		                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

   		        if (mAccountAuthenticatorResponse != null) {
   		            mAccountAuthenticatorResponse.onRequestContinued();
   		        }
   			*/
   			 String accountType = "com.mycompany.myapp";
            	// String KEmail = "bla@hoho.com";
            	 
            	 Log.d("LoginActivity", "getApplicationInfo je: " +getApplicationInfo().toString());
                 final Account account = new Account(mEmail, accountType);
                 
            	// Account account = new Account(KEmail, accountType);
                 accMgr = AccountManager.get(this);
                
                 
               
                 
                 
                 accMgr.addAccountExplicitly(account, mPassword, null);  
                 Log.d("AuthenticatorActivity", "Smo za addAccountExplicitly!");
       
                 // Now we tell our caller, could be the Andreoid Account Manager or even our own application  
                 // that the process was successful  
       
                 final Intent intentAuth = new Intent();  
                 intentAuth.putExtra(AccountManager.KEY_ACCOUNT_NAME, mEmail);  
                 intentAuth.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);  
                 intentAuth.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);  
                 this.setAccountAuthenticatorResult(intentAuth.getExtras());  
                 this.setResult(RESULT_OK, intentAuth);  
                 this.finishAuth(); 
   			
        		
        		// PROVA KONEC
        	}
        	
        	else if (resultCode == RESULT_CANCELED) {
    		// Login without Google+
        		Log.d(logtag, "Smo prisli iz LoginActivity v MainActivity: z Google+!");
        	}
        break;
        }
        }

	/*	if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
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
		}*/
		
      
    }
	
	void googlePlacesList(List<HashMap<String,String>> hmPlace) {
//		final int PLACE_ROW = 0;
	//	final int ID_ROW = 1;
		GooglePlacesList.clear();
		//ArrayAdapter<String> arrayAdapter;
    	for(int i=0;i<hmPlace.size();i++){
    		HashMap<String, String> hmGooglePlace = hmPlace.get(i);
    		String Google_place_name = hmGooglePlace.get("place_name");
    		String Google_place_ID = hmGooglePlace.get("place_id");
    		Log.i("Kraj v googlePlacesList", Google_place_name);
    		Log.i("Place ID v googlePlacesList je: ", Google_place_ID);
    		GooglePlacesList.add(Google_place_name);
    	//	GooglePlacesList.set(index1).add(index2)
   // 		GooglePlacesList.get(PLACE_ROW).add(Google_place_name);
    //		GooglePlacesList.get(ID_ROW).add(Google_place_ID);
    //		listOfLists.add(new ArrayList<String>());
    //		List<List<String>> GooglePlacesList = new ArrayList<List<String>>();
    	}
    //	Log.i("Kraji v googlePlacesList", GooglePlacesList.get(PLACE_ROW).toString());
    	
    	 mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
    	
    	placeTypeStart.clear();
    	Log.i("PlaceTypeStart pred clear", placeTypeStart.toString());
    	placeTypeStart.addAll(GooglePlacesList);
    //	placeTypeStart.addAll(GooglePlacesList.get(PLACE_ROW));
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
//            internetConn = false;
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
           if (result != null) {
        	   ParserTask parserTask = new ParserTask();
 
            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
           } else {
       		Toast.makeText(MainActivity.this, "Internet unreachable", Toast.LENGTH_SHORT).show();
           }
        }
 
    }
	
	
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        JSONObject jObject;
        // List<String> GooglePlacesList;
 
        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
 
          //  List<HashMap<String, String>> places = null;
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
               
                // Getting unique id
                String uniquePlaceId = hmPlace.get("place_id");
                Log.i("Place ID je: ", uniquePlaceId);
              //  Log.i("hmPlace je: ", hmPlace.toString());
 
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
        public int data;
        private String fullPath = mediaStorageDirString;
        //static int[] myIdList = {};
        // List<Integer> myIdList = new ArrayList<Integer>();
        //myIdList = new ArrayList<Integer>();
        //public ImageView imageviewpublic;

        //public PhotoBitmapTask(Context context, ViewGroup parent, ArrayList<String> images) {
        //public PhotoBitmapTask(Context context, LinearLayout parent, ArrayList<String> images) {
      //  public PhotoBitmapTask(Context context, LinearLayout horizontal, ArrayList<String> images) {
        public PhotoBitmapTask(Context context, LinearLayout horizontal) {
        	super();

        	this.context = context;
        	//this.parent = new WeakReference<ViewGroup>(parent);
        	//this.parent = parent;
        	this.horizontal = horizontal;
        	//   this.images = images;
        	//this.data = 0;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
        	this.data = params[0];

        	Bitmap outputImage = null; // Tuki bi lahko dali kakšno sliko z napisanim recimo Error occurred...
        	Log.d("mycompany.myapp", "params[0] v doInBackground je " + params[0]);
         //   Log.d("mycompany.myapp", "images length v doInBackground je " + images.size());
         //   Log.d("mycompany.myapp", "images v doInBackground je " + images.get(params[0]));
            //Log.d("mycompany.myapp", "images v doInBackground je " + images.get(0));
            //return getBitmapFromFile(images.get(params[0]), 600, 600);
       //     fullPath = fullPath.concat("/");
       //     fullPath = fullPath.concat(images.get(params[0]));
            //return getBitmapFromFile(images.get(params[0]), 300, 300);
        //    return getBitmapFromFile(fullPath, 200, 200);
          //  follow.getPath(context, fileUri);
        ///	Log.i("fileUri je: ",  follow.getPath(context, fileUri));
        //    try {
            //	fileUri = db.getItemDetails(data).get("image_path_uri"))
            //	Log.d("PhotoBitmapTask", "image_path_uri je: " + db.getItemDetails(data).get("image_path_uri"));
            	Log.d("PhotoBitmapTask", "image_path_uri v PhotoBitmapTask je: " + db.getItemDetailsByIndex(data).get("image_path_uri"));
            //	fileUri = Uri.parse(db.getItemDetails(data).get("image_path_uri"));
            	fileUri = Uri.parse(db.getItemDetailsByIndex(data).get("image_path_uri"));
            	Log.d("PhotoBitmapTask", "encoded uri path v PhotoBitmapTask je: " + fileUri.getEncodedPath());
            	
            //	itemDescriptionTxt.setText("" + db.getItemDetails(data).get("item_brand"));	
            //	InputStream image_stream = getContentResolver().openInputStream(fileUri);

            //	outputImage = BitmapFactory.decodeStream(image_stream );
            	outputImage = BitmapFactory.decodeFile(db.getItemDetailsByIndex(data).get("image_path_uri"));
           // 	outputImage = getBitmapFromUri(fileUri);
          //  	Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
          //  	outputImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(),fileUri);
            	
            //	outputImage =  MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), fileUri);
		/*	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  Log.d("Exception v outputImage: ",e.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				  Log.d("Exception v outputImage: ",e.toString());
			}*/
            
            return outputImage;
        //    MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
          /////  return getBitmapFromFile(fullPath, 200, 200);
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
            ///////       	imageviewpublic = getImageView(context);
                    //imageView.setImageBitmap(result);
              ///  	 imageviewpublic.setImageBitmap(result);
             //////   	 imageviewpublic.setImageBitmap(Bitmap.createScaledBitmap(result, 200, 200, false));
                //	 cellItem = getLayoutInflater().inflate(R.layout.cell_item, null);
                	 cellItem = getLayoutInflater().inflate(R.layout.cell_item, parentLayout, false);
                	 itemDescriptionTxt = (TextView) cellItem.findViewById(R.id.item_description);
                    
                    int uniqueID = data;
//                    imageView.getId();
                    
                    //Log.d("mycompany.myapp", "imageView.getId pred spreminjanjem v onPostExecute je: " +imageView.getId());
            ///////        Log.d("mycompany.myapp", "imageView.getId pred spreminjanjem v onPostExecute je: " +imageviewpublic.getId());
                    //imageView.setId(uniqueID);
            ///////        imageviewpublic.setId(uniqueID);
                    cellItem.setId(uniqueID);
                    //imageView.setTag(uniqueID);
           ////////         imageviewpublic.setTag(uniqueID);
                    cellItem.setTag(uniqueID);
                    //Log.d("mycompany.myapp", "imageView.getId po spreminjanju v onPostExecute je: " +imageView.getId());
 ////////                   Log.d("mycompany.myapp", "imageView.getId po spreminjanju v onPostExecute je: " +imageviewpublic.getId());
                   // imageView.getId();
                    Log.d("mycompany.myapp", "uniqueID v onPostExecute je: " +uniqueID);
                    //myImage.setId(uniqueID);
                    //myIdList.add(uniqueID);
                    myIdList.add(uniqueID);
                    //globalAccess.bitmapsArray.add(result);
                //    bitmapsArray.add(result);
                    bitmapsArray.add(Bitmap.createScaledBitmap(result, 400, 400, true));
                    Log.d("mycompany.myapp", "myIdList v onPostExecute je: " +myIdList);
                    //imageviewpublic = imageView;
                   // viewGroup.addView(imageView);
                    //viewGroup.addView(imageviewpublic);
                    //horizontal = viewGroup;
                    
                    
                    // TUKI PEJSTAMO!
                    
            		final int index = data;
                    //globalAccess.horizontal.setOnClickListener(new OnClickListener() {
            		mSprPlaceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							placeSelected = true;
							String spinnerText = mSprPlaceType.getSelectedItem().toString();
							uniquePlaceId = places.get(position).get("place_id");
							Log.d(logtag, "Izbran place_ID je: " +uniquePlaceId);
							Log.d(logtag, "Spinner text je: " +spinnerText);
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
            		});
           // 		imageviewpublic.setOnClickListener(new OnClickListener() {
             		cellItem.setOnClickListener(new OnClickListener() {
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
                    				BigDecimal itemPrice_BigDecimal = new BigDecimal(db.getItemDetailsByIndex(data).get("item_price"));
                    				BigDecimal itemDiscount_BigDecimal = new BigDecimal(db.getItemDetailsByIndex(data).get("item_discount"));
                    				//BigDecimal discountedPrice = itemPrice_BigDecimal * ((100 - itemDiscount_BigDecimal)/100);
                    				BigDecimal discountedPrice = new BigDecimal(100).subtract(itemDiscount_BigDecimal);
                    				discountedPrice = discountedPrice.divide(new BigDecimal(100));
                    				discountedPrice = itemPrice_BigDecimal.multiply(discountedPrice);
                    			
                    				String text_item_title =  "<font color='#F0F8FF'>" + db.getItemDetailsByIndex(data).get("item_brand") + " " 
                     						+ db.getItemDetailsByIndex(data).get("item_name") + "</font>" + "<br>";
                     
                    				String text_item_details =  "<font color='#F0F8FF'>" + db.getItemDetailsByIndex(data).get("item_description") + "</font>"
                     						+ "<br>" 
                     						+ "<font color='#FFFFFF'>" + db.getItemDetailsByIndex(data).get("item_price") 
                     						+ db.getItemDetailsByIndex(data).get("item_currency") + "</font>" + "<br>" + "<font color='#FF1A10'> <b>"
                     						+ db.getItemDetailsByIndex(data).get("item_discount") + "% OFF" + "</b> </font>" + "<font color='#F0F8FF'>" + "&#8680" + "</font>" + "<font color='#FF1A10'> <b>" + discountedPrice.toString() 
                     						+ db.getItemDetailsByIndex(data).get("item_currency") + "</b> </font>";
                    				
                    				int item_descriptionLength = db.getItemDetailsByIndex(data).get("item_description").length();
                    				int item_priceLength = db.getItemDetailsByIndex(data).get("item_price").length();
                    				int item_currencyLength = db.getItemDetailsByIndex(data).get("item_currency").length();
                    				
                    				Intent Intent_ItemShow = new Intent(MainActivity.this, ItemShowActivity.class);
                    				Intent_ItemShow.putExtra(EXTRA_MESSAGE, index);
                    				Intent_ItemShow.putExtra("item_title", text_item_title);
                    				Intent_ItemShow.putExtra("item_details", text_item_details);
                    				Intent_ItemShow.putExtra("item_image_path", db.getItemDetailsByIndex(data).get(KEY_IMAGE_PATH_URI));
                    				Intent_ItemShow.putExtra("item_descriptionLength", item_descriptionLength);
                    				Intent_ItemShow.putExtra("item_priceLength", item_priceLength);
                    				Intent_ItemShow.putExtra("item_currencyLength", item_currencyLength);
             
                    				MainActivity.this.startActivity(Intent_ItemShow);
                    				
                    				
                    				
                    				
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
           /////// 					Toast.makeText(MainActivity.this, "Your selected position = " + index, Toast.LENGTH_SHORT).show();
            					// show the selected Image
            					//selectedImage.setImageResource(mImageIds[position]);
            					//Log.d("mycompany.myapp", "indexOfChild v onClick je: " + globalAccess.horizontal.indexOfChild(v));
            					Log.d("mycompany.myapp", "viewID of selected position v onClick je: " + viewID);
            					
            					String[] Djukla = mediaStorageDir.list(null);
            					 ArrayList<String> listFileAL = new ArrayList<String>(Arrays.asList(Djukla));
            					//String imagePath = listFile[position].getAbsolutePath();
            					//String imagePath = listFileAL.get(position);
            					//String imagePath = (mediaStorageDir.getAbsolutePath());
            //					imagePath = (mediaStorageDir.getAbsolutePath());
            					
            					//imagePath = imagePath.concat(mediaStorageDir.getAbsolutePath());
            //					imagePath = imagePath.concat("/");
            //					imagePath = imagePath.concat(listFileAL.get(index));
            					
            					
            					imagePath = db.getItemDetailsByIndex(data).get(KEY_IMAGE_PATH_URI);
            					
            					
            /////////					BitmapFactory.Options options = new BitmapFactory.Options();
            					//options.inJustDecodeBounds = true;

            //			        options.inSampleSize = 10;
            			        Log.d("mycompany.myapp", "imagePath v onClick je: " + imagePath);
            ////////			        Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
            					//Bitmap bm = BitmapFactory.decodeFile(imagePath);
            		
            	////////				selectedImage.setImageBitmap(bm);
            					//itemDescriptionTxt.setText("Da vidimo, kam pišemo!");
            					
            					
            					//imageviewpublic.invalidate();
            			        //imageviewpublic.setImageBitmap(bm);
            				}
            			});
                    
                    // TUKI NEHAMO PEJSTAT!
                    
            		//itemDescriptionTxt.setText("Da vidimo, kam pišemo!\nSmo u drugi vrstici!");
             		//itemDescriptionTxt.setText("" + db.getItemDetails(data).get("item_brand"));
             		// itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("item_brand"));
             		//	itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("item_name"));
             		//	itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("description"));
             		//	itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("item_price"));
             		//	itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("item_discount"));
             		//	itemDescriptionTxt.setText("" + db.getItemDetailsByIndex(data).get("flag_item_follow"));
             	//	String text = db.getItemDetailsByIndex(data).get("item_brand") + "<font color='red'>red</font>";
             		//String text =  "<font color='#CCE6FF'>" + db.getItemDetailsByIndex(data).get("item_brand") + "</font>";
             		String text =  "<font color='#F0F8FF'>" + db.getItemDetailsByIndex(data).get("item_brand") + " " 
             						+ db.getItemDetailsByIndex(data).get("item_name") + "</font>" + "<br>" 
             						+ "<font color='#FFFFFF'>" + "<b>" + db.getItemDetailsByIndex(data).get("item_price") 
             						+ db.getItemDetailsByIndex(data).get("item_currency") + "</b> </font>";
             	//	String text = "This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.";
             	//	textView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
             		itemDescriptionTxt.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
             		itemDescriptionTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                  //  item.addView(itemDescriptionTxt);
                  //  item.addView(imageviewpublic);
                    
                 //   globalAccess.horizontal.addView(imageviewpublic);
            	
             //       globalAccess.horizontal.addView(imageviewpublic);
              
            		//itemPic.addView(imageviewpublic);
            		itemPic = (ImageView) cellItem.findViewById(R.id.item_pic);
            		//itemPic.setImageBitmap(result);
            		itemPic.setImageBitmap(Bitmap.createScaledBitmap(result, 400, 400, true));
            		//itemPic.setImageResource(images[i]); 
            	      // globalAccess.horizontal.addView(cellItem);
            		//if(cellItem.getParent()!=null) {
            		 //  ((ViewGroup)cellItem.getParent()).removeView(cellItem); // <- fix
            		//}
            	//	cellItem = getLayoutInflater().inflate(R.layout.cell_item, null);
            		
            		//itemDescriptionTxt = new TextView(this);
            //		itemDescriptionTxt = (TextView) cellItem.findViewById(R.id.item_description);  
            		
            		parentLayout.addView(cellItem);
                    
                    //horizontal.addView(imageviewpublic);
                //    Log.d("mycompany.myapp", "indexOfChild v onPostExecute je: " + horizontal.indexOfChild(imageviewpublic));
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

  //      @TargetApi(Build.VERSION_CODES.LOLLIPOP)
		public ImageView getImageView(Context context) {
            // width and height
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    //LinearLayout.LayoutParams.WRAP_CONTENT,
            		LinearLayout.LayoutParams.MATCH_PARENT,
                    //LinearLayout.LayoutParams.WRAP_CONTENT);
            		LinearLayout.LayoutParams.MATCH_PARENT);
            // margins
            params.setMargins(20, 20, 20, 20);
            ImageView view = new ImageView(context);
            view.setLayoutParams(params);
    //       view.setst
           // view.setBackgroundColor(Color.parseColor("#FAF3AC"));
           // view.setBackgroundResource(defaultItemBackground);
            // scale type
            
            /**
        	 * Set up the view.setElevation, if the API is available.
        	 */
        
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        			
        		    view.setElevation(20);   // To je za od API 21 navzgor
        }*/
        
 
            //view.setScaleType(ScaleType.CENTER);
            //view.setScaleType(ScaleType.CENTER_CROP);
            return view;
        }
    }


	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
    
    // KONEC COPY-PEJSTANJA
    
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing registration ID is not guaranteed to work with
	    // the new app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	 /**
     * Set the result that is to be sent as the result of the request that caused this
     * Activity to be launched. If result is null or this method is never called then
     * the request will be canceled.
     * @param result this is returned as the result of the AbstractAccountAuthenticator request
     */
    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }
	
	
	
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the registration ID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	               .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
	    new AsyncTask<Void, Void, String>() {
	        @Override
	        protected String doInBackground(Void... params) {
	            String msg = "";
	            try {
	                if (gcm == null) {
	                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
	                }
	                regid = gcm.register(SENDER_ID);
	                Log.d("mycompany.myapp", "Device registered, registration ID = " + regid);
	                msg = "Device registered, registration ID=" + regid;

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.
	                // The request to your server should be authenticated if your app
	                // is using accounts.
	      //          sendRegistrationIdToBackend();

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the
	                // message using the 'from' address in the message.

	                // Persist the registration ID - no need to register again.
	                storeRegistrationId(getApplicationContext(), regid);
	            } catch (IOException ex) {
	                msg = "Error :" + ex.getMessage();
	                // If there is an error, don't just keep trying to register.
	                // Require the user to click a button again, or perform
	                // exponential back-off.
	            }
	            return msg;
	        }

	        @Override
	        protected void onPostExecute(String msg) {
	        	Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	           // mDisplay.append(msg + "\n");
	        }
	    }.execute(null, null, null);
	}
	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	
	/**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finishAuth() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                        "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }
	
	
	/*
	 * Redraws the horizontal gallery in the UI
	 */
	
	public void redrawGallery() {
		// Getting fresh database handler
		db = SQLiteHandler.getInstance(getApplicationContext());
		Integer NumberOfItems = db.getItemRowCount();
	//	ArrayList<Uri> ImagesUris = db.getImagesUris();
	  // NumberOfItems = 0; // SAMO ZA DEBUGGING!!
		
		
      	for (int j = 0; j < NumberOfItems; j++) {
      	Log.d("mycompany.myapp", "Stevilo itemsov v lokalni bazi je: " + NumberOfItems);
      		if (NumberOfItems == 0) {
      			break;
      		}
      	task = new PhotoBitmapTask(this, globalAccess.horizontal);
			task.execute(j);
			Log.d("mycompany.myapp", "Drawables index j:" + j);
      }
	}
	
	/**
	 * Sign-out from google
	 * */
	//private void signOutFromGplus() {
	protected void signOutFromGplus() {
	    if (mGoogleApiPlusClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiPlusClient);
	        mGoogleApiPlusClient.disconnect();
	        mGoogleApiPlusClient.connect();
	        Log.d(TAG, "Google Plus disconnected!");
	       // updateUI(false);
	    }
	}
	
	
	/*private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
             getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
}*/
		
}