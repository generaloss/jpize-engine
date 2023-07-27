package pize.tests.voxelgame.main.time;

public enum TimeUnit{
    
    TICKS("ticks"),
    SECONDS("seconds"),
    MINUTES("minutes"),
    DAYS("days");

    private final String literal;

    TimeUnit(String literal){
        this.literal = literal;
    }

    public String getLiteral(){
        return literal;
    }

}
