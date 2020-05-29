package it.polimi.ingsw;

import java.util.List;

public class DataOutput {
    List<Space> listSpace;
    List<Integer> integerList;
    Worker worker;
    Space space;
    int code;
    String string;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Space> getListSpace() {
        return listSpace;
    }

    public void setListSpace(List<Space> listSpace) {
        this.listSpace = listSpace;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
