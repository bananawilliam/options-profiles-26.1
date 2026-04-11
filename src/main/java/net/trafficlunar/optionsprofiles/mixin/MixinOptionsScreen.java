package net.trafficlunar.optionsprofiles.mixin;

import net.trafficlunar.optionsprofiles.gui.ProfilesScreen; // The new import
import net.minecraft.client.Minecraft;
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

        // The button now correctly targets your new ProfilesScreen
        screen.addRenderableWidget(Button.builder(
            Component.translatable("optionsprofiles.menu.title"), 
            button -> {
                // This line opens the screen we just created!
                Minecraft.getInstance().setScreen(new ProfilesScreen(screen));
            })
            .dimensions(screen.width / 2 - 155, screen.height / 6 + 145, 150, 20)
            .build());
    }
}
