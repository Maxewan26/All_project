package graph;
import entity.*;
import java.util.Queue;
import java.util.ArrayDeque;


public class Graph {
	public static final int MAX_TROUP = 10000;
	public static final int DEFAULT_LENGTH = 100; //Doit être un résultat entier de la division de 192 par un naturel inféireure ou égale à 91
	public static final int DEFAULT_HEIGHT = 50; //Doit valoir la moitié supérieur de DEFAULT_LENGTH
	
	public static Node[][] Map = new Node[DEFAULT_HEIGHT][DEFAULT_LENGTH];
	public static Entity[] LeftTeam = new Entity[MAX_TROUP] ;
	public static Entity[] RightTeam = new Entity[MAX_TROUP];
	public static int nbLeftTroup = 0;
	public static int nbRightTroup = 0;
	
	private Graph() {};
	
	public static void initialiseMap() {
		for(int i = 0; i < DEFAULT_HEIGHT; i++) {
			for(int j = 0; j < DEFAULT_LENGTH; j++) {
				Map[i][j] = new Node(j,i);
			}
		}
	}
	
	public static void release(int x, int y) {
		Map[y][x].release();
	}
	
	public static void occupy(boolean team, Entity occupant, int x, int y) {
		Map[y][x].tryOccupy(occupant);
	}
	
	public static boolean addEntity(Entity elt, boolean team) {
		if(!team) {
			if( nbLeftTroup < MAX_TROUP) {
				LeftTeam[nbLeftTroup++] = elt;
				return true;
			}
			return false;

		}
		if(nbRightTroup < MAX_TROUP) {
			RightTeam[nbRightTroup++] = elt;
			return true;
		}
		return false;
	}
	
	//Renvoie l'identifiant en 0, et la position en 1 et 2
	public static Entity searchTarget(int x, int y, boolean team) {
		Node tempNode;
		Entity tempEntity;
		int[] tempValue;
		Queue<Node> file = new ArrayDeque<>();
		file.add(Map[y][x]);
		Map[y][x].setVisit(true);
		int[][] neighbor;
		while(!file.isEmpty()) {
		
			neighbor = file.poll().getNeighbor();
			if(team) {
				tempValue = neighbor[neighbor.length-1];
				neighbor[neighbor.length-1] = neighbor[0];
				neighbor[0] = tempValue;
			}
			
			for(int i = 0; i < neighbor.length; i++) {
					tempNode = Map[neighbor[i][1]][neighbor[i][0]];
					
					if(!tempNode.getVisit()) {
						tempEntity = tempNode.getOccupant();
						if(tempNode.getBusy() && tempEntity.getTeam() != team) {
							return tempNode.getOccupant();
						}
						
						tempNode.setVisit(true);
						file.add(tempNode);

					}
			}
			
		}
		return null;
	}
	
	public static void cleanVisit() {
		for(int i = 0; i < Map.length; i++) {
			for(int j = 0; j < Map[i].length; j++) {
				Map[i][j].setVisit(false);
			}
		}
	}
	
	
}

