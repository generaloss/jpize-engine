package pize.tests.minecraft.client.world.world.renderer;

import pize.tests.minecraft.utils.Priority;

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
