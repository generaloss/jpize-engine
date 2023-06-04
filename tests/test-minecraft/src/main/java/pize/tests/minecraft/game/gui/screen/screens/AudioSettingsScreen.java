package pize.tests.minecraft.game.gui.screen.screens;

import pize.audio.Audio;
import pize.graphics.util.batch.TextureBatch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.game.Session;
import pize.tests.minecraft.game.gui.components.*;
import pize.tests.minecraft.game.gui.constraints.GapConstraint;
import pize.tests.minecraft.game.gui.text.Component;
import pize.tests.minecraft.game.options.SoundCategory;

import java.util.List;

public class AudioSettingsScreen extends IOptionsScreen{

    private final BaseLayout layout;
    private final VolumeSlider masterVolume, musicVolume, ambientVolume, playersVolume, blocksVolume, weatherVolume;
    
    public AudioSettingsScreen(Session session){
        super(session);

        // Main Layout
        layout = new BaseLayout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TEXTS---------->
        // < Title >
        TextView titleTextView = new TextView(session, new Component().translation("audioSettings.title"));
        titleTextView.setY(GapConstraint.gap(71));
        layout.put("title", titleTextView);
        // Title
        
        ListView list = new ListView();
        list.setLayoutType(LayoutType.VERTICAL);
        list.alignItems(Align.UP);
        list.setSize(Constraint.relative(0.8), Constraint.relative(0.3));
        list.setY(GapConstraint.gap(3));
        layout.put("list", list);
        
        
        // <----------LINE 1---------->
        // [ Master Volume ]
        masterVolume = new VolumeSlider(session, SoundCategory.MASTER);
        masterVolume.setY(GapConstraint.gap(15));
        masterVolume.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("master", masterVolume);
        // Master Volume

        // <----------LINE 2---------->
        // [ Music ] [ Ambient ]
        
        // 2 Line Layout
        Layout layoutLine2 = new Layout();
        layoutLine2.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine2.setY(GapConstraint.gap(4));
        layoutLine2.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line2layout", layoutLine2);
        // Music
        musicVolume = new VolumeSlider(session, SoundCategory.MUSIC);
        musicVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine2.put("music", musicVolume);
        // Ambient
        ambientVolume = new VolumeSlider(session, SoundCategory.AMBIENT);
        ambientVolume.alignSelf(Align.RIGHT);
        ambientVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine2.put("ambient", ambientVolume);
        // <----------LINE 3---------->
        // [ Players ] [ Blocks ]

        // 3 Line Layout
        Layout layoutLine3 = new Layout();
        layoutLine3.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine3.setY(GapConstraint.gap(4));
        layoutLine3.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line3layout", layoutLine3);
        // Players
        playersVolume = new VolumeSlider(session, SoundCategory.PLAYERS);
        playersVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine3.put("players", playersVolume);
        // Blocks
        blocksVolume = new VolumeSlider(session, SoundCategory.BLOCKS);
        blocksVolume.alignSelf(Align.RIGHT);
        blocksVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine3.put("blocks", blocksVolume);
        // <----------LINE 4---------->
        // [ Wather ]

        // 4 Line Layout
        Layout layoutLine4 = new Layout();
        layoutLine4.setLayoutType(LayoutType.HORIZONTAL);
        layoutLine4.setY(GapConstraint.gap(4));
        layoutLine4.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("line4layout", layoutLine4);
        // Weather
        weatherVolume = new VolumeSlider(session, SoundCategory.WEATHER);
        weatherVolume.setSize(Constraint.pixel(155), Constraint.relative(1));
        layoutLine4.put("weather", weatherVolume);
        // <----------LINE 5---------->
        // [ Device ]

        // Device
        Button deviceButton = new Button(session, new Component().translation("audioSettings.device", new Component().formattedText(session.getOptions().getAudioDevice())));
        deviceButton.setY(GapConstraint.gap(4));
        deviceButton.setSize(Constraint.aspect(16), Constraint.pixel(20));
        list.put("device", deviceButton);

        // <----------DONE---------->
        // [ Done ]
        
        // Done
        Button doneButton = new Button(session, new Component().translation("gui.done"));
        doneButton.setY(GapConstraint.gap(4));
        doneButton.setSize(Constraint.aspect(10), Constraint.pixel(20));
        doneButton.setClickListener(this::close);
        layout.put("done", doneButton);

        // <----------CALLBACKS---------->

        // Device
        deviceButton.setClickListener(new Runnable(){
            int deviceIndex = 0;

            @Override
            public void run(){
                List<String> list = Audio.getAvailableDevices();
                if(list != null){
                    deviceIndex++;
                    if(deviceIndex >= list.size())
                        deviceIndex = 0;

                    String nextDevice = list.get(deviceIndex);

                    deviceButton.setText(new Component().translation("audioSettings.device", new Component().formattedText(nextDevice)));

                    session.getOptions().setAudioDevice(nextDevice);
                    session.getOptions().save();
                }
            }
        });
    }

    @Override
    public void render(TextureBatch batch){
        masterVolume.updateVolume();
        musicVolume.updateVolume();
        ambientVolume.updateVolume();
        playersVolume.updateVolume();
        blocksVolume.updateVolume();
        weatherVolume.updateVolume();
        
        layout.render(batch);
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