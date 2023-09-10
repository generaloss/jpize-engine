package jpize.tests.minecraftosp.client.time;

import jpize.tests.minecraftosp.client.ClientGame;
import jpize.tests.minecraftosp.main.time.GameTime;

public class ClientGameTime extends GameTime{

    private final ClientGame game;
    private long prevTickPointMillis;

    public ClientGameTime(ClientGame game){
        this.game = game;
    }

    public ClientGame getGame(){
        return game;
    }

    @Override
    public void tick(){
        super.tick();
        this.prevTickPointMillis = System.currentTimeMillis();
    }

    private float getLerpSecondsPart(){
        return (float) (System.currentTimeMillis() - prevTickPointMillis) / 1000;
    }

    public float getLerpSeconds(){
        return super.getSeconds() + getLerpSecondsPart();
    }

    public float getLerpMinutes(){
        return getLerpSeconds() / SECONDS_IN_MINUTE;
    }

    public float getLerpDays(){
        return getLerpSeconds() / SECONDS_IN_DAY;
    }

}
