import entity.*;

import graph.*;
import swing.DrawCombat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		 Graph.initialiseMap();
		 readFile();
		 startGame();
	}
	

	
	public static void readFile() {
		try {
            FileReader fr = new FileReader("data.txt");
            BufferedReader br = new BufferedReader(fr);
            boolean team = false;
            boolean reverseMode = false;
            int nbOfEntity;
            int x = -1;
            int y = 0;
            int id = 0;

            String ligne;
            while ((ligne = br.readLine()) != null) {
            	if(!team) x++; else x--;
            	reverseMode = false;
            	y = 0;
                if(ligne.equals("RIGHT")) {
                	team = true;
                	x = Graph.DEFAULT_LENGTH;
                }
                else if(ligne.equals("Barbarian")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Barbarian(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                	
                }
                else if(ligne.equals("Archer")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Archer(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Giant")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Giant(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Goblin")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Goblin(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Sapper")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Sapper(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Balloon")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Balloon(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Wizard")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Wizard(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Healer")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Healer(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Dragon")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Dragon(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else if(ligne.equals("Pekka")) {
                	nbOfEntity = Integer.parseInt(br.readLine());
                	if(nbOfEntity < 0) {
                		nbOfEntity = -nbOfEntity;
                		reverseMode = true;
                		y = Graph.DEFAULT_HEIGHT-1;
                	}
                	for(int i = 0; i < nbOfEntity; i++) {
                		Graph.addEntity(new Pekka(id++, team, new int[] {x,y}), team);
                		if(reverseMode) y--; else y++;
                	}
                }
                else {
                	System.err.println("Main.java, #01, Problème de lecture de la ligne "+ ligne );
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier");
        }
	}
	
	public static void startGame() {
		loop();
		
	}
	
	public static void loop() {
		DrawCombat combat = new DrawCombat(Graph.DEFAULT_LENGTH, Graph.DEFAULT_HEIGHT);
		JFrame frame = new JFrame("Pixel Combat");

	    frame.add(combat);
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	    combat.repaint();
		while(true) {
			for(int i = Graph.nbLeftTroup-1; i >= 0; i--) {
				Entity temp = Graph.LeftTeam[i];
				if(temp != null && !temp.getDead()) temp.action();
			}
			combat.update(Graph.LeftTeam, Graph.RightTeam);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = Graph.nbRightTroup; i >= 0; i--) {
				Entity temp = Graph.RightTeam[i];
				if(temp != null && !temp.getDead()) temp.action();
			}
			combat.update(Graph.LeftTeam, Graph.RightTeam);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}
}
//for(int i = 0; i <  Graph.nbLeftTroup; i++) {
//for(int i = 0; i <  Graph.nbRightTroup; i++)
