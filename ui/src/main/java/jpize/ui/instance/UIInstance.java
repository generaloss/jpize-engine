package jpize.ui.instance;

import jpize.Jpize;
import jpize.ui.component.OldComponent;

public class UIInstance{

    private final OldComponent<?> root;

    public UIInstance(OldComponent<?> root){
        this.root = root;

        Jpize.context().getCallbacks().addWinResizedCallback(((window, width, height) -> {
            // root.render();
        }));

        // root.render();
    }

    public void update(){

    }

}
