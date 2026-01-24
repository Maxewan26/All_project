package graph;
import entity.*;

public class Node {
	private final int x;
	private final int y;
	private boolean busy;
	private boolean visit;
	private Entity occupant;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.busy = false;
		this.visit = false;
		this.occupant = null;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;	
	}
	public int[] getPos() {
		return new int[] {x,y};
	}
	public boolean getBusy() {
		return this.busy;
	}
	public boolean getVisit() {
		return this.visit;
	}
	
	public Entity getOccupant() {
		return this.occupant;
	}
	
	public boolean setBusy(boolean busy) {
		if(busy && this.busy) {
			System.err.println("Node.java, #01 : La case (" + y + " , " + x + ") est déjà occupée");
			return false;
		}
		else if(!busy && !this.busy) {
			System.err.println("Node.java, #01 : La case (" + y + " , " + x + ") n'avait déjà personne");
		}
		this.busy = busy;
		return true;
	}
	public void setVisit(boolean visit) {
		this.visit = visit;
	}
	
	public void setOccupant(Entity occupant) {
		this.occupant = occupant;
	}
	
	public boolean equalsTo(int[] pos) {
		if(pos[0] == this.x && pos[1] == this.y) return true;
		return false;
	}
	
	public boolean neighborTo(int[] pos) {
		if((pos[0] == x-1 && pos[1] == y) ||(pos[0] == x+1 && pos[1] == y) ||(pos[0] == x && pos[1] == y-1) ||(pos[0] == x && pos[1] == y+1)) return true;
		return false;
	}
	
	public int[][] getNeighbor(){
		int nbNeigh = 0;
		int[][] neigh = new int[4][2];
		boolean [] isGood = new boolean[] {false,false,false,false};
		if(x+1 < Graph.DEFAULT_LENGTH) {
			nbNeigh++;
			neigh[0][0] = x+1;
			neigh[0][1] = y;
			isGood[0] = true;
		}
		if(y+1 < Graph.DEFAULT_HEIGHT) {
			nbNeigh++;
			neigh[1][0] = x;
			neigh[1][1] = y+1;
			isGood[1] = true;
		}
		if(y-1 >= 0) {
			nbNeigh++;
			neigh[2][0] = x;
			neigh[2][1] = y-1;
			isGood[2] = true;
		}

		if(x-1 >= 0) {
			nbNeigh++;
			neigh[3][0] = x-1;
			neigh[3][1] = y;
			isGood[3] = true;
		}

		int[][] neighbor = new int[nbNeigh][2];
		nbNeigh = 0;
		for(int i = 0; i < isGood.length; i++) {
			if(isGood[i]) {
				neighbor[nbNeigh++] = neigh[i];
			}
		}
		return neighbor;
	}
	
	public void release() {
		setBusy(false);
		occupant = null;
	}
	public boolean tryOccupy(Entity occupant) {
	    if (busy) return false;
	    setBusy(true);
	    this.occupant = occupant;
	    return true;
	}

	
	
}
