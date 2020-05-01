package it.polimi.ingsw;

public class Field {

    private final int ROW = 12;
    private final int COLUMN = 22;

    /*
        CONVERSIONE ISLANDBOARD -> VIEW
        Righe:  0 -> 2         Colonne: 0 -> 2
                1 -> 4                  1 -> 6
                2 -> 6                  2 -> 10
                3 -> 8                  3 -> 14
                4 -> 10                 4 -> 18
                (2x + 2)                (4x + 2)
         */
    String[][] field = new String[ROW][COLUMN];

    /**
     * The constructor methods initializes a field with all level 0
     */
    public Field(){
        loadEmpty();
    }

    private void loadEmpty(){

        for(int i = 0; i < ROW; i++)
            for(int j = 0; j < COLUMN; j++)
                field[i][j] = " ";

        //numeri fuori campo per coordinate, \u001B[32m è il verde
        field[2][0] = "\u001B[32m" +"0 ";
        field[4][0] = "\u001B[32m" +"1 ";
        field[6][0] = "\u001B[32m" +"2 ";
        field[8][0] = "\u001B[32m" +"3 ";
        field[10][0] = "\u001B[32m" +"4 ";
        field[0][3] = "\u001B[32m" +"  0  ";
        field[0][7] = "\u001B[32m" +" 1  ";
        field[0][11] = "\u001B[32m" +" 2  ";
        field[0][15] = "\u001B[32m" +" 3  ";
        field[0][19] = "\u001B[32m" +" 4  ";

        //margini prima riga
        field[1][1] = " ┌";
        field[1][5] = field [1][9] = field [1][13] = field [1][17] = "┬";
        field[1][21] = "┐";

        //tutti i margini orizzontali
        for(int i = 1; i < ROW; i = i + 2)
            for(int j = 2; j < COLUMN - 1; j++){
                if(j != 5 && j != 9 && j != 13 && j != 17)
                    field[i][j] = "--";
            }

        //margini intermedi
        for(int i = 3; i < ROW - 2; i = i + 2) {
            field[i][1] = " ├";
            field[i][5] = field[i][9] = field[i][13] = field[i][17] = "+";
            field[i][21] = "┤";
        }

        //margini finali
        field[11][1] = " └";
        field[11][5] = field [11][9] = field [11][13] = field [11][17] = "┴";
        field[11][21] = "┘";

        //tutti i margini verticali
        for(int i = 2; i < ROW - 1 ; i = i + 2)
            for(int j = 1; j < COLUMN; j = j + 4)
                field[i][j] = "│   ";

        //imposto livelli iniziali
        for(int i = 2; i < ROW - 1 ; i = i + 2)
            for(int j = 4; j < COLUMN - 1; j = j + 4)
                field[i][j] = "\u001B[34m" +"0";  //\u001B[34m è il blu

    }

    /**
     * This methods prints the board on screen
     */
    final void plot() {
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                System.out.print("\u001B[32m" + field[r][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * This method prints the build on screen
     * @param row is the space built row
     * @param column is the space built column
     * @param level is the level to build
     * @param hasDome is true if a dome is built
     */
    public void viewBuild(int row, int column, int level, int hasDome){
        row = 2*row + 2;
        column = 4*column + 4;
        if(hasDome == 1)
            field[row][column] = "\u001B[34m" + "C";    //"\u001B[34m" è il blu
        else
            field[row][column] = "\u001B[34m" + level;
        plot();
    }

    /**
     * This method prints the move on screen
     * @param oldRow is the start space row
     * @param oldColumn is the start space column
     * @param newRow is the finish space row
     * @param newColumn is the finish space column
     * @param color is the color player
     */
    public void viewMove(int oldRow, int oldColumn, int newRow, int newColumn, String color){
        oldRow = 2*oldRow + 2;
        oldColumn = 4*oldColumn + 2;
        newRow = 2*newRow + 2;
        newColumn = 4*newColumn + 2;

        field[oldRow][oldColumn] = " ";
        field[newRow][newColumn] = color + "W";
        plot();
    }

    /**
     * This method prints the starting position of a worker on screen
     * @param row is the start space row
     * @param column is the start space column
     */
    public void viewSetup(int row, int column, String color){
        row = 2*row + 2;
        column = 4*column + 2;

        field[row][column] = color + "W";
        plot();
    }

    /**
     * This method removes a worker from the field
     * @param row is the row of the worker to remove
     * @param column is the column of the worker to remove
     */
    public void viewRemoveWorker(int row, int column){
        row = 2*row + 2;
        column = 4*column + 2;

        field[row][column] = " ";
    }
}
