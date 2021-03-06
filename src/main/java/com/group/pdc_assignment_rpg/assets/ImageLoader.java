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
 * Singleton class to used to hold all our image assets that will be used in the game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class ImageLoader {

    /*
        Constants
    */
    private static final String WALL_TILE_PATH = "./resources/images/cave_wall_tile.png";
    private static final String CHARACTER_STATIC_PATH = "./resources/images/character_battle.png";
    private static final String CHARACTER_SHEET_PATH = "./resources/images/character_sprite_sheet.png";
    private static final String OPEN_TREASURE_PATH = "./resources/images/open_treasure.png";
    private static final String CLOSED_TREASURE_PATH = "./resources/images/closed_treasure.png";
    private static final String LOADING_BG_PATH = "./resources/images/loading_screen_bg.png";
    private static final String BOSS_DEAD_PATH = "./resources/images/golem_death.png";
    private static final String BOSS_IDLE_PATH = "./resources/images/golem_idle_sprite_sheet.png";
    private static final String GHOUL_STATIC_PATH = "./resources/images/ghoul_battle.png";
    private static final String GOLEM_STATIC_PATH = "./resources/images/golem_battle.png";
    private static final String EXECUTIONER_STATIC_PATH = "./resources/images/executioner_battle.png";
    private static final String SLIME_STATIC_PATH = "./resources/images/slime_battle.png";
    private static ImageLoader instance = null;
    
    /*
        Fields
    */
    private Image wallTile, openTreasure, closedTreasure, loadingBG, bossDead,
            characterStatic, ghoulStatic, golemStatic, slimeStatic, executionerStatic;
    private BufferedImage characterUp, characterDown, characterLeft, characterRight, bossIdle;
    private int nPlayerSheet, nBossSheet;

    /*
        Private constructor
    */
    private ImageLoader() {
        setWallTile();
        setTreasure();
        setLoadingBG();
        setCharacterSpriteSheet();
        setBossSheet();
        setStaticMonsters();
    }

    /*
        Setters
    */
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
        ImageIcon playerBattle = new ImageIcon(CHARACTER_STATIC_PATH);
        characterStatic = playerBattle.getImage();

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

    private void setStaticMonsters() {
        ImageIcon ghoulBattle = new ImageIcon(GHOUL_STATIC_PATH);
        ghoulStatic = ghoulBattle.getImage();

        ImageIcon golemBattle = new ImageIcon(GOLEM_STATIC_PATH);
        golemStatic = golemBattle.getImage();
        
        ImageIcon executionerBattle = new ImageIcon(EXECUTIONER_STATIC_PATH);
        executionerStatic = executionerBattle.getImage();
        
        ImageIcon slimeBattle = new ImageIcon(SLIME_STATIC_PATH);
        slimeStatic = slimeBattle.getImage();
    }

    public void addPlayerSheetNum() {
        if (nPlayerSheet == 2) {
            nPlayerSheet = -1;
        }

        nPlayerSheet++;
    }

    /*
        Methods used for animation.
    */
    public void resetPlayerSheetNum() {
        nPlayerSheet = 1;
    }

    public void addBossSheetNum() {
        if (nBossSheet == 7) {
            nBossSheet = -1;
        }

        nBossSheet++;
    }

    /*
        Getters
    */
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

    public Image getCharacterStatic() {
        return characterStatic;
    }

    public BufferedImage getBossIdle() {
        return bossIdle.getSubimage(nBossSheet * 100, 0, 100, 100);
    }
    
    /**
     * Get a mob image based on mob name given.
     * @param mobName
     * @return 
     */
    public Image getMob(String mobName) {
        switch (mobName) {
            case "Ghoul":
                return ghoulStatic;
            case "Slime":
                return slimeStatic;
            case "Guardian Golem":
                return golemStatic;
            case "Executioner":
                return executionerStatic;
            default:
                return slimeStatic;
        }
    }

    /**
     * Get singleton instance.
     * @return instance.
     */
    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }

        return instance;
    }

}
