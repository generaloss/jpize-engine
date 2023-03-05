package glit.tests.minecraft.client.game.gui.components;

import glit.Glit;
import glit.gui.constraint.Constraint;
import glit.math.Maths;

public class MConstraint{

    public static Constraint button(double percentage){
        return Constraint.relativeToHeight(()->
            Math.max(2,
                Maths.round(percentage * Glit.getHeight() / 20)
            ) * 20F / Glit.getHeight()
        );
    }

    public static Constraint text(double percentage){
        return Constraint.relativeToHeight(()->
            Math.max(2,
                Maths.round(percentage * Glit.getHeight() / 8)
            ) * 8F / Glit.getHeight()
        );
    }

}
