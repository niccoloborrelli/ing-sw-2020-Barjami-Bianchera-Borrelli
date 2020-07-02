package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GodFactoryTest {
    @Test
    public  void godFactoryTest1() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("SwapWorkers");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest2() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("AdditionalMove");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest3() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("DenyUpperMove");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest4() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("DomeEverywhere");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest5() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("AdditionalBuildNoInitial");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest6() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("CanBuildTwiceNotDome");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest7() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("PushBack");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest8() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("JumpMoreLevelsWin");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest9() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("BuildAlsoBeforeIfNotMoveUp");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest10() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("CompleteTowerWin");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest11() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("AdditionalBuildNotPerimeter");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest12() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("IfHigherNoMove");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest13() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("MustMoveUp");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

    @Test
    public  void godFactoryTest14() throws ParserConfigurationException, SAXException, IOException, NoSuchMethodException, ClassNotFoundException {
        Controller controller = new Controller();
        HandlerHub handlerHub = new HandlerHub();
        TurnManager turnManager = new TurnManager();
        Player player = new Player();
        IslandBoard islandBoard = new IslandBoard();
        StateManager stateManager = new StateManager();
        player.setStateManager(stateManager);
        player.setIslandBoard(islandBoard);
        player.setController(controller);
        controller.setHandlerHub(handlerHub);
        controller.setPlayer(player);
        stateManager.setTurnManager(turnManager);
        turnManager.getPlayers().add(player);
        controller.createFluxTable();
        stateManager.createBaseStates(player);
        GodFactory godFactory=new GodFactory();
        player.setPlayerGod("godTest");
        HashMap <String, List<String>> godMap=new HashMap<String , List<String>>();
        List<String> godEffect=new ArrayList<String>();
        godEffect.add("BuildUnderYou");
        godMap.put("godTest",godEffect);
        godFactory.decoratePlayer(godMap,player);
    }

}