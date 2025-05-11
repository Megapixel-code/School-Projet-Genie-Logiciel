package Labyrinthe;

import java.util.Scanner;

public class Start {

    public static void start(){
        System.out.println("Bienvenido en el CYnapso !! ");
        String answer;
        do{
            System.out.println("New game or load game ? N : L");
            Scanner input = new Scanner (System.in);
            answer = input.nextLine().toLowerCase();

            if (!("n".equals(answer)) && !("l".equals(answer))){
                System.out.println("Invalid input! Please type only N or L.");
            }
        }while(!("n".equals(answer)) && !("l".equals(answer)));

        if ("n".equals(answer)){
            Maze maze = Maze.initialize();
        } else {
            Load.search();
        }

    }

}
