package com.jonbartels.bootermcgooter;

import com.kaurpalang.mirth.annotationsplugin.annotation.MirthServerClass;
import com.mirth.connect.plugins.ServerPlugin;
import com.mirth.connect.server.controllers.ControllerFactory;
import com.mirth.connect.server.controllers.EventController;

import lombok.extern.slf4j.Slf4j;
@MirthServerClass
@Slf4j
public class BooterMcGooter implements ServerPlugin {
    private EventController eventController = ControllerFactory.getFactory().createEventController();

    private final EventHandler eventHandler = new EventHandler();

    public BooterMcGooter() {
        log.info("Instantiating. Adding listener..");
        eventController.addListener(eventHandler);
        log.info("Added listener. Instantiated.");
    }

    @Override
    public String getPluginPointName() {
        return "Booter McGooter";
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}