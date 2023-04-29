package pize.tests.minecraft.client.game.gui.screen.screens;

import pize.graphics.util.batch.Batch;
import pize.gui.Align;
import pize.gui.LayoutType;
import pize.gui.components.Layout;
import pize.gui.constraint.Constraint;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.gui.components.Button;
import pize.tests.minecraft.client.game.gui.components.TextView;
import pize.tests.minecraft.client.game.gui.text.Component;
import pize.tests.minecraft.client.game.lang.LanguageInfo;

import java.util.List;

public class OptionsScreen extends IOptionsScreen{

    private final Layout layout;

    public OptionsScreen(Session session){
        super(session);

        // [Main Layout]
        layout = new Layout();
        layout.setLayoutType(LayoutType.VERTICAL);
        layout.alignItems(Align.UP);

        // <----------TEXTS---------->
        // < Title >

        // Title (Options)
        TextView titleTextView = new TextView(session, new Component().translation("options.title"));
        titleTextView.setY(Constraint.relative(0.3));
        layout.put("title", titleTextView);

        // <----------LINE 1---------->
        // [ Video Settings ] [ Music & Sounds ]

        // 1 Line Layout
        Layout layoutLine1 = new Layout();
        layoutLine1.setY(Constraint.relative(0.1));
        layoutLine1.setSize(Constraint.aspect(16), Constraint.relative(BUTTON_HEIGHT));
        layoutLine1.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_1", layoutLine1);

        // Video Settings
        Button videoSettingsButton = new Button(session, new Component().translation("options.videoSettings"));
        videoSettingsButton.setClickListener(()->toScreen("video_settings"));
        videoSettingsButton.setSize(Constraint.aspect(7.7), Constraint.relative(1));
        layoutLine1.put("video_settings", videoSettingsButton);

        // Music & Sounds
        Button audioSettingsButton = new Button(session, new Component().translation("options.audioSettings"));
        audioSettingsButton.setClickListener(()->toScreen("audio_settings"));
        audioSettingsButton.setSize(Constraint.aspect(7.7), Constraint.relative(1));
        audioSettingsButton.alignSelf(Align.RIGHT);
        layoutLine1.put("audio_settings", audioSettingsButton);

        // <----------LINE 2---------->
        // [ Language ] [ Controls ]

        // 2 Line Layout
        Layout layoutLine2 = new Layout();
        layoutLine2.setY(Constraint.relative(BUTTON_OFFSET_Y));
        layoutLine2.setSize(Constraint.aspect(16), Constraint.relative(BUTTON_HEIGHT));
        layoutLine2.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_2", layoutLine2);

        // Language
        Button languageButton = new Button(session, new Component().translation("options.language"));
        languageButton.setSize(Constraint.aspect(7.7), Constraint.relative(1));
        layoutLine2.put("language", languageButton);

        // Controls
        Button controlsButton = new Button(session, new Component().translation("options.controls"));
        controlsButton.setClickListener(()->System.out.println("Controls"));
        controlsButton.setSize(Constraint.aspect(7.7), Constraint.relative(1));
        controlsButton.alignSelf(Align.RIGHT);
        layoutLine2.put("controls", controlsButton);

        // <----------LINE 3---------->
        // [ Resource Packs ]

        // 3 Line Layout
        Layout layoutLine3 = new Layout();
        layoutLine3.setY(Constraint.relative(BUTTON_OFFSET_Y));
        layoutLine3.setSize(Constraint.aspect(16), Constraint.relative(BUTTON_HEIGHT));
        layoutLine3.setLayoutType(LayoutType.HORIZONTAL);
        layout.put("layout_line_3", layoutLine3);

        // Resource Packs
        Button resourcePacksButton = new Button(session, new Component().translation("options.resourcePacks"));
        resourcePacksButton.setClickListener(()->System.out.println("Resource Packs"));
        resourcePacksButton.setSize(Constraint.aspect(7.7), Constraint.relative(1));
        layoutLine3.put("resource_packs", resourcePacksButton);

        // <----------DONE---------->
        // [ Done ]

        // Done
        Button doneButton = new Button(session, new Component().translation("gui.done"));
        doneButton.setClickListener(this::close);
        doneButton.setY(Constraint.relative(BUTTON_OFFSET_Y * 3));
        doneButton.setSize(Constraint.aspect(10), Constraint.relative(BUTTON_HEIGHT));
        layout.put("done", doneButton);

        // <----------CALLBACKS---------->

        // Language
        languageButton.setClickListener(new Runnable(){
            int langIndex = 0;

            @Override
            public void run(){
                List<LanguageInfo> availableLanguages = session.getLanguageManager().getAvailableLanguages();
                if(availableLanguages.size() != 0){
                    langIndex++;
                    if(langIndex >= availableLanguages.size())
                        langIndex = 0;

                    String languageCode = availableLanguages.get(langIndex).getCode();
                    System.out.println("Selected Lang: " + languageCode);
                    session.getLanguageManager().selectLanguage(languageCode);

                    session.getOptions().setLanguage(languageCode);
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
        toScreen(session.getGame().isInGame() ? "game_menu" : "main_menu");
        session.getOptions().save();
    }

}