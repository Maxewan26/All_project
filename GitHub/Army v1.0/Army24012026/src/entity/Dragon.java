package entity;

import graph.Graph;

public class Dragon extends Entity {
	public static int HEALTH_POINT = 4500;
	public static int DAMAGE = 350;
	public static int COLOR = 0x7F00FF;
	public static int RANGE = 8;
	public static int SPEED = 8;
	public static int DEFENSE = 0;
	public static int ELEXIR = 25000;
	
	public static int SIZE_OF_ATTACK = 5;

	public Dragon(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	
	@Override
	//direction peut être défini entre 0 et 3. Sers toi d'un cercle trigo pour savoir dans quelle direction attaquer.
	public void attack(Entity ennemy) {
		int direction = comparePosition(ennemy);
		attack(ennemy, SIZE_OF_ATTACK, direction, ennemy.getX(), ennemy.getY());

	}
	
	public void attack(Entity ennemy, int nbEnnemyToAttack, int direction, int x, int y) {
		if(nbEnnemyToAttack <= 0) {
			return;
		}

		
		if(ennemy != null) xp += ennemy.reducePv(damage);
		
		if(direction == 0) {
			if(x+1 < Graph.DEFAULT_LENGTH) attack(Graph.Map[y][x+1].getOccupant(),nbEnnemyToAttack-1, direction, x+1, y );
		}
		else if(direction == 1) {
			if(y-1 >= 0) attack(Graph.Map[y-1][x].getOccupant(),nbEnnemyToAttack-1, direction, x, y-1);
		}
		else if(direction == 2) {
			if(x-1 >= 0) attack(Graph.Map[y][x-1].getOccupant(),nbEnnemyToAttack-1, direction, x-1, y);
		}
		else{
			if(y+1 < Graph.DEFAULT_HEIGHT) attack(Graph.Map[y+1][x].getOccupant(),nbEnnemyToAttack-1, direction, x, y+1);
		}
	}
	
	public int comparePosition(Entity ennemy){
		int enX = ennemy.getX();
		int enY = ennemy.getY();
		boolean compDistance = compareDistanceAxis(enX, enY);
		int direction = directionOf(enX,enY);
		if(compDistance) {
			if(direction == 0 || direction == 3) {
				return 0;
			}
			return 2;
		}
		if(direction == 0 || direction == 1) {
			return 1;
		}
		return 3;
	}

}
