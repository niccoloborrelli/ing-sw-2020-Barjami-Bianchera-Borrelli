package it.polimi.ingsw;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NameSettingState extends State {
    private static final String MESSAGENAME="name";
    NameSettingState(Player player) {
        super(player);
    }

    @Override
    public void onInput(Visitor visitor) throws IOException {
        boolean nameSetted=false;
        String input=visitor.visit(this);
        TurnManager turnManager=player.getStateManager().getTurnManager();
        synchronized (turnManager){
            if(!turnManager.getNotAllowedNames().contains(input)) {
                turnManager.addName(input);
                player.setPlayerName(input);
                player.getStateManager().setNextState(player);
                nameSetted=true;
            }
        }
        if(!nameSetted) {
            uselessInputNotify();
        }
    }

    @Override
    public void onStateTransition() {
        try {
            player.getController().createFluxTable();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        nameNotify();
    }

    public String toString(){
        return "NameSettingState";
    }

    private void nameNotify(){
        LastChange inputExpected = player.getLastChange();
        inputExpected.setCode(0);
        inputExpected.setSpecification(MESSAGENAME);
        player.notifyController();
    }
}
