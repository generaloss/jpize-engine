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

#### Load Audio:
``` java
Sound sound = new Sound("sound.mp3");

sound.setVolume(0.5F);
sound.setLooping(true);
sound.setPitch(1.5F);

sound.play();
```

### Net:
* Security Keys (AES, RSA)
* TCP / UDP Client and Server

#### Server-Client example:
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

#### Example:
``` java
BoxBody body_1 = new BoxBody( new BoundingBox(-1,-1,-1,  1, 1, 1) ); // 2x2x2 box
BoxBody body_2 = new BoxBody( new BoundingBox(-1,-1,-1,  1, 1, 1) ); // another box

body_1.getPosition().set(-5F, 0, 0);

Vec3d b1_velocity = new Vec3d(10F, 0, 0);
velocity = Collider3D.getCollidedMove(body_1, b1_velocity, body_2);

body_1.getPosition().add( b1_velocity ); // box will move only 3 units
```

### UI:
* Constraint (pixel, relative, aspect) - used to determine the static or dynamic position and size of ui components.
* Align (center, up, right, ...etc)
* LayoutType (vertical / horizontal / none)

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/GeneralPashon/Pizza-Engine/issues).
