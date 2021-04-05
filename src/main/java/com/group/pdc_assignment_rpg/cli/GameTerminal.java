package com.group.pdc_assignment_rpg.cli;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.group.pdc_assignment_rpg.logic.Boundaries;
import com.group.pdc_assignment_rpg.logic.Coordinates;
import com.group.pdc_assignment_rpg.logic.Creature;
import com.group.pdc_assignment_rpg.logic.Mob;
import com.group.pdc_assignment_rpg.logic.Navigation;
import com.group.pdc_assignment_rpg.logic.Player;
import java.io.IOException;
import java.util.List;

/**
 * Class that contains the Lanterna Terminal which will be used as the CLI for
 * our RPG game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameTerminal {

    /**
     * Constants
     */
    private static final int FPS = 1000 / 60;
    private static final int TERMINAL_WIDTH = 120;
    private static final int TERMINAL_HEIGHT = 40;
    private static final String CURSOR = ">>>";

    /**
     * Method that starts our game and its game loop.
     *
     * @param mapScene is the first game map we're playing on.
     * @param player is our playable character.
     * @param inventoryScene contains the CLI for our inventory.
     * @param mob is a monster/s in the map.
     */
    public static void start(MapScene mapScene, InventoryScene inventoryScene, Player player, Mob mob) {
        // Create our Lanterna Terminal which we will use for the game.
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        TerminalSize terminalSize = new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT);
        defaultTerminalFactory.setInitialTerminalSize(terminalSize);

        Screen screen = null;

        // Placeholder for map visibility. Needs to be refactored later on 
        // in its own Map Scene class.
        boolean mapIsVisible = true;

        // Placeholder for dummy Battle Scene.
        Coordinates battleSceneCoords = new Coordinates(
                BattleSceneConstants.CURSOR_X,
                BattleSceneConstants.CURSOR_Y_START);
        Boundaries battleSceneBounds = new Boundaries(
                BattleSceneConstants.CURSOR_X,
                BattleSceneConstants.CURSOR_Y_START,
                BattleSceneConstants.CURSOR_X,
                BattleSceneConstants.CURSOR_Y_END);
        Navigation battleSceneNavigation = new Navigation(
                battleSceneCoords,
                battleSceneBounds);
        BattleScene battleScene = new BattleScene(
                battleSceneNavigation,
                player,
                mob);

        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            // Will be used for drawing things on the console.
            TextGraphics textGraphics = screen.newTextGraphics();

            // Game loop
            while (true) {
                screen.clear();

                // Non-blocking read input from keyboard which means
                // the program won't come to a stop to wait for input.
                KeyStroke keyStroke = screen.pollInput();

                // Null check since we're using non-blocking read input
                // which means keyStroke will be null if we don't press
                // a key on the keyboard.
                if (keyStroke != null) {
                    // If the key pressed is Esc, quit the game.
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        screen.clear();
                        printExitMessage(textGraphics, terminal);
                        screen.refresh();
                        Thread.sleep(1000);
                        break;
                    } else if (keyStroke.getKeyType() == KeyType.Character
                            && keyStroke.getCharacter() == 'i'
                            && !battleScene.isVisible()) {
                        // Check if the key pressed is a character.
                        // if the character pressed is the letter i and
                        // the battle scene is not visible,
                        // toggle the inventory's visibility.
                        inventoryScene.toggle();
                        mapScene.toggle();
                    } else if (inventoryScene.isVisible()) {
                        // Navigating the inventory.
                        inventoryNavigation(keyStroke, inventoryScene);
                    } else if (battleScene.isVisible()) {
                        // If the battle scene is visible,
                        // enable battle scene navigation.
                        battleNavigation(keyStroke, battleScene);
                    } else if (mapIsVisible) {
                        // Navigate the map.
                        mapNavigation(keyStroke, player, mapScene.createScene());
                    }
                }

                // Detect monster and player collision
                if (player.getX() == mob.getX()
                        && player.getY() == mob.getY()
                        && !battleScene.isVisible()) {
                    battleScene.toggle();
                    mapScene.toggle();
                }

                int cursorPos = 0;
                // Print the inventory to the console if it is toggled by the user.
                if (inventoryScene.isVisible()) {
                    cursorPos = drawScene(inventoryScene, textGraphics);
                    drawNavigationCursor(inventoryScene.getNavigation(), textGraphics);
                } else if (battleScene.isVisible()) {
                    cursorPos = drawScene(battleScene, textGraphics);
                    drawNavigationCursor(battleScene.getNavigation(), textGraphics);
                } else if (mapIsVisible) {
                    // Print the game map instead if the inventory is invisbile.
                    cursorPos = drawScene(mapScene, textGraphics);

                    // Draw Player
                    drawCreature(player, mapScene, textGraphics);
                    
                    // Draw Mob
                    drawCreature(mob, mapScene, textGraphics);
                }

                // Print out the keys the user can press for the game.
                printAvailableKeys(textGraphics, cursorPos);

                // Refresh the screen to show changes if any.
                screen.refresh();

                // Game speed.
                Thread.sleep(FPS);
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        } finally {
            // Close the terminal once the game loop ends.
            if (screen != null) {
                try {
                    screen.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

    }

    /**
     * Prints the available keys that the user can press during the game.
     *
     * @param textGraphics used for drawing to our Lanterna console.
     * @param row is y position on where we want to start drawing on the
     * console.
     */
    private static void printAvailableKeys(TextGraphics textGraphics, int row) {
        String keysMenu = "Keys: [I] - Inventory   [Esc] - Exit Game\n";
        textGraphics.putString(0, row + 1, keysMenu);
    }

    /**
     * Prints the exit message when user presses the Esc key.
     *
     * @param textGraphics used for drawing to our Lanterna console.
     * @param terminal is the Lanterna terminal.
     * @throws IOException exception thrown when using TextGraphics putString
     * method.
     */
    private static void printExitMessage(TextGraphics textGraphics, Terminal terminal) throws IOException {
        String message = "Thanks for playing our game!";
        int colPos = (terminal.getTerminalSize().getColumns() / 2) - message.length() / 2;
        int rowPos = terminal.getTerminalSize().getRows() / 2;
        textGraphics.putString(colPos, rowPos, message);
    }

    /**
     * Handles inventory navigation.
     *
     * @param keyStroke is the key pressed.
     * @param inventoryScene contains the CLI for our inventory.
     */
    private static void inventoryNavigation(KeyStroke keyStroke, InventoryScene inventoryScene) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                inventoryScene.down();
                break;
            case ArrowUp:
                inventoryScene.up();
                break;
            case ArrowRight:
                inventoryScene.right();
                break;
            case ArrowLeft:
                inventoryScene.left();
        }
    }

    /**
     * Handles player navigation in the map.
     *
     * @param keyStroke
     * @param player
     */
    private static void mapNavigation(KeyStroke keyStroke, Player player, List<String> map) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                if (player.getY() < map.size()
                        && map.get(player.getY() + 1).charAt(player.getX()) != '#') {
                    player.down();
                }

                break;
            case ArrowUp:
                if (player.getY() > 0
                        && map.get(player.getY() - 1).charAt(player.getX()) != '#') {
                    player.up();
                }

                break;
            case ArrowRight:
                if (player.getX() < map.get(player.getY()).length()
                        && map.get(player.getY()).charAt(player.getX() + 1) != '#') {
                    player.right();
                }

                break;
            case ArrowLeft:
                if (player.getX() > 0
                        && map.get(player.getY()).charAt(player.getX() - 1) != '#') {
                    player.left();
                }
        }
    }

    /**
     * Handles navigation for battle scenes. Allowing players to navigate
     * between Attack, Defend, and Escape.
     *
     * @param keyStroke
     * @param battleScene
     */
    private static void battleNavigation(KeyStroke keyStroke, BattleScene battleScene) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                battleScene.down();
                break;
            case ArrowUp:
                battleScene.up();
                break;
            case Enter:
                switch (BattleSceneConstants.toEnum(battleScene.getNavigation()
                        .getCoordinates().getY())) {
                    case ATTACK:
                        System.out.println("You have attacked!");
                        break;
                    case DEFEND:
                        System.out.println("You have defended!");
                        break;
                    case ESCAPE:
                        System.out.println("You have escaped!");
                }
        }
    }

    /**
     * Helper method to draw creatures on the map. Creatures can be players or
     * mobs.
     *
     * @param creature creature to draw.
     * @param rowNum which row of the map.
     * @param mapFullRow a full for from the map.
     * @param textGraphics used to draw to our Lanterna console.
     */
    private static void drawCreature(
            Creature creature,
            MapScene mapScene,
            TextGraphics textGraphics) {

        List<String> map = mapScene.createScene();

        if (map.get(creature.getY()).charAt(creature.getX()) != '#') {
            textGraphics.setForegroundColor(creature.getColor());
            textGraphics.setCharacter(creature.getX(), creature.getY(),
                    creature.getSymbol());
            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        }
    }

    /**
     * Helper method to draw Scenes such as Maps, Inventory, Battles.
     *
     * @param scene is a scene to draw.
     * @param textGraphics is used to draw to our Lanterna console.
     */
    private static int drawScene(Scene scene, TextGraphics textGraphics) {
        List<String> sceneStrList = scene.createScene();

        int cursorPos = 0;
        for (int i = 0; i < sceneStrList.size(); i++) {
            textGraphics.putString(0, i, sceneStrList.get(i));
            cursorPos = i;
        }

        return cursorPos + 1;
    }

    private static void drawNavigationCursor(Navigation navigation, TextGraphics textGraphics) {
        textGraphics.setForegroundColor(TextColor.ANSI.BLUE);
        textGraphics.putString(navigation.getCoordinates().getX(),
                navigation.getCoordinates().getY(), CURSOR, SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }
}
