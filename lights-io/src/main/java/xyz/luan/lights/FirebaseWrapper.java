package xyz.luan.lights;

import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FirebaseWrapper {

    private static String key;

    private static String getKey() {
        if (key == null) {
            key = parseKey();
        }
        return key;
    }

    private static String parseKey() {
        InputStream stream = FirebaseWrapper.class.getResourceAsStream("/lights-io-firebase.key");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Didn't find firebase secret key in resources folder!", e);
        }
    }

    public static void put(final String path, final Object object) {
        try {
            HttpFacade facade = new HttpFacade("https://lights-io.firebaseio.com" + path + ".json");
            facade.query("auth", getKey());
            facade.body(object);
            Response response = facade.put();
            System.out.println("Requested to Firebase; response: " + response.status());
            System.out.println(response.content());
        } catch (IOException e) {
            System.err.println("There was a problem updating Firebase!");
            e.printStackTrace();
        }
    }
}
