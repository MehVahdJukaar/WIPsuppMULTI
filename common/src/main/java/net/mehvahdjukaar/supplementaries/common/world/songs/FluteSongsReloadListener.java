package net.mehvahdjukaar.supplementaries.common.world.songs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FluteSongsReloadListener extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static final FluteSongsReloadListener RELOAD_INSTANCE = new FluteSongsReloadListener();

    public FluteSongsReloadListener() {
        super(GSON, "flute_songs");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profile) {
        List<Song> temp = new ArrayList<>();

        jsons.forEach((key, input) -> {
            try {
                Song song = GSON.fromJson(input, Song.class);
                if (song.getNotes().length == 0) {
                    Supplementaries.LOGGER.error("Failed to parse JSON object for song " + key + ": a song can't have 0 notes!");
                } else {
                    temp.add(song);
                    SongsManager.addSong(key, song);
                }
            } catch (Exception e) {
                Supplementaries.LOGGER.error("Failed to parse JSON object for song " + key);
            }
        });
        if (temp.size() != 0) Supplementaries.LOGGER.info("Loaded  " + temp.size() + " flute songs");
        temp.forEach(Song::processForPlaying);
    }

    public static void saveRecordedSong(Song song) {

        File folder = PlatformHelper.getGamePath().resolve("recorded_songs").toFile();

        if (!folder.exists()) {
            folder.mkdir();
        }

        File exportPath = new File(folder, song.getTranslationKey() + ".json");

        try {
            try (FileWriter writer = new FileWriter(exportPath)) {
                GSON.toJson(song, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
