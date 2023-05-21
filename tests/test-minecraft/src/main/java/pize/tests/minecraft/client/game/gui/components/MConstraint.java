package pize.tests.minecraft.client.game.gui.components;

import pize.Pize;
import pize.gui.constraint.Constraint;
import pize.math.Maths;

public class MConstraint{

    public static Constraint button(double percentage){
        return Constraint.relativeToHeight(()->
            Math.max(2,
                Maths.round(percentage * Pize.getHeight() / 20)
            ) * 20F / Pize.getHeight()
        );
    }

    public static Constraint text(double percentage){
        return Constraint.relativeToHeight(()->
            Math.max(2,
                Maths.round(percentage * Pize.getHeight() / 8)
            ) * 8F / Pize.getHeight()
        );
    }

}
