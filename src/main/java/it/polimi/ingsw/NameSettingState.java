package it.polimi.ingsw;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class NameSettingState extends State {
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
        if(nameSetted==false)
            player.notify(1);
    }

    @Override
    public void onStateTransition() {
        try {
            player.getController().createFluxTable();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        // player.getStateManager().notifyState();
    }

    public String toString(){
        return "NameSettingState";
    }
}
