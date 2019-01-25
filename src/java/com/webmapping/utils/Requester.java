/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webmapping.utils;

import com.webmapping.servlets.MainController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ruvic
 */
public class Requester {
    
    public static String requestGet(String urlQuery){
        URL url;
        InputStream in = null;
        try {
            url = new URL(urlQuery);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String encoding = new String(new Base64().encode("admin:geoserver".getBytes()));
            con.setRequestProperty  ("Authorization", "Basic " + encoding);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("GET");
            con.connect();
            
            //get Result
            in = con.getInputStream();
            BufferedReader buffer = new BufferedReader(
                new InputStreamReader(in)
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = buffer.readLine()) != null) {
                content.append(inputLine);
            }
            
            
            
            return content.toString();
            
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static void requestPost(String urlQuery, String parameters)
    {
        try {
            URL url = new URL(urlQuery);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String authorization = "Basic " + new String(new org.apache.commons.codec.binary.Base64().encode("admin:geoserver".getBytes()));
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", authorization);
            conn.setRequestProperty("Content-type","text/plain");
            
            // Send post request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();
            conn.connect();
            
            int responseCode = conn.getResponseCode();
//            System.out.println("Sending 'PUT' request to URL : " + url);
//            System.out.println("Put parameters : " + parameters);
//            System.out.println("Response Code : " + responseCode);
            
        } catch (Exception e) {
            e.printStackTrace();            
        }

    }
    
}
