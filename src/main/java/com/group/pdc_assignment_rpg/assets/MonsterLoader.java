/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group.pdc_assignment_rpg.assets;

import com.group.pdc_assignment_rpg.logic.entities.Mob;
import com.group.pdc_assignment_rpg.utilities.ResourceLoaderUtility;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Vinson Beduya - 19089783 <vinsonemb.151994@gmail.com>
 */
public class MonsterLoader {

    public static final String BOSS_MOB = "Guardian Golem";
    public static final double BOSS_ATTACK_PERSONALITY = .5;
    public static final double BOSS_DEFEND_PERSONALITY = .5;
    public static final double BOSS_ESCAPE_PERSONALITY = 0;
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
        if (mobName.equals("Guardian Golem")) {
            Mob guardianGolem = monsterList.get(mobName).cloneMob();
            guardianGolem.setMaxHP(150);
            guardianGolem.getCoordinates().setCoordinates(86, 2);
            guardianGolem.populatePersonality(
                    BOSS_ATTACK_PERSONALITY, // Boss should attack more
                    BOSS_DEFEND_PERSONALITY, // Boss should defend sometimes.
                    BOSS_ESCAPE_PERSONALITY); // Boss shouldn't flee., 0, 0);
            return guardianGolem;
        }

        return monsterList.get(mobName).cloneMob();
    }

    public Mob getRandomMob() {
        int num = (int) (Math.random() * 3);
        switch (num) {
            case 1:
                return getMob("Ghoul");
            case 2:
                return getMob("Executioner");
            default:
                return getMob("Slime");
        }
    }

    public static void initMonsterLoader() {
        if (instance == null) {
            instance = new MonsterLoader();
        }
    }

    public static MonsterLoader getInstance() {
        return instance;
    }
}
