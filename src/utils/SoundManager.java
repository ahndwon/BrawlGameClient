package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.*;


public class SoundManager {
    private static HashMap<Integer, HashMap<Integer, Sound>> soundLibrary = new HashMap<>();

    private static class Sound {
        String path;
        AudioInputStream ais;
        Sound(String path, AudioInputStream ais) {
            this.path = path;
            this.ais = ais;
        }

    }

    public static void loadSound(int id, int index, String path) {
        if (soundLibrary.containsKey(id)) {
            Sound sound = new Sound(path, null);
            soundLibrary.get(id).put(index, sound);
        } else {
//            List<String> filePaths = new ArrayList<>();
//            filePaths.add(path);
            HashMap<Integer, Sound> temp = new HashMap<>();
            temp.put(index, new Sound(path, null));
            soundLibrary.putIfAbsent(id, temp);
        }
    }

    public static void play(int id, int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundLibrary.get(id).get(index).path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            soundLibrary.get(id).get(index).ais = ais;
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void loop(int id, int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundLibrary.get(id).get(index).path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            soundLibrary.get(id).get(index).ais = ais;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stop(int id, int index) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(soundLibrary.get(id).get(index).ais);
            clip.stop();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }
}
