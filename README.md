# PIZE - Pizza Engine 

## Getting Started
Repository contains examples in 'tests' module

The engine focuses on being clear, simple and productive

Modules:
* Core
* Net
* Physics
* UI

### Core:
* Audio (OGG, MP3, WAV)
* Graphics (2D, 3D; Fonts, Postprocessing)
* Math (Vectors, Matrices)
* IO (Keyboard, Mouse, Monitors)
* Files (Resources: Internal / External)
* Utils (FastReader, FpsCounter, Sync, Stopwatch, TickGenerator, ...etc)

#### 2D Graphics:
``` java
TextureBatch batch = new TextureBatch(); // canvas for textures
Texture texture = new Texture("texture.png");

batch.begin();

// rotate, shift and scale for subsequent textures
batch.rotate(angle);
batch.shear(angle_x, angle_y);
batch.scale(scale);
// texture drawing
batch.draw(texture, x, y, width, height);

batch.end();
```

### Net:
* Security Keys (AES, RSA)
* TCP / UDP Client and Server

``` java
KeyAES key = new KeyAES(512); // generate key for connection encoding

TcpServer server = new TcpServer(new TcpListener<byte[]>(){
    public void received(byte[] data, NetChannel<byte[]> sender){
        System.out.printf("received: %f\n", new String(data)); // 'received: Hello, World!'
    }
    public void connected(NetChannel<byte[]> channel){
        channel.encode(key);
    }
    public void disconnected(NetChannel<byte[]> channel){ ... }
});
server.run("localhost", 5454);

TcpClient client = new TcpClient(new TcpListener<byte[]>(){ ... });
client.connect("localhost", 5454);
client.encode(key);
client.send("Hello, World!".getBytes());
```

### Physics:
* AABB Collider (2D, 3D)
* Utils (Velocity, Body) 

### UI:
* Constraint (pixel, relative, aspect) - used to determine the static or dynamic position and size of ui components.
* Align (center, up, right, ...etc)
* LayoutType (vertical / horizontal / none)

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/GeneralPashon/Pizza-Engine/issues).
