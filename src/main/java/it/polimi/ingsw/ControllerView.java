package it.polimi.ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ControllerView {

    private String ip;
    private int port;

    /*
    0: stampa a schermo e basta
    1: inviami un intero per scelta space
    2: inviami una stringa per nome e colore
    3: inviami due interi per inserire worker in una casella
    4. scegli worker o utilizzo potere
    5: conferma valore valido
    6: partita finita
     */

    public ControllerView(String string, int port){
        this.ip = string;
        this.port = port;
    }

    public void main() throws IOException {
        //ControllerView client = new ControllerView("127.0.0.1", 5050);
        boolean game = true;
        Socket socket = new Socket(ip, port);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {
            while (game) {
                String message = socketIn.nextLine();
                int typeMessage  = socketIn.nextInt();

                if(typeMessage == 0){
                    System.out.println(message);
                }
                else if(typeMessage == 1){
                    int number;
                    do {
                        do {
                            System.out.println(message);
                            number = stdin.nextInt();
                        } while (number < 0 || number > 8);
                        socketOut.println(number);
                        socketOut.flush();
                        message = socketIn.nextLine();
                        typeMessage  = socketIn.nextInt();
                    } while (typeMessage != 5);
                }
                else if(typeMessage == 2){
                    do {
                        System.out.println(message);
                        String inputLine = stdin.nextLine();
                        socketOut.println(inputLine);
                        socketOut.flush();
                        message = socketIn.nextLine();
                        typeMessage  = socketIn.nextInt();
                    } while (typeMessage != 5);
                }
                else if(typeMessage == 3){
                    do {
                        System.out.println(message);
                        socketOut.println(getCoordinate(true, stdin));
                        socketOut.flush();
                        socketOut.println(getCoordinate(false, stdin));
                        socketOut.flush();
                        message = socketIn.nextLine();
                        typeMessage  = socketIn.nextInt();
                    } while (typeMessage != 5);
                }
                else if(typeMessage == 4){
                    int worker;
                    do {
                        do {
                            System.out.println(message);
                            worker = stdin.nextInt();
                        } while (worker < 0 || worker > 1);
                        socketOut.println(worker);
                        socketOut.flush();
                        message = socketIn.nextLine();
                        typeMessage  = socketIn.nextInt();
                    } while (typeMessage != 5);
                }
                else if(typeMessage == 6){
                    System.out.println(message);
                    game = false;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        }
        finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    /**
     * This method asks the coordinate of a space to the player
     * @param row is true if it's the row, false if column
     * @return the coordinate
     */
    private int getCoordinate(boolean row, Scanner stdin){
        int coordinate;

        if(row){
            do {
                System.out.println("Insert the row coordinate");
                coordinate = stdin.nextInt();
            } while(coordinate < 0 || coordinate > 4);
        }
        else {
            do {
                System.out.println("Insert the column coordinate");
                coordinate = stdin.nextInt();
            } while (coordinate < 0 || coordinate > 4);
        }
        return coordinate;
    }

}
