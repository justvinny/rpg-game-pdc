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
    
        @Override
    public String toString(){
        return "L" + this.getLvl();
    }
    
	private static class Values {
            public static int BASE_THRESHOLD = 32;
    }
}
