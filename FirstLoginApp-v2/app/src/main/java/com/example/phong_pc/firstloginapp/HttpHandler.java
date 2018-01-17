package com.example.phong_pc.firstloginapp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Linh on 1/16/2018.
 */

public class HttpHandler {

    private final String USER_AGENT = "Mozilla/5.0";
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceGet(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public  String makeServicePostOrPut(String url,String jsonString,String actionName) {

        StringBuffer response = new StringBuffer();
        try {
            // Send post request
            String method="POST";

            if(!actionName.equalsIgnoreCase("")){
                method="PUT";
                url+=actionName;
            }

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add request header
            con.setRequestProperty("Content-length", String.valueOf(jsonString.length()));
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //con.setRequestProperty("User-Agent", "Opera/9.26 (X11; Linux i686; U; en)");


            con.setRequestMethod(method);
            con.setDoOutput(true);
            con.setDoInput(true);

            // send data into server
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(jsonString);
            wr.flush();
            wr.close();

            // get status code server return
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {

                // get content server return
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;

                // read content server return
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response.toString();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
