/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.assets;

import static com.group.pdc_assignment_rpg.MainDriver.BOSS_ATTACK_PERSONALITY;
import static com.group.pdc_assignment_rpg.MainDriver.BOSS_DEFEND_PERSONALITY;
import static com.group.pdc_assignment_rpg.MainDriver.BOSS_ESCAPE_PERSONALITY;
import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MonsterLoader {

    private static MonsterLoader instance = null;
    private Map<String, Mob> monsterList;

    private MonsterLoader() {
        monsterList = ResourceLoaderUtility.loadAllMobs();
        setMobDrops();
    }

    private void setMobDrops() {
        Iterator<Mob> iterator = monsterList.values().iterator();

        while (iterator.hasNext()) {
            String mobName = iterator.next().getName();
            monsterList.get(mobName).setInventory(ResourceLoaderUtility.loadMobDrops(mobName));
        }
    }

    public Mob getMob(String mobName) {
        if (mobName.equals("Ghoul King")) {
            Mob ghoulKing = monsterList.get(mobName).cloneMob();
            ghoulKing.setMaxHP(350);
            ghoulKing.getCoordinates().setCoordinates(86, 2);
            ghoulKing.populatePersonality(
                    BOSS_ATTACK_PERSONALITY, // Boss should attack more
                    BOSS_DEFEND_PERSONALITY, // Boss should defend sometimes.
                    BOSS_ESCAPE_PERSONALITY); // Boss shouldn't flee., 0, 0);
            return ghoulKing;
        }
        
        return monsterList.get(mobName).cloneMob();
    }

    public Mob getRandomMob() {
        int num = (int) (Math.random() * 3);
        switch (num) {
            case 1:
                return getMob("Red Goblin");
            case 2:
                return getMob("Red Bandit");
            default:
                return getMob("Red Slime");
        }
    }

    public static void initMonsterLoader() {
        instance = new MonsterLoader();
    }

    public static MonsterLoader getInstance() {
        if (instance == null) {
            initMonsterLoader();
        }

        return instance;
    }
}
