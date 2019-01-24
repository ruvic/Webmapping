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
    
    public static String requestPost(String urlQuery, File file)
    {
        try {
            URL url = new URL(urlQuery);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);

            java.io.FileInputStream fis = new java.io.FileInputStream(file);
            byte [] fileContents = org.apache.commons.io.IOUtils.toByteArray(fis);
            
            String authorization = "Basic " + new String(new org.apache.commons.codec.binary.Base64().encode("admin:geoserver".getBytes()));

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", authorization);
            conn.setRequestProperty("User-Agent","curl/7.58.0");
            conn.setRequestProperty("Host", "localhost:8080");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Content-type","text/plain");
            conn.setRequestProperty("Content-Length", String.valueOf(fileContents.length));
            conn.setRequestProperty("body", "file://"+file.getAbsolutePath());
            
//conn.setRequestProperty("Expect","100-continue");
//            java.io.OutputStream out = conn.getOutputStream();
//            out.write(fileContents);
//            out.close();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                //not sure what to do here, but I'm not getting a 100 return code anyway
                return "goood";
            }else{
                return conn.getResponseMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erroooooooooooor";
        }
    }
    
   
//    public static String requestPost(String urlQuery, File file){
//        URL url;
//        try {
//            
//            url = new URL(urlQuery);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            String encoding = new String(new Base64().encode("admin:geoserver".getBytes()));
//            con.setRequestProperty  ("Authorization", "Basic " + encoding);
//            
//            // Indicate that we want to write to the HTTP request body
//            con.setDoOutput(true);
//            con.setRequestMethod("PUT");
//            con.addRequestProperty("Content-Type", "text/plain");
//
//            OutputStream outputStreamToRequestBody = con.getOutputStream();
//            BufferedWriter httpRequestBodyWriter =
//                new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
//            
//            String boundaryString = "******";
//                    
//            // Include value from the myFileDescription text area in the post data
//            httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
//            httpRequestBodyWriter.write("Content-Disposition: form-data; name=\""+file.getName()+"\"");
//            httpRequestBodyWriter.write("\n\n");
//            httpRequestBodyWriter.write("A new shapefile");
//
//            // Include the section to describe the file
//            httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
//            httpRequestBodyWriter.write("Content-Disposition: form-data;"
//                    + "name=\""+file.getName()+"\";"
//                    + "filename=\""+ file.getName() +"\""
//                    + "\nContent-Type: text/plain\n\n");
//            httpRequestBodyWriter.flush();
//
//            // Write the actual file contents
//            FileInputStream inputStreamToLogFile = new FileInputStream(file);
//
//            int bytesRead;
//            byte[] dataBuffer = new byte[(int)file.length()];
//            while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
//                outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
//            }
//
//            outputStreamToRequestBody.flush();
//
//            // Mark the end of the multipart http request
//            httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
//            httpRequestBodyWriter.flush();
//
//            // Close the streams
//            outputStreamToRequestBody.close();
//            httpRequestBodyWriter.close();
//            
////            con.connect();
//            int status = con.getResponseCode();
//            if(status == HttpURLConnection.HTTP_OK){
//                //get Result
//                InputStream in = con.getInputStream();
//                BufferedReader buffer = new BufferedReader(
//                    new InputStreamReader(in)
//                );
//                String inputLine;
//                StringBuffer content = new StringBuffer();
//                while ((inputLine = buffer.readLine()) != null) {
//                    content.append(inputLine);
//                }
//
//                System.out.println("********************************");
//                System.out.println(content.toString());
//                System.out.println("*********************************");
//            }else{
//                throw new IOException("Server returned non-OK status: " + status);
//            }
//            
//            
//        } catch (Exception ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return "";
//        
//    }
//    
    
    
}
