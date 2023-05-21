package pize.tests.minecraft.client.game.gui.screen.screens;

import pize.Pize;
import pize.files.Resource;
import pize.graphics.camera.PerspectiveCamera;
import pize.graphics.postprocess.effects.GaussianBlur;
import pize.graphics.texture.Texture;
import pize.graphics.texture.TextureRegion;
import pize.graphics.util.SkyBox;
import pize.graphics.util.batch.Batch;
import pize.graphics.util.color.ImmutableColor;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.Image;
import pize.gui.components.Layout;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.gui.components.Button;
import pize.tests.minecraft.client.game.gui.components.MConstraint;
import pize.tests.minecraft.client.game.gui.components.TextView;
import pize.tests.minecraft.client.game.gui.screen.Screen;
import pize.tests.minecraft.client.game.gui.text.Component;
import pize.tests.minecraft.client.game.gui.text.formatting.StyleFormatting;

public class MainMenuScreen extends Screen{

    public static final ImmutableColor SPLASH_COLOR = new ImmutableColor(1, 1, 0, 1F);

    private final SkyBox skyBox;
    private final PerspectiveCamera camera;
    private final Texture panorama_overlay;

    private final Layout layout, layoutTexts;

    private final Resource resSplashes;
    private final TextView splashTextView;

