package xyz.luan.lights;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class FirebaseWrapper {

    private static String getToken() {
        try {
            InputStream serviceAccount = FirebaseWrapper.class.getResourceAsStream("/lights-io-key.json");
            GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);
            GoogleCredential scoped = googleCred.createScoped(Arrays.asList(
                    "https://www.googleapis.com/auth/firebase",
                    "https://www.googleapis.com/auth/firebase.database",
                    "https://www.googleapis.com/auth/userinfo.email"
            ));

            scoped.refreshToken();
            return scoped.getAccessToken();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void put(final String path, final Object object) {
        try {
            HttpFacade facade = new HttpFacade("https://lights-io.firebaseio.com" + path + ".json");
            facade.query("access_token", getToken());
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
