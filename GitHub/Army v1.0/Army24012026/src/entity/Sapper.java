package entity;
import graph.*;

public class Sapper extends Entity {
	public static int HEALTH_POINT = 130;
	public static int DAMAGE = 3760;
	public static int COLOR = 0xFFFFFF;
	public static int RANGE = 1;
	public static int SPEED = 12;
	public static int DEFENSE = 0;
	public static int ELEXIR = 600;
	
	public Sapper(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	@Override
	public void attack(Entity ennemy) {
		Node ennemyNode = Graph.Map[ennemy.getY()][ennemy.getX()];
		Entity neighbor;
		ennemy.reducePv(damage);
		int[][] neighbors = ennemyNode.getNeighbor();
		for(int i = 0;i < neighbors.length; i++) {
			neighbor = Graph.Map[neighbors[i][1]][neighbors[i][0]].getOccupant();
			if(neighbor != null && neighbor.getTeam() != team) neighbor.reducePv(damage);
		}
	}

}
