package dev.typeracist.typeracist.logic.global;

import dev.typeracist.typeracist.utils.ResourceName;
import dev.typeracist.typeracist.utils.SceneName;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    private static final int FADE_DURATION_MS = 1000; // 1 second fade
    private static MusicPlayer instance;
    private final Map<String, String> musicTracks = new HashMap<>();
    // Map scene names to music tracks
    private final Map<String, String> sceneMusicMap = new HashMap<>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer nextMediaPlayer;
    private String currentTrack;
    private boolean isPlaying = false;
    private int volumeLevel = 3; // Default volume level (0-5)

    private MusicPlayer() {
        // Initialize music tracks
        initializeMusicTracks();
        // Initialize scene to music mapping
        initializeSceneMusicMap();
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    private void initializeMusicTracks() {
        // Add music tracks to the map using ResourceName constants
        URL mainThemeUrl = getClass().getResource(ResourceName.MUSIC_MAIN_THEME);
        URL battleThemeUrl = getClass().getResource(ResourceName.MUSIC_BATTLE_THEME);
        URL shopThemeUrl = getClass().getResource(ResourceName.MUSIC_SHOP_THEME);
        URL bossThemeUrl = getClass().getResource(ResourceName.MUSIC_BOSS_THEME);

        if (mainThemeUrl != null) {
            musicTracks.put("main_theme", mainThemeUrl.toExternalForm());
        }
        if (battleThemeUrl != null) {
            musicTracks.put("battle_theme", battleThemeUrl.toExternalForm());
        }
        if (shopThemeUrl != null) {
            musicTracks.put("shop_theme", shopThemeUrl.toExternalForm());
        }
        if (bossThemeUrl != null) {
            musicTracks.put("boss_theme", bossThemeUrl.toExternalForm());
        }
    }

    private void initializeSceneMusicMap() {
        // Map scene names to music tracks using SceneName constants
        sceneMusicMap.put(SceneName.MAIN, "main_theme");
        sceneMusicMap.put(SceneName.CHARACTERS, "main_theme");
        sceneMusicMap.put(SceneName.SETTINGS, "main_theme");
        sceneMusicMap.put(SceneName.MAP, "main_theme");
        sceneMusicMap.put(SceneName.CHEST, "main_theme");
        sceneMusicMap.put(SceneName.NEXT_MAP, "main_theme");

        sceneMusicMap.put(SceneName.SHOP, "shop_theme");

        sceneMusicMap.put(SceneName.BATTLE_SCENE1, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE2, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE3, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE4, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE5, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE6, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE7, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE8, "battle_theme");
        sceneMusicMap.put(SceneName.BATTLE_SCENE9, "battle_theme");
        sceneMusicMap.put(SceneName.BOSS_SCENE, "boss_theme");
    }

    /**
     * Play music based on the current scene with volume fade
     *
     * @param sceneName The name of the current scene
     */
    public void playMusicForScene(String sceneName) {
        String trackName = sceneMusicMap.getOrDefault(sceneName, "main_theme");

        // If the same track is already playing, don't restart it
        if (currentTrack != null && currentTrack.equals(trackName) && isPlaying) {
            return;
        }

        // If volume is 0, don't bother with fading
        if (volumeLevel == 0) {
            playMusic(trackName);
            return;
        }

        // Prepare the next track
        prepareNextTrack(trackName);

        // If there's a current track playing, fade it out and then start the new one
        if (mediaPlayer != null && isPlaying) {
            fadeOutCurrentAndFadeInNext();
        } else {
            // No current track, just start the new one with fade in
            startNextTrackWithFadeIn();
        }
    }

    private void prepareNextTrack(String trackName) {
        if (!musicTracks.containsKey(trackName)) {
            System.err.println("Track not found: " + trackName);
            return;
        }

        try {
            // Create new media player with the selected track
            Media media = new Media(musicTracks.get(trackName));
            nextMediaPlayer = new MediaPlayer(media);

            // Set initial volume to 0 for fade in
            nextMediaPlayer.setVolume(0);

            // Set to loop indefinitely
            nextMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Store the track name
            currentTrack = trackName;
        } catch (Exception e) {
            System.err.println("Error preparing next track: " + e.getMessage());
            e.printStackTrace();
            nextMediaPlayer = null;
        }
    }

    private void fadeOutCurrentAndFadeInNext() {
        if (mediaPlayer == null || nextMediaPlayer == null)
            return;

        // Calculate the target volume based on volume level
        double targetVolume = volumeLevel * 0.2; // 0, 0.2, 0.4, 0.6, 0.8, 1.0

        // Create fade out timeline for current track
        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), mediaPlayer.getVolume())),
                new KeyFrame(Duration.millis(FADE_DURATION_MS), event -> {
                    // Stop and dispose the old media player
                    mediaPlayer.stop();
                    mediaPlayer.dispose();

                    // Set the next media player as the current one
                    mediaPlayer = nextMediaPlayer;
                    nextMediaPlayer = null;

                    // Start playing the new track
                    mediaPlayer.play();
                    isPlaying = true;

                    // Create fade in timeline for new track
                    Timeline fadeInTimeline = new Timeline(
                            new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), 0)),
                            new KeyFrame(Duration.millis(FADE_DURATION_MS),
                                    new KeyValue(mediaPlayer.volumeProperty(), targetVolume)));
                    fadeInTimeline.play();
                }, new KeyValue(mediaPlayer.volumeProperty(), 0)));
        fadeOutTimeline.play();
    }

    private void startNextTrackWithFadeIn() {
        if (nextMediaPlayer == null)
            return;

        // If there was a previous media player, dispose it
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        // Set the next media player as the current one
        mediaPlayer = nextMediaPlayer;
        nextMediaPlayer = null;

        // Calculate the target volume based on volume level
        double targetVolume = volumeLevel * 0.2; // 0, 0.2, 0.4, 0.6, 0.8, 1.0

        // Start playing the new track
        mediaPlayer.play();
        isPlaying = true;

        // Create fade in timeline
        Timeline fadeInTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), 0)),
                new KeyFrame(Duration.millis(FADE_DURATION_MS),
                        new KeyValue(mediaPlayer.volumeProperty(), targetVolume)));
        fadeInTimeline.play();
    }

    /**
     * Play music immediately without fading
     *
     * @param trackName The name of the track to play
     */
    public void playMusic(String trackName) {
        if (!musicTracks.containsKey(trackName)) {
            System.err.println("Track not found: " + trackName);
            return;
        }

        // If the same track is already playing, don't restart it
        if (currentTrack != null && currentTrack.equals(trackName) && isPlaying) {
            return;
        }

        // Stop current track if playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        try {
            // Create new media player with the selected track
            Media media = new Media(musicTracks.get(trackName));
            mediaPlayer = new MediaPlayer(media);

            // Set volume based on current volume level
            updateVolume();

            // Set to loop indefinitely
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Play the music
            mediaPlayer.play();
            currentTrack = trackName;
            isPlaying = true;
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(int level) {
        if (level >= 0 && level <= 5) {
            volumeLevel = level;
            updateVolume();
        }
    }

    private void updateVolume() {
        if (mediaPlayer != null) {
            // Convert volume level (0-5) to volume value (0.0-1.0)
            double volume = volumeLevel * 0.2; // 0, 0.2, 0.4, 0.6, 0.8, 1.0

            // Create a smooth transition to the new volume
            Timeline fadeTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), mediaPlayer.getVolume())),
                    new KeyFrame(Duration.millis(300), new KeyValue(mediaPlayer.volumeProperty(), volume)));
            fadeTimeline.play();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public String getCurrentTrack() {
        return currentTrack;
    }
}