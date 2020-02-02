package xyz.luan.instances;

import io.yawp.commons.http.annotation.POST;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;

import java.io.IOException;

public class InstanceControlAction extends Action<Instance> {

	@POST("start")
	public String start(IdRef<Instance> id) throws IOException {
		ComputeEngineService.startInstance(id.fetch());
		return "Instance started.";
	}

	@POST("stop")
	public String stop(IdRef<Instance> id) {
		throw new RuntimeException("Not implemented yet!");
		// return "Instance stopped.";
	}
}
