package com.jonbartels.bootermcgooter;

import com.mirth.connect.donkey.model.event.Event;
import com.mirth.connect.donkey.server.event.EventType;
import com.mirth.connect.model.ServerEvent;
import com.mirth.connect.model.ServerSettings;
import com.mirth.connect.server.controllers.ConfigurationController;
import com.mirth.connect.server.controllers.ControllerFactory;
import com.mirth.connect.server.event.EventListener;
import org.apache.commons.lang3.StringUtils;
import com.mirth.connect.client.core.ControllerException;

import java.util.Collections;
import java.util.Set;

public class EventHandler extends EventListener {
    @Override
    protected void onShutdown() {

    }

    @Override
    public Set<EventType> getEventTypes() {
        return Collections.singleton(EventType.SERVER);
    }

    @Override
    protected void processEvent(Event event) {
        //TODO is this if statement necessary?
        //TODO the event pipeline should only send me events I am interested in from getEventTypes
        //I guess we have to have it to avoid an unchecked cast warning
        if (event instanceof ServerEvent) {
            ServerEvent serverEvent = (ServerEvent) event;
            if (StringUtils.equalsIgnoreCase(serverEvent.getName(), "Server startup complete")) {
                String mcServerName = System.getenv("MC_SERVER_NAME");
                String mcEnvName = System.getenv("MC_ENV_NAME");

                ServerSettings serverSettings = new ServerSettings(mcEnvName, mcServerName, null);
                ConfigurationController configurationController = ControllerFactory.getFactory().createConfigurationController();
                try {
                    configurationController.setServerSettings(serverSettings);
                } catch (ControllerException controllerException){
                    //TODO log something
                }
            }
        }
    }
}
