package it.polimi.ingsw;

public interface Command {
    public void manageCommand(GeneralStringRequestCommand generalStringRequestCommand);
    public void manageCommand(SentenceBottomRequestCommand sentenceBottomRequestCommand);
    public void manageCommand(PopUpCommand popUpCommand);
    public void manageCommand(ShowAvCells showAvCells);
    public void manageCommand(RemovingCommand removingCommand);
    public void manageCommand(SettingPawnCommand settingPawnCommand);
    public void manageCommand(LimitedOptionsCommand limitedOptionsCommand);
    public void manageCommand(TransitionSceneCommand transitionSceneCommand);
    public void manageCommand(MoveUpdateCommand moveUpdateCommand);
    public void manageCommand(BuildUpdateCommand buildUpdateCommand);
    void manageCommand(SetUpCommand setUpCommand);
    void manageCommand(QuitCommand quitCommand);
}

