package it.polimi.ingsw;

public enum ColorConverter {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_GREY("\u001B[37m"),
    ANSI_WHITE("\u001B[30m");

    static final String RESET = "\u001B[0m";
    public static final String CYAN = "cyan";
    public static final String GREY = "grey";
    public static final String RED = "red";
    public static final String PURPLE = "purple";
    public static final String WHITE="white";
    public static final String INVALID_COLOR = "none";

    private String escape;

    ColorConverter(String escape) {
        this.escape = escape;
    }

    public String escape(){
        return escape;
    }

}