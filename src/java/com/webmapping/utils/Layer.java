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
public class Layer {
    private List<LayerItem> layer;

    public void print(){
        System.out.println("****  Layer *****");
        for (LayerItem layerItem : layer) {
            System.out.println(layerItem.toString());
        }
    }
    
    /**
     * @return the layer
     */
    public List<LayerItem> getLayer() {
        return layer;
    }

    /**
     * @param layer the layer to set
     */
    public void setLayer(List<LayerItem> layer) {
        this.layer = layer;
    }
}
