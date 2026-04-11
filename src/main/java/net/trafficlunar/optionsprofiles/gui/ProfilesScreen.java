package net.trafficlunar.optionsprofiles.gui;

import net.trafficlunar.optionsprofiles.data.Profiles;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.List;

public class ProfilesScreen extends Screen {
    private final Screen lastScreen;

    public ProfilesScreen(Screen lastScreen) {
        super(Component.translatable("optionsprofiles.menu.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        // 1. Get the list of profiles from our 'Profiles' data class
        List<String> savedProfiles = Profiles.listProfiles();
        
        int yOffset = 40; // Start drawing buttons below the title

        // 2. Loop through each profile name and create a button for it
        for (String profileName : savedProfiles) {
            this.addRenderableWidget(Button.builder(Component.literal(profileName), (button) -> {
                Profiles.loadProfile(profileName);
                // Optional: Close the screen or show a "Success" message
                this.minecraft.setScreen(this.lastScreen);
            })
            .dimensions(this.width / 2 - 100, yOffset, 200, 20)
            .build());
            
            yOffset += 24; // Move the next button down
        }

        // 3. Back Button (Always at the bottom)
        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"), (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
