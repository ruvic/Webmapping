/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webmapping.utils;

import java.util.List;

/**
 *
 * @author ruvic
 */
public class Layers {
    private Layer layers;

    
    public void print(){
        layers.print();
    }
    
    /**
     * @return the layers
     */
    public Layer getLayers() {
        return layers;
    }

    /**
     * @param layers the layers to set
     */
    public void setLayers(Layer layers) {
        this.layers = layers;
    }
}

