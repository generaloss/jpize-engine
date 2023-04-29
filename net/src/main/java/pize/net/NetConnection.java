package pize.net;

import java.io.Closeable;

public interface NetConnection<P> extends Closeable{

    void send(P p);
    int available();
    P next();
    boolean isClosed();

}
