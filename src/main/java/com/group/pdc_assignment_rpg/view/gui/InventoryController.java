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
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventoryController {

    private ScreenManager screenManager;
    private MainFrameView mainFrame;
    private InventoryView inventoryView;
    private GameView game;

    public InventoryController() {
        screenManager = ScreenManager.getInstance();
        mainFrame = screenManager.getMainFrameView();
        inventoryView = screenManager.getInventory();
        game = screenManager.getGame();

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

        // button listeners.
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

        inventoryView.addBtnCloseInvListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getCurrentScreen() instanceof InventoryView) {
                    mainFrame.setCurrentScreen(ScreenManager.getInstance().getGame());
                }

                mainFrame.requestFocusInWindow();
            }
        });

        inventoryView.addBtnExitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryView.getPlayer().savePlayer();
                screenManager.closeGame();
            }
        });
    }

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
