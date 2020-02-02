package xyz.luan.instances;

import io.yawp.repository.transformers.Transformer;

import java.util.Map;

public class InstanceTransformer extends Transformer<Instance> {

	@Override
	public Object defaults(Instance instance) {
		Map<String, Object> map = asMap(instance);
		map.put("status", ComputeEngineService.instanceStatus(instance));
		return map;
	}
}
