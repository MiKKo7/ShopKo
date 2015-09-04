package com.mycompany.myapp.helper;

import java.util.ArrayList;
import java.util.HashMap;
 


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
 
public class SQLiteHandler extends SQLiteOpenHelper {
 
    private static final String TAG = SQLiteHandler.class.getSimpleName();
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 23;
 
    // Database Name
    private static final String DATABASE_NAME = "android_api";
 
    // Login table name
    private static final String TABLE_LOGIN = "login";
 
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
//    private static final String KEY_CGM_REG_ID = "cgm_reg_id";
    
 // ItemFollow table name
    private static final String TABLE_ITEM_FOLLOW = "item_follow";
 
    // ItemFollow Table Columns names
    private static final String KEY_ITEM_ID = "item_id";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_ITEM_BRAND = "brand";
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_DESCRIPTION = "description";
    private static final String KEY_ITEM_PRICE = "price";
    private static final String KEY_PRICE_CURRENCY = "currency";
    private static final String KEY_ITEM_DISCOUNT = "discount";
    private static final String KEY_UNIQUE_ID = "item_unique_id";
    private static final String KEY_ITEM_CREATED_AT = "created_at";
    private static final String KEY_UNIQUE_PLACE_ID = "unique_place_id";
    private static final String KEY_IMAGE_PATH_URI = "image_path_uri";
    private static final String KEY_FLAG_ITEM_NOT_SYNCHRONIZED = "flag_item_not_synchronized";
    private static final String KEY_FLAG_ITEM_FOLLOW = "flag_item_follow";
    private static final String KEY_FLAG_ITEM_IN_CART = "flag_item_in_cart";
    
   //private Context context;
    
    private static SQLiteHandler sInstance;

   // private static final String DATABASE_NAME = "database_name";
   // private static final String DATABASE_TABLE = "table_name";
   // private static final int DATABASE_VERSION = 1;

