package xyz.luan.lights;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;

@Endpoint(path = "/switches")
public class Switch {

    @Id
    private IdRef<Switch> id;

    private IdRef<Light> lightId;

    public IdRef<Light> getLightId() {
        return lightId;
    }
}
