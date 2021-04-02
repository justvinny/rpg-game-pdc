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
     * @param map is the first map shown when we start the game.
     * @param inventoryView contains the CLI for our inventory.
     */
    public static void start(List<String> map, InventoryView inventoryView) {
        // Create our Lanterna Terminal which we will use for the game.
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(TERMINAL_WIDTH, TERMINAL_HEIGHT));

        Screen screen = null;
        try {
            Terminal terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            // Will be used for drawing things on the console.
            TextGraphics textGraphics = screen.newTextGraphics();

            String[] lines = inventoryView.makeView().split("\n");

            int cursorPositionCol = InventoryView.ITEM_COL_START;
            int cursorPositionRow = InventoryView.ITEM_ROW_START;

            // Game loop
            while (true) {
                screen.clear();

                int row = 0;
                // Print the inventory to the console if it is toggled by the user.
                if (inventoryView.getInventory().isVisible()) {
                    for (String each : lines) {
                        textGraphics.putString(0, row, each);
                        textGraphics.setForegroundColor(TextColor.ANSI.BLUE);
                        textGraphics.putString(cursorPositionCol, cursorPositionRow, CURSOR, SGR.BOLD);
                        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                        row++;
                    }
                    // Print the game map instead if the inventory is invisbile.
                } else {
                    for (String each : map) {
                        textGraphics.putCSIStyledString(0, row, each);
                        row++;
                    }
                }

                // Print out the keys the user can press for the game.
                printAvailableKeys(textGraphics, row);

                // Refresh the screen to show changes if any.
                screen.refresh();

                // Game speed.
                Thread.sleep(FPS);

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
                        // Check if key stroke is a character.
                    } else if (keyStroke.getKeyType() == KeyType.Character) {
                        // If the character pressed is the letter i,
                        // toggle the inventory's visibility.
                        if (keyStroke.getCharacter() == 'i') {
                            inventoryView.getInventory().toggle();
                        }
                        // Only allow inventory navigation if it is visible.
                    } else if (inventoryView.getInventory().isVisible()) {
                        // Navigating the inventory.
                        inventoryNavigation(keyStroke, inventoryView);
                    }
                }
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
     * @param row is y position on where we want to start drawing on the console.
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
     * @throws IOException exception thrown when using TextGraphics putString method.
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
     * @param inventoryView contains the CLI for our inventory.
     */
    private static void inventoryNavigation(KeyStroke keyStroke, InventoryView inventoryView) {
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                inventoryView.moveDown();
                break;
            case ArrowUp:
                inventoryView.moveUp();
                break;
            case ArrowRight:
                inventoryView.moveRight();
                break;
            case ArrowLeft:
                inventoryView.moveLeft();
        }
    }
}
