/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webmapping.servlets;

import com.webmapping.utils.ParameterStringBuilder;
import com.webmapping.utils.Requester;
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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ruvic
 */

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controller</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "http://localhost:8080/geoserver/rest/workspaces/cameroon/layers";
        String result = Requester.requestGet(url);
        request.setAttribute("layers", result);
        this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        Path filePath = Paths.get(filePart.getSubmittedFileName());
        String filename = filePath.getFileName().toString();
        
        String shapefilesPath = this.getServletContext().getRealPath("/WEB-INF/shapefiles/");
        File uploads = new File(shapefilesPath);
        
        File file = new File(uploads, filename);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }
        
        String url = "http://localhost:8080/geoserver/rest/workspaces/cameroon/datastores/cameroon_map/external.shp";
        
        System.out.println("*******************************");
        System.out.println(file.getAbsolutePath());
        System.out.println("*******************************");
        
        
        
        String result = Requester.requestPost(url, file);
        
        System.out.println("---------------------------------------");
        System.out.println(result);
        System.out.println("---------------------------------------");
        
        
        file.delete();
    }
    
   
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
//    private String streamToString(InputStream inputStream) throws IOException {
//        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
//        inputStream.close();
//        return text;
//    }
    
    
//    public String requestGet(String urlQuery){
//        URL url;
//        InputStream in = null;
//        try {
//            url = new URL(urlQuery);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            String encoding = new String(new Base64().encode("admin:geoserver".getBytes()));
//            con.setRequestProperty  ("Authorization", "Basic " + encoding);
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Accept", "application/json");
//            con.setRequestMethod("GET");
//            con.connect();
//            
//            //get Result
//            in = con.getInputStream();
//            BufferedReader buffer = new BufferedReader(
//                new InputStreamReader(in)
//            );
//            String inputLine;
//            StringBuffer content = new StringBuffer();
//            while ((inputLine = buffer.readLine()) != null) {
//                content.append(inputLine);
//            }
//            
//            return content.toString();
//            
//        } catch (Exception ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//        
//    }
    
//    public String requestPost(String urlQuery, File file){
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
////            BufferedWriter httpRequestBodyWriter =
////                new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
////
////            // Include value from the myFileDescription text area in the post data
////            httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
////            httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"shapefile\"");
////            httpRequestBodyWriter.write("\n\n");
////            httpRequestBodyWriter.write("Log file for 20150208");
////
////            // Include the section to describe the file
////            httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
////            httpRequestBodyWriter.write("Content-Disposition: form-data;"
////                    + "name=\""+file.getName()+"\";"
////                    + "filename=\""+ file.getName() +"\""
////                    + "\nContent-Type: text/plain\n\n");
////            httpRequestBodyWriter.flush();
//
//            // Write the actual file contents
//            FileInputStream inputStreamToLogFile = new FileInputStream(file);
//
//            int bytesRead;
//            byte[] dataBuffer = new byte[1024];
//            while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
//                outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
//            }
//
//            outputStreamToRequestBody.flush();
//
//            // Mark the end of the multipart http request
////            httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
////            httpRequestBodyWriter.flush();
//
//            // Close the streams
//            outputStreamToRequestBody.close();
////            httpRequestBodyWriter.close();
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
    
}
