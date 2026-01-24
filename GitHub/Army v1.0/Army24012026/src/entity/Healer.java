package entity;

import graph.Graph;
import graph.Node;

public class Healer extends Entity {
	public static int HEALTH_POINT = 1700;
	public static int DAMAGE = 72;
	public static int COLOR = 0xE8DCCA;
	public static int RANGE = 13;
	public static int SPEED = 8;
	public static int DEFENSE = 0;
	public static int ELEXIR = 5000 ;
	
	public Healer(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	
	@Override
	public void attack(Entity ennemy) {
		Node myNode = Graph.Map[getY()][getX()];
		Entity neighbor;
		int[][] neighbors = myNode.getNeighbor();
		for(int i = 0;i < neighbors.length; i++) {
			neighbor = Graph.Map[neighbors[i][1]][neighbors[i][0]].getOccupant();
			if(neighbor != null && neighbor.getTeam() == team) neighbor.increasePv(damage);
		}

	}

}
