/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.items.Armour;
import com.group.pdc_assignment_rpg.logic.items.ConsumableItem;
import com.group.pdc_assignment_rpg.logic.items.Item;
import com.group.pdc_assignment_rpg.logic.items.Weapon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller class that handles the events for the Inventory View.
 * Events such as updating the inventory list, using an item, dropping an item, 
 * closing the inventory, and exiting the game.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class InventoryController {

    /*
        Fields
     */
    private ScreenManager screenManager;
    private MainFrameView mainFrame;
    private InventoryView inventoryView;
    private GameView game;

    /*
        Constructor
     */
    public InventoryController() {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        inventoryView = screenManager.getInventory();
        game = screenManager.getGame();

        initListeners();
    }

    /*
        Initialise listeners
    */
    private void initListeners() {
        initInventoryListListener();
        initBtnDropListener();
        initBtnUseListener();
        initBtnCloseInventoryListener();
        initBtnExitListener();
    }

    /**
     * Initialises the inventory list listener that allows user to select an
     * item and interact with it.
     */
    private void initInventoryListListener() {
        // JList listener item selection
        inventoryView.addInventoryListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displayItemDetails();
                }
            }
        });

        // Jlist key listener since it will receive focus once you select an 
        // item which means the mainframe key listener will not work as it
        // is not in focus.
        inventoryView.addInventoryKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_I:
                        screenManager.setCurrentScreen(ScreenManagerConstants.GAME);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        inventoryView.getPlayer().savePlayer();
                        screenManager.closeGame();
                }
            }
        });
    }

    /**
     * Initialise the listener for the drop button to drop the item currently
     * being selected in the inventory.
     */
    private void initBtnDropListener() {
        inventoryView.addBtnDropListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item item = inventoryView.getItemSelected();
                inventoryView.getInventory().drop(item);
                inventoryView.updateInventoryData();
                inventoryView.clearItemDetails();
                mainFrame.requestFocusInWindow();
            }
        });
    }

    /**
     * Initialise the listener for the use button to use or equip items being currently
     * selected.
     */
    private void initBtnUseListener() {
        inventoryView.addBtnUseListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item item = inventoryView.getItemSelected();
                try {
                    inventoryView.getInventory().use(inventoryView.getPlayer(), item);
                    inventoryView.updateInventoryData();
                    game.updatePlayerInformation();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(mainFrame, ex.getMessage());
                }

                mainFrame.requestFocusInWindow();
            }
        });
    }

    /**
     * Initialise the close inventory button listener to close the inventory
     * and get back to the main game.
     */
    private void initBtnCloseInventoryListener() {
        inventoryView.addBtnCloseInvListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getCurrentScreen() instanceof InventoryView) {
                    mainFrame.setCurrentScreen(ScreenManager.getInstance().getGame());
                }

                mainFrame.requestFocusInWindow();
            }
        });
    }
    
    
    /**
     * Initialises the listener for the exit button to exit the game and save
     * the player data.
     */
    private void initBtnExitListener() {
        inventoryView.addBtnExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryView.getPlayer().savePlayer();
                screenManager.closeGame();
            }
        });
    }

    /**
     * Helper method to display further information of an item being selected in
     * the JList inventory.
     */
    private void displayItemDetails() {
        Item item = inventoryView.getItemSelected();

        if (item != null) {
            String itemDetails = item.getName() + "\n";

            if (item instanceof Weapon) {
                itemDetails += "Damage: " + ((Weapon) item).getDamage() + "\n";
            } else if (item instanceof Armour) {
                itemDetails += "Protection: " + ((Armour) item).getProtection() + "\n";
            } else if (item instanceof ConsumableItem) {
                itemDetails += "Special Effect: "
                        + ((ConsumableItem) item).getSpecialValue() + "\n";
            }

            itemDetails += String.format("Type: %s | Weight: %d | Value: %d",
                    item.getItem().getType(),
                    item.getItem().getWeight(),
                    item.getItem().getValue());

            inventoryView.setItemDetailsTxt(itemDetails);
        }
    }
}
