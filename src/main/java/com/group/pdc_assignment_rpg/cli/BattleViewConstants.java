/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.cli;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public enum BattleViewConstants {
    INVALID(-1), ATTACK(5), DEFEND(6), ESCAPE(7);

    private int rowNum;

    private BattleViewConstants(int rowNum) {
        this.rowNum = rowNum;
    }

    public static BattleViewConstants toEnum(int value) {
        switch (value) {
            case 5:
                return ATTACK;
            case 6:
                return DEFEND;
            case 7:
                return ESCAPE;
            default:
                return INVALID;
        }
    }
}
