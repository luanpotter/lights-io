package xyz.luan.lights;

import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;

import java.util.Map;

public class ToggleAction extends Action<Switch> {

    @PUT
    public Light toggle(IdRef<Switch> id, Map<String, String> params) {
        Light light = id.fetch().getLightId().fetch();
        if (params.containsKey("s")) {
            light.setState("on".equals(params.get("s")));
        } else {
            light.toggle();
        }
        FirebaseWrapper.put(light.getId().toString(), light.getState());
        return yawp.save(light);
    }
}
