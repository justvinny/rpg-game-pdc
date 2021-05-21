/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.assets.ImageLoader;
import com.group.pdc_assignment_rpg.camera.GameCamera;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MapView extends JPanel implements ActionListener {
    
    public static final int TILE_HEIGHT = 100;
    public static final int TILE_WIDTH = 100;
    private static final int FPS = 25;

    private static float pX = 9;
    private static float pY = 23;
    
    private List<String> mapTxt;
    private GameCamera gameCamera;
    private Player player;
    
    public MapView() {
        setMap();
        panelSettings();
        setTimer();
        player = new Player("Placeholder");
        gameCamera = new GameCamera(player, FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        drawPlayer(g);
    }

    private void drawMap(Graphics g) {
        int x = -TILE_WIDTH + (int) gameCamera.getXOffset();
        int y = -TILE_HEIGHT + (int) gameCamera.getYOffset();
        Image caveWallTile = ImageLoader.getInstance().getWallTile();
        for (String mapRow : mapTxt) {
            for (Character tile : mapRow.toCharArray()) {
                if (tile == '#') {
                    g.drawImage(caveWallTile, x, y, TILE_WIDTH, TILE_HEIGHT, null);
                }
                x += TILE_WIDTH;
            }
            x = -TILE_WIDTH + (int) gameCamera.getXOffset();
            y += TILE_HEIGHT;
        }
    }
    
    private void drawPlayer(Graphics g) {
        int x = -TILE_WIDTH + FRAME_WIDTH / 2;
        int y = -TILE_HEIGHT + FRAME_HEIGHT / 2;
        Image characterSprite = ImageLoader.getInstance().getCharacter();
        g.drawImage(characterSprite, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private void setMap() {
        try {
            mapTxt = ResourceLoaderUtility.loadMap("game-map");
        } catch (InvalidMapException ex) {
            Logger.getLogger(MapView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void panelSettings() {
        setBackground(Color.BLACK);
    }

    private void setTimer() {
        Timer timer = new Timer(FPS, this);
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    public void setPlayerCamera(Player player) {
        this.player = player;
        gameCamera.setPlayer(player);
    }

    public List<String> getMapTxt() {
        return mapTxt;
    }
}
