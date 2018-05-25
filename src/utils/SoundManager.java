package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.*;


public class SoundManager {
    private static HashMap<Integer, List<String>> soundLibrary = new HashMap<>();

    public static void loadSound(int id, String path) {
        if (soundLibrary.containsKey(id)) {
            soundLibrary.get(id).add(path);
        } else {
            List<String> filePaths = new ArrayList<>();
            filePaths.add(path);
            soundLibrary.putIfAbsent(id, filePaths);
        }
    }

    public static void play(int id, int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundLibrary.get(id).get(index)));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void loop(int id, int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundLibrary.get(id).get(index)));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
