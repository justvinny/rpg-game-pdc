package com.group.pdc_assignment_rpg;

import com.group.pdc_assignment_rpg.assets.MonsterLoader;
import com.group.pdc_assignment_rpg.cli.*;
import com.group.pdc_assignment_rpg.cli.BattleScene;
import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import com.group.pdc_assignment_rpg.logic.navigation.Boundaries;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.cli.GameTerminal;
import com.group.pdc_assignment_rpg.cli.InventoryScene;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_X_STEP;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_END;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_START;
import static com.group.pdc_assignment_rpg.cli.InventorySceneConstants.CURSOR_Y_STEP;
import com.group.pdc_assignment_rpg.exceptions.InvalidMapException;
import com.group.pdc_assignment_rpg.logic.entities.PlayerListModel;
import com.group.pdc_assignment_rpg.logic.items.Treasure;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import com.group.pdc_assignment_rpg.view.gui.CombatController;
import com.group.pdc_assignment_rpg.view.gui.GameController;
import com.group.pdc_assignment_rpg.view.gui.InventoryController;
import com.group.pdc_assignment_rpg.view.gui.MainFrameController;
import com.group.pdc_assignment_rpg.view.gui.MainFrameView;
import com.group.pdc_assignment_rpg.view.gui.PlayerLoadingController;
import com.group.pdc_assignment_rpg.view.gui.ScreenManager;
import com.group.pdc_assignment_rpg.view.gui.ScreenManagerConstants;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point to the RPG game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MainDriver {

    /**
     * Constants
     */
    public static final String BOSS_MOB = "Ghoul King";
    public static final int GAME_PAUSE_MS = 1000;
    public static final double BOSS_ATTACK_PERSONALITY = .7;
    public static final double BOSS_DEFEND_PERSONALITY = .3;
    public static final double BOSS_ESCAPE_PERSONALITY = 0;

    public static void main(String[] args) {
        initGUI();

        // To use the console version, uncomment the next lines
        // and comment out initGUI(); 
//        try {
//            initConsole();
//        } catch (InvalidMapException ex) {
//            Logger.getLogger(MainDriver.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private static void initGUI() {
        // Player list
        PlayerListModel playerListModel = new PlayerListModel();

        // Init monster loader.
        MonsterLoader.initMonsterLoader();
        
        // Treasure list
        List<Treasure> treasures = ResourceLoaderUtility.loadTreasures();

        // GUI
        // Main frame that will contain all our screns
        MainFrameView mainFrame = new MainFrameView();
        
        // Screen manager singleton used for switching between screens.
        ScreenManager.initScreenManager(mainFrame);
        ScreenManager screenManager = ScreenManager.getInstance();
        screenManager.getPlayerLoading().setPlayerListModel(playerListModel.getPlayerList());
        screenManager.setCurrentScreen(ScreenManagerConstants.PLAYER_LOADING); // First Screen

        // Controllers for different screens from the ScreenManager.
        // Note. All controllers have access to the view thanks to the
        // ScreenManager so none of them need a reference passed of the
        // views to their constructors.
        MainFrameController mainFrameController = new MainFrameController(treasures);
        PlayerLoadingController playerLoadingController = 
                new PlayerLoadingController(playerListModel, treasures);
        GameController gameController = new GameController();
        InventoryController inventoryController = new InventoryController();
        CombatController combaController = new CombatController();
    }

    private static void initConsole() throws InvalidMapException {
        try {
            // Load player and start screen.
            Player player = loadStartScreen();

            // Load boss monster.
            Mob boss = ResourceLoaderUtility.loadMobFromDB(BOSS_MOB);
            boss.setInventory(ResourceLoaderUtility.loadMobDrops(BOSS_MOB));
            boss.populatePersonality(
                    BOSS_ATTACK_PERSONALITY, // Boss should attack more
                    BOSS_DEFEND_PERSONALITY, // Boss should defend sometimes.
                    BOSS_ESCAPE_PERSONALITY); // Boss shouldn't flee.

            // Load map treasures.
            List<Treasure> treasures = ResourceLoaderUtility.loadTreasures();

            // Load map.
            List<String> map = ResourceLoaderUtility.loadMap("game-map");
            MapScene mapScene = new MapScene(map, treasures, player);
            mapScene.toggle(); // Make map visible.

            // Make inventory scene.
            InventoryScene inventoryScene = generateInventoryScene(player);

            // Make battle scene.
            BattleScene battleScene = generateBattleScene(player);

            // Start our game.
            GameTerminal gameTerminal = new GameTerminal(mapScene, inventoryScene, battleScene, player, boss);

            // Message that says game terminal is loaded.
            System.out.println("Game terminal is now running! Check the new popup window.");
            gameTerminal.start();

        } catch (IOException | InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Helper method to initialise our start screen which asks the user for
     * their player name. Then it checks the name in our database and loads the
     * player if it exists, otherwise, it will create a new player.
     *
     * @return either a new player or a saved player.
     * @throws InterruptedException
     */
    private static Player loadStartScreen() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our RPG game!");
        System.out.println("What is your name?");
        String playerName = scanner.nextLine();

        // Default if no records exist of player.
        Player player = new Player(playerName);

        // Check if player exists in the system.
        // Load the player from the DB if it exists.
        // Otherwise, create a new player.
        if (ResourceLoaderUtility.playerExists(playerName)) {
            player = Player.loadPlayerFactory(playerName);
            System.out.println("Welcome back!");
            System.out.println("Loading player...");
        } else {
            // Saves the new player data to our database.
            ResourceLoaderUtility.writePlayerData(player);
            ResourceLoaderUtility.writeInventoryData(player);
            ResourceLoaderUtility.writeEquippedData(player);
            System.out.println("Creating new player...");
        }

        System.out.println("Loading game terminal...");
        Thread.sleep(GAME_PAUSE_MS); // Adds delay to emulate loading screen.
        return player;
    }

    /**
     * Helper method to generate inventory and inventory scene. This helps set
     * up the navigation for the inventory and make sure the cursor does not go
     * out of bounds.
     *
     * @return
     */
    private static InventoryScene generateInventoryScene(Player player) {
        // Set up navigtaion for inventory scene.
        Coordinates inventoryCoords = new Coordinates(
                CURSOR_X_START,
                CURSOR_Y_START,
                CURSOR_X_STEP,
                CURSOR_Y_STEP);

        Boundaries inventoryBounds = new Boundaries(
                CURSOR_X_START,
                CURSOR_Y_START,
                CURSOR_X_END,
                CURSOR_Y_END);

        Navigation inventoryNavigation = new Navigation(
                inventoryCoords,
                inventoryBounds);

        // Make inventory scene.
        InventoryScene inventoryScene = new InventoryScene(
                inventoryNavigation,
                player);

        return inventoryScene;
    }

    /**
     * Helper method to generate the battle scene UI for the our game.
     *
     * @param player the player that will be playable by the user.
     * @return the battle scene CLI.
     */
    private static BattleScene generateBattleScene(Player player) {
        // Placeholder mob to generate battle scene.
        Mob mob = new Mob("No Mob");

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

        return battleScene;
    }
}