    public static synchronized SQLiteHandler getInstance(Context context) {

      // Use the application context, which will ensure that you 
      // don't accidentally leak an Activity's context.
      // See this article for more information: http://bit.ly/6LRzfx
      if (sInstance == null) {
        sInstance = new SQLiteHandler(context.getApplicationContext());
      }
      return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private SQLiteHandler(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
 
   /* public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }*/
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
//                + KEY_CGM_REG_ID + "TEXT,"
                + KEY_CREATED_AT + " DATETIME" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_ITEM_FOLLOW_TABLE = "CREATE TABLE " + TABLE_ITEM_FOLLOW + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BARCODE + " TEXT,"
        		+ KEY_ITEM_BRAND + " TEXT,"
                + KEY_ITEM_NAME + " TEXT," + KEY_ITEM_DESCRIPTION + " TEXT,"
                + KEY_ITEM_PRICE + " INTEGER," + KEY_PRICE_CURRENCY + " TEXT," + KEY_ITEM_DISCOUNT + " INTEGER,"
                + KEY_UNIQUE_ID + " TEXT UNIQUE," + KEY_UNIQUE_PLACE_ID + " TEXT,"  
            //    + KEY_ITEM_CREATED_AT + " DATETIME," + KEY_IMAGE_PATH_URI + " TEXT UNIQUE," + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " INTEGER" + ")";
                    + KEY_ITEM_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," 
                + KEY_IMAGE_PATH_URI + " TEXT UNIQUE," + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " INTEGER, " 
                + KEY_FLAG_ITEM_FOLLOW + " INTEGER, " + KEY_FLAG_ITEM_IN_CART + " INTEGER" + ")";
        
             //          + KEY_ITEM_CREATED_AT + " DATETIME('now','localtime')," + KEY_IMAGE_PATH_URI + " TEXT UNIQUE," + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " INTEGER" + ")";
   //     db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_ITEM_FOLLOW_TABLE);
 
        Log.d(TAG, "Database tables created");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_FOLLOW);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
 
        // Inserting Row
        long id = db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
 
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
 
    /**
     * Storing item details in database
     * */
//    public long addItem(String barcode, String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id, String unique_place_id, String image_path_uri, Integer flag_item_not_synchronized) {
    public long addItem(String barcode, String brand, String name, String description, Double price, String currency, Integer discount, String item_unique_id, String unique_place_id, String item_created_at, String image_path_uri, Integer flag_item_not_synchronized) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
       // values.put(KEY_ITEM_ID, 1); // ID tega lahko pustimo praznega, ker je AUTOINCREMENT
        values.put(KEY_BARCODE, barcode); // Barcode
        values.put(KEY_ITEM_BRAND, brand); // Brand
        values.put(KEY_ITEM_NAME, name); // Name
        values.put(KEY_ITEM_DESCRIPTION, description); // Description
        values.put(KEY_ITEM_PRICE, price); // Price
        values.put(KEY_PRICE_CURRENCY, currency); // Currency
        values.put(KEY_ITEM_DISCOUNT, discount); // Discount
        values.put(KEY_UNIQUE_ID, item_unique_id); // Item unique id
        values.put(KEY_UNIQUE_PLACE_ID, unique_place_id); // Unique place id
        values.put(KEY_ITEM_CREATED_AT, item_created_at); // Item created at je lokalno v bistvu item_followed_at
        values.put(KEY_IMAGE_PATH_URI, image_path_uri); // Image path uri
        values.put(KEY_FLAG_ITEM_NOT_SYNCHRONIZED, flag_item_not_synchronized); // Flag item not synchronized
        values.put(KEY_FLAG_ITEM_FOLLOW, 1); // Follow flag
        values.put(KEY_FLAG_ITEM_IN_CART, 0); // Item in cart flag
        
        // Inserting Row
        long id = db.insert(TABLE_ITEM_FOLLOW, null, values);
        db.close(); // Closing database connection
       // SQLiteDatabase db2 = this.getReadableDatabase();
       // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
       // String itemFollowedFrom = 
        Log.d(TAG, "New item inserted into sqlite: " + id + ", " + name);
    /*    String CREATE_ITEM_FOLLOW_TABLE = "CREATE TABLE " + TABLE_ITEM_FOLLOW + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BARCODE + " TEXT,"
        		+ KEY_ITEM_BRAND + " TEXT,"
                + KEY_ITEM_NAME + " TEXT," + KEY_ITEM_DESCRIPTION + " TEXT,"
                + KEY_ITEM_PRICE + " INTEGER,"+ KEY_ITEM_DISCOUNT + " INTEGER,"
                + KEY_UNIQUE_ID + " TEXT UNIQUE," + KEY_UNIQUE_PLACE_ID + " TEXT,"  
            //    + KEY_ITEM_CREATED_AT + " DATETIME," + KEY_IMAGE_PATH_URI + " TEXT UNIQUE," + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " INTEGER" + ")";
                    + KEY_ITEM_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," 
                + KEY_IMAGE_PATH_URI + " TEXT UNIQUE," + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " INTEGER " 
                + KEY_FLAG_ITEM_FOLLOW + " INTEGER" + ")";
        Log.d(TAG, "Create table string: " + CREATE_ITEM_FOLLOW_TABLE);*/
        
        return id;
    }
 
    /**
     * Unfollow item in database
     * */
//    public long addItem(String barcode, String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id, String unique_place_id, String image_path_uri, Integer flag_item_not_synchronized) {
    public void unfollowItem(String item_unique_id) {
        SQLiteDatabase db = this.getWritableDatabase();
 
      /*  ContentValues values = new ContentValues();
       // values.put(KEY_ITEM_ID, 1); // ID tega lahko pustimo praznega, ker je AUTOINCREMENT
        values.put(KEY_BARCODE, barcode); // Barcode
        values.put(KEY_ITEM_BRAND, brand); // Brand
        values.put(KEY_ITEM_NAME, name); // Name
        values.put(KEY_ITEM_DESCRIPTION, description); // Description
        values.put(KEY_ITEM_PRICE, price); // Price
        values.put(KEY_ITEM_DISCOUNT, discount); // Discount
        values.put(KEY_UNIQUE_ID, item_unique_id); // Item unique id
        values.put(KEY_UNIQUE_PLACE_ID, unique_place_id); // Unique place id
 //       values.put(KEY_ITEM_CREATED_AT, item_created_at); // Item created at
        values.put(KEY_IMAGE_PATH_URI, image_path_uri); // Image path uri
        values.put(KEY_FLAG_ITEM_NOT_SYNCHRONIZED, flag_item_not_synchronized); // Flag item not synchronized
        values.put(KEY_FLAG_ITEM_FOLLOW, 0); // Follow flag
*/ 
        String selectQuery = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_FOLLOW + " = 0 WHERE " + KEY_UNIQUE_ID + " = " + item_unique_id;
      
        db.rawQuery(selectQuery, null);
        //long id = db.insert(TABLE_ITEM_FOLLOW, null, values);
        //db.close(); // Closing database connection
       // SQLiteDatabase db2 = this.getReadableDatabase();
       // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
       // String itemFollowedFrom = 
        Log.d(TAG, "Item unfollowed");
        //return id;
    }
 
