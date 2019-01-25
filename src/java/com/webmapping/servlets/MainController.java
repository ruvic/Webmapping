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
        this.getServletContext().getRequestDispatcher("/index1.jsp").forward(request, response);
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
            System.out.println("Dans la methode");
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
        
        this.getServletContext().getRequestDispatcher("/index1.jsp").forward(request, response);
        
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
    
}
