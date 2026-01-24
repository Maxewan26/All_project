package entity;

import graph.Graph;
import graph.Node;

public class Wizard extends Entity {
	public static int HEALTH_POINT = 330;
	public static int DAMAGE = 310;
	public static int COLOR = 0xADD8E6;
	public static int RANGE = 8;
	public static int SPEED = 8;
	public static int DEFENSE = 0;
	public static int ELEXIR = 1500;
	
	public Wizard(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	
	@Override
	public void attack(Entity ennemy) {
		Node ennemyNode = Graph.Map[ennemy.getY()][ennemy.getX()];
		Entity neighbor;
		xp += ennemy.reducePv(damage);
		int[][] neighbors = ennemyNode.getNeighbor();
		for(int i = 0;i < neighbors.length; i++) {
			neighbor = Graph.Map[neighbors[i][1]][neighbors[i][0]].getOccupant();
			if(neighbor != null && !equalsTo(neighbor.getId()) && neighbor.getTeam() != team) xp += neighbor.reducePv(damage);
		}
		tryToLevelUp();
	}

}
