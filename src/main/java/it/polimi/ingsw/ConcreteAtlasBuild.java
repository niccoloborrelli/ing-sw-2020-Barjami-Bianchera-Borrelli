package it.polimi.ingsw;

public class ConcreteAtlasBuild extends PowerBuildingDecoratorAB {

    public ConcreteAtlasBuild(BuildAB buildAB){
        this.build = buildAB;
    }

    @Override
    public boolean build(Worker worker, Space buildSpace, IslandBoard islandBoard) {

        if(Controller.isUsepower()) {
            int startLevel = buildSpace.getLevel();
            buildSpace.setLevel(3);
            build.build(worker, buildSpace, islandBoard);
            buildSpace.setLevel(startLevel);
            return true;
        }else
            build.build(worker, buildSpace, islandBoard);
        return false;
    }
}
