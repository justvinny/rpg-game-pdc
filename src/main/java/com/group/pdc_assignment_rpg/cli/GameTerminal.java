package com.group.pdc_assignment_rpg.cli;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains the Lanterna Terminal which will be used as the CLI for
 * our RPG game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class GameTerminal {
    private static final int FPS = 1000 / 60;
    
    public static void start(List<String> map) {
        // Create our Lanterna Terminal which we will use for the game.
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;
        try {

            terminal = defaultTerminalFactory.createTerminal();
            // Disallow user scrolling.
            terminal.enterPrivateMode();

            // Initialise text graphics for console output.
            final TextGraphics textGraphics = terminal.newTextGraphics();

            // Game loop
            while (true) {
                terminal.clearScreen();
                for (String line : map) {
                    terminal.putString(line);
                    terminal.putCharacter('\n');
                }

                terminal.flush();
                Thread.sleep(FPS);

                // Non-blocking read input from keyboard which means
                // the program won't come to a stop to wait for input.
                KeyStroke keyStroke = terminal.pollInput();

                // Null check since we're using non-blocking read input
                // which means keyStroke will be null if we don't press
                // a key on the keyboard.
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        System.out.println("Exit!");
                        break;
                    }
                }
            }

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(GameTerminal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the terminal once the game loop ends.
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

    }
}
