package glit.tests.minecraft.client.world.world.renderer;

import glit.tests.minecraft.utils.Priority;

public enum FovEffect{

    NOTHING(Priority.MIN),
    ZOOM(Priority.MAX),
    SPRINT(Priority.NORMAL);


    private final Priority priority;

    FovEffect(Priority priority){
        this.priority = priority;
    }

    public Priority getPriority(){
        return priority;
    }

}
