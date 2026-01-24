package entity;

public class Goblin extends Entity {
	public static int HEALTH_POINT = 146;
	public static int DAMAGE = 72;
	public static int COLOR = 0x00FF00;
	public static int RANGE = 1;
	public static int SPEED = 16;
	public static int DEFENSE = 0;
	public static int ELEXIR = 25;
	
	public Goblin(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	@Override
	public void attack(Entity ennemy) {
		xp += ennemy.reducePv(damage);
		tryToLevelUp();
	}

}
