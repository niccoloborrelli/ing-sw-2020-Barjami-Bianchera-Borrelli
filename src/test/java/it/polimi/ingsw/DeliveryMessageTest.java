package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class DeliveryMessageTest {

    @Test
    void sendTest1() throws InterruptedException {
        Thread t = new Thread(()-> {
            Socket socket = null;
            String string = "ahahhaha";
            try {
                socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
                deliveryMessage.send(string);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerListener(1);
        server.start();
        t.start();
        t.join();
        server.join();

    }

    @Test
    void sendTest2() throws IOException, InterruptedException {
        Thread t = new Thread(()-> {
            Socket socket = null;
            String string1 = "w0+1-1";
            String string2 = "w1+5-5";
            String string3 = "wA+1-1";
            String string4 = "w1+a-2";
            String string5 = "w0+1-h";
            String string6 = "w0-1+5";
            try {
                socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
                deliveryMessage.send(string1);
                deliveryMessage.send(string2);
                deliveryMessage.send(string3);
                deliveryMessage.send(string4);
                deliveryMessage.send(string5);
                deliveryMessage.send(string6);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerListener(6);
        server.start();
        t.start();
        t.join();
        server.join();

    }

    @Test
    void sendTest3() throws InterruptedException {
        Thread t = new Thread(()-> {
            Socket socket = null;
            String string = "1";
            try {
                socket = new Socket("localhost", 62100);
                DeliveryMessage deliveryMessage = new DeliveryMessage(socket);
                deliveryMessage.send(string);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerListener(1);
        server.start();
        t.start();
        t.join();
        server.join();

    }

    private Thread createThreadServerListener(int num){
        return new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(62100);
                Socket sc = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(sc.getInputStream());
                for(int i=0; i<num; i++)
                    System.out.println(dataInputStream.readUTF());
                sc.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void receiveTest1() throws InterruptedException {
        List<String> strings = new ArrayList<>();
        String prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        String code = "<data><code>0</code>";
        String strangeCode = "<data><code>A</code>";
        String player1 = "<player><name>null</name><color>null</color></player>";
        String player2 = "<player><name>I</name><color>red</color></player>";
        String player3 = "<player><name>I</name><color>cyan</color></player>";
        String player4 = "<player><name>I</name><color>white</color></player>";
        String player5 = "<player><name>I</name><color>grey</color></player>";
        String player6 = "<player><name>I</name><color>purple</color></player>";
        String player7 = "<player><name>I</name><color>evviva</color></player>";
        String specification = "<specification>endTurn</specification>";
        String specification1 = "<specification>disconnection</specification>";
        String message = "</data>";

        strings.add(prefix+code+specification+message);
        strings.add(prefix+code+player1+specification+message);
        strings.add(prefix+code+player2+specification+message);
        strings.add(prefix+code+player3+specification+message);
        strings.add(prefix+code+player4+specification+message);
        strings.add(prefix+code+player5+specification+message);
        strings.add(prefix+code+player6+specification+message);
        strings.add(prefix+code+player7+specification+message);
        strings.add(prefix+strangeCode+player1+specification+message);
        strings.add(prefix+code+player1+specification1+message);

        Thread client = new Thread(()->{
            try {
                Socket sc = new Socket("localhost", 62100);
                CommandCLIManager commandCLIManager = new CommandCLIManager(sc);
                commandCLIManager.getDeliveryMessage().startReading();
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerWriter(strings);

        server.start();
        client.start();
        server.join();
        client.join();
    }

    @Test
    void receiveTest2() throws InterruptedException {
        List<String> strings = new ArrayList<>();
        String prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        String code = "<data><code>1</code>";
        String player = "<player><name>I</name><color>red</color></player>";
        String specification1 = "<specification>preLobby</specification>";
        String message1 = "<message><int>1</int><int>3</int><int>4</int></message></data>";
        String specification2 = "<specification>godChoice</specification>";
        String message2 = "<message><string>aaaa</string><string>babababa</string><string>bcbcbcbcb</string></message></data>";
        String specification3 = "<specification>move</specification>";
        String message3 = "<message><worker><char>A</char></worker><space><row>2</row><column>5</column></space><space><row>1</row><column>2</column></space>" +
                "<space><row>2</row><column>1</column></space><space><row>2</row><column>0</column></space></message></data>";

        strings.add(prefix+code+player+specification1+message1);
        strings.add(prefix+code+player+specification2+message2);
        strings.add(prefix+code+player+specification3+message3);

        Thread client = new Thread(()->{
            try {
                Socket sc = new Socket("localhost", 62100);
                CommandCLIManager commandCLIManager = new CommandCLIManager(sc);
                commandCLIManager.getDeliveryMessage().startReading();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerWriter(strings);

        server.start();
        client.start();
        server.join();
        client.join();
    }


    @Test
    void receiveTest3() throws InterruptedException {
        List<String> strings = new ArrayList<>();
        String prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        String code = "<data><code>2</code>";
        String player = "<player><name>I</name><color>red</color></player>";
        String specification1 = "<specification>move</specification>";
        String message1 = "<message><worker><char>B</char><row>1</row><column>3</column></worker>" +
                "<space><row>1</row><column>4</column></space></message></data>";
        String specification2 = "<specification>build</specification>";
        String message2 = "<message><worker><char>A</char></worker><space><row>1</row><column>4</column>" +
                "<level>3</level><dome>false</dome></space></message></data>";
        String message3 = "<message><worker><char>A</char></worker><space><row>A</row><column>b</column><level>b</level><dome>false</dome></space></message></data>";
        String specification3 = "<specification>workerSetting</specification>";
        String specification4 = "<specification>deleted</specification>";

        strings.add(prefix+code+player+specification1+message1);
        strings.add(prefix+code+player+specification2+message2);
        strings.add(prefix+code+player+specification2+message3);
        strings.add(prefix+code+player+specification3+message1);
        strings.add(prefix+code+player+specification4+message1);

        Thread client = new Thread(()->{
            try {
                Socket sc = new Socket("localhost", 62100);
                CommandCLIManager commandCLIManager = new CommandCLIManager(sc);
                commandCLIManager.getDeliveryMessage().startReading();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerWriter(strings);

        server.start();
        client.start();
        server.join();
        client.join();
    }

    @Test
    void receiveTest4() throws InterruptedException {
        List<String> strings = new ArrayList<>();
        String prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        String code = "<data><code>3</code>";
        String player = "<player><name>I</name><color>red</color></player>";
        String specification1 = "<specification>endGame</specification></data>";

        strings.add(prefix+code+player+specification1);

        Thread client = new Thread(()->{
            try {
                Socket sc = new Socket("localhost", 62100);
                CommandCLIManager commandCLIManager = new CommandCLIManager(sc);
                commandCLIManager.getDeliveryMessage().startReading();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        Thread server = createThreadServerWriter(strings);

        server.start();
        client.start();
        server.join();
        client.join();
    }


    private Thread createThreadServerWriter(List<String> messages){
        return new Thread(()->{
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(62100);
                Socket sc = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(sc.getOutputStream());

                sleep(200);

                for(String s: messages )
                    dataOutputStream.writeUTF(s);

                sc.close();
                serverSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

}