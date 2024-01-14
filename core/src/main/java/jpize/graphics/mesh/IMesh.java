package jpize.graphics.mesh;

import jpize.graphics.buffer.VertexBuffer;
import jpize.app.Disposable;

public interface IMesh extends Disposable{

    void render();

    VertexBuffer getBuffer();

}
