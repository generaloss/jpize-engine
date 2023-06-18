package pize.tests.particle;

import pize.Pize;
import pize.app.AppAdapter;

public class Main extends AppAdapter{

    public static void main(String[] args){
        Pize.create("Noise", 1280, 720);
        Pize.run(new Main());
    }
    
}
