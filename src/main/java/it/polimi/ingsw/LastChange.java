package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class LastChange {
    List<Space> listSpace;
    List<Integer> integerList;
    List<String> stringList;
    Worker worker;
    Space space;
    int code;
    String specification;

    public LastChange(){
        listSpace = new ArrayList<>();
        integerList = new ArrayList<>();
        stringList = new ArrayList<>();
    }

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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
