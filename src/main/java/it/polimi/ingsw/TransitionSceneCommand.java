package it.polimi.ingsw;

import static it.polimi.ingsw.FinalCommunication.*;

public class TransitionSceneCommand extends ReplyCommand {

    private String playerName;
    private String playerColor;
    private String specification;

    public TransitionSceneCommand(String playerName, String playerColor, String specification) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.specification = specification;
    }

    @Override
    public void execute(GraphicInterface gui) {
    }

    public void execute(App app){
        if(specification.equals(NAME))
            app.setNameStage();
        else if(specification.equals(GODSET))
            app.setGodsStage();
        else if(specification.equals(WAITING_COLOR))
            app.setWaitingColor();
        else if(specification.equals(WAITING_PLAYER))
            app.setWaitingPlayer();
        else if(specification.equals(WAITING_GAME))
            app.setWaitingGame();
    }

    @Override
    public void execute(Field field) {
        new SentenceBottomRequestCommand(playerName, playerColor ,specification).execute(field);
    }
}
