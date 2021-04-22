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
import com.group.pdc_assignment_rpg.logic.Combat;
import com.group.pdc_assignment_rpg.logic.navigation.Boundaries;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import com.group.pdc_assignment_rpg.logic.entities.Creature;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.io.IOException;
import java.util.Iterator;
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
    private static final String GAME_TITLE = "RPG Game";

    /**
     * Fields
     */
    private MapScene mapScene;
    private InventoryScene inventoryScene;
    private BattleScene battleScene;
    private Player player;
    private Mob mob;
    private Terminal terminal;
    private Screen screen;
    private TextGraphics textGraphics;
    private Combat combat;

    /**
     *
     * @param mapScene is the first game map we're playing on.
     * @param player is our playable character.
     * @param inventoryScene contains the CLI for our inventory.
     * @param mob is a monster/s in the map.
     */
    public GameTerminal(MapScene mapScene, InventoryScene inventoryScene, Player player, Mob mob) {
        this.mapScene = mapScene;
        this.inventoryScene = inventoryScene;
        this.player = player;
        this.mob = mob;
    }

    /**
     * Method that starts our game and its game loop.
     */
    public void start() throws IOException, InterruptedException {
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
        battleScene = new BattleScene(
                battleSceneNavigation,
                player,
                mob);

        initTerminal();
        gameLoop();
    }

    /**
     * Setup our Lanterna Terminal for the game.
     *
     * @throws IOException
     */
    private void initTerminal() throws IOException {
        // Create our Lanterna Terminal which we will use for the game.
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        TerminalSize terminalSize = new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT);
        defaultTerminalFactory.setInitialTerminalSize(terminalSize);
        defaultTerminalFactory.setTerminalEmulatorTitle(GAME_TITLE);
        terminal = defaultTerminalFactory.createTerminal();

        // Create the screen for the terminal.
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        // Will be used for drawing things on the console.
        textGraphics = screen.newTextGraphics();
    }

    /**
     * Our main game loop where are our core gameplay is located.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void gameLoop() throws IOException, InterruptedException {
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
                    // Save the player and its inventory upon exit to persisent storage.
                    ResourceLoaderUtility.writePlayerData(player);
                    ResourceLoaderUtility.writeInventoryData(player);

                    screen.clear();
                    printExitMessage();
                    screen.refresh();
                    Thread.sleep(1000);
                    screen.close();
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
                    mapScene.refreshScene();
                } else if (inventoryScene.isVisible()) {
                    // Navigating the inventory.
                    inventoryNavigation(keyStroke);
                } else if (battleScene.isVisible()) {
                    // If the battle scene is visible,
                    // enable battle scene navigation.
                    battleNavigation(keyStroke);
                } else if (mapScene.isVisible()) {
                    // Navigate the map.
                    mapNavigation(keyStroke);

                    // Detect collision of player with other objects.
                    detectCollision();
                }
            }

            int cursorPos = 0;
            // Print the inventory to the console if it is toggled by the user.
            if (inventoryScene.isVisible()) {
                cursorPos = drawScene(inventoryScene);
                drawNavigationCursor(inventoryScene.getNavigation());
            } else if (battleScene.isVisible()) {
                cursorPos = drawScene(battleScene);
                drawNavigationCursor(battleScene.getNavigation());

                // Check if battle still ongoing.
                if (combat != null) {
                    if (!combat.isFighting()) {
                        screen.refresh();
                        // Pause screen to show player who won the battle.
                        Thread.sleep(2000);
                        player.up();
                        battleScene.toggle();
                        mapScene.toggle();
                    }
                }
            } else if (mapScene.isVisible()) {
                // Print the game map instead if the inventory is invisbile.
                cursorPos = drawScene(mapScene);

                // Draw Player
                drawCreature(player);

                // Draw Mob
                drawCreature(mob);

                // Colour treasures yellow.
                colourTreasures();
            }

            // Refresh the screen to show changes if any.
            screen.refresh();

            // Game speed.
            Thread.sleep(FPS);
        }
    }

    /**
     * Prints the exit message when user presses the Esc key.
     *
     * @param textGraphics used for drawing to our Lanterna console.
     * @param terminal is the Lanterna terminal.
     * @throws IOException exception thrown when using TextGraphics putString
     * method.
     */
    private void printExitMessage() throws IOException {
        String message = "Thanks for playing our game!";
        int colPos = (terminal.getTerminalSize().getColumns() / 2) - message.length() / 2;
        int rowPos = terminal.getTerminalSize().getRows() / 2;
        textGraphics.putString(colPos, rowPos, message);
    }

    /**
     * Handles inventory navigation. Using arrow keys (Up, Down, Left, Right) in
     * the inventory will allow the inventory cursor to move around our UI and
     * select your items.
     *
     * @param keyStroke is the key pressed.
     * @param inventoryScene contains the CLI for our inventory.
     */
    private void inventoryNavigation(KeyStroke keyStroke) {
        // Resets the action message in the inventory scene if we pressed 
        // a key that is not a character. For example, Arrow Keys are considered
        // as keys that are not characters. Hence, pressing these will trigger
        // this condition.
        if (keyStroke.getKeyType() != KeyType.Character) {
            inventoryScene.resetActionMessage();
        }

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
                break;
            case Character:
                switch (keyStroke.getCharacter()) {
                    case 'u':
                        inventoryScene.use();
                        break;
                    case 'd':
                        inventoryScene.drop();
                }
        }
    }

    /**
     * Handles player navigation in the map. Using the arrow keys (up, down,
     * left, right) will allow the player to move on the map.
     *
     * @param keyStroke
     * @param player
     */
    private void mapNavigation(KeyStroke keyStroke) {
        List<String> map = mapScene.createScene();

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
    private void battleNavigation(KeyStroke keyStroke) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                battleScene.down();
                break;
            case ArrowUp:
                battleScene.up();
                break;
            case Enter:
                BattleSceneConstants action = BattleSceneConstants.toEnum(
                        battleScene.getNavigation().getCoordinates().getY());
                switch (action) {
                    case ATTACK:
                        combat.battle(action);
                        System.out.println("You have attacked!");
                        break;
                    case DEFEND:
                        combat.battle(action);
                        System.out.println("You have defended!");
                        break;
                    case ESCAPE:
                        combat.battle(action);
                        if (!combat.isFighting()) {
                            System.out.println("You have escaped!");
                        }
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
    private void drawCreature(Creature creature) {
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
    private int drawScene(Scene scene) {
        List<String> sceneStrList = scene.createScene();

        int cursorPos = 0;
        for (int i = 0; i < sceneStrList.size(); i++) {
            textGraphics.putString(0, i, sceneStrList.get(i));
            cursorPos = i;
        }

        return cursorPos + 1;
    }

    /**
     * Draws the cursor we use for Inventory/Battle Scene navigation.
     *
     * @param navigation
     * @param textGraphics
     */
    private void drawNavigationCursor(Navigation navigation) {
        textGraphics.setForegroundColor(TextColor.ANSI.BLUE);
        textGraphics.putString(navigation.getCoordinates().getX(),
                navigation.getCoordinates().getY(), CURSOR, SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }

    /**
     * Colours the treasures on the map so it really pops out to the user.
     */
    private void colourTreasures() {
        List<String> map = mapScene.createScene();
        for (Treasure treasure : mapScene.getTreasures()) {
            if (map.get(treasure.getCoordinates().getY()).charAt(treasure.getCoordinates().getX()) != '#') {
                textGraphics.setForegroundColor(TextColor.ANSI.valueOf(Treasure.COLOUR));
                textGraphics.setCharacter(treasure.getCoordinates().getX(), treasure.getCoordinates().getY(),
                        Treasure.SYMBOl);
                textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            }
        }

    }

    /**
     * Detects collision by the player and different objects.
     */
    private void detectCollision() {
        Iterator<Treasure> iterator = mapScene.getTreasures().iterator();

        // Detect collision with treasures.
        while (iterator.hasNext()) {
            Treasure treasure = iterator.next();

            if (player.getX() == treasure.getCoordinates().getX()
                    && player.getY() == treasure.getCoordinates().getY()
                    && mapScene.isVisible()) {
                Item item = treasure.open();
                inventoryScene.getInventory().add(item);
                inventoryScene.getInventory().toString();
                mapScene.setActionMessage("Picked up " + item);
                iterator.remove();
            }
        }

        // Detect monster and player collision
        if (player.getX() == mob.getX()
                && player.getY() == mob.getY()
                && !battleScene.isVisible()) {
            battleScene.toggle();
            mapScene.toggle();
            combat = new Combat(player, mob);
            battleScene.setCombat(combat);
        }

    }
}
