package entity;

import graph.Graph;
import graph.Node;

public class Balloon extends Entity {
	public static int HEALTH_POINT = 1040;
	public static int DAMAGE = 276;
	public static int COLOR = 0xFF0000;
	public static int RANGE = 1;
	public static int SPEED = 5;
	public static int DEFENSE = 0;
	public static int ELEXIR = 2000;
	
	public Balloon(int id, boolean team, int[] pos) {
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
