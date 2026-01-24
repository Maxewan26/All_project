package entity;

public class Barbarian extends Entity{
	public static int HEALTH_POINT = 270;
	public static int DAMAGE = 45;
	public static int COLOR = 0xFFDE21;
	public static int RANGE = 1;
	public static int SPEED = 8;
	public static int DEFENSE = 0;
	public static int ELEXIR = 25;
	
	public Barbarian(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	@Override
	public void attack(Entity ennemy) {
		xp += ennemy.reducePv(damage);
		tryToLevelUp();
	}
}
