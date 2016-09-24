package soliddomino.game.dominos;

import soliddomino.game.managers.ConsoleBoard;
import soliddomino.game.movement.ConsoleMovementBuilder;

public class ConsoleDomino extends Domino{
    public ConsoleDomino(){
        super(new ConsoleBoard(), new ConsoleMovementBuilder());
    }
}
