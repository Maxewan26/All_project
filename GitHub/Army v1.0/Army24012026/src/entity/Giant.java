package entity;

public class Giant extends Entity {
	public static int HEALTH_POINT = 3000;
	public static int DAMAGE = 114;
	public static int COLOR = 0xFFA500;
	public static int RANGE = 1;
	public static int SPEED = 6;
	public static int DEFENSE = 0;
	public static int ELEXIR = 1000;
	
	public Giant(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	@Override
	public void attack(Entity ennemy) {
		xp += ennemy.reducePv(damage);
		tryToLevelUp();
	}
}
