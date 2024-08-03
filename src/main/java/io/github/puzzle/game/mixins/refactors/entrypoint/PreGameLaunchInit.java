package io.github.puzzle.game.mixins.refactors.entrypoint;

import finalforeach.cosmicreach.lwjgl3.Lwjgl3Launcher;
import io.github.puzzle.game.Globals;
import io.github.puzzle.loader.entrypoint.interfaces.PreModInitializer;
import io.github.puzzle.loader.launch.Piece;
import io.github.puzzle.loader.mod.ModLocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Lwjgl3Launcher.class)
public class PreGameLaunchInit {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/lwjgl3/Lwjgl3Launcher;createApplication()Lcom/badlogic/gdx/backends/lwjgl3/Lwjgl3Application;"))
    private static void loadPreLaunch(String[] args, CallbackInfo ci) {
        ModLocator.getMods();
        Piece.provider.addBuiltinMods();
        Globals.initRenderers();
        PreModInitializer.invokeEntrypoint();
    }
}
