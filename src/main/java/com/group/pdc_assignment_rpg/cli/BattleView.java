package com.group.pdc_assignment_rpg.cli;

import com.group.pdc_assignment_rpg.logic.Mob;
import com.group.pdc_assignment_rpg.logic.Player;
import com.group.pdc_assignment_rpg.utilities.TextUtility;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class BattleView {

    private static final int CURSOR_X_START = 2;
    private static final int CURSOR_Y_START = 5;
    private static final int CURSOR_Y_END = 7;
    
    private boolean visible;
    private Player player;
    private Mob mob;
    private int x, y;

    public BattleView(Player player, Mob mob) {
        this.visible = false;
        this.player = player;
        this.mob = mob;
        this.x = CURSOR_X_START;
        this.y = CURSOR_Y_START;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void toggle() {
        if (!visible) {
            visible = true;
        } else {
            visible = false;
        }
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
    
    public String[] createBattleScene() {

        return (statusBars() + mergeCommandMenuAndBattleLog()).split("\n");
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
    
    public void down() {
        if (y < CURSOR_Y_END) {
            y++;
        }
    }
    
    public void up() {
        if (y > CURSOR_Y_START) {
            y--;
        }
    }
}
