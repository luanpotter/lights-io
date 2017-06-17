package xyz.luan.lights;

import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;

public class LightShield extends Shield<Light> {

    @Override
    public void defaults() {
        allow(false);
    }

    @Override
    public void show(IdRef<Light> id) {
        allow();
    }

    @Override
    public void index(IdRef<?> parentId) {
        allow();
    }
}
