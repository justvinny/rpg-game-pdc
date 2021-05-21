/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MapView extends JPanel {
    
//    public static final Dimension GAME_MAP_DIMS = new Dimension((int) (MainFrameView.FRAME_WIDTH * .99), (int) (MainFrameView.FRAME_HEIGHT * .98));
    
    public MapView() {
        panelSettings();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 100, 100);
    }
    
    
    private void panelSettings() {
        setBackground(Color.BLACK);
//        setPreferredSize(GAME_MAP_DIMS);
    }
    
}
