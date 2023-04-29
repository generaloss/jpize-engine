package pize.net;

@FunctionalInterface
public interface NetListener<P>{

    void received(P packet);

}
