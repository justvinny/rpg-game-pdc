package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.navigation.Navigation;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.navigation.Boundaries;
import com.group.pdc_assignment_rpg.logic.navigation.Coordinates;
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

    public BattleScene(Navigation navigation, Player player, Mob mob) {
        super();
        this.navigation = navigation;
        this.player = player;
        this.mob = mob;
    }

    public Navigation getNavigation() {
        return navigation;
    }

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

    public String commandMenu() {
        String attack = String.format("#     %-7s #\n", "Attack");
        String defend = String.format("#     %-7s #\n", "Defend");
        String escape = String.format("#     %-7s #\n", "Escape");
        String hashLine = TextUtility.repeatCharacter((attack.length() - 1), '#') + "\n";

        return hashLine + attack + defend + escape + hashLine;
    }

    public String battleLog() {
        String hashLineLog = TextUtility.repeatCharacter(69, '#') + "\n";
        String hashMiddleLog = "#" + TextUtility.repeatCharacter(67, ' ') + "#\n";

        return hashLineLog + String.join("", TextUtility.repeatStringIntoArray(18, hashMiddleLog)) + hashLineLog;
    }

    public List<String> createScene() {
        String[] strArr = (statusBars() + mergeCommandMenuAndBattleLog()).split("\n");
        
        return Arrays.asList(strArr);
    }

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
    
    public void startBattle(Mob mob) {
        this.mob = mob;
        this.toggle();
    }
}