    /**
     * Unfollow item in database
     * */
//    public long addItem(String barcode, String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id, String unique_place_id, String image_path_uri, Integer flag_item_not_synchronized) {
    public void unfollowItemByIndex(Integer itemNumber, Integer flag) {
    	   SQLiteDatabase db = this.getWritableDatabase();
    	   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	   String index ;
    	// Gremo s cursorjem na zaporedni item in poglejmo, kjeri ID ima
    	  Cursor cursor = db.rawQuery(selectQuery, null);
          cursor.moveToPosition(itemNumber);
          if (cursor.getCount() > 0) {
        	  index = cursor.getString(0);
        	  Log.d("unfollowItemByIndex", "id je: " + index);
        	//  cursor.close();
        	// Potem spremenimo follow flag itemu z IDjem, ki smo ga najdli prej
        	  String setQuery = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_FOLLOW + " = " + flag + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery);
        	  // Spodaj damo flag KEY_FLAG_ITEM_NOT_SYNCHRONIZED na 1, da se potem reci apdejtajo
        	  String setQuery2 = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + "1" + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery2);
        	  //Cursor cursor_set = db.rawQuery(setQuery, null);
        	  //cursor_set.close();
        	  //db.close();
        	   Log.d(TAG, "Smo v unfollowItemByIndex!");
        	  cursor.close();
          }
    }
    
     /**
     * (Un)follow item in database using barcode, shop ID
     * */
//    public long addItem(String barcode, String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id, String unique_place_id, String image_path_uri, Integer flag_item_not_synchronized) {
    public void unFollowItemByBarcodePlace(String barcode, String shop_id, Integer flag) {
    	   SQLiteDatabase db = this.getWritableDatabase();
    	//   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	   shop_id = "'" + shop_id + "'";
    	   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id;
    	    //    String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	        
    	   Cursor cursor = db.rawQuery(selectQuery, null);
    	   // Move to first row
    	   cursor.moveToFirst();
    	   
    	   String index ;
    	// Gremo s cursorjem na zaporedni item in poglejmo, kjeri ID ima
    	//  Cursor cursor = db.rawQuery(selectQuery, null);
        //  cursor.moveToPosition(itemNumber);
          if (cursor.getCount() > 0) {
        	  index = cursor.getString(0);
        	  Log.d("unfollowItemByIndex", "id je: " + index);
        	//  cursor.close();
        	// Potem spremenimo follow flag itemu z IDjem, ki smo ga najdli prej
        	  String setQuery = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_FOLLOW + " = " + flag + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery);
        	  // Spodaj damo flag KEY_FLAG_ITEM_NOT_SYNCHRONIZED na 1, da se potem reci apdejtajo
        	  String setQuery2 = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + "1" + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery2);
        	  //Cursor cursor_set = db.rawQuery(setQuery, null);
        	  //cursor_set.close();
        	  //db.close();
        	   Log.d(TAG, "Smo v unFollowItemByBarcodePlace!");
        	  cursor.close();
          }
    }
    
    
    /**
     * Add item to cart
     * */
