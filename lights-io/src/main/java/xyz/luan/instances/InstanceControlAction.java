package xyz.luan.instances;

import io.yawp.commons.http.annotation.POST;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;

import java.io.IOException;

public class InstanceControlAction extends Action<Instance> {

	@POST("start")
	public void start(IdRef<Instance> id) throws IOException {
		ComputeEngineService.startInstance(id.fetch());
	}

	@POST("stop")
	public void stop(IdRef<Instance> id) {
		throw new RuntimeException("Not implemented yet!");
	}
}
