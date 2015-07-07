package com.mycompany.myapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.mycompany.myapp.app.Config;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
 
public class UploadImage extends Activity {
    InputStream inputStream;
    String res = "";
    String imagePath;
    String serverImageName;
    String uniqueUserId;
        @Override
    public void onCreate(Bundle dummy) {
            super.onCreate(dummy);
            setContentView(R.layout.main);
            Log.d("UploadImage", "Smo v UploadImage!");
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                imagePath = extras.getString("IMAGE_PATH");
                serverImageName = extras.getString("SERVER_IMAGE_NAME");
                uniqueUserId = extras.getString("UNIQUE_USER_ID").replaceAll("[^a-zA-Z0-9-]", "_");
            }
            Log.d("UploadImage", "uniqueUserId je: " +uniqueUserId);
        	/*BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inJustDecodeBounds = true;
	        options.inSampleSize = 5;
           // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ////    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
            Log.d("UploadImage", "imagePath je: " +imagePath);
           // byte[] imageBytes = yourByteArray;
         //   String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            byte [] byte_arr = stream.toByteArray();
           // String image_str = Base64.encodeBytes(byte_arr);
            
            String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            final ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
 
            nameValuePairs.add(new BasicNameValuePair("image",image_str));
            nameValuePairs.add(new BasicNameValuePair("server_image_name",serverImageName));
            nameValuePairs.add(new BasicNameValuePair("unique_user_id",uniqueUserId));
            */
 
             Thread t = new Thread(new Runnable() {
             
            @Override
            public void run() {
                  try{
                	  BitmapFactory.Options options = new BitmapFactory.Options();
          			//options.inJustDecodeBounds = true;
          	        options.inSampleSize = 5;
                     // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
                      Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                      ByteArrayOutputStream stream = new ByteArrayOutputStream();
                      bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
                      Log.d("UploadImage", "imagePath je: " +imagePath);
                     // byte[] imageBytes = yourByteArray;
                   //   String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                      byte [] byte_arr = stream.toByteArray();
                     // String image_str = Base64.encodeBytes(byte_arr);
                      
                      String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                      final ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
           
                      nameValuePairs.add(new BasicNameValuePair("image",image_str));
                      nameValuePairs.add(new BasicNameValuePair("server_image_name",serverImageName));
                      nameValuePairs.add(new BasicNameValuePair("unique_user_id",uniqueUserId));
                      
                         HttpClient httpclient = new DefaultHttpClient();
                      //   HttpPost httppost = new HttpPost("http://10.0.2.2/Upload_image_ANDROID/upload_image.php");
                    //     HttpPost httppost = new HttpPost("http://10.10.0.146/upload_image.php");
                         final String server_URL = Config.URL_MYSQL_SERVER_UNSECURE + "/upload_image.php";
                         HttpPost httppost = new HttpPost(server_URL);
                         //HttpPost httppost = new HttpPost("http://192.168.1.148/upload_image.php");
                         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                         HttpResponse response = httpclient.execute(httppost);
                         final String the_string_response = convertResponseToString(response);
                         httpclient.getConnectionManager().shutdown(); // Close the instance here
                         
                         runOnUiThread(new Runnable() {
                                 
                                @Override
                                public void run() {
                                    Toast.makeText(UploadImage.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();
                                    Log.d("UploadImage", "Response je: " + the_string_response);
                                }
                            });
                          
                     }catch(final Exception e){
                          runOnUiThread(new Runnable() {
                             
                            @Override
                            public void run() {
                                Toast.makeText(UploadImage.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show(); 
                                Log.d("UploadImage", "ERROR je: " + e.getMessage());
                            }
                        });
                           System.out.println("Error in http connection "+e.toString());
                           Log.d("UploadImage", "Error in http connection: " + e.toString());
                     }  
            }
        });
         t.start();
        }
 
        public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{
 
             //String res = "";
             StringBuffer buffer = new StringBuffer();
             inputStream = response.getEntity().getContent();
             final int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
              runOnUiThread(new Runnable() {
             
            @Override
            public void run() {
                Toast.makeText(UploadImage.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();                     
            }
        });
          
             if (contentLength < 0){
             }
             else{
                    byte[] data = new byte[512];
                    int len = 0;
                    try
                    {
                        while (-1 != (len = inputStream.read(data)) )
                        {
                            buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        inputStream.close(); // closing the stream…..
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    res = buffer.toString();     // converting stringbuffer to string…..
                    
                    runOnUiThread(new Runnable() {
                     
                    @Override
                    public void run() {
                       Toast.makeText(UploadImage.this, "Result : " + res, Toast.LENGTH_LONG).show();
                    }
                });
                    //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
             }
             return res;
        }
}