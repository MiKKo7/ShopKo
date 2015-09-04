package com.mycompany.myapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mycompany.myapp.app.Config;
import com.mycompany.myapp.helper.Product;
import com.mycompany.myapp.helper.SQLiteHandler;
import com.paypal.android.sdk.payments.PayPalItem;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class ItemShowActivity extends Activity {
//public class ItemShowActivity extends MainActivity {
	
	ImageView itemPic;	
	public SQLiteHandler db;
	
	JSONObject productJSON;
	Product product;
	
	// To store all the products
    private List<Product> productsList = new ArrayList<Product>();
	
	// To store the products those are added to cart
    private List<PayPalItem> productsInCart = new ArrayList<PayPalItem>();
	
    public Integer itemIndex;
    public String itemTitle;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_show);	
		Bundle extras = getIntent().getExtras();
		Button watchButton = (Button)findViewById(R.id.buttonWatch);
		Button add2CartButton = (Button)findViewById(R.id.buttonAddToCart);
		
		
		//Integer itemIndex = extras.getInt("message");   // To je index itemsa
		itemIndex = extras.getInt("message");   // To je index itemsa
		itemTitle = extras.getString("item_title");
		String itemDescription = extras.getString("item_details");
		Integer item_descriptionLength = extras.getInt("item_descriptionLength");
		Integer item_priceLength = extras.getInt("item_priceLength");
		Integer item_currencyLength = extras.getInt("item_currencyLength");
		String item_image_path = extras.getString("item_image_path");
		// Provajmo dodat item2cart_flag
		Integer flag_item_in_cart = extras.getInt("flag_item_in_cart");
		
		
		// Set title
		TextView ItemTitle = (TextView)findViewById(R.id.textView2);
		ItemTitle.setText(Html.fromHtml(itemTitle), TextView.BufferType.SPANNABLE);
		ItemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		
		// Set watch button

	    db = SQLiteHandler.getInstance(getApplicationContext());
	  //  if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString() == "1") {
	    if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString().equals("1")) {
	    	watchButton.setText("Unfollow");
	//    } else if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString() == "0") {
	    } else if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString().equals("0")) {
	    	watchButton.setText("Follow");
	    }
		
	 // Set add to cart button

	  //  if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString() == "1") {
	    if (db.getItemDetailsByIndex(itemIndex).get("flag_item_in_cart").toString().equals("1")) {
	    	add2CartButton.setText("Remove from cart");
	//    } else if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString() == "0") {
	    } else if (db.getItemDetailsByIndex(itemIndex).get("flag_item_in_cart").toString().equals("0")) {
	    	add2CartButton.setText("Add to cart");
	    }
	    
	    
	    productJSON = new JSONObject(db.getItemDetailsByIndex(itemIndex));
	    
		//ItemTitle.setText(itemTitle);
		
		// Set description
		TextView ItemDescription = (TextView)findViewById(R.id.textView1);
		ItemDescription.setText(Html.fromHtml(itemDescription), TextView.BufferType.SPANNABLE);
		Spannable spannable = (Spannable) ItemDescription.getText();
		spannable.setSpan(new StrikethroughSpan(), item_descriptionLength, item_descriptionLength+item_priceLength+item_currencyLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ItemDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		//ItemDescription.setText(itemDescription);
		
		// Overlay sale sign on item image
		Resources r = getResources();
		Drawable[] layers = new Drawable[2];		
		
		itemPic = (ImageView) findViewById(R.id.imageView1);
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeFile(item_image_path, options);
		layers[0] = new BitmapDrawable(getResources(), bm);
		layers[1] = r.getDrawable(R.drawable.sale_50_off);
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		
	//	itemPic.setImageBitmap(bm);
		itemPic.setImageDrawable(layerDrawable);
		
		itemIndex = extras.getInt("message");   // To je index itemsa
		Log.d("ItemShowActivity", "itemIndex je: " + itemIndex);
		
watchButton.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v){

		//respond to clicks
		Log.d("ItemShowActivity", "smo v watchButton onClick");
		Bundle extras = getIntent().getExtras();
		Button watchButton = (Button)findViewById(R.id.buttonWatch);
		
		
//		LocationListener locationListener = new LocationListener() {
//			public void onLocationChanged(Location location) {
//				// Called when a new location is found by the network location provider.
//				////////makeUseOfNewLocation(location);
//			}
//		};
		// Integer itemIndex = extras.getInt("message");   // To je index itemsa
	//	itemIndex = extras.getInt("message");   // To je index itemsa
		int id = v.getId();
	//	if (id == R.id.buttonWatch) {
			if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString().equals("0")) {
				Log.d("ItemShowActivity", "flag_item_follow je: " +db.getItemDetailsByIndex(itemIndex).get("flag_item_follow"));
				db.unfollowItemByIndex(itemIndex, 1);
				Log.d("ItemShowActivity", "flag_item_not_synchronized je: " + db.getItemDetailsByIndex(itemIndex).get("flag_item_not_synchronized").toString());
				Log.d("ItemShowActivity", "itemTitle je: " + itemTitle);
				watchButton.setText("Unfollow");
				Toast.makeText(ItemShowActivity.this,"Following item",Toast.LENGTH_SHORT).show();
			} else if (db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString().equals("1")) {
			//	Log.d("ItemShowActivity", "flag_item_follow je: " +db.getItemDetailsByIndex(itemIndex).get("flag_item_follow"));
				Log.d("ItemShowActivity", "flag_item_not_synchronized pred unfollow je: " + db.getItemDetailsByIndex(itemIndex).get("flag_item_not_synchronized").toString());
				db.unfollowItemByIndex(itemIndex, 0);
				Log.d("ItemShowActivity", "flag_item_not_synchronized za unfollow je: " + db.getItemDetailsByIndex(itemIndex).get("flag_item_not_synchronized").toString());
				Log.d("ItemShowActivity", "flag_item_follow je: " + db.getItemDetailsByIndex(itemIndex).get("flag_item_follow").toString());
				Log.d("ItemShowActivity", "itemTitle je: " + itemTitle);
				watchButton.setText("Follow");
				Toast.makeText(ItemShowActivity.this,"Item unfollowed",Toast.LENGTH_SHORT).show();
			}
	/*	} else if (id == R.id.buttonAddToCart) {
			//listener.onAddToCartPressed(product);
			// Fetching products from server
	        fetchProducts();
			onAddToCartPressed(product);
	        
		}*/
	}
	});

