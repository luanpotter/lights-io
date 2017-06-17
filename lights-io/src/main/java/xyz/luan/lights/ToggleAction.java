package xyz.luan.lights;

import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;

public class ToggleAction extends Action<Switch> {

    @PUT
    public Light toggle(IdRef<Switch> id) {
        Light light = id.fetch().getLightId().fetch();
        light.toggle();
        return yawp.save(light);
    }
}
