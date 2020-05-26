package it.polimi.ingsw;

import java.io.IOException;

public class GodChoice extends State {
    private TurnManager turnManager;

    GodChoice(Player player) {
        super(player);
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        String input=visitor.visit(this);
        if(getAllowedInputs().contains(input)){
            player.setPlayerGod(input);
            turnManager.removeGod(input);
            System.out.println("Ha scelto: " + input);
            try {
                player.getController().decoratePlayer(player);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            player.getStateManager().setNextState(player);
        }
        else
            player.notify(1);
    }

    @Override
    public void onStateTransition() {

        turnManager = player.getStateManager().getTurnManager();
        if(turnManager.getAvailableGods().size()==1) {
            System.out.println("Ha forzatamanete scelto: " + turnManager.getAvailableGods().get(0));
            player.setPlayerGod(turnManager.getAvailableGods().get(0));
            turnManager.getAvailableGods().remove(player.getPlayerGod());
            try {
                player.getController().decoratePlayer(player);
                player.getStateManager().setNextState(player);
                //player.getStateManager().setNextState(player);
            } catch (IOException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            player.notifyLeft(turnManager.getAvailableGods());
            setAllowedInputs(turnManager.getAvailableGods());
        }
    }

    public String toString(){
        return "GodChoiceState";
    }
}
