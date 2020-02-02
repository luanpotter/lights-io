package xyz.luan.lights;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import lombok.Data;

@Data
@Endpoint(path = "/lights")
public class Light {

    @Id
    private IdRef<Light> id;

    private String description;

    private boolean state;

    public void toggle() {
        this.state = !this.state;
    }
}
