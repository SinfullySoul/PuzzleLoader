package dev.crmodders.puzzle.providers.api;

import dev.crmodders.puzzle.mod.Version;
import dev.crmodders.puzzle.launch.PuzzleClassLoader;

import java.util.Collection;

public interface GameProviderScaffold {

    // Game Names
    String getId();
    String getName();

    // Game Version
    Version getGameVersion();
    String getRawVersion();

    // Extra Data
    String getEntrypoint();
    Collection<String> getArgs();

    // Inits
    void initArgs(String[] args);
    void inject(PuzzleClassLoader classLoader);

}
