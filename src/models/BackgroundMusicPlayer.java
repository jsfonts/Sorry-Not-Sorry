package models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusicPlayer {

    public BackgroundMusicPlayer() {
    }

    public void play(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File audioFile = new File(filePath);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                    AudioFormat baseFormat = audioStream.getFormat();

                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);


                    try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                        line.open(baseFormat);  
                        line.start();

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = audioStream.read(buffer)) != -1) {
                            line.write(buffer, 0, bytesRead);
                        }

                        line.drain();
                        line.stop();
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }).start();
    }
}
