package soliddomino.game.main;

import soliddomino.game.dominos.Domino;
import java.util.Scanner;
import soliddomino.game.dominos.ConsoleDomino;

public class SolidDomino {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String replayAnswer = "n";
        
        do {
            Domino game = new ConsoleDomino();
            game.init();
            String winner = game.play();
            System.out.println(winner + " has won.");
            System.out.print("Do you want to play again? Y/N: ");
            replayAnswer = scan.next();
        } while(replayAnswer.toLowerCase().charAt(0) == 'y');
    }
}
