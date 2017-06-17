package xyz.luan.lights;

import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;

public class SwitchShield extends Shield<Switch> {

    @Override
    public void defaults() {
        allow(false);
    }

    @Override
    public void show(IdRef<Switch> id) {
        allow();
    }

    @PUT
    public void toggle(IdRef<Switch> id) {
        allow();
    }
}
