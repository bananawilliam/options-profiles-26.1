package net.trafficlunar.optionsprofiles.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ProfilesScreen extends Screen {
    private final Screen lastScreen;

    public ProfilesScreen(Screen lastScreen) {
        super(Component.translatable("optionsprofiles.menu.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        // Back Button - Using the 26.1 Builder Pattern
        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"), (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
        
        // Your logic for listing profiles from the JAR goes here next!
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
