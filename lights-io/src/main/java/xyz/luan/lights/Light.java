package xyz.luan.lights;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;

@Endpoint(path = "/lights")
public class Light {

    @Id
    private IdRef<Light> id;

    private String description;

    private boolean state;

    public void toggle() {
        this.state = !this.state;
    }

    public IdRef<Light> getId() {
        return id;
    }

    public boolean getState() {
        return state;
    }
}
