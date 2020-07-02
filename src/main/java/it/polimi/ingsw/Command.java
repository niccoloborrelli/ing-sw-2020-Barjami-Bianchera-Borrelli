package it.polimi.ingsw;

public interface Command {

    /**
     * Manages a command that represents a string.
     * @param generalStringRequestCommand is command that represents a string.
     */
    public void manageCommand(GeneralStringRequestCommand generalStringRequestCommand);

    /**
     * Manages a command that represents a sentence to print in bottom, if possible.
     * @param sentenceBottomRequestCommand
     */
    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand);

    /**
     * Manages a command that represents a sentence to print in a pop-up, if possible.
     * @param popUpCommand is command that represents a sentence to print in a pop-up.
     */
    public void manageCommand(PopUpCommand popUpCommand);

    /**
     * Manages a command that represents an updating of determined cells.
     * @param showAvCells is a command that represents an updating of determined cells.
     */
    public void manageCommand(ShowAvCells showAvCells);

    /**
     * Manages a command that represents a remove in field.
     * @param removingCommand is command that represents a remove in field.
     */
    public void manageCommand(RemovingCommand removingCommand);

    /**
     * Manages a command that represents pawn setting in field.
     * @param settingPawnCommand is command that represents pawn setting in field.
     */
    public void manageCommand(SettingPawnCommand settingPawnCommand);

    /**
     * Manages a command that represents a list of limited options.
     * @param limitedOptionsCommand is the command that represents a list of limited options.
     */
    public void manageCommand(LimitedOptionsCommand limitedOptionsCommand);

    /**
     * Manages a command that represents a transition of moment in game.
     * @param transitionSceneCommand is the command that represents a transition of moment in game..
     */
    public void manageCommand(TransitionSceneCommand transitionSceneCommand);

    /**
     * Manages a command that represents an update caused by a moving in field.
     * @param moveUpdateCommand is command that represents an update caused by a moving in field.
     */
    void manageCommand(MoveUpdateCommand moveUpdateCommand);

    /**
     * Manages a command that represents an update caused by a build in field.
     * @param buildUpdateCommand is the command that represents an update caused by a build in field.
     */
    void manageCommand(BuildUpdateCommand buildUpdateCommand);

    /**
     * Manages a command that represents a set up request.
     * @param setUpCommand is command that represents a set up request.
     */
    void manageCommand(SetUpCommand setUpCommand);

    /**
     * Manages a quit command.
     * @param quitCommand is quit command.
     */
    void manageCommand(QuitCommand quitCommand);

    /**
     * Manages a command that represents an exit of game.
     * @param exitCommand is the ommand that represents an exit of game.
     */
    void manageCommand(ExitCommand exitCommand);
}

