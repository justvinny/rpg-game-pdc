/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.view.gui;

import com.group.pdc_assignment_rpg.MainDriver;
import com.group.pdc_assignment_rpg.assets.ImageLoader;
import com.group.pdc_assignment_rpg.cli.BattleSceneConstants;
import com.group.pdc_assignment_rpg.logic.Combat;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.logic.entities.Player;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.BG_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_FONT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.DEFAULT_MARGIN;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.FRAME_WIDTH;
import static com.group.pdc_assignment_rpg.view.gui.MainFrameView.TEXT_COLOR;
import static com.group.pdc_assignment_rpg.view.gui.MapView.TILE_HEIGHT;
import static com.group.pdc_assignment_rpg.view.gui.MapView.TILE_WIDTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public final class CombatView extends JPanel {

    private static final String ATTACK_BTN = "Attack";
    private static final String DEFEND_BTN = "Defend";
    private static final String ESCAPE_BTN = "Escape";

    private static final Dimension COMMAND_BOX_DIMS
            = new Dimension((int) (FRAME_WIDTH * .25), (int) (FRAME_HEIGHT * .25));
    private static final Dimension STATUS_BOX_DIMS
            = new Dimension((int) (FRAME_WIDTH * .48), 30);
    private static final Dimension PLAYER_SPRITE_DIMS = new Dimension(TILE_WIDTH, TILE_HEIGHT);
    private static final Dimension MOB_SPRITE_DIMS = new Dimension(TILE_WIDTH * 2, TILE_HEIGHT * 2);
    private static final Dimension BOSS_SPRITE_DIMS = new Dimension(TILE_WIDTH * 3, TILE_HEIGHT * 3);

    private SpringLayout layout, commandPanelLayout;
    private JPanel panelCommand;
    private JLabel labelPlayer, labelMonster;
    private JButton btnAttack, btnDefend, btnEscape;
    private JTextArea txtAreaBattleLog;
    private JScrollPane scrollBatteLog;
    private PlayerBattleSprite playerSprite;
    private MobBattleSprite mobSprite;

    private Player player;
    private Mob mob;
    private Combat combat;

    public CombatView() {
        this.player = new Player("Placeholder");
        this.mob = new Mob("Placeholder");
        this.combat = new Combat(player, mob);

        panelSettings();

        // Components
        setStatusBars();
        setBattleSprites();
        setCommandBox();
        setBattleLogBox();

        updateStatusBars();

        // Layout constraints
        setSpringLayoutConstraints();
    }

    private void panelSettings() {
        setBackground(BG_COLOR);

        layout = new SpringLayout();
        setLayout(layout);
    }

    private void setStatusBars() {
        labelPlayer = new JLabel();
        labelPlayer.setPreferredSize(STATUS_BOX_DIMS);
        labelPlayer.setBackground(BG_COLOR);
        labelPlayer.setForeground(TEXT_COLOR);
        labelPlayer.setFont(DEFAULT_FONT);
        labelPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        labelMonster = new JLabel();
        labelMonster.setPreferredSize(STATUS_BOX_DIMS);
        labelMonster.setBackground(BG_COLOR);
        labelMonster.setForeground(TEXT_COLOR);
        labelMonster.setFont(DEFAULT_FONT);
        labelMonster.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));

        add(labelPlayer);
        add(labelMonster);
    }

    private void setBattleSprites() {
        playerSprite = new PlayerBattleSprite();
        playerSprite.setPreferredSize(PLAYER_SPRITE_DIMS);

        mobSprite = new MobBattleSprite("Slime");
        mobSprite.setPreferredSize(MOB_SPRITE_DIMS);

        add(playerSprite);
        add(mobSprite);
    }

    private void setCommandBox() {
        // Panel
        commandPanelLayout = new SpringLayout();
        panelCommand = new JPanel();
        panelCommand.setPreferredSize(COMMAND_BOX_DIMS);
        panelCommand.setOpaque(false);
        panelCommand.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        panelCommand.setLayout(commandPanelLayout);

        // Buttons
        btnAttack = new JButton(ATTACK_BTN);
        btnAttack.setBackground(BG_COLOR);
        btnAttack.setForeground(TEXT_COLOR);
        btnAttack.setFont(DEFAULT_FONT);

        btnDefend = new JButton(DEFEND_BTN);
        btnDefend.setBackground(BG_COLOR);
        btnDefend.setForeground(TEXT_COLOR);
        btnDefend.setFont(DEFAULT_FONT);

        btnEscape = new JButton(ESCAPE_BTN);
        btnEscape.setBackground(BG_COLOR);
        btnEscape.setForeground(TEXT_COLOR);
        btnEscape.setFont(DEFAULT_FONT);

        // Add to sub panel
        panelCommand.add(btnAttack);
        panelCommand.add(btnDefend);
        panelCommand.add(btnEscape);

        // Add to main panel
        add(panelCommand);
    }

    private void setBattleLogBox() {
        txtAreaBattleLog = new JTextArea(" Battle Log");
        txtAreaBattleLog.setOpaque(false);
        txtAreaBattleLog.setForeground(TEXT_COLOR);
        txtAreaBattleLog.setFont(DEFAULT_FONT);

        // Scroll for battle log
        scrollBatteLog = new JScrollPane(txtAreaBattleLog);
        scrollBatteLog.setPreferredSize(COMMAND_BOX_DIMS);
        scrollBatteLog.setOpaque(false);
        scrollBatteLog.getViewport().setOpaque(false);
        scrollBatteLog.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        scrollBatteLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollBatteLog);
    }

    private void setSpringLayoutConstraints() {
        // Status bar for player
        layout.putConstraint(SpringLayout.WEST, labelPlayer, DEFAULT_MARGIN, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, labelPlayer, DEFAULT_MARGIN, SpringLayout.NORTH, this);

        // Status bar for monster
        layout.putConstraint(SpringLayout.EAST, labelMonster, -DEFAULT_MARGIN, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, labelMonster, DEFAULT_MARGIN, SpringLayout.NORTH, this);

        // Player battle sprite
        layout.putConstraint(SpringLayout.WEST, playerSprite, DEFAULT_MARGIN, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, playerSprite, (int) -(FRAME_HEIGHT * .15), SpringLayout.VERTICAL_CENTER, this);

        // Monster battle spite
        layout.putConstraint(SpringLayout.EAST, mobSprite, -DEFAULT_MARGIN, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, mobSprite, (int) -(FRAME_HEIGHT * .2), SpringLayout.VERTICAL_CENTER, this);

        // Command box
        layout.putConstraint(SpringLayout.WEST, panelCommand, DEFAULT_MARGIN, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, panelCommand, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);

        // Battle log
        layout.putConstraint(SpringLayout.WEST, scrollBatteLog, DEFAULT_MARGIN, SpringLayout.EAST, panelCommand);
        layout.putConstraint(SpringLayout.EAST, scrollBatteLog, -DEFAULT_MARGIN, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollBatteLog, -DEFAULT_MARGIN, SpringLayout.SOUTH, this);

        // Command panel sub layout constraints.
        // Attack btn
        commandPanelLayout.putConstraint(SpringLayout.WEST, btnAttack, 0, SpringLayout.WEST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.EAST, btnAttack, 0, SpringLayout.EAST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.NORTH, btnAttack, 0, SpringLayout.NORTH, panelCommand);

        // Defend btn
        commandPanelLayout.putConstraint(SpringLayout.WEST, btnDefend, 0, SpringLayout.WEST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.EAST, btnDefend, 0, SpringLayout.EAST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.NORTH, btnDefend, 0, SpringLayout.SOUTH, btnAttack);

        // Escape btn
        commandPanelLayout.putConstraint(SpringLayout.WEST, btnEscape, 0, SpringLayout.WEST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.EAST, btnEscape, 0, SpringLayout.EAST, panelCommand);
        commandPanelLayout.putConstraint(SpringLayout.NORTH, btnEscape, 0, SpringLayout.SOUTH, btnDefend);
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombatants(Player player, Mob mob) {
        this.player = player;
        this.mob = mob;
        this.combat = new Combat(player, mob);
        setMobSprite(mob);
        updateStatusBars();
        updateBattleLog();
        repaint();
        revalidate();
    }

    public void setMobSprite(Mob mob) {
        this.mobSprite.setMobName(mob.getName());

        if (mob.getName().equals(MainDriver.BOSS_MOB)) {
            this.mobSprite.setPreferredSize(BOSS_SPRITE_DIMS);
        } else {
            this.mobSprite.setPreferredSize(MOB_SPRITE_DIMS);
        }
    }

    public boolean battle(BattleSceneConstants action) {
        combat.battle(action);
        return combat.isFighting();
    }

    public void updateStatusBars() {
        this.labelPlayer.setText(player.getBattleStatusText());
        this.labelMonster.setText(mob.getBattleStatusText());
    }

    public void updateBattleLog() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Battle Log\n");
        combat.getFullLog().forEach(s -> builder.append(" ").append(s).append("\n"));
        builder.deleteCharAt(builder.length() - 1); // Remove last \n
        txtAreaBattleLog.setText(builder.toString());
    }

    public void addBtnAttackListener(ActionListener actionListener) {
        btnAttack.addActionListener(actionListener);
    }

    public void addBtnDefendListener(ActionListener actionListener) {
        btnDefend.addActionListener(actionListener);
    }

    public void addBtnEscapeListener(ActionListener actionListener) {
        btnEscape.addActionListener(actionListener);
    }

    private class PlayerBattleSprite extends JComponent {

        public PlayerBattleSprite() {
            super();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image player = ImageLoader.getInstance().getCharacterStatic();
            g.drawImage(player, 0, 0, TILE_WIDTH, TILE_HEIGHT, null);
        }
    }

    private class MobBattleSprite extends JComponent {

        private String mobName;

        public MobBattleSprite(String mobName) {
            super();
            this.mobName = mobName;
        }

        public void setMobName(String mobName) {
            this.mobName = mobName;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image mob = ImageLoader.getInstance().getMob(mobName);

            if (mobName.equals(MainDriver.BOSS_MOB)) {
                g.drawImage(mob, 0, 0, TILE_WIDTH * 3, TILE_HEIGHT * 3, null);
            } else {
                g.drawImage(mob, 0, 0, TILE_WIDTH * 2, TILE_HEIGHT * 2, null);
            }
        }
    }
}