add2CartButton.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v){
		
		Log.d("ItemShowActivity", "smo v add2CartButton onClick");
        fetchProducts();
		onAddToCartPressed(product);    
		db.changeItemInCartFlagByIndex(itemIndex, 1);
	}
});
}
	
	public void onAddToCartPressed(Product product) {
 
        PayPalItem item = new PayPalItem(product.getName(), 1,
                product.getPrice(), Config.DEFAULT_CURRENCY, product.getSku());
 
        productsInCart.add(item);  // Tega za zdej ne rabimo glih
        
        db.changeItemInCartFlagByIndex(itemIndex, 1);
 
        Toast.makeText(getApplicationContext(),
                item.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
 
    }
	
	/**
     * Fetching the products from our server
     * */
    
    private void fetchProducts() {
        // Showing progress dialog before making request
 
      //  pDialog.setMessage("Fetching products...");
 
      //  showpDialog();
    	
        // Making json object request
       /* JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Config.URL_PRODUCTS, null, new Response.Listener<JSONObject>() {
 
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
 
                        try {
                            JSONArray products = response
                                    .getJSONArray("products");
 
                            // looping through all product nodes and storing
                            // them in array list
                            for (int i = 0; i < products.length(); i++) { */
 
  /*                              JSONObject product = (JSONObject) products
 //                                       .get(i); */
 								try {
		//	productJSON.get("flag_item_follow").toString();
			String id = productJSON.getString("item_id");
			String name = productJSON.getString("item_name");
			String description = productJSON.getString("item_description");
			String sku = productJSON.getString("barcode");
			BigDecimal price = new BigDecimal(productJSON.getString("item_price"));
			String image = productJSON.getString("image_path_uri");
			
			//Product p = new Product(id, name, description,
			product = new Product(id, name, description,
                    image, price, sku);
			//productsList.add(p);
			productsList.add(product);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 Toast.makeText(getApplicationContext(),
                     "Error: " + e.getMessage(),
                     Toast.LENGTH_LONG).show();
		}
 								/*
                                String id = product.getString("id");
                                String name = product.getString("name");
                                String description = product
                                        .getString("description");
                                String image = product.getString("image");
//                                BigDecimal price = new BigDecimal(product
//                                        .getString("price"));
                                String sku = product.getString("sku");
 
                                Product p = new Product(id, name, description,
                                        image, price, sku);
 
                                productsList.add(p);
                            }
 
                            // notifying adapter about data changes, so that the
                            // list renders with new data
                            adapter.notifyDataSetChanged();
 
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
 
                        // hiding the progress dialog
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        // hide the progress dialog
                        hidepDialog();
                    }
                });*/
 
        // Adding request to request queue
      //  AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
	
}
