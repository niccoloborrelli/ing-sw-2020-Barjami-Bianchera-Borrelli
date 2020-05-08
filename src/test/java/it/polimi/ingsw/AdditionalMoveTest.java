package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

class AdditionalMoveTest {

    /*
    Questo test verifica che il worker non si sposta ulteriormente se il giocatore non usa il potere
     */
    @Test
    public void additionalMoveTest1() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest1(islandBoard);
    }

    private void createThreadsTest1(IslandBoard islandBoard) throws InterruptedException {

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest1(islandBoard);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest1(islandBoard.getObserver(), islandBoard);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();

    }

    private void startServerTest1(Controller controller, IslandBoard islandBoard) throws IOException, InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler(sc);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        islandBoard.getObserver().getSocketStreamMap().get(sc).run();
                    }
                },"threadC");

                Thread t4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        t3.start();
                        AdditionalMove additionalMove = new AdditionalMove(new BaseMovement());
                        Player player = new Player(sc);
                        player.setPlayerColor("aaa");
                        player.setMove(additionalMove);
                        player.setRestriction(new BaseRestriction());
                        player.setWinCondition(new BaseWinCondition());

                        Worker worker1 = player.getWorkers().get(0);
                        Worker worker2 = player.getWorkers().get(1);
                        worker1.setWorkerPlayer(player);
                        worker2.setWorkerPlayer(player);
                        Space finishSpace = islandBoard.getSpace(1, 1);

                        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
                        islandBoard.getSpace(0, 0).setOccupator(worker1);
                        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
                        islandBoard.getSpace(4, 4).setOccupator(worker2);

                        player.getRestriction().restrictionEffectMovement(player,islandBoard);

                        try {
                            additionalMove.move(worker1, finishSpace, islandBoard);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Worker's row =" + worker1.getWorkerSpace().getRow() + " / Worker's column = " + worker1.getWorkerSpace().getColumn());
                    }
                }, "ThreadF");
                t4.start();
                try {
                    t4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"server");
        server.start();
        server.join();
    }

    public void startClientTest1(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = new Socket("localhost",60100);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataOutputStream dataOutputStream;
                try {
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataInputStream dataInputStream;
                try{
                    dataInputStream = new DataInputStream(sc.getInputStream());
                }catch (IOException e){
                    e.printStackTrace();
                    return;
                }

                try{
                    String message = dataInputStream.readUTF();
                    System.out.println(message);
                }catch (IOException e){
                    e.printStackTrace();
                    return;
                }
                try {
                    dataOutputStream.writeUTF("<data>" + "<code>" + "1" + "</code>" + "<int>" + "0" + "</int>" + "</data>");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        },"client");
        client.start();
        client.join();
    }

    /*
    Questo test verifica che se il worker scelto non può muoversi, non viene fatta la richiesta
    (se il giocatore scrive viene buttato tutto)
     */

    @Test
    public void additionalMoveTest2() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest2(islandBoard);
    }

    private void createThreadsTest2(IslandBoard islandBoard) throws InterruptedException {

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest2(islandBoard);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest2(islandBoard.getObserver(), islandBoard);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();

    }

    private void startServerTest2(Controller controller, IslandBoard islandBoard) throws IOException, InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler(sc);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        islandBoard.getObserver().getSocketStreamMap().get(sc).run();
                    }
                },"threadC");

                Thread t4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        t3.start();
                        AdditionalMove additionalMove = new AdditionalMove(new BaseMovement());
                        Player player = new Player(sc);
                        player.setPlayerColor("aaa");
                        player.setMove(additionalMove);
                        player.setRestriction(new BaseRestriction());
                        player.setWinCondition(new BaseWinCondition());

                        Worker worker1 = player.getWorkers().get(0);
                        Worker worker2 = player.getWorkers().get(1);
                        worker1.setWorkerPlayer(player);
                        worker2.setWorkerPlayer(player);
                        Space finishSpace = islandBoard.getSpace(0, 0);


                        worker1.setWorkerSpace(islandBoard.getSpace(1, 1));
                        islandBoard.getSpace(1, 1).setOccupator(worker1);
                        islandBoard.getSpace(1,0).setHasDome(true);
                        islandBoard.getSpace(0,1).setHasDome(true);
                        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
                        islandBoard.getSpace(4, 4).setOccupator(worker2);

                        player.getRestriction().restrictionEffectMovement(player,islandBoard);


                        try {
                            additionalMove.move(worker1, finishSpace, islandBoard);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Worker's row =" + worker1.getWorkerSpace().getRow() + " / Worker's column = " + worker1.getWorkerSpace().getColumn());
                    }
                }, "ThreadF");
                t4.start();
                try {
                    t4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"server");
        server.start();
        server.join();
    }

    public void startClientTest2(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = new Socket("localhost",60100);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataOutputStream dataOutputStream;
                try {
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataInputStream dataInputStream;
                try{
                    dataInputStream = new DataInputStream(sc.getInputStream());
                }catch (IOException e){
                    e.printStackTrace();
                    return;
                }

                try {
                    dataOutputStream.writeUTF("<data>" + "<code>" + "1" + "</code>" + "<int>" + "0" + "</int>" + "</data>");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try{
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        },"client");
        client.start();
        client.join();
    }
    /*
    Questo test verifica che il worker si muove effettivamente due volte, nel caso il giocatore utilizzi il potere
    e scelga la prima posizione disponibile
     */

    @Test
    public void additionalMoveTest3() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest3(islandBoard);
    }

    private void createThreadsTest3(IslandBoard islandBoard) throws InterruptedException {

        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest3(islandBoard);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest3(islandBoard.getObserver(), islandBoard);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();

    }

    private void startServerTest3(Controller controller, IslandBoard islandBoard) throws IOException, InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler(sc);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        islandBoard.getObserver().getSocketStreamMap().get(sc).run();
                    }
                },"threadC");

                Thread t4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        t3.start();
                        AdditionalMove additionalMove = new AdditionalMove(new BaseMovement());
                        Player player = new Player(sc);
                        player.setPlayerColor("aaa");
                        player.setMove(additionalMove);
                        player.setRestriction(new BaseRestriction());
                        player.setWinCondition(new BaseWinCondition());

                        Worker worker1 = player.getWorkers().get(0);
                        Worker worker2 = player.getWorkers().get(1);
                        worker1.setWorkerPlayer(player);
                        worker2.setWorkerPlayer(player);
                        Space finishSpace = islandBoard.getSpace(1, 1);


                        worker1.setWorkerSpace(islandBoard.getSpace(0, 0));
                        islandBoard.getSpace(0, 0).setOccupator(worker1);
                        worker2.setWorkerSpace(islandBoard.getSpace(4, 4));
                        islandBoard.getSpace(4, 4).setOccupator(worker2);

                        player.getRestriction().restrictionEffectMovement(player,islandBoard);


                        try {
                            additionalMove.move(worker1, finishSpace, islandBoard);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Worker's row =" + worker1.getWorkerSpace().getRow() + " / Worker's column = " + worker1.getWorkerSpace().getColumn());
                    }
                }, "ThreadF");
                t4.start();
                try {
                    t4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"server");
        server.start();
        server.join();
    }

    public void startClientTest3(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = new Socket("localhost",60100);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataOutputStream dataOutputStream;
                try {
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                DataInputStream dataInputStream;
                try{
                    dataInputStream = new DataInputStream(sc.getInputStream());
                }catch (IOException e){
                    e.printStackTrace();
                    return;
                }

                try{
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    dataOutputStream.writeUTF("<data>" + "<code>" + "1" + "</code>" + "<int>" + "1" + "</int>" + "</data>");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try{
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try{
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    dataOutputStream.writeUTF("<data>" + "<code>" + "1" + "</code>" + "<int>" + "0" + "</int>" + "</data>");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try{
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }


            }
        },"client");
        client.start();
        client.join();
    }


}