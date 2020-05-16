package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.management.RuntimeMXBean;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;


public class ClientHandlerTest {


    // TEST 1: client invia 2 messaggi quando non e' il suo turno, server scarta questi e dice al client che non e' il suo turno
    @Test
    public void clientHandlerTest1() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest1(islandBoard);
    }

    private void createThreadsTest1(IslandBoard islandBoard) throws InterruptedException {
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest1(islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest1(islandBoard.getObserver(), islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();
    }

    public void startClientTest1(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                DataInputStream dataInputStream;
                DataOutputStream dataOutputStream;
                try {
                    sc = new Socket("localhost",60100);
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                    dataInputStream = new DataInputStream(sc.getInputStream());
                    String message;
                    dataOutputStream.writeUTF("messaggio che devi scartare");
                    message = dataInputStream.readUTF();
                    System.out.println(message);
                    dataOutputStream.writeUTF("messaggio che devi scartare");
                    message = dataInputStream.readUTF();
                    System.out.println(message);
                }

                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        },"client");
        client.start();
        client.join();
    }


    public void startServerTest1(Controller controller,IslandBoard islandBoard) throws InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler();
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
                        Player player = new Player(sc);
                        player.setPlayerColor("aaa");

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


    //TEST DOVE CLIENT INVIA UNA STRINGA QUANDO EFFETTIVAMENTE E' IL SUO TURNO
    @Test
    public void clientHandlerTest2() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest2(islandBoard);
    }

    private void createThreadsTest2(IslandBoard islandBoard) throws InterruptedException {
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest2(islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest2(islandBoard.getObserver(), islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();
    }

    public void startClientTest2(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                DataInputStream dataInputStream;
                DataOutputStream dataOutputStream;
                try {
                    sc = new Socket("localhost",60100);
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                    dataInputStream = new DataInputStream(sc.getInputStream());
                    System.out.println(dataInputStream.readUTF());
                    String message;
                    Scanner sc2=new Scanner(System.in);
                    String nome="Rei";
                    dataOutputStream.writeUTF("<DATA><code>2</code><string>"+nome+"</string></DATA>");
                }

                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        },"client");
        client.start();
        client.join();
    }


    public void startServerTest2(Controller controller,IslandBoard islandBoard) throws InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler();
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
                        try {
                            String message=islandBoard.getObserver().requiredName(sc,"inviami il nome",new ArrayList<String>());
                            System.out.println(message);
                        }
                        catch (Exception e){
                            System.out.println("error");
                        }

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


    //Test ibrido: prima il client scrive quando non e' il suo turno, poi quando lo e'
    @Test
    public void clientHandlerTest3() throws IOException, InterruptedException {
        IslandBoard islandBoard = new IslandBoard();
        createThreadsTest3(islandBoard);
    }

    private void createThreadsTest3(IslandBoard islandBoard) throws InterruptedException {
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startClientTest3(islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "ThreadD");

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startServerTest3(islandBoard.getObserver(), islandBoard);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "threadE");

        t5.start();
        t6.start();
        t5.join();
        t6.join();
    }

    public void startClientTest3(IslandBoard islandBoard) throws InterruptedException {
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                DataInputStream dataInputStream;
                DataOutputStream dataOutputStream;
                try {
                    sc = new Socket("localhost",60100);
                    dataOutputStream = new DataOutputStream(sc.getOutputStream());
                    dataInputStream = new DataInputStream(sc.getInputStream());
                    String message;
                    Scanner sc2=new Scanner(System.in);
                    String nome="Rei";
                    dataOutputStream.writeUTF("<DATA><code>2</code><string>"+nome+"</string></DATA>");
                    System.out.println(dataInputStream.readUTF());
                    sleep(1000);
                    System.out.println(dataInputStream.readUTF());
                    dataOutputStream.writeUTF("<DATA><code>2</code><string>"+nome+"</string></DATA>");
                }

                catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        },"client");
        client.start();
        client.join();
    }


    public void startServerTest3(Controller controller,IslandBoard islandBoard) throws InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket sc;
                try {
                    sc = islandBoard.requiredChallengerSocket();
                    islandBoard.getObserver().createClientHandler();
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
                        try {
                            sleep(100);
                            String message=islandBoard.getObserver().requiredName(sc,"inviami il nome",new ArrayList<String>());
                            System.out.println(message);
                        }
                        catch (Exception e){
                            System.out.println("error");
                        }

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


}
