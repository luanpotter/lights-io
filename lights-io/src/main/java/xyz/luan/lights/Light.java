package xyz.luan.lights;

import io.yawp.repository.annotations.Endpoint;

@Endpoint(path = "/lights")
public class Light {

    private String desciption;

    private boolean state;

    public void toggle() {
        this.state = !this.state;
    }
}
