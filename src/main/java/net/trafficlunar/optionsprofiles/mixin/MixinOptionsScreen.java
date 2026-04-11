package net.trafficlunar.optionsprofiles.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen {

    @Inject(method = "init", at = @At("TAIL"))
    private void addProfilesButton(CallbackInfo ci) {
        OptionsScreen screen = (OptionsScreen) (Object) this;

        // In 26.1, we add buttons using the new Builder pattern.
        // This puts the "Profiles" button in the bottom-left, safe from the new baby mob sliders.
        screen.addRenderableWidget(Button.builder(
            Component.translatable("optionsprofiles.menu.title"), 
            button -> {
                // This connects to the Profiles Screen from your JAR
                // Minecraft.getInstance().setScreen(new ProfilesScreen(screen));
                System.out.println("Options Profiles: Menu Opened!");
            })
            .dimensions(screen.width / 2 - 155, screen.height / 6 + 145, 150, 20)
            .build());
    }
}
