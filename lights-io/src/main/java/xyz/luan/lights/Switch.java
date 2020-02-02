package xyz.luan.lights;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import lombok.Data;

@Data
@Endpoint(path = "/switches")
public class Switch {

    @Id
    private IdRef<Switch> id;

    private String description;

    private IdRef<Light> lightId;
}
