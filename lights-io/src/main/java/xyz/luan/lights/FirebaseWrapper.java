package xyz.luan.lights;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;

import java.io.*;

public class FirebaseWrapper {

    private static FirebaseApp app;

    private static FirebaseApp getApp() {
        if (app == null) {
            app = createApp();
        }
        return app;
    }

    private static FirebaseApp createApp() {
        try {
            InputStream serviceAccount = FirebaseWrapper.class.getResourceAsStream("/lights-io-key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://lights-io.firebaseio.com/")
                    .build();

            return FirebaseApp.initializeApp(options);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void put(final String path, final Object object) {
        FirebaseDatabase db = FirebaseDatabase.getInstance(getApp());
        db.getReference(path).setValue(object, (error, ref) -> {
            if (error != null) {
                System.err.println("There was a problem updating Firebase!");
                error.toException().printStackTrace();
            } else {
                System.out.println("Requested to Firebase succeeded!");
            }
        });
    }
}
