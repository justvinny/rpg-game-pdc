/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.items.Item;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMS;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class InventoryView extends JPanel {

    private static final Dimension LIST_DIMS = new Dimension((int) (MainFrameView.FRAME_WIDTH * .47), (int) (MainFrameView.FRAME_HEIGHT * .65));
    private static final String LABEL_TITLE = "Player Inventory";
    private static final String BTN_USE_EQUIP = "Use / Equip Item";
    private static final String BTN_DROP = "Drop Item";
    private static final String BTN_CLOSE_INVENTORY = "[ I ] - Close Inventory";
    private static final String BTN_EXIT = "[ESC] - Exit";
    private static final Font FONT_TITLE = new Font("Impact", Font.BOLD, 32);

    private SpringLayout layout;
    private JLabel labelTitle;
    private JList jListInventory;
    private JTextArea txtAreaEquippedStats, txtAreaItemDetails;
    private JScrollPane scrollInventory;
    private JButton btnUseEquip, btnDrop, btnCloseInventory, btnExit;

    private Player player;

    public InventoryView() {
        player = new Player("Placeholder");
        panelSettings();
        createTitleLabel();
        createInventoryList();
        createEquippedStats();
        createItemDetails();
        createBottomMenu();
        setSpringLayoutConstraints();
    }

    private void panelSettings() {
        setBackground(Color.WHITE);

        // layout
        layout = new SpringLayout();
        setLayout(layout);
    }

    // Inventory title
    private void createTitleLabel() {
        labelTitle = new JLabel(LABEL_TITLE);
        labelTitle.setFont(FONT_TITLE);

        add(labelTitle);
    }

    // Inventory list
    private void createInventoryList() {
        jListInventory = new JList();
        jListInventory.setFont(DEFAULT_FONT);
        jListInventory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollInventory = new JScrollPane(jListInventory);
        scrollInventory.setPreferredSize(LIST_DIMS);
        scrollInventory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollInventory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollInventory.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(scrollInventory);
    }

    // Currently equipped items.
    private void createEquippedStats() {
        txtAreaEquippedStats = new JTextArea();
        txtAreaEquippedStats.setFont(DEFAULT_FONT);
        txtAreaEquippedStats.setPreferredSize(LIST_DIMS);
        txtAreaEquippedStats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAreaEquippedStats.setEditable(false);

        add(txtAreaEquippedStats);
    }

    // item description
    private void createItemDetails() {
        txtAreaItemDetails = new JTextArea();
        txtAreaItemDetails.setFont(DEFAULT_FONT);
        txtAreaItemDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtAreaItemDetails.setEditable(false);

        add(txtAreaItemDetails);
    }

    // Buttons for equipping, using, and going back to the main game.
    private void createBottomMenu() {
        btnUseEquip = new JButton(BTN_USE_EQUIP);
        btnUseEquip.setPreferredSize(DEFAULT_BTN_DIMS);

        btnDrop = new JButton(BTN_DROP);
        btnDrop.setPreferredSize(DEFAULT_BTN_DIMS);

        btnCloseInventory = new JButton(BTN_CLOSE_INVENTORY);
        btnCloseInventory.setPreferredSize(DEFAULT_BTN_DIMS);

        btnExit = new JButton(BTN_EXIT);
        btnExit.setPreferredSize(DEFAULT_BTN_DIMS);

        // add
        add(btnUseEquip);
        add(btnDrop);
        add(btnCloseInventory);
        add(btnExit);
    }

    // Spring layout constraints
    private void setSpringLayoutConstraints() {
        // Header
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, labelTitle, DEFAULT_MARGIN, SpringLayout.NORTH, this);

        // Inventory list
        layout.putConstraint(SpringLayout.WEST, scrollInventory, (int) (MainFrameView.FRAME_WIDTH * .02), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scrollInventory, DEFAULT_MARGIN, SpringLayout.SOUTH, labelTitle);

        // Equipped items and stats
        layout.putConstraint(SpringLayout.EAST, txtAreaEquippedStats, (int) (-MainFrameView.FRAME_WIDTH * .02), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, txtAreaEquippedStats, DEFAULT_MARGIN, SpringLayout.SOUTH, labelTitle);

        // Item details
        layout.putConstraint(SpringLayout.EAST, txtAreaItemDetails, (int) (-MainFrameView.FRAME_WIDTH * .02), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, txtAreaItemDetails, (int) (MainFrameView.FRAME_WIDTH * .02), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, txtAreaItemDetails, (int) (MainFrameView.FRAME_HEIGHT * .02), SpringLayout.SOUTH, txtAreaEquippedStats);
        layout.putConstraint(SpringLayout.SOUTH, txtAreaItemDetails, (int) (-MainFrameView.FRAME_HEIGHT * .02), SpringLayout.NORTH, btnUseEquip);

        // btn for using or equipping item
        layout.putConstraint(SpringLayout.SOUTH, btnUseEquip, (int) (-MainFrameView.FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnUseEquip, -DEFAULT_MARGIN, SpringLayout.WEST, btnDrop);

        // btn for dropping items
        layout.putConstraint(SpringLayout.SOUTH, btnDrop, (int) (-MainFrameView.FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnDrop, -DEFAULT_MARGIN, SpringLayout.WEST, btnCloseInventory);

        // btn for closing inventory
        layout.putConstraint(SpringLayout.SOUTH, btnCloseInventory, (int) (-MainFrameView.FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnCloseInventory, -DEFAULT_MARGIN, SpringLayout.WEST, btnExit);

        // btn for exit
        layout.putConstraint(SpringLayout.SOUTH, btnExit, (int) (-MainFrameView.FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnExit, (int) (-MainFrameView.FRAME_WIDTH * .02), SpringLayout.EAST, this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Inventory getInventory() {
        return player.getInventory();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void clearItemDetails() {
        txtAreaItemDetails.setText("");
    }
    
    public void updateInventoryData() {
        // Set inventory
        jListInventory.setListData(player.getInventory().getAllItemNames());

        // Set player stats and equipped items.
        txtAreaEquippedStats.setText(player.getPlayerInformation());

    }

    public void addInventoryKeyListener(KeyListener keyListener) {
        jListInventory.addKeyListener(keyListener);
    }
    
    public void addInventoryListSelectionListener(ListSelectionListener listSelectionListener) {
        jListInventory.addListSelectionListener(listSelectionListener);
    }
    
    public Item getItemSelected() {
        if (jListInventory.getSelectedValue() == null) {
            return null;
        }
        
        String itemName = jListInventory.getSelectedValue().toString();
        return player.getInventory().getItem(itemName);
    }
    
    public void setItemDetailsTxt(String itemDetails) {
        txtAreaItemDetails.setText(itemDetails);
    }
    
    public void addBtnUseListener(ActionListener actionListener) {
        btnUseEquip.addActionListener(actionListener);
    }

    public void addBtnDropListener(ActionListener actionListener) {
        btnDrop.addActionListener(actionListener);

    }

    public void addBtnCloseInvListener(ActionListener actionListener) {
        btnCloseInventory.addActionListener(actionListener);

    }

    public void addBtnExitListener(ActionListener actionListener) {
        btnExit.addActionListener(actionListener);
    }
}