package com.mycompany.myapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
 








import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 








import android.os.AsyncTask;
import android.util.Log;
 
public class JSONParser extends AsyncTask<Object, Void, JSONObject> {
 
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
 
    // constructor
    public JSONParser() {
 
    }
    
    @Override
    protected JSONObject doInBackground(Object... paramts) {
        String url = (String) paramts[0];
        List<NameValuePair> params = (List<NameValuePair>) paramts[1];
        try {
        	HttpsURLConnection urlConnection = setUpHttpsConnection(url);
        	urlConnection.setRequestMethod("POST");
        	urlConnection.setDoInput(true);
        	urlConnection.setDoOutput(true);
        	try {
        		OutputStream os = urlConnection.getOutputStream();
        		
        		BufferedWriter writer = new BufferedWriter(
        	    new OutputStreamWriter(os, "UTF-8"));
        		os.close();
        		}
        	catch (Exception ex) {
        			Log.e("Buffer Error", "getOutputStream ne dela: " + ex.toString());
        			ex.printStackTrace();
        		}
        	
        	is = urlConnection.getInputStream();
       
        } catch (UnsupportedEncodingException e) {
        	Log.e("com.mycompany.myapp", "UnsupportedEncodingException v JSONParser");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
        	Log.e("com.mycompany.myapp", "ClientProtocolException v JSONParser");
            e.printStackTrace();
        } catch (IOException e) {
        	Log.e("com.mycompany.myapp", "IOException v JSONParser");
            e.printStackTrace();
        } catch (Exception e) {
	         e.printStackTrace();
  		};
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            		   is, "UTF-8"), 8);
            
                //    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                Log.v("err", line);
                
            }
            is.close();
            json = sb.toString();
            //this is the error
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
        	Log.e("JSON Parser", "Error parsing data, to ne moremo zdzezonirat: " + json);
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }

    protected void onPostExecute(JSONObject result) {
        //In here, call back to Activity or other listener that things are done
       // mCallback.onRequestCompleted(result);
    }
    // COPY PEJSTAMO


    
    public HttpsURLConnection setUpHttpsConnection(String urlString) {
    	try
        {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            if (cf == null) {
            	Log.e("mycompany.myapp", "X.509 ni loudan!");
            }
            else {
            	Log.d("mycompany.myapp", "X.509 je loudan!");
            }
            InputStream caInput = new BufferedInputStream(MainApplication.getContext().getAssets().open("ShopCoDejmo.crt"));
            
            caInput.available();
            if (caInput.available() == 0) {
            	Log.e("mycompany.myapp", "ShopCo.crt ni loudan!");
            	}
            else {
            	Log.d("mycompany.myapp", "ShopCo.crt je loudan!");
            	}
            
            java.security.cert.Certificate ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            
            caInput.close();
            
            
            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            
            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            
            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            
            //SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            context.init(null, tmf.getTrustManagers(), null);
            
            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL(urlString);
            // To spodaj je zaradi problema s povezavo na server s certifikatom preko IPja, ne pa imena serverja
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            	  public boolean verify(String hostname, SSLSession session) {
            	  return true;
            	  }
            	});
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            //urlConnection.setHostnameVerifier(new BrowserCompatHostnameVerifier());
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            Log.d("mycompany.myapp", "SSL connection to server established.");
            
            //InputStream in = urlConnection.getInputStream();
            
            //urlConnection.setRequestMethod("GET");
            //urlConnection.connect();
            
            // InputStream in = urlConnection.getInputStream();
            
            return urlConnection;
        }
        catch (Exception ex)
        {
            Log.e("mycompany.myapp", "Failed to establish SSL connection to server: " + ex.toString());
            return null;
        }
    }
 
    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {
 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);            
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
 
    }
}
