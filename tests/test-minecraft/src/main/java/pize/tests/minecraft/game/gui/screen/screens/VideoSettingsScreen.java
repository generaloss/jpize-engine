package pize.tests.minecraft.game.gui.screen.screens;

import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.constraint.Constraint;
import pize.math.Maths;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.components.*;
import pize.tests.minecraft.game.gui.constraints.GapConstraint;
import pize.tests.minecraft.game.gui.text.Component;

public class VideoSettingsScreen extends IOptionsScreen{

    public static final float MIN_FOV = 30;
    public static final float MAX_FOV = 110;
    public static final float MIN_RENDER_DISTANCE = 1;
    public static final float MAX_RENDER_DISTANCE = 128;
    public static final int MAX_SETTING_FRAMERATE = 255;
    public static final int FRAMERATE_SETTING_INTERVAL = 5;
    public static final int MAX_MIPMAP_LEVELS = 4;
    
    private final BaseLayout layout;
    private final Slider fovSlider, renderDistanceSlider, maxFramerateSlider, mipmapSlider;
    
    public VideoSettingsScreen(Session session){
        super(session);

        // Main Layout
        layout = new BaseLayout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);
        
        // <----------TEXTS---------->
        // < Title >
        
        // Title
        TextView title = new TextView(session, new Component().translation("videoSettings.title"));
        title.setY(GapConstraint.gap(108));
        layout.put("title", title);
        
        // <----------OPTIONS: 1 LINE---------->
        // [ FOV ] [ Render Distance ]

        // 1 Line Layout
        Layout layoutLine1 = new Layout();
        layoutLine1.setY(GapConstraint.gap(15));
        layoutLine1.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layout.put("1lineLayout", layoutLine1);
        // Fov
        fovSlider = new Slider(session);
        fovSlider.setSize(Constraint.pixel(155), Constraint.relative(1));
        fovSlider.setText(new Component().translation("videoSettings.fov", new Component().text(String.valueOf(getOptions().getFov())) ));
        fovSlider.setValue(getOptions().getFov() / (MAX_FOV - MIN_FOV));
        layoutLine1.put("fov", fovSlider);
        // Render Distance
        renderDistanceSlider = new Slider(session);
        renderDistanceSlider.setSize(Constraint.pixel(155), Constraint.relative(1));
        renderDistanceSlider.setText(new Component().translation("videoSettings.renderDistance", new Component().text(String.valueOf(getOptions().getRenderDistance()))) );
        renderDistanceSlider.setValue(getOptions().getRenderDistance() / (MAX_RENDER_DISTANCE - MIN_RENDER_DISTANCE));
        renderDistanceSlider.alignSelf(Align.RIGHT);
        layoutLine1.put("renderDistance", renderDistanceSlider);
        // <----------OPTIONS: 2 LINE---------->
        // [ Max Framerate ] [ Show FPS ]

        // 2 Line Layout
        Layout layoutLine2 = new Layout();
        layoutLine2.setY(GapConstraint.gap(4));
        layoutLine2.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layout.put("2lineLayout", layoutLine2);
        // Max Framerate
        maxFramerateSlider = new Slider(session);
        maxFramerateSlider.setSize(Constraint.pixel(155), Constraint.relative(1));
        maxFramerateSlider.setText(new Component().translation("videoSettings.maxFramerate", setMaxFramerateComponent(new Component(), getOptions().getMaxFramerate())) );
        maxFramerateSlider.setValue((float) getOptions().getMaxFramerate() / MAX_SETTING_FRAMERATE);
        maxFramerateSlider.setDivisions(MAX_SETTING_FRAMERATE / FRAMERATE_SETTING_INTERVAL);
        layoutLine2.put("maxFramerate", maxFramerateSlider);
        // Show FPS
        Button showFpsButton = new Button(session, new Component().translation("videoSettings.showFps", setBooleanComponent(new Component(), getOptions().isShowFps())));
        showFpsButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        showFpsButton.alignSelf(Align.RIGHT);
        layoutLine2.put("showFPS", showFpsButton);
        // <----------OPTIONS: 3 LINE---------->
        // [ Mipmap Levels ] [ Fullscreen ]

