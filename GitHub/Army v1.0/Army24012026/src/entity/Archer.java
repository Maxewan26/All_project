package entity;

public class Archer extends Entity{
	public static int HEALTH_POINT = 68;
	public static int DAMAGE = 40;
	public static int COLOR = 0xFFC0CB;
	public static int RANGE = 9;
	public static int SPEED = 12;
	public static int DEFENSE = 0;
	public static int ELEXIR = 50;
	
	public Archer(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}
	
	@Override
	public void attack(Entity ennemy) {
		xp += ennemy.reducePv(damage);
		tryToLevelUp();
	}
}
