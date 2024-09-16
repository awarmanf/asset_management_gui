/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yudi.swing.util;

import java.awt.Point;
import java.util.Random;

public class GetRandomLocation {
    
    public static Point NewLocation(int margin, int width, int height) {
        
        Random test = new Random();
        
        int x = margin + test.nextInt(width);
        int y = margin + test.nextInt(height);
        
        return new Point(x,y);
    }
    
    public static void main (String[] args) {
        
        Point p = GetRandomLocation.NewLocation(25, 775, 575);
       
    }
    
}
