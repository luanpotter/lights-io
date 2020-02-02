package xyz.luan.instances;

import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.POST;
import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;
import org.apache.commons.lang3.StringUtils;

public class InstanceShield extends Shield<Instance> {

	@Override
	public void defaults() {
		allow(false);
	}

	@Override
	public void show(IdRef<Instance> id) {
		allowIfAuth(id);
	}

	@POST("start")
	public void start(IdRef<Instance> id) {
		allowIfAuth(id);
	}

	@POST("stop")
	public void stop(IdRef<Instance> id) {
		allowIfAuth(id);
	}

	private void allowIfAuth(IdRef<Instance> id) {
		Instance instance = id.fetch();

		String authKeyHeader = requestContext.req().getHeader("Instance-Auth-Key");
		if (StringUtils.isEmpty(authKeyHeader)) {
			throw new HttpException(401, "Header Instance-Auth-Key is required.");
		}
		if (!authKeyHeader.equals(instance.getAuthKey())) {
			throw new HttpException(401, "Wrong Instance-Auth-Key provided.");
		}
		if (!instance.isEnabled()) {
			throw new HttpException(401, "The requested instance is disabled and cannot be accessed.");
		}

		// else
		allow(true);
	}
}
