package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyManagerTest {

    /*
    Questo test crea una lobby da 2 giocatori
     */
    @Test
    void addPlayerTest1() {
        Player ciro = new Player();
        Player francois = new Player();
        Player pippo = new Player();

        LobbyManager lobbyManager = new LobbyManager();
        lobbyManager.addPlayer(ciro, 3);
        lobbyManager.addPlayer(francois, 2);
        lobbyManager.addPlayer(pippo, 2);
    }

    /*
    Questo test crea una lobby da 3 giocatori
     */
    @Test
    void addPlayerTest2(){
        Player ciro = new Player();
        Player francois = new Player();
        Player pippo = new Player();
        Player pluto = new Player();

        LobbyManager lobbyManager = new LobbyManager();
        lobbyManager.addPlayer(ciro, 2);
        lobbyManager.addPlayer(francois, 3);
        lobbyManager.addPlayer(pippo, 3);
        lobbyManager.addPlayer(pluto, 3);
    }
}