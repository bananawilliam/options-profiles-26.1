package net.trafficlunar.optionsprofiles.data;

import net.minecraft.client.Minecraft;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.List;
import java.util.stream.Collectors;

public class Profiles {
    // 26.1 Pathing: We store profiles in the config folder
    private static final Path ROOT_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("config/optionsprofiles");

    public static void saveProfile(String name) {
        try {
            if (Files.notExists(ROOT_DIR)) Files.createDirectories(ROOT_DIR);
            
            Path currentOptions = Minecraft.getInstance().gameDirectory.toPath().resolve("options.txt");
            Path targetProfile = ROOT_DIR.resolve(name + ".txt");
            
            Files.copy(currentOptions, targetProfile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProfile(String name) {
        try {
            Path sourceProfile = ROOT_DIR.resolve(name + ".txt");
            Path optionsFile = Minecraft.getInstance().gameDirectory.toPath().resolve("options.txt");
            
            if (Files.exists(sourceProfile)) {
                Files.copy(sourceProfile, optionsFile, StandardCopyOption.REPLACE_EXISTING);
                // MUST reload options in-memory for 26.1 to notice the change
                Minecraft.getInstance().options.load();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> listProfiles() {
        try (Stream<Path> stream = Files.list(ROOT_DIR)) {
            return stream.filter(file -> !Files.isDirectory(file))
                         .map(Path::getFileName)
                         .map(Path::toString)
                         .filter(name -> name.endsWith(".txt"))
                         .map(name -> name.replace(".txt", ""))
                         .collect(Collectors.toList());
        } catch (IOException e) {
            return List.of(); // Return empty list if folder doesn't exist yet
        }
    }
}
