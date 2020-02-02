package xyz.luan.instances;

import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.POST;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;

import java.io.IOException;

public class InstanceControlAction extends Action<Instance> {

	@POST("start")
	public String start(IdRef<Instance> id) {
		Instance instance = id.fetch();
		try {
			ComputeEngineService.instanceStart(instance);
			return "Instance started.";
		} catch (IOException e) {
			System.err.println("Exception while stopping the instance " + instance.getName());
			e.printStackTrace();
			throw new HttpException(500, e.getMessage());
		}
	}

	@POST("stop")
	public String stop(IdRef<Instance> id) {
		Instance instance = id.fetch();
		try {
			return ComputeEngineService.instanceStop(instance);
		} catch (IOException e) {
			System.err.println("Exception while stopping the instance " + instance.getName());
			e.printStackTrace();
			throw new HttpException(500, e.getMessage());
		}
	}
}
