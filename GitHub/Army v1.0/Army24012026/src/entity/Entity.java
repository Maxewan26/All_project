package entity;
import graph.Graph;

public abstract class Entity {
	protected int id;
	protected int lvl;
	protected int xp;
	protected int xpRequiredToLvlUp;
	protected int healthPoint;
	protected int maxHealthPoint;
	protected int damage;
	protected int range;
	protected int speed;
	protected int defense;
	protected Entity target;
	protected boolean team;
	protected boolean dead;
	protected int color;
	protected int pos[];
	
	public Entity(int id, int healthPoint, int damage,int range, int speed,int defense,boolean team, int color, int pos[], int xpRequiredToLvlUp) {
		this.id = id;
		this.lvl = 1;
		this.xp = 0;
		this.xpRequiredToLvlUp = xpRequiredToLvlUp;
		this.healthPoint = healthPoint;
		this.maxHealthPoint = healthPoint;
		this.damage = damage;
		this.range = range;
		this.speed = speed;
		this.defense = defense;
		this.team = team;
		this.dead = false;
		this.color = color;
		this.pos = pos;
		target = null;
		Graph.occupy(team, this, pos[0], pos[1]);

	}
	
	public int getId() {
		return id;
	}
	public int getHP() {
		return healthPoint;
	}
	public int getDm() {
		return damage;
	}
	public int getRg() {
		return range;
	}
	public boolean getTeam() {
		return team;
	}
	public int getColor() {
		return color;
	}
	public int getSp() {
		return speed;
	}
	public int[] getPos() {
		return pos;
	}

	public int getX() {
		return pos[0];
	}
	
	public int getY() {
		return pos[1];
	}
	
	public boolean getDead() {
		return dead;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public String toString() {
		return "Entité n°"+id+" x: " + pos[0]+ " y: "+ pos[1] + " hp : " +healthPoint + " mort? : " + dead;
	}
	
	public boolean equalsTo(int id) {
		if(this.id == id) return true;
		return false;
	}
	
	public int reducePv(int value) {
		healthPoint -= value - defense;
		
		if(healthPoint <= 0) {
			healthPoint = 0;
			setDead(true);
			Graph.release(pos[0], pos[1]);
			return xpRequiredToLvlUp;
		}
		return 0;
	}
	
	public void increasePv(int value) {
		healthPoint += value;
		if(healthPoint > maxHealthPoint) {
			healthPoint = maxHealthPoint;
		}
	}
	
	public void tryToLevelUp() {
		if(xp >= xpRequiredToLvlUp) {
			levelUp();
		}
	}
	
	public void levelUp() {
		lvl++;
		System.out.println(id + " est passé niveau "+lvl + "!" );
		defense++;
		maxHealthPoint *=2;
		healthPoint *= 2;
		damage *= 2;
		defense++;
		xp -= xpRequiredToLvlUp;
		xpRequiredToLvlUp *= 2;
		tryToLevelUp();
	}
	
	public boolean move(int x, int y) {
	    if (Graph.Map[y][x].tryOccupy(this)) {
	        Graph.release(pos[0], pos[1]);
	        pos[0] = x;
	        pos[1] = y;
	        return true;
	    }
	    return false;
	}

	
	public void action() {
		target = Graph.searchTarget(this.pos[0], this.pos[1], this.team);
		Graph.cleanVisit();
		
		if(target != null && !target.getDead()) {
			int x = target.getX();
			int y = target.getY();
			if(reachable(x,y)) {
				attack(target);
			}
			else {
				for(int i = 0; i < speed; i++) {
					if(compareDistanceAxis(x,y)) {
						if(!tryToMoveAxisX(x)) {
							tryToMoveAxisY(y);
						}
					}
					else {
						if(!tryToMoveAxisY(y)) {
							tryToMoveAxisX(x);
						}
					}

				}
			}
		}
	}
	
	public boolean tryToMoveAxisX(int x) {
		if(pos[0] < x && !Graph.Map[pos[1]][pos[0]+1].getBusy()) {
			return move(pos[0]+1,pos[1]);
		}
		else if(pos[0] > x && !Graph.Map[pos[1]][pos[0]-1].getBusy()) {
			return move(pos[0]-1,pos[1]);
		}
		return false;
	}
	
	public boolean tryToMoveAxisY(int y) {
		if(pos[1] < y && !Graph.Map[pos[1]+1][pos[0]].getBusy()) {
			return move(pos[0],pos[1]+1);
		}
		else if(pos[1] > y && !Graph.Map[pos[1]-1][pos[0]].getBusy()) {
			return move(pos[0],pos[1]-1);
		}
		return false;
	}
	
	public boolean reachable(int x, int y) {
		int dist = 0;
		if(x <= pos[0]) {
			dist = pos[0] - x;
		}
		else {
			dist = x - pos[0];
		}
		if(y <= pos[1]) {
			dist += pos[1] - y;
		}
		else {
			dist += y - pos[1];
		}
		if(dist <= this.range) {
			return true;
		}
		return false;
	}
	
	public boolean compareDistanceAxis(int x, int y) {
		int distX = this.pos[0] - x;
		int distY = this.pos[1] - y;
		if(distX < 0) {
			distX = -distX;
		}
		if(distY < 0) {
			distY = -distY;
		}
		if(distX <= distY) {
			return false;
		}
		return true;
	}
	
	public int directionOf(int x, int y) {
		boolean behindInX = false;
		boolean behindInY = false;
		if(pos[0] <= x) {
			behindInX = true;
		}
		if(pos[1] <= y) {
			behindInY = true;
		}
		if(!behindInX && !behindInY) {
			return 1;
		}
		if(behindInX && !behindInY) {
			return 0;
		}
		if(!behindInX && behindInY) {
			return 2;
		}
		return 3;
	}
	
	public abstract void attack(Entity ennemy);
	
	
	
	
}
