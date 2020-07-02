package it.polimi.ingsw;

import static it.polimi.ingsw.FinalCommunication.*;

public class TransitionSceneCommand extends ReplyCommand {
    /**
     * Represents a transition of moments in the game.
     */

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

    /**
     * Sets different scene depending of information.
     * @param app
     */
    public void execute(App app){
        switch (specification) {
            case NAME:
                app.setNameStage();
                break;
            case GODSET:
                app.setGodsStage();
                break;
            case WAITING_COLOR:
                app.setWaitingColor();
                break;
            case WAITING_PLAYER:
                app.setWaitingPlayer();
                break;
            case WAITING_GAME:
                app.setWaitingGame();
                break;
        }
    }

    /**
     * Prints a specific sentence depending of information of command.
     * @param field is the interface.
     */

    @Override
    public void execute(Field field) {
        new SentenceBottomRequestCommand(playerName, playerColor ,specification).execute(field);
    }
}
