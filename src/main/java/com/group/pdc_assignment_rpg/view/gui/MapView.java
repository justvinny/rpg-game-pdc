/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.MainDriver;
import com.group.pdc_assignment_rpg.assets.ImageLoader;
import com.group.pdc_assignment_rpg.assets.MonsterLoader;
import com.group.pdc_assignment_rpg.camera.GameCamera;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.Combat;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.logic.navigation.Collision;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.DOWN;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.LEFT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.RIGHT;
import static com.group.pdc_assignment_rpg.logic.navigation.Direction.UP;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class MapView extends JPanel implements ActionListener {

    public static final int TILE_HEIGHT = 100;
    public static final int TILE_WIDTH = 100;
    private static final int FPS = 60;

    private List<String> mapTxt;
    private List<Treasure> treasures;
    private GameCamera gameCamera;
    private Player player;
    private Mob boss;

    public MapView(List<Treasure> treasures) {
        setMap();
        setTreasures(treasures);
        panelSettings();
        setTimer();
        player = new Player("Placeholder");
        boss = MonsterLoader.getInstance().getMob(MainDriver.BOSS_MOB);
        gameCamera = new GameCamera(player, FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Game logic
        playerMovement();
        bossCombat();
        randomEncounter();
        
        // Render
        drawMap(g);
        drawTreasure(g);
        drawBoss(g);
        drawPlayer(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void playerMovement() {
        if (player.isPlayerRunning()) {
            if (!Collision.wallCollision(player, mapTxt)
                    && !Collision.treasureCollision(player, treasures)
                    && !Collision.bossCollision(player, boss)) {
                switch (player.getDirection()) {
                    case UP:
                        player.up();
                        player.increaseStep();
                        break;
                    case DOWN:
                        player.down();
                        player.increaseStep();
                        break;
                    case LEFT:
                        player.left();
                        player.increaseStep();
                        break;
                    case RIGHT:
                        player.right();
                        player.increaseStep();
                }
            } else {
                player.setIdle();

            }
        }
    }

    private void bossCombat() {
        if (Collision.bossCollision(player, boss)) {
            player.setIdle();
            ScreenManager screenManager = ScreenManager.getInstance();
            screenManager.getCombat().setCombatants(player, boss);
            ScreenManager.getInstance().setCurrentScreen(ScreenManagerConstants.COMBAT);
        }
    }

    private void randomEncounter() {
        if (player.getSteps() == Combat.getNStepsToCombat()) {
            player.setIdle();
            player.resetSteps();
            Combat.resetNStepsToCombat();
            ScreenManager screenManager = ScreenManager.getInstance();

            // Adjust random mob based on player level.
            if (player.getLevel().getLvl() < 2) {
                screenManager.getCombat().setCombatants(player, MonsterLoader.getInstance().getMob("Slime"));
            } else if (player.getLevel().getLvl() < 3) {
                int rand = (int) (Math.random() * 2);
                switch (rand) {
                    case 0:
                        screenManager.getCombat().setCombatants(player, MonsterLoader.getInstance().getMob("Slime"));
                        break;
                    case 1:
                        screenManager.getCombat().setCombatants(player, MonsterLoader.getInstance().getMob("Ghoul"));
                }
            } else {
                screenManager.getCombat().setCombatants(player, MonsterLoader.getInstance().getRandomMob());
            }

            ScreenManager.getInstance().setCurrentScreen(ScreenManagerConstants.COMBAT);
        }
    }

    private void drawMap(Graphics g) {
        // Draw the map relative to the player's center which gives the
        // illusion that we have a moving screen thanks to the game camera offset.
        // Note: -TILE_WIDTH and -TILE_HEIGHT is included in the calculation
        // to center our character more.
        int x = -TILE_WIDTH + (int) gameCamera.getXOffset();
        int y = -TILE_HEIGHT + (int) gameCamera.getYOffset();
        Image caveWallTile = ImageLoader.getInstance().getWallTile();
        // Draw the map using a wall tile based on the ASCII text map representation
        // we have.
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

    private void drawTreasure(Graphics g) {
        Image openTreasure = ImageLoader.getInstance().getOpenTreasure();
        Image closedTreasure = ImageLoader.getInstance().getClosedTreasure();

        for (Treasure treasure : treasures) {
            int x = -TILE_WIDTH + (int) gameCamera.getXOffset()
                    + treasure.getCoordinates().getX() * TILE_WIDTH;
            int y = -TILE_HEIGHT + (int) gameCamera.getYOffset()
                    + treasure.getCoordinates().getY() * TILE_HEIGHT;

            // Two images for treasures. A closed chest image if the treasure
            // has still not been opened and an open image otherwise.
            if (treasure.isOpened()) {
                g.drawImage(openTreasure, x, y, TILE_WIDTH, TILE_HEIGHT, null);
            } else {
                g.drawImage(closedTreasure, x, y, TILE_WIDTH, TILE_HEIGHT, null);
            }
        }
    }

    private void drawBoss(Graphics g) {
        // -(TILE_WIDTH * 3) to properly scale the boss to 3x the size 
        // of everything else. Same with height.
        int x = -(TILE_WIDTH * 3) + (int) gameCamera.getXOffset()
                + boss.getCoordinates().getX() * TILE_WIDTH;
        int y = -(TILE_HEIGHT * 2) + (int) gameCamera.getYOffset()
                + boss.getCoordinates().getY() * TILE_HEIGHT;

        if (boss.isAlive()) {
            BufferedImage bossSheet = ImageLoader.getInstance().getBossIdle();
            g.drawImage(bossSheet, x, y, TILE_WIDTH * 3, TILE_HEIGHT * 3, null);
            ImageLoader.getInstance().addBossSheetNum();
        } else {
            Image dead = ImageLoader.getInstance().getDeadBoss();
            g.drawImage(dead, x, y, TILE_WIDTH * 3, TILE_HEIGHT * 3, null);
        }
    }

    private void drawPlayer(Graphics g) {
        // Center player to screen
        int x = -TILE_WIDTH + FRAME_WIDTH / 2;
        int y = -TILE_HEIGHT + FRAME_HEIGHT / 2;

        ImageLoader loader = ImageLoader.getInstance();
        if (!player.isPlayerRunning()) {
            // Reset to idle animation if player is not running.
            loader.resetPlayerSheetNum();
        }
        // Default starting animation where character is facing down.
        BufferedImage characterSprite = loader.getCharacterDown();
        switch (player.getDirection()) {
            case UP:
                // Character facing up
                characterSprite = loader.getCharacterUp();
                break;
            case DOWN:
                // Character facing down
                characterSprite = loader.getCharacterDown();
                break;
            case LEFT:
                // Character facing left
                characterSprite = loader.getCharacterLeft();
                break;
            case RIGHT:
                // Character facing right
                characterSprite = loader.getCharacterRight();
        }
        // Load a different part of the sprite sheet.
        loader.addPlayerSheetNum();
        g.drawImage(characterSprite, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private void setMap() {
        try {
            mapTxt = ResourceLoaderUtility.loadMap("game-map");
        } catch (InvalidMapException ex) {
            Logger.getLogger(MapView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
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
