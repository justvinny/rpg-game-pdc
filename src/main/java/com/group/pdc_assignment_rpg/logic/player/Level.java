package com.group.pdc_assignment_rpg.logic.player;

public enum Level {

	L1, L2, L3, L4, L5, L6, L7, L8, L9, L10, L11, L12, L13, L14, L15, L16, L17, L18, L19, L20;
	
	
	private int xpThreshold;

	private Level() {
		this.setThreshold(Values.BASE_THRESHOLD);
		Values.BASE_THRESHOLD *= 1.8;
	}
	
	public int getThreshold() {
		return xpThreshold;
	}

	public void setThreshold(int xpThreshold) {
		this.xpThreshold = xpThreshold;
	}
	
	private static class Values {
        public static int BASE_THRESHOLD = 32;
    }
	
}
