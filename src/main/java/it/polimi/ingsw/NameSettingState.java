package it.polimi.ingsw;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static it.polimi.ingsw.DefinedValues.nameSettingState;
import static it.polimi.ingsw.FinalCommunication.NAME;
import static it.polimi.ingsw.FinalCommunication.UPDATE_TO_PRINT;

public class NameSettingState extends State {
    /**
     * In this class a name is registered, if it's correct.
     */

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
            nameNotify();
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
        return nameSettingState ;
    }

    /**
     * Sends a notify indicating that the current state is name setting.
     */
    private void nameNotify(){
        LastChange inputExpected = player.getLastChange();
        inputExpected.setCode(UPDATE_TO_PRINT);
        inputExpected.setSpecification(NAME);
        player.notifyController();
    }
}
