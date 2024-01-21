# [Jpize](https://github.com/GeneralPashon/jpize-engine) - Java Game Engine
![jpize](jpize-logo.svg)

### The Engine focuses on being:
* **Intuitive**
* **Simple**
* **Efficient**

### Support OS:
* **Windows**
* **Linux x64**
* _(possibly MacOS)_

---

## Demos
* *[Demo Repository](https://github.com/GeneralPashon/jpize-demos)*
* *[Minecraft Open Source Edition](https://github.com/GeneralPashon/minecraft-open-source-edition)*

## Libraries: 
* *[libsdl4j](https://github.com/libsdl4j/libsdl4j)* - SDL2
* *[lwjgl3](https://github.com/LWJGL/lwjgl3)* - OpenGL, OpenAL, STB
* *[joribs](https://github.com/ymnk/jorbis)* - OGG
* *[jlayer](https://github.com/umjammer/jlayer)* - MP3

## Forks
* *[Kotlin Fork](https://github.com/Raf0707/Pizza-Engine-Kotlin)* by *[Raf0707](https://github.com/Raf0707)*
* *[C++ Version](https://github.com/GeneralPashon/Pizza-Engine-Cpp)* (abandoned)

---

# Getting Started

### What you need to know about the engine?
*  Matrices indexed with _**mᵢⱼ**_, where _**i**_ - column index, _**j**_ - row index
*  Left-Hand Coordinate System

---

## Modules:
* *Util*
* *Core*
* *Net*
* *Physic*
* *UI*

---

## Module *Util*:
* *[Maths](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/math)* - Vectors, Matrices, Utils ...
* *[Arrays](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/array)* - IntList, FloatList, [...], ArrayUtils, ArraysSupport (Java 17)
* *[Files](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/file)* - Resource, [...], MapConfig
* *[IO](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/io)* - FastReader, Jpize IO Streams ...
* *[Colors](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/color)* - IColor, Color, ImmutableColor
* *[Time](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/time)* - Stopwatch, Sync, TickGenerator, FpsCounter, DeltaTimeCounter, [...]
* *[Streams](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util/stream)* - FloatSupplier
* *[Other](https://github.com/GeneralPashon/jpize-engine/tree/master/util/src/main/java/jpize/util)* - Utils, StringUtils, SyncTaskExecutor

### Usage example:
``` java
// Utils
EulerAngles rotation = new EulerAngles(45, 0, 0);

Vec3f direction = rotation.getDir();
Vec3f position = new Vec3f(1, 2, 3);

Matrix4f projection = new Matrix4f()
    .setPerspective(Jpize.getAspect(), 0.1F, 1000F, 70);

Matrix4f view = new Matrix4f()
    .setLookAt(position, direction);

// Core
Shader shader = new Shader(Resource.internal("shader.vsh"), Resource.internal("shader.fsh"));
shader.bind();
shader.uniform("u_view", view);
shader.uniform("u_projection", projection);

Mesh mesh = new Mesh(new GlVertAttr(3, GlType.FLOAT), new GlVertAttr(4, GlType.FLOAT));
mesh.getBuffer().setData(new float[]{ ... });
mesh.render();
```

### 1. Vectors:
| **Type**     | **2D**  | **3D**  | **4D**  |
|--------------|---------|---------|---------|
| _**Double**_ | _Vec2d_ | _Vec3d_ | _Vec4d_ |
| _**Float**_  | _Vec2f_ | _Vec3f_ | _Vec4f_ |
| _**Int**_    | _Vec2i_ | _Vec3i_ | _Vec4i_ |

#### Available operations:
| _**Operations**_                                                        | **Description**                                                        | **Has 2D** | **Has 3D** | **Has 4D** | **Has Int** |
|-------------------------------------------------------------------------|------------------------------------------------------------------------|------------|------------|------------|-------------|
| **add, sub, mul, div**                                                  | Addition, Subtraction, Multiplication, Division                        | ✔️         | ✔️         |            | ✔️          |
| **set**                                                                 | Sets new value                                                         | ✔️         | ✔️         | ✔️         | ✔️          |
| **dst**                                                                 | Returns distance between vectors                                       | ✔️         | ✔️         |            | ✔️          |
| **min, max**                                                            | Returns a vector with greater\|less length                             | ✔️         | ✔️         |            | ✔️          |
| **len, len2, lenh**                                                     | Returns length \| squared length \| horizontal length                  | ✔️         | ✔️         |            | ✔️          |
| **nor**                                                                 | Normalize vector                                                       | ✔️         | ✔️         |            |             |
| **abs**                                                                 | Applies Math.abs() to each vector component                            | ✔️         | ✔️         |            | ✔️          |
| **zero, isZero**                                                        | Sets to zero \| Returns true is components equals zero                 | ✔️         | ✔️         |            | ✔️          |
| **zeroThatLess, zeroThatZero, zeroThatBigger**                          | Sets to zero components that is less\|zero\|bigger argument components | ✔️         | ✔️         |            | ✔️          |
| **dot**                                                                 | Returns dot product                                                    | ✔️         | ✔️         |            | ✔️          |
| **crs**                                                                 | Returns cross product                                                  | ✔️         | ✔️         |            | ✔️          |
| **rotX, rotY, rotZ**                                                    | Rotate point around axis                                               |            | ✔️         |            |             |
| **frac**                                                                | Returns fractional part                                                | ✔️         | ✔️         |            |             |
| **lerp**                                                                | Returns linear interpolated vector                                     | ✔️         | ✔️         |            |             |
| **xy, xz, yz**                                                          | Takes 3D vector components and creates 2D vector                       |            | ✔️         |            | ✔️          |
| **floor, round, ceil**                                                  | Rounds vector components                                               | ✔️         | ✔️         |            |             |
| **xFloor, xRound, xCeil, yFloor, yRound, yCeil, zFloor, zRound, zCeil** | Returns a rounded component                                            | ✔️         | ✔️         |            |             |
| **area**                                                                | Returns area (X * Y)                                                   | ✔️         |            |            | ✔️          |
| **volume**                                                              | Returns volume (X * Y * Z)                                             |            |            |            | ✔️          |
| **mulMat3**                                                             | Multiply to Matrix3f                                                   | ✔️         | ✔️         |            | ✔️          |
| **mulMat4**                                                             | Multiply to Matrix4f                                                   |            | ✔️         | ✔️         | ✔️          |
| **castDouble, castFloat, castInt**                                      | Creates vector of the same dimension but different datatype            | ✔️         | ✔️         |            | ✔️          |
| **deg, rad**                                                            | Get angle in degrees\|radians between vectors                          | ✔️         |            |            |             |
| **rotd, rotr**                                                          | Rotate vector degrees\|radians around origin                           | ✔️         |            |            |             |
| **copy**                                                                | Creates a copy                                                         | ✔️         | ✔️         | ✔️         | ✔️          |

### 2. Matrices:
| **Matrices** | **3D**     | **4D**     |
|--------------|------------|------------|
| _**Float**_  | _Matrix3f_ | _Matrix4f_ |

#### Available operations:
| _**Operations**_                             | **Description**                             | **has 3D** | **has 4D** |
|----------------------------------------------|---------------------------------------------|------------|------------|
| **set**                                      | Sets new values                             | ✔️         | ✔️         |
| **zero**                                     | Fills with zeros                            | ✔️         | ✔️         |
| **identity**                                 | Sets matrix to identity                     | ✔️         | ✔️         |
| **setOrthographic, setPerspective**          | Sets to projection                          |            | ✔️         |
| **setLookAt**                                | Sets to lookAt matrix                       |            | ✔️         |
| **cullPosition, cullRotation**               | Removes matrix part with rotation\|position | ✔️         | ✔️         |
| **translate**                                | Translates current matrix                   | ✔️         | ✔️         |
| **setTranslate**                             | Sets to translated                          | ✔️         | ✔️         |
| **scale**                                    | Scales current matrix                       |            | ✔️         |
| **setScale**                                 | Sets to scaled                              | ✔️         | ✔️         |
| **shear**                                    | Shear current matrix                        | ✔️         |            |
| **setShear**                                 | Sets to sheared                             | ✔️         |            |
| **rotate**                                   | Rotates current matrix                      |            | ✔️         |
| **setRotation**                              | Sets to rotated                             | ✔️         | ✔️         |
| **setRotationX, setRotationY, setRotationZ** | Sets rotation around axis                   |            | ✔️         |
| **setQuaternion**                            | Sets position and quaternion rotation       |            | ✔️         |
| **lerp**                                     | Returns linear interpolated matrix          | ✔️         | ✔️         |
| **mul**                                      | Multiplies                                  | ✔️         | ✔️         |
| **getMul**                                   | Returns multiply result                     | ✔️         | ✔️         |
| **copy**                                     | Creates a copy                              | ✔️         | ✔️         |

---

## Module *Core*:
* *[Graphics](https://github.com/GeneralPashon/jpize-engine/tree/master/core/src/main/java/jpize/graphics)* - Camera, Fonts, Postprocessing, Meshes, Textures, Utils ...
* *[Audio](https://github.com/GeneralPashon/jpize-engine/tree/master/core/src/main/java/jpize/audio)* - OGG, MP3, WAV

#### 1. Main class
``` java
public class App extends JpizeApplication{

    public static void main(String[] args){
        // Create window context
        ContextBuilder.newContext(1280, 720, "Window Title")
                .icon("icon.png")
                .register()
                .setAdapter(new App());
                
        // Run created contexts
        Jpize.runContexts();
    }
    
    public App(){ } // Constructor calls before init()
    
    public void init(){ } // Init
    
    public void render(){ } // Render loop
    
    public void update(){ } // Update loop
    
    public void resize(int widht, int height){ } // Calls when window resizes
    
    public void dispose(){ } // Exit app
    
}
```

#### 2. 2D Graphics:
``` java
TextureBatch batch = new TextureBatch(); // canvas for textures
Texture texture = new Texture("texture.png");

Gl.clearColorBuffer();
batch.begin();

// rotate, shear and scale for subsequent textures
batch.rotate(angle);
batch.shear(angle_x, angle_y);
batch.scale(scale);
// texture drawing
batch.draw(texture, x, y, width, height);

batch.end();
```

#### 3. Fonts:
``` java
// load
Font font = FontLoader.getDefault();

Font font = FontLoader.loadFnt(path_or_resource);

Font font = FontLoader.loadTrueType(path_or_resource, size);
Font font = FontLoader.loadTrueType(path_or_resource, size, charset);

// options
FontOptions options = font.getOptions();

options.scale = 1.5F;
options.rotation = 45;
options.italic = true;
options.invLineWrap = true;

// bounds
float width = font.getLineWidth(line);
float height = font.getTextHeight(text);
Vec2f bounds = font.getBounds(text);

// render
font.drawText(batch, text, x, y)
```

#### 4. Input:
``` java
// mouse
Jpize.getX()  // position
Jpize.getY()

Jpize.isTouched()    // touch
Jpize.isTouchDown()
Jpize.isTouchReleased()

Jpize.input().getScroll()  // scroll

Btn.LEFT.isDown()     // buttons
Btn.LEFT.isPressed()
Btn.LEFT.isReleased()

// keyboard
Key.ENTER.isPressed()
Key.ESCAPE.isDown()
Key.SPACE.isReleased()

// window
Jpize.getWidth()
Jpize.getHeight()
Jpize.getAspect()

// monitor
Jpize.monitor()         // window monitor
Jpize.primaryMonitor()  // primary monitor

// FPS & Delta Time
Jpize.getFPS()
Jpize.getDt()
Jpize.setFixedUpdateTPS(update_rate)
Jpize.getFixedUpdateDt()
```

#### 5. Audio:
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

#### 6. Resources:
``` java
// internal / external
Resource res = new Resource(path, true); // external
Resource res = new Resource(path); // internal

res.isExternal()
res.isInternal()


// as file
Resource res = new Resource("file.ext");

res.getExtension()  // returns 'ext' of 'file.ext'
res.getSimpleName() // returns 'file' of 'file.ext'

res.getFile()
res.exists()

res.mkDirsAndFile()
res.mkParentDirs()

// io
res.inStream()
res.outStream()

res.getJpizeIn()  // JpizeInputStream
res.getJpizeOut() // JpizeOutputStream

res.getReader()  // FastReader
res.getWriter()  // PrintStream

// write/read
res.writeString(text)
res.appendString(text)

res.readString()
res.readBytes()
res.readByteBuffer()  // ByteBuffer


// resources (images, sounds, fonts, ...etc)
Resource res = new Resource( ... );

new Texture(res);
new Sound(res);
new Shader(res_vert, res_frag);
AudioLoader.load(audio_buffer, res);
FontLoader.loadFnt(res);
PixmapIO.load(res);
```

---

## Module *Net*:
* *[Security](https://github.com/GeneralPashon/jpize-engine/tree/master/net/src/main/java/jpize/net/security)* - AES, RSA
* *[TCP](https://github.com/GeneralPashon/jpize-engine/tree/master/net/src/main/java/jpize/net/tcp)* - Packets, Server / Client
* *[UDP](https://github.com/GeneralPashon/jpize-engine/tree/master/net/src/main/java/jpize/net/udp)* - Server / Client

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

---

## Module *Physic*:
* *[Axis-Aligned](https://github.com/GeneralPashon/jpize-engine/tree/master/physic/src/main/java/jpize/physic/axisaligned)* - Box, Rect & Collider
* *[Utils](https://github.com/GeneralPashon/jpize-engine/tree/master/physic/src/main/java/jpize/physic/utils)* - Velocity, Intersector, Ray

#### 1. AABB Collider Example:
``` java
AABoxBody body_1 = new AABoxBody( new AABox(-1,-1,-1,  1, 1, 1) ); // 2x2x2 axis-aligned box
AABoxBody body_2 = new AABoxBody( new AABox(-1,-1,-1,  1, 1, 1) ); // another axis-aligned box

body_1.getPosition().set(-5F, 0, 0);

Vec3f b1_velocity = new Vec3f(10F, 0, 0);
velocity = AABoxCollider.getCollidedMovement(b1_velocity, body_1, body_2);

body_1.getPosition().add( b1_velocity ); // box will be move only 3 units
```

---

## Module *UI*:
* *[UIComponent](ui/src/main/java/jpize/ui/component/UIComponent.java)* - Base component class
* *[Constraint](ui/src/main/java/jpize/ui/constraint)* - Pixel, Relative, Aspect, Flag (Used to determine the static or dynamic position and size of ui components)
* *[AbstractLayout](ui/src/main/java/jpize/ui/component/AbstractLayout.java)* - implemented in: ConstraintLayout, VBox, HBox

#### 1. UI Java Example:
``` java
// context
UIContext ui = new UIContext();

// Layout
AbstractLayout layout = new VBox(Constr.win_width, Constr.win_height);
ui.setRootComponent(layout);

// Button
Button button = new Button(Constr.aspect(10), Constr.px(100), "Button Text", font);
button.padding().set(Constr.relh(0.35), Constr.zero, Constr.auto, Constr.zero);

// Slider
Slider slider = new Slider(Constr.aspect(10), Constr.px(100), "Slider: 0", font);
slider.padding().set(Constr.px(10), Constr.zero, Constr.auto, Constr.zero);

// Add to layout Button & Slider
layout.add(button);
layout.add(slider);

// Callbacks
button.input().addPressCallback((component, button, action) -> {
    System.out.println("Press Button");
});
button.input().addReleaseCallback((component, button, action) -> {
    System.out.println("Release Button");
});

slider.addSliderCallback(((component, value) -> {
    component.textview().setText("Slider: " + Maths.round(value * 100));
}));

// Render
ui.render();

// Dispose
ui.dispose();

// Enables/Disables touch & resize handling (disabled by defalut)
ui.enable();
ui.disable();
```

#### 2. [PUI Markup Language](https://github.com/GeneralPashon/jpize-ui-idea-plugin) Example:
#### Java:
``` java
// resources
Texture bg_0 = new Texture("ui/bg_0.jpg");
Font font = FontLoader.getDefault();
font.setScale(0.8F);

// load context
PuiLoader loader = new UILoader()
    .putRes("font", font)
    .putRes("layout:bg_0", bg_0)
    .putRes("button:aspect", Constr.aspect(7))
    .addComponentAlias("Btn", Button.class);

UIContext ui = loader.loadRes("view_file.pui");
ui.enable();

// callbacks
Button button = ui.getByID("button");
Slider slider = ui.getByID("slider");

button.input().addPressCallback((component, btn) -> component.style().background().color().setRgb(0.75));
button.input().addReleaseCallback((component, btn) -> component.style().background().color().setRgb(0.5));

slider.addSliderCallback(((component, value) -> {
    slider.textview().setText("Slider: " + Maths.round(value * 100));
    slider.textview().color().setRgb(1 - value);
    component.style().background().color().setA(value);
}));

...
```
#### PUI File:
``` pui
@VBox {
    style.background.image: !layout:bg_0

    @Btn (!button:aspect, 70px, 'Button Text', !font) {
        ID: 'button'
        padding: (0.35rh, zero, auto, zero)
        style: {
            background.color: (0.5, 0.5, 0.5, 0.75)
            border_size: 3px
            border_color: (1.0, 1.0, 1.0, 0.9)
            corner_radius: 35px
        }
    }

    @Slider (7ap, 70px, 'Slider: 0', !font) {
        ID: 'slider'
        padding: (10px, 0px, auto, 0px)
        style: {
            background.color.a: 0
            border_size: 3px
            border_color: (1.0, 1.0, 1.0, 0.9)
            corner_radius: 35px
        }
        handle.style: {
            background.color: (1.0, 1.0, 1.0, 0.9)
            corner_radius: 35px
        }
        textview.color: (1, 1, 1, 1)
    }
}
```
#### Result: ![preview](ui/preview.png)

---

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/GeneralPashon/jpize-engine/issues).
