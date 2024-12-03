package models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusicPlayer {

    public BackgroundMusicPlayer() {
    }

    public void play(final String filePath) {
        // Run the audio playback in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Open the audio file
                    File audioFile = new File(filePath);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                    // Get the format of the audio file
                    AudioFormat baseFormat = audioStream.getFormat();

                    // Print the original format for debugging
                    System.out.println("Original Audio format: " + baseFormat);

                    // Attempt to get a line to play audio in its original format
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);

                    // Check if the line is supported
                    if (!AudioSystem.isLineSupported(info)) {
                        System.out.println("Line for the original format is not supported.");

                        // Attempt fallback formats
                        System.out.println("Trying PCM_SIGNED 22050Hz, 8-bit, mono...");
                        AudioFormat decodedFormat = new AudioFormat(
                                AudioFormat.Encoding.PCM_SIGNED,
                                22050,  // Lower sample rate for fallback
                                8,      // 8-bit audio
                                1,      // Mono channel
                                1,      // 1 byte per frame
                                22050,  // Lower frame rate
                                false   // Little-endian
                        );
                        info = new DataLine.Info(SourceDataLine.class, decodedFormat);

                        if (!AudioSystem.isLineSupported(info)) {
                            System.out.println("Trying PCM_SIGNED 11025Hz, 8-bit, mono...");
                            decodedFormat = new AudioFormat(
                                    AudioFormat.Encoding.PCM_SIGNED,
                                    11025,  // Even lower sample rate for fallback
                                    8,      // 8-bit audio
                                    1,      // Mono channel
                                    1,      // 1 byte per frame
                                    11025,  // Lower frame rate
                                    false   // Little-endian
                            );
                            info = new DataLine.Info(SourceDataLine.class, decodedFormat);
                        }
                    }

                    // Check if the adjusted format is supported
                    if (!AudioSystem.isLineSupported(info)) {
                        throw new LineUnavailableException("Line for adjusted format not found.");
                    }

                    // Check if the audio line is supported and print a message
                    if (AudioSystem.isLineSupported(info)) {
                        System.out.println("Audio line is supported.");
                    } else {
                        System.out.println("Audio line is NOT supported.");
                    }

                    // Get the line and play the audio using the format from the audioStream
                    try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                        line.open(baseFormat);  // Open with the base format from the audio stream
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
