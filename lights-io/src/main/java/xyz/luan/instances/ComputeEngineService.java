package xyz.luan.instances;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.compute.Compute;
import xyz.luan.facade.HttpFacade;

import java.io.IOException;
import java.util.Collections;

public class ComputeEngineService {

	private static Compute.Instances instances;

	public static void instanceStart(Instance instance) throws IOException {
		instances().start(projectId(), instance.getZone(), instance.getName()).execute();
	}

	public static String instanceStatus(Instance instance) {
		try {
			String statusApi = instance.getIp() + ":8080/api/status";
			HttpFacade request = new HttpFacade(statusApi).timeout(3000);
			return request.get().content();
		} catch (IOException ex) {
			return "Off";
		}
	}

	public static String instanceStop(Instance instance) throws IOException {
		String statusApi = instance.getIp() + ":8080/api/stop";
		HttpFacade request = new HttpFacade(statusApi).timeout(3000);
		return request.post().content();
	}

	private static String projectId() {
		return "lights-io"; // TODO maybe get that from context/env
	}

	private static synchronized Compute.Instances instances() throws IOException {
		if (instances == null) {
			HttpTransport transport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
			GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(
				Collections.singletonList("https://www.googleapis.com/auth/compute")
			);
			Compute compute = new Compute.Builder(transport, jsonFactory, credential).build();
			instances = compute.instances();
		}
		return instances;
	}
}
