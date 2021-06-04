/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.logic.entities.Player;
import com.group.pdc_assignment_rpg.logic.items.Inventory;
import com.group.pdc_assignment_rpg.logic.items.Item;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.BG_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.BOX_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_BTN_DIMS;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.TEXT_COLOR;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
 * UI Inventory view that displays the player's inventory where you can interact
 * with the items such as using a potion, equipping a weapon/armour for more stats,
 * etc.
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class InventoryView extends JPanel {

    /*
        Constants
     */
    private static final Dimension LIST_DIMS = new Dimension((int) (MainFrameView.FRAME_WIDTH * .47), (int) (MainFrameView.FRAME_HEIGHT * .65));
    private static final String LABEL_TITLE = "Player Inventory";
    private static final String BTN_USE_EQUIP = "Use / Equip Item";
    private static final String BTN_DROP = "Drop Item";
    private static final String BTN_CLOSE_INVENTORY = "[ I ] - Back";
    private static final String BTN_EXIT = "[ESC] - Exit";
    private static final Font FONT_TITLE = new Font("Impact", Font.BOLD, 32);

    /*
        Fields
     */
    private SpringLayout layout;
    private JLabel labelTitle;
    private JList jListInventory;
    private JTextArea txtAreaEquippedStats, txtAreaItemDetails;
    private JScrollPane scrollInventory;
    private JButton btnUseEquip, btnDrop, btnCloseInventory, btnExit;
    private Player player;

    /*
        Constructor
     */
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

    /**
     * JPanel settings go here.
     */
    private void panelSettings() {
        setBackground(Color.WHITE);

        // layout
        layout = new SpringLayout();
        setLayout(layout);
    }

    /**
     * Initialise JComponent to display the Inventory main header.
     */
    private void createTitleLabel() {
        labelTitle = new JLabel(LABEL_TITLE);
        labelTitle.setFont(FONT_TITLE);
        labelTitle.setForeground(TEXT_COLOR);

        add(labelTitle);
    }

    /**
     * Initialise JComponents to display the inventory JList where the user can
     * select an item to interact with.
     */
    private void createInventoryList() {
        jListInventory = new JList();
        jListInventory.setFont(DEFAULT_FONT);
        jListInventory.setBackground(BOX_COLOR);
        jListInventory.setForeground(TEXT_COLOR);
        jListInventory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollInventory = new JScrollPane(jListInventory);
        scrollInventory.setPreferredSize(LIST_DIMS);
        scrollInventory.setBackground(BOX_COLOR);
        scrollInventory.setForeground(TEXT_COLOR);
        scrollInventory.getViewport().setBackground(BOX_COLOR);
        scrollInventory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollInventory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollInventory.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        add(scrollInventory);
    }

    /**
     * Initialise JComponent to display a comprehensive breakdown of the
     * player's stats.
     */
    private void createEquippedStats() {
        txtAreaEquippedStats = new JTextArea();
        txtAreaEquippedStats.setFont(DEFAULT_FONT);
        txtAreaEquippedStats.setBackground(BOX_COLOR);
        txtAreaEquippedStats.setForeground(TEXT_COLOR);
        txtAreaEquippedStats.setPreferredSize(LIST_DIMS);
        txtAreaEquippedStats.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        txtAreaEquippedStats.setEditable(false);

        add(txtAreaEquippedStats);
    }

    /**
     * Initialise JComponent to display a more detailed information of an item
     * currently being selected in the JList.
     */
    private void createItemDetails() {
        txtAreaItemDetails = new JTextArea();
        txtAreaItemDetails.setFont(DEFAULT_FONT);
        txtAreaItemDetails.setBackground(BOX_COLOR);
        txtAreaItemDetails.setForeground(TEXT_COLOR);
        txtAreaItemDetails.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        txtAreaItemDetails.setEditable(false);

        add(txtAreaItemDetails);
    }

    /**
     * Initialise JComponents for all the buttons in the inventory such as
     * Use/Equip, Drop, Close Inventory, and Exit Game.
     */
    private void createBottomMenu() {
        btnUseEquip = new JButton(BTN_USE_EQUIP);
        btnUseEquip.setPreferredSize(DEFAULT_BTN_DIMS);
        btnUseEquip.setBackground(BOX_COLOR);
        btnUseEquip.setForeground(TEXT_COLOR);
        btnUseEquip.setFont(DEFAULT_FONT);

        btnDrop = new JButton(BTN_DROP);
        btnDrop.setPreferredSize(DEFAULT_BTN_DIMS);
        btnDrop.setBackground(BOX_COLOR);
        btnDrop.setForeground(TEXT_COLOR);
        btnDrop.setFont(DEFAULT_FONT);

        btnCloseInventory = new JButton(BTN_CLOSE_INVENTORY);
        btnCloseInventory.setPreferredSize(DEFAULT_BTN_DIMS);
        btnCloseInventory.setBackground(BOX_COLOR);
        btnCloseInventory.setForeground(TEXT_COLOR);
        btnCloseInventory.setFont(DEFAULT_FONT);

        btnExit = new JButton(BTN_EXIT);
        btnExit.setPreferredSize(DEFAULT_BTN_DIMS);
        btnExit.setBackground(BOX_COLOR);
        btnExit.setForeground(TEXT_COLOR);
        btnExit.setFont(DEFAULT_FONT);

        // add
        add(btnUseEquip);
        add(btnDrop);
        add(btnCloseInventory);
        add(btnExit);
    }

    /**
     * Spring Layout constraints to position our Components.
     */
    private void setSpringLayoutConstraints() {
        // Header
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelTitle, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, labelTitle, DEFAULT_MARGIN, SpringLayout.NORTH, this);

        // Inventory list
        layout.putConstraint(SpringLayout.WEST, scrollInventory, (int) (FRAME_WIDTH * .02), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scrollInventory, DEFAULT_MARGIN, SpringLayout.SOUTH, labelTitle);

        // Equipped items and stats
        layout.putConstraint(SpringLayout.EAST, txtAreaEquippedStats, (int) (-FRAME_WIDTH * .02), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, txtAreaEquippedStats, DEFAULT_MARGIN, SpringLayout.SOUTH, labelTitle);

        // Item details
        layout.putConstraint(SpringLayout.EAST, txtAreaItemDetails, (int) (-FRAME_WIDTH * .02), SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, txtAreaItemDetails, (int) (FRAME_WIDTH * .02), SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, txtAreaItemDetails, (int) (FRAME_HEIGHT * .02), SpringLayout.SOUTH, txtAreaEquippedStats);
        layout.putConstraint(SpringLayout.SOUTH, txtAreaItemDetails, (int) (-FRAME_HEIGHT * .02), SpringLayout.NORTH, btnUseEquip);

        // btn for using or equipping item
        layout.putConstraint(SpringLayout.SOUTH, btnUseEquip, (int) (-FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnUseEquip, -DEFAULT_MARGIN, SpringLayout.WEST, btnDrop);

        // btn for dropping items
        layout.putConstraint(SpringLayout.SOUTH, btnDrop, (int) (-FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnDrop, -DEFAULT_MARGIN, SpringLayout.WEST, btnCloseInventory);

        // btn for closing inventory
        layout.putConstraint(SpringLayout.SOUTH, btnCloseInventory, (int) (-FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnCloseInventory, -DEFAULT_MARGIN, SpringLayout.WEST, btnExit);

        // btn for exit
        layout.putConstraint(SpringLayout.SOUTH, btnExit, (int) (-FRAME_HEIGHT * .02), SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.EAST, btnExit, (int) (-FRAME_WIDTH * .02), SpringLayout.EAST, this);
    }

    /**
     * Change view background.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
    }

    /*
     * Getters 
     */
    public Inventory getInventory() {
        return player.getInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItemSelected() {
        if (jListInventory.getSelectedValue() == null) {
            return null;
        }

        // Remove 'xQty - ' at the beggining of the item name.
        String itemName = jListInventory.getSelectedValue().toString().split(" - ")[1];
        return player.getInventory().getItem(itemName);
    }

    /**
     * Set the player model that will be used for the inventory.
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the text for the current extended item information being selected in the
     * inventory.
     * @param itemDetails 
     */
    public void setItemDetailsTxt(String itemDetails) {
        txtAreaItemDetails.setText(itemDetails);
    }

    /**
     * Used to remove the text information being displayed for an item when it's
     * not selected anymore.
     */
    public void clearItemDetails() {
        txtAreaItemDetails.setText("");
    }

    /**
     * Updates the inventory list to reflect changes made in the model.
     */
    public void updateInventoryData() {
        // Set inventory
        jListInventory.setListData(player.getInventory().getAllItemNames());

        // Set player stats and equipped items.
        txtAreaEquippedStats.setText(player.getPlayerInformation());

    }
    
    
    /*
     * Evente listener methods that will be used by the controller.
     * 
     */
    public void addInventoryKeyListener(KeyListener keyListener) {
        jListInventory.addKeyListener(keyListener);
    }

    public void addInventoryListSelectionListener(ListSelectionListener listSelectionListener) {
        jListInventory.addListSelectionListener(listSelectionListener);
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
