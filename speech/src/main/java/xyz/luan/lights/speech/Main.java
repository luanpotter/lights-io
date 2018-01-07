package xyz.luan.lights.speech;

import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    private static final int SAMPLE_RATE = 16000;

    public static void main(String[] args) throws Exception {
        String switchUrl = args[0];
        InputStream credentialFile = Main.class.getResourceAsStream("/lights-io-key.json");

        Microphone mic = new Microphone(SAMPLE_RATE);
        SpeechAPI api = new SpeechAPI(SAMPLE_RATE, credentialFile);

        mic.listen(api::speak);

        api.listen(text -> {
            try {
                System.out.println("> " + text);
                if (text.contains("lights on") || text.contains("light on")) {
                    put(switchUrl + "/toggle?s=on");
                } else if (text.contains("lights off") || text.contains("light off")) {
                    put(switchUrl + "/toggle?s=off");
                } else if (text.contains("lights") || text.contains("light")) {
                    put(switchUrl + "/toggle");
                } else if (text.contains("exit")) {
                    System.out.println("I heard 'exit'. It's time to say goodbye o/");
                    mic.close();
                    api.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        mic.start();
    }

    private static void put(String url) throws IOException {
        Response put = new HttpFacade(url).body("").put();
        if (put.status() != 200) {
            System.err.println("Error performing request; status: " + put.status());
            System.err.println(put.content());
        }
    }
}
