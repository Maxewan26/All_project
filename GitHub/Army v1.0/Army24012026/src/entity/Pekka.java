package entity;

public class Pekka extends Entity {
	
	public static int HEALTH_POINT = 6700;
	public static int DAMAGE = 680;
	public static int COLOR = 0x003366;
	public static int RANGE = 1;
	public static int SPEED = 8;
	public static int DEFENSE = 0;
	public static int ELEXIR = 30000;

	public Pekka(int id, boolean team, int[] pos) {
		super(id,HEALTH_POINT, DAMAGE, RANGE, SPEED, DEFENSE,team,  COLOR, pos, ELEXIR);
	}

	@Override
	public void attack(Entity ennemy) {
		xp += ennemy.reducePv(damage);
		tryToLevelUp();
	}

}
