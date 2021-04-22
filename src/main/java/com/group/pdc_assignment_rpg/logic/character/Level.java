package com.group.pdc_assignment_rpg.logic.character;


public enum Level implements IterableEnum<Level> {

	L1, L2, L3, L4, L5, L6, L7, L8, L9, L10, L11, L12, L13, L14, L15, L16, L17, L18, L19, L20;
	
	
	private int xpThreshold;
        private int lvl;

	private Level() {
		this.setThreshold(Values.BASE_THRESHOLD);
                this.setLvl(this.ordinal() + 1);
                
		Values.BASE_THRESHOLD = (int)Math.round(Values.BASE_THRESHOLD * 1.8);
	}
	
    public int getThreshold() {
            return xpThreshold;
    }
    
    public int getLvl() {
        return lvl;
    }

    public void setThreshold(int xpThreshold) {
            this.xpThreshold = xpThreshold;
    }
	
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    public static Level createLvl(int lvl) {
        Level level = L1;
        switch(lvl) {
            case 1:
                level = L1;
                break;
            case 2:
                level = L2;
                break;
            case 3:
                level = L3;
                break;
            case 4:
                level = L4;
                break;
            case 5:
                level = L5;
                break;
            case 6:
                level = L6;
                break;
            case 7:
                level = L7;
                break;
            case 8:
                level = L8;
                break;
            case 9:
                level = L9;
                break;
            case 10:
                level = L10;
                break;
            case 11:
                level = L11;
                break;
            case 12:
                level = L12;
                break;
            case 13:
                level = L13;
                break;
            case 14:
                level = L14;
                break;
            case 15:
                level = L15;
                break;
            case 16:
                level = L16;
                break;
            case 17:
                level = L17;
                break;
            case 18:
                level = L18;
                break;
            case 19:
                level = L19;
                break;
            case 20:
                level = L20;
                break;
            default:
                level = L20;
                
                
        }
        
        return level;
    }
        @Override
    public String toString(){
        return "L" + this.getLvl();
    }
    
	private static class Values {
            public static int BASE_THRESHOLD = 32;
    }
}
