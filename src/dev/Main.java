package dev;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String myLocations = """        
                lake,at the edge of Lake Tim,E:ocean,W:forest,S:well house,N:cave
                ocean,on Tim's beach before an angry sea,W:lake
                cave,at the mouth of Tim's bat cave,E:ocean,W:forest,S:lake
                """;
		AdventureGame adventureGame = new AdventureGame(myLocations);
		
		adventureGame.play("road");
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			String choice = scanner.nextLine().toUpperCase().substring(0,1); 
			if(choice.equals("Q")) {
				System.out.println("The Game Ended!");
				break;
			}
			adventureGame.move(choice);
		}
	}
}
