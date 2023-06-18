# PIZE - Pizza Engine 
The engine focuses on being clear, simple and productive

## Getting Started
Repository contains examples in 'tests' module

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
Pize.getX(); // position
Pize.getY();

Pize.isTouched(); // touch
Pize.isTouchDown();
Pize.isTouchReleased();

Pize.mouse().getScroll(); // scroll

// keyboard
Key.SPACE.isPressed();
Key.ENTER.isDown();
Key.ESCAPE.isReleased();

// window
Pize.getWidth();
Pize.getHeight();
Pize.getAspect();

// monitor
Pize.monitor().getWidth();
Pize.monitor().getHeight();
Pize.monitor().getAspect();

// fps
Pize.getFPS();
Pize.getDeltaTime();
```

#### 3. Audio:
``` java
Sound sound = new Sound("sound.mp3");

sound.setVolume(0.5F);
sound.setLooping(true);
sound.setPitch(1.5F);

sound.play();
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

res.getReader(); // returns pize.util.io.FastReader
res.getWriter(); // returns PrintStream

// resource (image, sound, ...etc)
Resource res = new Resource( ... );

new Texture(res);
new Sound(res);
PixmapIO.load(res);
new Shader(res_vert, res_frag);
AudioLoader.load(new AudioBuffer(), res);
```

### Net:
* Security Keys (AES, RSA)
* TCP / UDP Client and Server

#### 1. (Low)Encrypted Server-Client Example:
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

### Physics:
* AABB Collider (2D, 3D)
* Utils (Velocity, Body)

#### 1. Collider Example:
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

#### 1. UI Example:
``` java
// init
Layout layout = new Layout();
layout.setLayoutType( LayoutType.HORIZONTAL );
layout.alignItems( Align.CENTER );

Image image = new Image(image_texture);
layout.put("image_id", image);

// render
batch.begin();
layout.render(batch);
batch.end();
```

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/GeneralPashon/Pizza-Engine/issues).