        // 3 Line Layout
        Layout layoutLine3 = new Layout();
        layoutLine3.setY(GapConstraint.gap(4));
        layoutLine3.setSize(Constraint.aspect(16), Constraint.pixel(20));
        layout.put("3lineLayout", layoutLine3);
        // Mipmap Levels
        mipmapSlider = new Slider(session);
        mipmapSlider.setSize(Constraint.pixel(155), Constraint.relative(1));
        mipmapSlider.setText(new Component().translation("videoSettings.mipmapLevels", new Component().text(String.valueOf(getOptions().getMipmapLevels()))) );
        mipmapSlider.setValue((float) getOptions().getMipmapLevels() / MAX_MIPMAP_LEVELS);
        mipmapSlider.setDivisions(MAX_MIPMAP_LEVELS);
        layoutLine3.put("mipmap", mipmapSlider);
        // Fullscreen
        Button fullscreenButton = new Button(session, new Component().translation("videoSettings.fullscreen", setBooleanComponent(new Component(), getOptions().isFullscreen())));
        fullscreenButton.setSize(Constraint.pixel(155), Constraint.relative(1));
        fullscreenButton.alignSelf(Align.RIGHT);
        layoutLine3.put("fullscreen", fullscreenButton);
        // <----------DONE---------->
        // [ Done ]
        Button doneButton = new Button(session, new Component().translation("gui.done"));
        doneButton.setClickListener(this::close);
        doneButton.setY(GapConstraint.gap(15));
        doneButton.setSize(Constraint.aspect(10), Constraint.pixel(20));
        layout.put("done", doneButton);
        // Done

        // <----------CALLBACKS---------->
        showFpsButton.setClickListener(()->{
            getOptions().setShowFps(!getOptions().isShowFps());
            setBooleanComponent(showFpsButton.getText().getComponentAsTranslation(0).getArg(0).clear().reset(), getOptions().isShowFps());
        });
        
        fullscreenButton.setClickListener(()->{
            getOptions().setFullscreen(!getOptions().isFullscreen());
            setBooleanComponent(fullscreenButton.getText().getComponentAsTranslation(0).getArg(0).clear().reset(), getOptions().isFullscreen());
        });
    }

    @Override
    public void render(TextureBatch batch){
        layout.render(batch);
        
        if(fovSlider.isChanged()){
            getOptions().setFov( Maths.round(fovSlider.getValue() * (MAX_FOV - MIN_FOV) + MIN_FOV) );
            fovSlider.getText().getComponentAsTranslation(0).getArg(0).clear().text(session.getOptions().getFov());
        }
        
        if(renderDistanceSlider.isChanged()){
            getOptions().setRenderDistance( Maths.round(renderDistanceSlider.getValue() * (MAX_RENDER_DISTANCE - MIN_RENDER_DISTANCE) + MIN_RENDER_DISTANCE) );
            renderDistanceSlider.getText().getComponentAsTranslation(0).getArg(0).clear().text(String.valueOf(session.getOptions().getRenderDistance()));
        }
        
        if(maxFramerateSlider.isChanged()){
            final int maxFramerate = Maths.round(maxFramerateSlider.getValue() * MAX_SETTING_FRAMERATE);
            setMaxFramerateComponent(
                maxFramerateSlider.getText().getComponentAsTranslation(0).getArg(0).clear().reset(), maxFramerate);
        }
        if(maxFramerateSlider.isTouchReleased()){
            getOptions().setMaxFramerate(Maths.round(maxFramerateSlider.getValue() * MAX_SETTING_FRAMERATE), MAX_SETTING_FRAMERATE);
            saveOptions();
        }
        
        if(mipmapSlider.isChanged()){
            final int mipmapLevels = Maths.round(mipmapSlider.getValue() * MAX_MIPMAP_LEVELS);
            mipmapSlider.getText().getComponentAsTranslation(0).getArg(0).clear().text(String.valueOf(mipmapLevels));
        }
        if(mipmapSlider.isTouchReleased()){
            getOptions().setMipmapLevels(Maths.round(mipmapSlider.getValue() * MAX_MIPMAP_LEVELS));
            saveOptions();
        }
    }
    
    
    private Component setMaxFramerateComponent(Component component, int maxFramerate){
        if(maxFramerate == 0)
            component.translation("text.vSync");
        else if(maxFramerate == MAX_SETTING_FRAMERATE)
            component.translation("text.unlimited");
        else
            component.text(String.valueOf(maxFramerate));
        
        return component;
    }
    
    private Component setBooleanComponent(Component component, boolean showFPS){
        if(showFPS)
            component.translation("text.on");
        else
            component.translation("text.off");
        
        return component;
    }
    

    @Override
    public void resize(int width, int height){ }

    @Override
    public void onShow(){ }

    @Override
    public void close(){
        toScreen("options");
    }

}