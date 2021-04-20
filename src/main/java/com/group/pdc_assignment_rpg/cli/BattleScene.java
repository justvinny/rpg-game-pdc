package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.utilities.TextUtility;
import java.util.Arrays;
import java.util.List;

/**
 * Represents our UI for the battle scenes on our Lanterna terminal.
 * 
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class BattleScene extends Scene {

    private Navigation navigation;
    private Player player;
    private Mob mob;

    /**
     * Constructor for our battle scene which will be used for combat with mobs.
     * @param navigation - allows player to choose commands related to combat.
     * @param player - our playable player which will go against a mob.
     * @param mob - the enemy that the play is in combat with.
     */
    public BattleScene(Navigation navigation, Player player, Mob mob) {
        super();
        this.navigation = navigation;
        this.player = player;
        this.mob = mob;
    }

    /*
     * Getters
     */
    public Navigation getNavigation() {
        return navigation;
    }

    /**
     * String representation for player and mob status bars which has their
     * symbol, name, health, and max health.
     * @return the status bars.
     */
    public String statusBars() {
        String playerBar = String.format("# %c %-20s      HP %3d/%3d #",
                player.getSymbol(), player.getName(),
                player.getHP(), player.getMaxHP());

        String mobBar = String.format("# %c %-20s      HP %3d/%3d #",
                mob.getSymbol(), mob.getName(),
                mob.getHP(), mob.getMaxHP());

        String hashLine = TextUtility.repeatCharacter((playerBar.length()), '#');

        return String.format("%s %s\n%s %s\n%s %s\n\n", hashLine, hashLine,
                playerBar, mobBar, hashLine, hashLine);

    }

    /**
     * String representation for the command menu where the player can choose from
     * Attack, Defend, and Escape commands.
     * @return the command menu.
     */
    public String commandMenu() {
        String attack = String.format("#     %-7s #\n", "Attack");
        String defend = String.format("#     %-7s #\n", "Defend");
        String escape = String.format("#     %-7s #\n", "Escape");
        String hashLine = TextUtility.repeatCharacter((attack.length() - 1), '#') + "\n";

        return hashLine + attack + defend + escape + hashLine;
    }

    /**
     * String representation for the battle log where all the actions by the 
     * player and mob in battle are printed to for each turn. 
     * For example, if a player attack for 10 damage to the mob, this action
     * will be printed to our log.
     * @return battle log.
     */
    public String battleLog() {
        String hashLineLog = TextUtility.repeatCharacter(69, '#') + "\n";
        String hashMiddleLog = "#" + TextUtility.repeatCharacter(67, ' ') + "#\n";

        return hashLineLog + String.join("", TextUtility.repeatStringIntoArray(18, hashMiddleLog)) + hashLineLog;
    }

    /**
     * Creates our battle scene by incorporating the different UIs like Status
     * Bars, Battle Logs, and Command Menu.
     * @return our battle scene.
     */
    @Override
    public List<String> createScene() {
        String[] strArr = (statusBars() + mergeCommandMenuAndBattleLog()).split("\n");
        
        return Arrays.asList(strArr);
    }

    /**
     * Merges our command menu and battle log side by side to give that more 
     * classic J-RPG look and feel.
     * @return our merged command menu and battle log.
     */
    private String mergeCommandMenuAndBattleLog() {
        String[] commandMenu = commandMenu().split("\n");
        String[] battleLog = battleLog().split("\n");

        int commandMenuLineLen = commandMenu[0].length();
        String[] mergedArr = new String[battleLog.length];
        for (int i = 0; i < battleLog.length; i++) {
            String leadingChar = TextUtility.repeatCharacter(commandMenuLineLen, ' ');

            if (i < commandMenu.length) {
                leadingChar = commandMenu[i];
            }

            mergedArr[i] = leadingChar + " " + battleLog[i];
        }

        return String.join("\n", mergedArr);
    }

    /**
     * Battle navigation
     *
     * Note: Uses composition as not all scenes may implement all navigation
     * methods.
     */
    public void up() {
        navigation.up();
    }

    public void down() {
        navigation.down();
    }
}
