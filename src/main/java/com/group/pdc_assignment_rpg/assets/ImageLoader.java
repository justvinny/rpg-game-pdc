/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.assets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ImageLoader {

    private static final String WALL_TILE_PATH = "./resources/images/cave_wall_tile.png";
    private static final String CHARACTER_SHEET_PATH = "./resources/images/character_sprite_sheet.png";
    private static final String OPEN_TREASURE_PATH = "./resources/images/open_treasure.png";
    private static final String CLOSED_TREASURE_PATH = "./resources/images/closed_treasure.png";
    private static final String LOADING_BG_PATH = "./resources/images/loading_screen_bg.png";
    private static final String BOSS_DEAD_PATH = "./resources/images/ghoul_dead.png";
    private static final String BOSS_IDLE_PATH = "./resources/images/ghoul_idle_sprite_sheet.png";
    private static ImageLoader instance = null;
    private Image wallTile, openTreasure, closedTreasure, loadingBG, bossDead;
    private BufferedImage characterUp, characterDown, characterLeft, characterRight, bossIdle;
    private int nPlayerSheet, nBossSheet;

    private ImageLoader() {
        setWallTile();
        setTreasure();
        setLoadingBG();
        setCharacterSpriteSheet();
        setBossSheet();
    }

    private void setWallTile() {
        ImageIcon icon = new ImageIcon(WALL_TILE_PATH);
        wallTile = icon.getImage();
    }

    private void setTreasure() {
        ImageIcon open = new ImageIcon(OPEN_TREASURE_PATH);
        openTreasure = open.getImage();
        
        ImageIcon closed = new ImageIcon(CLOSED_TREASURE_PATH);
        closedTreasure = closed.getImage();
    }

    private void setLoadingBG() {
        ImageIcon bg = new ImageIcon(LOADING_BG_PATH);
        loadingBG = bg.getImage();
    }
    
    private void setCharacterSpriteSheet() {
        try {
            BufferedImage sheet = ImageIO.read(new File(CHARACTER_SHEET_PATH));
            characterUp = sheet.getSubimage(0, 144, 144, 48);
            characterDown = sheet.getSubimage(0, 0, 144, 48);
            characterLeft = sheet.getSubimage(0, 48, 144, 48);
            characterRight = sheet.getSubimage(0, 96, 144, 48);
            nPlayerSheet = 1;
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setBossSheet() {
        ImageIcon dead = new ImageIcon(BOSS_DEAD_PATH);
        bossDead = dead.getImage();
        
        try {
            bossIdle = ImageIO.read(new File(BOSS_IDLE_PATH));
            nBossSheet = 0;
        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addPlayerSheetNum() {
        if (nPlayerSheet == 2) {
            nPlayerSheet = -1;
        }
        
        nPlayerSheet++;
    }
    
    public void resetPlayerSheetNum() {
        nPlayerSheet = 1;
    }
    
    public void addBossSheetNum() {
        if (nBossSheet == 3) {
            nBossSheet = -1;
        }
        
        nBossSheet++;
    }
    
    public Image getWallTile() {
        return wallTile;
    }

    public Image getOpenTreasure() {
        return openTreasure;
    }
    
    public Image getClosedTreasure() {
        return closedTreasure;
    }    
    
    public Image getLoadingBG() {
        return loadingBG;
    }
    
    public Image getDeadBoss() {
        return bossDead;
    }

    public BufferedImage getCharacterUp() {
        return characterUp.getSubimage(nPlayerSheet * 48, 0, 48, 48);
    }

    public BufferedImage getCharacterDown() {
        return characterDown.getSubimage(nPlayerSheet * 48, 0, 48, 48);
    }

    public BufferedImage getCharacterLeft() {
        return characterLeft.getSubimage(nPlayerSheet * 48, 0, 48, 48);
    }

    public BufferedImage getCharacterRight() {
        return characterRight.getSubimage(nPlayerSheet * 48, 0, 48, 48);
    }
    
    public BufferedImage getBossIdle() {
        return bossIdle.getSubimage(nBossSheet * 32, 0, 32, 32);
    }
    
    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }

        return instance;
    }

}