//    public long addItem(String barcode, String brand, String name, String description, String item_created_at, Double price, Integer discount, String item_unique_id, String unique_place_id, String image_path_uri, Integer flag_item_not_synchronized) {
    public void changeItemInCartFlagByIndex(Integer itemNumber, Integer flag) {
    	   SQLiteDatabase db = this.getWritableDatabase();
    	   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	   String index ;
    	// Gremo s cursorjem na zaporedni item in poglejmo, kjeri ID ima
    	  Cursor cursor = db.rawQuery(selectQuery, null);
          cursor.moveToPosition(itemNumber);
          if (cursor.getCount() > 0) {
        	  index = cursor.getString(0);
        	//  Log.d("ItemShowActivity", "id je: " + index);
        	  cursor.close();
        	// Potem spremenimo follow flag itemu z IDjem, ki smo ga najdli prej
        	  String setQuery = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_IN_CART + " = " + flag + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery);
        	  // Spodaj damo flag KEY_FLAG_ITEM_NOT_SYNCHRONIZED na 1, da se potem reci apdejtajo
        	  String setQuery2 = "UPDATE " + TABLE_ITEM_FOLLOW + " SET " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + "1" + " WHERE " + KEY_ITEM_ID + " = " + index;
        	  db.execSQL(setQuery2);
        	  //Cursor cursor_set = db.rawQuery(setQuery, null);
        	  //cursor_set.close();
        	  //db.close();
        	  cursor.close();
          }
    }
    
    
    
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
          //  user.put("cgmRegId", cursor.getString(4));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        //db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
 
        return user;
    }
 
    /**
     * Getting item data from database
     * */
    public HashMap<String, String> getItemDetails(Integer itemNumber) {
    	//String item_unique_id = itemUniqueId;
        HashMap<String, String> item = new HashMap<String, String>();
       // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
     ////   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW + " ORDER BY created_at";
       // itemNumber = "'" + itemNumber + "'";
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_ITEM_ID + " = " + itemNumber;
    //    String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
    //    cursor.moveToPosition(itemNumber);
        if (cursor.getCount() > 0) {
        	item.put("item_id", cursor.getString(0));
        	Log.d(TAG, "item_id je: " + cursor.getString(0));
        	item.put("barcode", cursor.getString(1));
        	Log.d(TAG, "barcode je: " + cursor.getString(1));
        	item.put("item_brand", cursor.getString(2));
        	Log.d(TAG, "item_brand je: " + cursor.getString(2));
        	item.put("item_name", cursor.getString(3));
        	Log.d(TAG, "item_name je: " + cursor.getString(3));
        	item.put("item_description", cursor.getString(4));
        	Log.d(TAG, "item_description je: " + cursor.getString(4));
        	item.put("item_price", cursor.getString(5));
        	Log.d(TAG, "item_price je: " + cursor.getString(5));
        	item.put("item_currency", cursor.getString(6));
        	Log.d(TAG, "item_currency je: " + cursor.getString(6));
          	item.put("item_discount", cursor.getString(7));
          	Log.d(TAG, "item_discount je: " + cursor.getString(7));
         	item.put("item_unique_id", cursor.getString(8));
         	Log.d(TAG, "item_unique_id je: " + cursor.getString(8));
         	item.put("unique_place_id", cursor.getString(9));
         	Log.d(TAG, "unique_place_id je: " + cursor.getString(9));
        	item.put("created_at", cursor.getString(10));
        	Log.d(TAG, "created_at je: " + cursor.getString(10));
        	item.put("image_path_uri", cursor.getString(11));
        	Log.d(TAG, "image_path_uri je: " + cursor.getString(11));
        	item.put("flag_item_not_synchronized", cursor.getString(12));
        	Log.d(TAG, "flag_item_not_synchronized je: " + cursor.getString(12));
        	item.put("flag_item_follow", cursor.getString(13));
        	Log.d(TAG, "flag_item_follow je: " + cursor.getString(13));
        	
          //  user.put("cgmRegId", cursor.getString(4));

        }
        cursor.close();
        //db.close();
        // return user
        Log.d(TAG, "Fetching item from Sqlite: " + item.toString());
 
        return item;
    }
    
    /**
     * Getting item data from database
     * */
    public HashMap<String, String> getItemDetailsByIndex(Integer itemNumber) {
    	//String item_unique_id = itemUniqueId;
        HashMap<String, String> item = new HashMap<String, String>();
       // String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
     ////   String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW + " ORDER BY created_at";
       // itemNumber = "'" + itemNumber + "'";
    //    String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_ITEM_ID + " = " + itemNumber;
        String selectQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
   //     cursor.moveToFirst();
        cursor.moveToPosition(itemNumber);
        if (cursor.getCount() > 0) {
        	item.put("item_id", cursor.getString(0));
        	Log.d(TAG, "item_id je: " + cursor.getString(0));
        	item.put("barcode", cursor.getString(1));
        	Log.d(TAG, "barcode je: " + cursor.getString(1));
        	item.put("item_brand", cursor.getString(2));
        	Log.d(TAG, "item_brand je: " + cursor.getString(2));
        	item.put("item_name", cursor.getString(3));
        	Log.d(TAG, "item_name je: " + cursor.getString(3));
        	item.put("item_description", cursor.getString(4));
        	Log.d(TAG, "item_description je: " + cursor.getString(4));
        	item.put("item_price", cursor.getString(5));
        	Log.d(TAG, "item_price je: " + cursor.getString(5));
        	item.put("item_currency", cursor.getString(6));
        	Log.d(TAG, "item_currency je: " + cursor.getString(6));
          	item.put("item_discount", cursor.getString(7));
          	Log.d(TAG, "item_discount je: " + cursor.getString(7));
         	item.put("item_unique_id", cursor.getString(8));
         	Log.d(TAG, "item_unique_id je: " + cursor.getString(8));
         	item.put("unique_place_id", cursor.getString(9));
         	Log.d(TAG, "unique_place_id je: " + cursor.getString(9));
        	item.put("created_at", cursor.getString(10));
        	Log.d(TAG, "created_at je: " + cursor.getString(10));
        	item.put("image_path_uri", cursor.getString(11));
        	Log.d(TAG, "image_path_uri je: " + cursor.getString(11));
        	item.put("flag_item_not_synchronized", cursor.getString(12));
        	Log.d(TAG, "flag_item_not_synchronized je: " + cursor.getString(12));
        	item.put("flag_item_follow", cursor.getString(13));
        	Log.d(TAG, "flag_item_follow je: " + cursor.getString(13));
        	item.put("flag_item_in_cart", cursor.getString(14));
        	Log.d(TAG, "flag_item_in_cart je: " + cursor.getString(14));
     /*   	item.put("item_id", cursor.getString(1));
        	item.put("item_brand", cursor.getString(2));
        	item.put("item_name", cursor.getString(3));
        	item.put("item_description", cursor.getString(4));
        	item.put("item_price", cursor.getString(5));
          	item.put("item_discount", cursor.getString(6));
         	item.put("item_unique_id", cursor.getString(7));
          //  user.put("cgmRegId", cursor.getString(4));
        	item.put("created_at", cursor.getString(10));
        	item.put("image_path_uri", cursor.getString(11));*/
        }
        cursor.close();
        //db.close();
        // return user
        Log.d(TAG, "Fetching item from Sqlite: " + item.toString());
 
        return item;
    }
    
    
    
    public ArrayList<Uri> getImagesUris() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String selectQuery = "SELECT  image_path_uri FROM " + TABLE_ITEM_FOLLOW;
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	String array[] = new String[cursor.getCount()];
    	//Uri array[] = new Uri[cursor.getCount()];
    	ArrayList<Uri> ImagesUris = new ArrayList<Uri>();
    	Integer i = 0;

    	cursor.moveToFirst();
    	while (cursor.isAfterLast() == false) {
    	    //array[i] = cursor.getString(0);
    		array[i] = cursor.getString(0);
    	//	ImagesUris.set(i, Uri.parse(array[i]));
    		ImagesUris.add(Uri.parse(array[i]));
    	    i++;
    	    cursor.moveToNext();
    	}
    	cursor.close();
		return ImagesUris;	
    }
    
    /**
     * Getting items that are not yet synced with server database -
     * returning unique item ids
     * */
   
    public ArrayList<String> getNotSyncedItems() {
    	final int Ena = 1;
    	SQLiteDatabase db = this.getReadableDatabase();
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = 1";
    	String selectQuery1 = "SELECT barcode FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	Cursor cursor1 = db.rawQuery(selectQuery1, null);
    	Log.d(TAG, "cursor1.getCount() v getNotSyncedItems je: " + Integer.toString(cursor1.getCount()));
    	String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW; // + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	String array[] = new String[cursor.getCount()];
    	Log.d(TAG, "cursor.getCount() v getNotSyncedItems je: " + Integer.toString(cursor.getCount()));
    	//Uri array[] = new Uri[cursor.getCount()];
    	ArrayList<String> ItemsNotInSync = new ArrayList<String>();
    	Integer i = 0;
    	Log.d(TAG, "cursor.toString() v getNotSyncedItems je: " + cursor.toString());
    	if (cursor.moveToFirst()) {
    		Log.d(TAG, "Smo v getNotSyncedItems!");
    		while (cursor.isAfterLast() == false) {
    			//array[i] = cursor.getString(0);
    			array[i] = cursor.getString(0);
    			//	ImagesUris.set(i, Uri.parse(array[i]));
    			ItemsNotInSync.add(array[i]);
    			i++;
    			Log.d(TAG, "i v while v getNotSyncedItems je: " + i);
    			cursor.moveToNext();
    		}
    	} else {
    		Log.d(TAG, "cursor.moveToFirst() ni izpounjen!");
    	}
    	
    	cursor.close();
		return ItemsNotInSync;
    }
    
    /**
     * Getting items that are not yet synced with server database -
     * returning unique item ids
     * */
   
    public ArrayList<Integer> getNotSyncedItemsIndexes() {
    	final int Ena = 1;
    	SQLiteDatabase db = this.getReadableDatabase();
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = 1";
    	String selectQuery1 = "SELECT barcode FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	Cursor cursor1 = db.rawQuery(selectQuery1, null);
    	Log.d(TAG, "cursor1.getCount() v getNotSyncedItems je: " + Integer.toString(cursor1.getCount()));
    	String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW; // + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	String array[] = new String[cursor.getCount()];
    	Log.d(TAG, "cursor.getCount() v getNotSyncedItems je: " + Integer.toString(cursor.getCount()));
    	//Uri array[] = new Uri[cursor.getCount()];
    	//ArrayList<String> ItemsNotInSync = new ArrayList<String>();
    	ArrayList<Integer> ItemsNotInSync = new ArrayList<Integer>();
    	Integer i = 0;
    	Log.d(TAG, "cursor.toString() v getNotSyncedItems je: " + cursor.toString());
    	if (cursor.moveToFirst()) {
    		Log.d(TAG, "Smo v getNotSyncedItems!");
    		while (cursor.isAfterLast() == false) {
    			//array[i] = cursor.getString(0);
    			array[i] = cursor.getString(0);
    			//	ImagesUris.set(i, Uri.parse(array[i]));
    			//ItemsNotInSync.add(array[i]);
    			ItemsNotInSync.add(cursor.getPosition());
    			i++;
    			Log.d(TAG, "i v while v getNotSyncedItems je: " + i);
    			cursor.moveToNext();
    		}
    	} else {
    		Log.d(TAG, "cursor.moveToFirst() ni izpounjen!");
    	}
    	cursor.close();
		return ItemsNotInSync;
    }
    
    
    
    
    /**
     * Getting all the items from the database -
     * returning their unique item ids
     * */
   
    public ArrayList<String> getAllItems() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String selectQuery = "SELECT  item_unique_id FROM " + TABLE_ITEM_FOLLOW;
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	String array[] = new String[cursor.getCount()];
    	//Uri array[] = new Uri[cursor.getCount()];
    	ArrayList<String> allItems = new ArrayList<String>();
    	Integer i = 0;

    	cursor.moveToFirst();
    	while (cursor.isAfterLast() == false) {
    	    //array[i] = cursor.getString(0);
    		array[i] = cursor.getString(0);
    	//	ImagesUris.set(i, Uri.parse(array[i]));
    		allItems.add(array[i]);
    	    i++;
    	    cursor.moveToNext();
    	}
    	cursor.close();
		return allItems;
    }
    
    
    
    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        //db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
    
    /**
     * Getting number of items in table
     * */
    public int getItemRowCount() {
       // String countQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW);
     //   queryNumEntries(db, TABLE_ITEM_FOLLOW);
    /*    Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        Log.d("SQLiteHandler", "getRowCount() je: " + rowCount);
        db.close();
        cursor.close();*/
 
        // return row count
        Log.d("SQLiteHandler", "getItemRowCount() je: " + rowCount);
        return rowCount;
    }
 
    /**
     * Getting number of followed items in table
     * */
    public int getFollowedItemRowCount() {
       // String countQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
        SQLiteDatabase db = this.getReadableDatabase();
     //   int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW);
        
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = 1";
    	String selectQuery1 = "SELECT barcode FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_FOLLOW + " = 1";
    	Cursor cursor = db.rawQuery(selectQuery1, null);
   // 	Log.d(TAG, "cursor1.getCount() v getNotSyncedItems je: " + Integer.toString(cursor1.getCount()));
    //	String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	//String selectQuery = "SELECT item_unique_id FROM " + TABLE_ITEM_FOLLOW; // + " WHERE " + KEY_FLAG_ITEM_NOT_SYNCHRONIZED + " = " + Ena;
    	//Cursor cursor = db.rawQuery(selectQuery, null);
    	// String array[] = new String[cursor.getCount()];
        int rowCount = cursor.getCount();
        
     //   queryNumEntries(db, TABLE_ITEM_FOLLOW);
    /*    Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        Log.d("SQLiteHandler", "getRowCount() je: " + rowCount);
        db.close();
        cursor.close();*/
 
        // return row count
        Log.d("SQLiteHandler", "getFollowedItemRowCount() je: " + rowCount);
        return rowCount;
    }
    
    /**
     * Item already in database checker
     * */
    public int getItemRowCount(String barcode, String shop_id) {
       // String countQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	 shop_id = "'" + shop_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id);
        //int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode &&  KEY_UNIQUE_PLACE_ID + " = " + shop_id);
    //    db.delete(TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id, null);
     //   queryNumEntries(db, TABLE_ITEM_FOLLOW);
    /*    Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        Log.d("SQLiteHandler", "getRowCount() je: " + rowCount);
        db.close();
        cursor.close();*/
 
        // return row count
        Log.d("SQLiteHandler", "getRowCount() checker je: " + rowCount);
        return rowCount;
    }
    /**
     * Item already in database checker, flag_item_follow included into query as 3rd parameter
     * */
    public int getItemRowCount(String barcode, String shop_id, Integer flag_item_follow) {
       // String countQuery = "SELECT  * FROM " + TABLE_ITEM_FOLLOW;
    	 shop_id = "'" + shop_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id + " AND " + KEY_FLAG_ITEM_FOLLOW + " = " + flag_item_follow);
        //int rowCount = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode &&  KEY_UNIQUE_PLACE_ID + " = " + shop_id);
    //    db.delete(TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id, null);
     //   queryNumEntries(db, TABLE_ITEM_FOLLOW);
    /*    Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        Log.d("SQLiteHandler", "getRowCount() je: " + rowCount);
        db.close();
        cursor.close();*/
 
        // return row count
        Log.d("SQLiteHandler", "getRowCount() checker je: " + rowCount);
        return rowCount;
    }
    
    
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        //db.close();
 
        Log.d(TAG, "Deleted all user info from sqlite");
    }
    
    public void deleteItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ITEM_FOLLOW, null, null);
        //db.close();
 
        Log.d(TAG, "Deleted all items info from sqlite");
    }
    
    public void deleteSingleItem(String barcode, String shop_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        shop_id = "'" + shop_id + "'";
        db.delete(TABLE_ITEM_FOLLOW, KEY_BARCODE + " = " + barcode + " AND " + KEY_UNIQUE_PLACE_ID + " = " + shop_id, null);
       // db.close();
        Log.d(TAG, "Item deleted from sqlite");
    }
 
}