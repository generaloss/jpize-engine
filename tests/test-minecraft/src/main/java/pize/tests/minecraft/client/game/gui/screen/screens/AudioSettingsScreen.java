package pize.tests.minecraft.client.game.gui.screen.screens;

import pize.audio.Audio;
import pize.graphics.util.batch.Batch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.Layout;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.gui.components.Button;
import pize.tests.minecraft.client.game.gui.text.Component;

import java.util.List;

public class AudioSettingsScreen extends IOptionsScreen{

    private final Layout layout;

    public AudioSettingsScreen(Session session){
        super(session);

        // Main Layout
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TEXTS---------->
        // < Title >

        // Title

        // <----------LINE 1---------->
        // [ Master Volume ]

        // Master Volume

        // <----------LINE 2---------->
        // [ Music ] [ Ambient ]

        // 2 Line Layout

        // Music

        // Ambient

        // <----------LINE 3---------->
        // [ Players ] [ Blocks ]

        // 3 Line Layout

        // Players

        // Blocks

        // <----------LINE 4---------->
        // [ Wather ]

        // 3 Line Layout

        // Weather

        // <----------LINE 5---------->
        // [ Device ]

        // Device
        Button deviceButton = new Button(session, new Component().translation("audioSettings.device", new Component().formattedText(session.getOptions().getAudioDevice())));
        deviceButton.setY(Constraint.relative(0.2));
        deviceButton.setSize(Constraint.aspect(25), Constraint.relative(BUTTON_HEIGHT));
        layout.put("device", deviceButton);

        // <----------DONE---------->
        // [ Done ]

        // Done


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
    public void render(Batch batch){
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