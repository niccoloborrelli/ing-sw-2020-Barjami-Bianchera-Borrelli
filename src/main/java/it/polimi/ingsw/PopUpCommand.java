package it.polimi.ingsw;

public class PopUpCommand extends ReplyCommand {

    private String sentence;

    public PopUpCommand(String sentence) {
        this.sentence = sentence;
    }

    @Override
    public void execute(GraphicInterface hellofx) { ;
        hellofx.printPopUp(sentence);
    }

    @Override
    public void execute(Field field) {

    }
}
