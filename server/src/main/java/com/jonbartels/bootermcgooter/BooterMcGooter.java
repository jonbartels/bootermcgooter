package com.jonbartels.bootermcgooter;

import com.kaurpalang.mirth.annotationsplugin.annotation.MirthServerClass;
import com.mirth.connect.donkey.model.event.Event;
import com.mirth.connect.donkey.server.event.EventType;
import com.mirth.connect.plugins.ServerPlugin;
import com.mirth.connect.server.controllers.ControllerFactory;
import com.mirth.connect.server.controllers.EventController;
import com.mirth.connect.server.event.EventListener;
import com.mirth.connect.model.ServerEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Set;

@MirthServerClass
public class BooterMcGooter implements ServerPlugin {
    private EventController eventController = ControllerFactory.getFactory().createEventController();
    private final EventHandler eventHandler = new EventHandler();
    @Override
    public String getPluginPointName() {
        return "Booter McGooter";
    }

    @Override
    public void start() {
        eventController.addListener(eventHandler);
    }

    @Override
    public void stop() {
        eventController.removeListener(eventHandler);
    }
}