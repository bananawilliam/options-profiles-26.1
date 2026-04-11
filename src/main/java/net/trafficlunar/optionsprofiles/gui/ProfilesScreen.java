package net.trafficlunar.optionsprofiles.gui;

import net.trafficlunar.optionsprofiles.data.Profiles;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.List;

public class ProfilesScreen extends Screen {
    private final Screen lastScreen;
    private EditBox nameInput; // The text box for naming profiles

    public ProfilesScreen(Screen lastScreen) {
        super(Component.translatable("optionsprofiles.menu.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        // 1. Setup the Text Input (EditBox)
        // Positioned at the top so it doesn't move when the list grows
        this.nameInput = new EditBox(this.font, this.width / 2 - 100, 40, 200, 20, Component.literal("Profile Name"));
        this.addRenderableWidget(this.nameInput);

        // 2. The "Save Current" Button
        this.addRenderableWidget(Button.builder(Component.translatable("optionsprofiles.button.save"), (button) -> {
            String name = this.nameInput.getValue();
            if (!name.isEmpty()) {
                Profiles.saveProfile(name);
                this.nameInput.setValue(""); // Clear the box after saving
                this.clearWidgets();         // Refresh the screen to show the new button
                this.init();
            }
        })
        .dimensions(this.width / 2 - 100, 65, 200, 20)
        .build());

        // 3. The List of Saved Profiles
        List<String> savedProfiles = Profiles.listProfiles();
        int yOffset = 100;

        for (String profileName : savedProfiles) {
            this.addRenderableWidget(Button.builder(Component.literal(profileName), (button) -> {
                Profiles.loadProfile(profileName);
                this.minecraft.setScreen(this.lastScreen);
            })
            .dimensions(this.width / 2 - 100, yOffset, 200, 20)
            .build());
            yOffset += 24;
        }

        // 4. Back Button
        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"), (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        // In 26.1, we use guiGraphics for all text rendering
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
