package glit.net;

@FunctionalInterface
public interface NetListener<P>{

    void received(P packet);

}
