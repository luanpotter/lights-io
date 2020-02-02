package xyz.luan.instances;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import io.yawp.repository.annotations.Index;
import lombok.Data;

@Data
@Endpoint(path = "/instances")
public class Instance {

	@Id
	private IdRef<Instance> id;

	@Index
	private String name;

	private String zone;

	private String authKey;

	private String ip;

	@Index
	private boolean enabled;
}
