/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webmapping.utils;

import java.util.ArrayList;

/**
 *
 * @author ruvic
 */
public class CardData {
    public String layers;
    public ArrayList<String> attributes;
    
    public CardData(String layers, ArrayList<String> attributes){
        this.layers = layers;
        this.attributes = attributes;
    }
}
