package xyz.luan.lights.speech;

import java.io.InputStream;

public class Main {

    private static final int SAMPLE_RATE = 16000;

    public static void main(String[] args) throws Exception {
        InputStream credentialFile = Main.class.getResourceAsStream("/lights-io-key.json");

        Microphone mic = new Microphone(SAMPLE_RATE);
        SpeechAPI api = new SpeechAPI(SAMPLE_RATE, credentialFile);

        mic.listen(api::speak);

        api.listen(text -> {
            System.out.println("> " + text);
            if (text.contains("exit")) {
                System.out.println("I heard 'exit'. It's time to say goodbye o/");
                mic.close();
                api.close();
            }
        });

        mic.start();
    }
}
