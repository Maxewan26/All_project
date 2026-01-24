package util;

import entity.Entity;

public class Printer {
	private Printer() {};
	
	public static void printTab(Entity []tab) {
		System.out.println("[");
		for(int i = 0; i < tab.length; i++) {
			System.out.println(tab[i] + ", \n");
		}
		System.out.println("]");
	}
	public static void printTab(Entity [][]tab) {
		System.out.println("[");
		for(int i = 0; i < tab.length; i++) {
			System.out.println("{");
			for(int j = 0; j < tab[i].length; j++) {
				System.out.println(tab[i][j] + "\n");
			}
			System.out.println("}, \\n");
		}
		System.out.println("] \n");
	}
	
	public static void printTab(Object []tab) {
		System.out.println("[");
		for(int i = 0; i < tab.length; i++) {
			System.out.println(tab[i] + ", \n");
		}
		System.out.println("]");
	}
	
	
	public static void printTab(Object [][]tab) {
		System.out.println("[");
		for(int i = 0; i < tab.length; i++) {
			System.out.println("{");
			for(int j = 0; j < tab[i].length; j++) {
				System.out.println(tab[i][j] + "\n");
			}
			System.out.println("}, \\n");
		}
		System.out.println("] \n");
	}
	
}
