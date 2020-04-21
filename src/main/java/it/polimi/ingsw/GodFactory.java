package it.polimi.ingsw;

public class GodFactory {
    public void apollo(Player player){
        RestrictionAB restriction = new ConcreteApolloRestriction(player.getRestriction());
        MovementAB movement = new ConcreteApolloMove(player.getMove());
        player.setRestriction(restriction);
        player.setMove(movement);
    }

    public void artemis(Player player){
        MovementAB movement = new ConcreteArtemisMove(player.getMove());
        player.setMove(movement);
    }

    public void athena(Player player){
        MovementAB movement= new ConcreteAthenaMove(player.getMove());
        player.setMove(movement);
    }


    public void atlas(Player player){
        BuildAB build = new ConcreteAtlasBuild(player.getBuild());
        player.setBuild(build);
    }

    public void demeter(Player player){
        BuildAB build = new ConcreteDemeterBuild(player.getBuild());
        player.setBuild(build);
    }

    public void hephaestus(Player player){
        BuildAB build = new ConcreteHephaestusBuild(player.getBuild());
        player.setBuild(build);
    }

    public void minoutaur(Player player){
        RestrictionAB restriction = new ConcreteMinotaurRestriction(player.getRestriction());
        MovementAB movement = new ConcreteMinotaurMove(player.getMove());
        player.setRestriction(restriction);
        player.setMove(movement);
    }

    public void pan(Player player){
        WinConditionAB wincond = new ConcretePanWin(player.getWinCondition());
        player.setWinCondition(wincond);
    }

    public void prometheus(Player player){
        MovementAB movement = new ConcretePrometheusMove(player.getMove());
        player.setMove(movement);
    }

    //dei avanzati
    public void chronus(Player player){
        WinConditionAB wincond = new ConcreteChronusWin(player.getWinCondition());
        player.setWinCondition(wincond);
    }

    public void hestia(Player player){
        BuildAB build = new ConcreteHestiaBuild(player.getBuild());
        player.setBuild(build);
    }

    public void hypnus(Player player){
        RestrictionAB restriction = new ConcreteHypnusRestriction(player.getRestriction());
        player.setRestriction(restriction);
    }

    public void persephone(Player player){
        RestrictionAB restriction = new ConcretePersephoneRestriction(player.getRestriction());
        player.setRestriction(restriction);
    }

    public void zeus(Player player){
        RestrictionAB restriction = new ConcreteZeusRestriction(player.getRestriction());
        player.setRestriction(restriction);
    }
}
