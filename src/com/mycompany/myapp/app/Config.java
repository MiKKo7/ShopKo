package com.mycompany.myapp.app;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
 
public class Config {
 
    // PayPal app configuration
    public static final String PAYPAL_CLIENT_ID = "AWk-HkCNyIsZ2aTqFzfRFttngNPtwhUu5fq1BI9nKP-bwjjI2errQyfvCPP62q0mmPQ56SfNarZdmL_1";
    public static final String PAYPAL_CLIENT_SECRET = "EKGSWUkNRM2Bpg0UFgJQ7yLgxLlcTv4xtvNE7IfJP1gnYGc3KQMpIjcIJ5Wd9zmTmsye3V21lRDUYO-_";
 
    public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    public static final String DEFAULT_CURRENCY = "EUR";
 
    // PayPal server urls
    public static final String URL_PRODUCTS = "http://192.168.1.146/PayPalServer/v1/products";
  //  public static final String URL_PRODUCTS = "http://10.10.0.146/PayPalServer/v1/products";
    
    public static final String URL_VERIFY_PAYMENT = "http://192.168.1.146/PayPalServer/v1/verifyPayment";
  //  public static final String URL_VERIFY_PAYMENT = "http://10.10.0.146/PayPalServer/v1/verifyPayment";
    
    // MySQL server address
    public static final String URL_MYSQL_SERVER = "https://192.168.1.146";
  //  public static final String URL_MYSQL_SERVER = "https://10.10.0.146";
    public static final String URL_MYSQL_SERVER_UNSECURE = "http://192.168.1.146";
  //  public static final String URL_MYSQL_SERVER_UNSECURE = "http://10.10.0.146";
    
 
}