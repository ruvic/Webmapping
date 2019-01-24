/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webmapping.servlets;

import com.google.gson.Gson;
import com.webmapping.utils.CardData;
import com.webmapping.utils.LayerItem;
import com.webmapping.utils.Layers;
import com.webmapping.utils.ParameterStringBuilder;
import com.webmapping.utils.Requester;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.ArrayList;
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

@MultipartConfig()
public class MainController extends HttpServlet {
    
    public static final int TAILLE_TAMPON = 10240;
    public static String CHEMIN_FICHIERS=""; 
    
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
        CardData card = getCardData();
        request.setAttribute("layers", card.layers);
        request.setAttribute("attributes", card.attributes);
        this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
    
    public CardData getCardData(){
        String baseUrl = "http://localhost:8080/geoserver/rest/workspaces/cameroon/datastores/cameroon_map/featuretypes/";
        String url = "http://localhost:8080/geoserver/rest/workspaces/cameroon/layers";
        String result = Requester.requestGet(url);
        
        Layers obj = new Gson().fromJson(result, Layers.class);
        ArrayList<String> attributesBase = new ArrayList<>();
        for (LayerItem layerItem : obj.getLayers().getLayer()) {
            String res = Requester.requestGet(baseUrl+layerItem.getName());
            attributesBase.add(res);
        }
        
        return new CardData(result, attributesBase);
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
        CHEMIN_FICHIERS = this.getServletContext().getRealPath("/WEB-INF/shapefiles/")+'/';
        
        if (filename != null && !filename.isEmpty()) { // Si on a bien un fichier
            String nomChamp = filePart.getName();
            // Corrige un bug du fonctionnement d'Internet Explorer
             filename = filename.substring(filename.lastIndexOf('/') + 1)
                    .substring(filename.lastIndexOf('\\') + 1);
            writeFile(filePart, filename, CHEMIN_FICHIERS);  // On écrit définitivement le fichier sur le disque
        }
        
        String body = "file:///" + CHEMIN_FICHIERS + filename;
        String url = "http://localhost:8080/geoserver/rest/workspaces/cameroon/datastores/cameroon_map/external.shp";
        Requester.requestPost(url, body);
        
        CardData card = getCardData();
        request.setAttribute("layers", card.layers);
        request.setAttribute("attributes", card.attributes);
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        
    }
    
    private void writeFile( Part part, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
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
