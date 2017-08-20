package ua.ztest;

public class Place {

	private static final Building[] buildings = new Building[16];
	
	public static void place(Building building){
		for(int i = 0; i < buildings.length; i++) {
			if(buildings[i]==null) {
				buildings[i] = building;
			}
		}
	}
	
	public static void destroy(int index){
		buildings[index] = null;
		for(int i = index; i < buildings.length-1; i++) {
			if(buildings[i+1]!=null) {
				buildings[i] = buildings[i+1];
			}
		}
	}
}
