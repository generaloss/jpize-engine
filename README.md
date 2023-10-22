# jpize - Java Game Engine 
### The engine focuses on being clear, simple and productive

## Projects & demos
[Demo Repository](https://github.com/GeneralPashon/jpize-demos)

[Minecraft Open Source Edition](https://github.com/GeneralPashon/Minecraft-Open-Source-Edition)

## Forks

Kotlin fork: [Pizza-Engine-Kotlin](https://github.com/Raf0707/Pizza-Engine-Kotlin) by [Raf0707](https://github.com/Raf0707)

С++ version (In Dev): [Pizza-Engine-C++](https://github.com/GeneralPashon/Pizza-Engine-Cpp)

## Getting Started

Maven:
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- https://jitpack.io/#GeneralPashon/jpize-engine -->
    <dependency>
        <groupId>com.github.GeneralPashon</groupId>
        <artifactId>jpize-engine</artifactId>
        <version>v23.10.4</version>
    </dependency>
</dependencies>
```

Modules:
* Core
* Net
* Physic
* UI

### Core:
* Audio (OGG, MP3, WAV)
* Graphics (2D, 3D, Fonts, Postprocessing)
* Maths (Vectors, Matrices, Quaternion)
* IO (Keyboard, Mouse, Monitors)
* Files (Resources: Internal / External)
* Utils (FastReader, FpsCounter, Sync, Stopwatch, TickGenerator, JpizeInputStream, JpizeOutputStream ...etc)

#### 0. Main class
``` java
public class App extends JpizeApplication{

    public static void main(String[] args){
        // Create window context
        ContextBuilder.newContext(1280, 720, "Window Title")
                .icon("icon.png")
                .create() 
                .init(new App());
        // Also you can create multiple windows
        // Run
        Jpize.runContexts();
    }
    
    public App(){ } // Calls before ContextAdapter.init()
    
    public void init(){ } // Init
    
    public void render(){ } // Render loop
    
    public void update(){ } // Update loop
    
    public void fixedUpdate(){ } // Fixed TPS Update loop
    
    public void resize(int widht, int height){ } // Calls when window resizes
    
    public void dispose(){ } // Exit program
    
}
```

#### 1. 2D Graphics:
``` java
TextureBatch batch = new TextureBatch(); // canvas for textures
Texture texture = new Texture("texture.png");

batch.begin();

// rotate, shear and scale for subsequent textures
batch.rotate(angle);
batch.shear(angle_x, angle_y);
batch.scale(scale);
// texture drawing
batch.draw(texture, x, y, width, height);

batch.end();
```

#### 2. Input:
``` java
// mouse
Jpize.getX(); // position
Jpize.getY();

Jpize.isTouched(); // touch
Jpize.isTouchDown();
Jpize.isTouchReleased();

Jpize.mouse().getScroll(); // scroll

// keyboard
Key.ENTER.isPressed();
Key.BACKSPACE.isDown();
Key.SPACE.isReleased();
Key.ESCAPE.getName();

// window
Jpize.getWidth();
Jpize.getHeight();
Jpize.getAspect();

// monitor
Jpize.monitor();        // Window monitor
Jpize.primaryMonitor(); // Primary monitor

// FPS & Delta Time
Jpize.getFPS();
Jpize.getDt();
Jpize.setFixedUpdateTPS(update_rate);
Jpize.getFixedUpdateDt();
```

#### 3. Audio:
``` java
// sound
Sound sound = new Sound("sound.mp3");

sound.setVolume(0.5F);
sound.setLooping(true);
sound.setPitch(1.5F);

sound.play();

// buffers and sources
AudioBuffer buffer = new AudioBuffer();
AudioLoader.load(buffer, resource);

AudioSource source = new AudioSource();
source.setBuffer(buffer);
source.play();
```

#### 4. Resources:
``` java
// internal / external
Resource res = new Resource(path, true); // external
Resource res = new Resource(path); // internal

res.isExternal();
res.isInternal();

// text
Resource text = new Resource("file.txt");
text.writeString("write text");
text.appendString("append text");
text.readString();

// file
Resource res = new Resource("file.ext");

res.getExtension(); // returns 'ext'
res.getSimpleName(); // returns 'file'

res.getFile();
res.exists();
res.mkDirsAndFile();

res.inStream();
res.outStream();

res.getReader(); // returns jpize.util.io.FastReader
res.getWriter(); // returns PrintStream

// resource (image, sound, ...etc)
Resource res = new Resource( ... );

new Texture(res);
new Sound(res);
PixmapIO.load(res);
new Shader(res_vert, res_frag);
AudioLoader.load(audio_buffer, res);
```

### Net:
* Security Keys (AES, RSA)
* TCP / UDP Client and Server

#### 1. Encrypted Server-Client Example:
``` java
KeyAES key = new KeyAES(128); // generate key for connection encoding

// server
TcpServer server = new TcpServer(new TcpListener(){
    public void received(byte[] bytes, TcpConnection sender){
        System.out.printf("received: %f\n", new String(bytes)); // 'received: Hello, World!'
    }
    public void connected(TcpChannel channel){
        channel.encrypt(key);
    }
    public void disconnected(TcpChannel channel){ ... }
});
server.run("localhost", 8080);

// client
TcpClient client = new TcpClient(new TcpListener(){ ... });
client.connect("localhost", 8080);
client.encrypt(key);
client.send("Hello, World!".getBytes()); // send 'Hello, World!'
```

#### 2. Packet Example:
``` java
// Packets Handler
class MyPacketHandler implements PacketHandler{
    public void handleMyPacket(MyPacket packet){ ... }
    public void handleAnotherPacket(AnotherPacket packet){ ... }
}

// Packet
class MyPacket extends IPacket<MyPacketHandler>{ // MyPacketHandler
    public static final int PACKET_ID = /*Your packet ID*/;
    
    public MyPacket(){
        super(PACKET_ID);
    }
    public MyPacket(some_data){
        this();
        ...
    }

    public void write(JpizeOutputStream outStream){ ... } // write data when sending
    public void read(JpizeInputStream inStream){ ... } // read data when receivind

    public void handle(MyPacketHandler handler){ // handle this packet
        handler.handleMyPacket(this); 
    }
}



// packet sending
TcpConnection connection = ...;
connection.send(new MyPacket(some_data));

// packet receiving
MyPacketHandler handler = ...;
// register packets
PacketDispatcher packetDispatcher = new PacketDispatcher();
packetDispatcher.register(MyPacket     .PACKET_ID, MyPacket     .class);
packetDispatcher.register(AnotherPacket.PACKET_ID, AnotherPacket.class);
...
void received(byte[] bytes, TcpConnection sender){
    packetDispatcher.handlePacket(bytes, handler);
}
```

### Physic:
* Axis-Aligned Box & Rect Collider
* Utils (Velocity, Body)

#### 1. AABB Collider Example:
``` java
AABoxBody body_1 = new AABoxBody( new AABox(-1,-1,-1,  1, 1, 1) ); // 2x2x2 axis-aligned box
AABoxBody body_2 = new AABoxBody( new AABox(-1,-1,-1,  1, 1, 1) ); // another axis-aligned box

body_1.getPosition().set(-5F, 0, 0);

Vec3f b1_velocity = new Vec3f(10F, 0, 0);
velocity = AABoxCollider.getCollidedMovement(b1_velocity, body_1, body_2);

body_1.getPosition().add( b1_velocity ); // box will be move only 3 units
```

### UI:
* Constraint (pixel, relative, aspect) - used to determine the static or dynamic position and size of ui components.
* Align (center, up, right, ...etc)
* LayoutType (vertical / horizontal / none)

#### 1. UI Example:
``` java
// init
Layout layout = new Layout();
layout.setLayoutType(LayoutType.HORIZONTAL);
layout.alignItems(Align.CENTER);

Image image = new Image(image_texture);
layout.put("image_id", image);

// render
batch.begin();
layout.render(batch);
batch.end();
```

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/GeneralPashon/Jpize-engine/issues).