    public MainMenuScreen(Session session){
        super(session);

        // Panorama
        skyBox = new SkyBox(
            "vanilla/textures/gui/title/background/panorama_1.png",
            "vanilla/textures/gui/title/background/panorama_3.png",
            "vanilla/textures/gui/title/background/panorama_4.png",
            "vanilla/textures/gui/title/background/panorama_5.png",
            "vanilla/textures/gui/title/background/panorama_0.png",
            "vanilla/textures/gui/title/background/panorama_2.png"
        );
        camera = new PerspectiveCamera(0.1, 2, 79);
        camera.getRot().set(90, -25);
        panorama_overlay = session.getResourceManager().getTexture("panorama_overlay").getTexture();

        // Main Layout
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TITLE---------->
        // [   Mine|craft   ]
        // [  Java Edition  ]
        //         < SPLASH >

        // Title (MINECRAFT)
        TextureRegion titleLeftPartTexture = session.getResourceManager().getTexture("title_left_part");
        TextureRegion titleRightPartTexture = session.getResourceManager().getTexture("title_right_part");
        TextureRegion titleEditionTexture = session.getResourceManager().getTexture("title_edition");

        Layout titleLayout = new Layout();
        titleLayout.setY(Constraint.relative(0.07));
        titleLayout.setSize(
            Constraint.aspect(titleLeftPartTexture.aspect() + titleRightPartTexture.aspect()),
            MConstraint.button(TITLE_SIZE)
        );
        titleLayout.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("title", titleLayout);

        Image titleLeftPart = new Image(titleLeftPartTexture);
        titleLeftPart.setSize(Constraint.aspect(titleLeftPartTexture.aspect()), Constraint.relative(1));
        titleLayout.put("title_left", titleLeftPart);
        Image titleRightPart = new Image(titleRightPartTexture);
        titleRightPart.setSize(Constraint.aspect(titleRightPartTexture.aspect()), Constraint.relative(1));
        titleLayout.put("title_right", titleRightPart);

        // Edition (Java Edition)
        Image edition = new Image(titleEditionTexture);
        edition.setSize(Constraint.aspect(7), MConstraint.button(TITLE_SIZE / 3.1));
        edition.setY(Constraint.relative(-0.025));
        layout.put("edition", edition);

        // Splash
        resSplashes = new Resource("vanilla/texts/splashes.txt");

        splashTextView = new TextView(session, new Component().color(SPLASH_COLOR).style(StyleFormatting.ITALIC).formattedText("Splash!"));
        splashTextView.setSize(MConstraint.text(TEXT_HEIGHT * 2));
        splashTextView.setRotation(15);
        layout.put("splash", splashTextView);

        // <----------BUTTONS: 1-2 LINE---------->
        // [ Singleplayer ]
        // [  Multiplayer ]
        // [     Mods     ]

        // Singleplayer
        Button singleplayerButton = new Button(session, new Component().translation("menu.singleplayer"));
        singleplayerButton.setClickListener(()->toScreen("world_selection"));
        singleplayerButton.setY(Constraint.relative(0.2));
        singleplayerButton.setSize(Constraint.aspect(10), MConstraint.button(BUTTON_HEIGHT));
        layout.put("singleplayer", singleplayerButton);

        // Multiplayer
        Button multiplayerButton = new Button(session, new Component().translation("menu.multiplayer"));
        multiplayerButton.setClickListener(()->System.out.println("Multiplayer"));
        multiplayerButton.setY(Constraint.relative(BUTTON_OFFSET_Y));
        multiplayerButton.setSize(Constraint.aspect(10), MConstraint.button(BUTTON_HEIGHT));
        layout.put("multiplayer", multiplayerButton);

        // Mods
        Button modsButton = new Button(session, new Component().translation("menu.mods"));
        modsButton.setClickListener(()->System.out.println("Mods"));
        modsButton.setY(Constraint.relative(BUTTON_OFFSET_Y));
        modsButton.setSize(Constraint.aspect(10), MConstraint.button(BUTTON_HEIGHT));
        layout.put("mods", modsButton);

        // <----------BUTTONS: 3 LINE---------->
        // [ Options... ] [ Quit Game ]

        // Horizontal Layout
        Layout horizontalLayout = new Layout();
        horizontalLayout.setY(Constraint.relative(BUTTON_OFFSET_Y * 3));
        horizontalLayout.setSize(Constraint.aspect(10), MConstraint.button(BUTTON_HEIGHT));
        horizontalLayout.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("horizontal_layout", horizontalLayout);

        // Options...
        Button optionsButton = new Button(session, new Component().translation("menu.options"));
        optionsButton.setClickListener(()->toScreen("options"));
        optionsButton.setSize(Constraint.aspect(4.9), Constraint.relative(1));
        horizontalLayout.put("options", optionsButton);

        // Quit Game
        Button quitButton = new Button(session, new Component().translation("menu.quit"));
        quitButton.setClickListener(this::close);
        quitButton.alignSelf(Align.RIGHT);
        quitButton.setSize(Constraint.aspect(4.9), Constraint.relative(1));
        horizontalLayout.put("quit", quitButton);

        // <----------TEXTS---------->
        // < Version > < Copyright >

        // Texts Layout
        layoutTexts = new Layout();

        // Version (Minecraft 1.0.0)
        TextView versionTextView = new TextView(session, new Component().formattedText("Minecraft §n1§r.§n01§r.§n0"));
        versionTextView.setPosition(Constraint.relativeToHeight(0.005));
        layoutTexts.put("version", versionTextView);

        // Copyright (Pashok AB)
        TextView copyrightTextView = new TextView(session, new Component().formattedText("Copyright §oПашок§r AB. Do not distribute!"));
        copyrightTextView.setPosition(Constraint.relativeToHeight(0.005));
        copyrightTextView.alignSelf(Align.RIGHT_DOWN);
        layoutTexts.put("copyright", copyrightTextView);

    }

    @Override
    public void render(Batch batch){
        // Panorama
        camera.getRot().yaw -= Pize.getDeltaTime() * 2;
        camera.update();
        skyBox.render(camera);
        // Panorama Overlay
        batch.setAlpha(0.8F);
        batch.draw(panorama_overlay, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.setAlpha(1F);
        
        // UI
        layout.render(batch);
        layoutTexts.render(batch);

        // Splash
        // ..Code
    }


    @Override
    public void onShow(){
        camera.getRot().set(90, -25);
    }

    @Override
    public void resize(int width, int height){

    }


    @Override
    public void close(){
        Pize.exit();
    }

    @Override
    public void dispose(){
        skyBox.dispose();
    }

    @Override
    public boolean shouldCloseOnEsc(){
        return false;
    }

    @Override
    public boolean renderDirtBackground(){
        return false;
    }

}
