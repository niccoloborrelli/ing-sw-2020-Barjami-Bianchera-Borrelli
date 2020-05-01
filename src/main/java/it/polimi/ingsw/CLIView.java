package it.polimi.ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLIView {

    private String ip;
    private int port;
    private Field field;

    /*
    0: stampa a schermo e basta
    1: inviami un intero
    2: inviami una stringa
    3: richiesta di 2 interi sotto forma di stringa (es 1/1)
    4. partita finita
    5: conferma valore valido
    6: invio posizioni usabili
    7: viewMove
    8: viewBuild
    9: viewRemove
    10: viewSetup
     */

    public CLIView(String string, int port){
        this.ip = string;
        this.port = port;
        this.field = new Field();
    }

    public void clientCLIView() throws IOException {
        boolean game = true;
        Socket socket = new Socket(ip, port);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {
            while (game) {
                String message = socketIn.nextLine();
                int typeMessage  = socketIn.nextInt();
                socketOut.flush();

                if(typeMessage == 0){
                    System.out.println(message);
                }
                else if(typeMessage == 1){
                    int number;
                    do {
                        System.out.println(message);
                        number = stdin.nextInt();
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
                        System.out.println("Insert row");
                        String row = stdin.nextLine();
                        System.out.println("Insert column");
                        String column = stdin.nextLine();
                        socketOut.println("<x>" + row + "</x><y>" + column + "</y>");
                        socketOut.flush();
                        message = socketIn.nextLine();
                        typeMessage  = socketIn.nextInt();
                    } while (typeMessage != 5);
                }
                else if(typeMessage == 4){
                    System.out.println(message);
                    game = false;
                }
                else if(typeMessage == 6){
                    //invio posizioni usabili per GUI
                }
                else if(typeMessage == 7){
                    //move
                    int oldRow = parseStringInt(message, "<oldRow>", "</oldRow>");
                    int oldColumn = parseStringInt(message, "<oldColumn>", "</oldColumn>");
                    int newRow = parseStringInt(message, "<newRow>", "</newRow>");
                    int newColumn = parseStringInt(message, "<newColumn>", "</newColumn>");
                    String color = parseString(message, "<color>", "</color>");

                    field.viewMove(oldRow, oldColumn, newRow, newColumn, color);
                }
                else if(typeMessage == 8){
                    //build
                    int row = parseStringInt(message, "<row>", "</row>");
                    int column = parseStringInt(message, "<column>", "</column>");
                    int level = parseStringInt(message, "<level>", "</level>");
                    int hasDome = parseStringInt(message, "<hasDome>", "</hasDome>");

                    field.viewBuild(row, column, level, hasDome);
                }
                else if(typeMessage == 9){
                    //remove
                    int row = parseStringInt(message, "<row>", "</row>");
                    int column = parseStringInt(message, "<column>", "</column>");

                    field.viewRemoveWorker(row, column);
                }
                else if(typeMessage == 10){
                    //setup
                    int row = parseStringInt(message, "<row>", "</row>");
                    int column = parseStringInt(message, "<column>", "</column>");
                    String color = parseString(message, "<color>", "</color>");

                    field.viewSetup(row, column, color);
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

    private int parseStringInt(String completeMessage, String parsingStart, String parsingFinish){
        int start = completeMessage.indexOf(parsingStart) + parsingStart.length();
        int end = completeMessage.indexOf(parsingFinish);
        return Integer.parseInt(completeMessage.substring(start, end));
    }

    private String parseString(String completeMessage, String parsingStart, String parsingFinish){
        int start = completeMessage.indexOf(parsingStart) + parsingStart.length();
        int end = completeMessage.indexOf(parsingFinish);
        return completeMessage.substring(start, end);
    }
}
