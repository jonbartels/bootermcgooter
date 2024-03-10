package com.jonbartels.bootermcgooter;

import com.mirth.connect.donkey.model.event.Event;
import com.mirth.connect.donkey.model.message.Message;
import com.mirth.connect.donkey.server.event.EventType;
import com.mirth.connect.model.ServerEvent;
import com.mirth.connect.model.ServerSettings;
import com.mirth.connect.server.controllers.ConfigurationController;
import com.mirth.connect.server.controllers.ControllerFactory;
import com.mirth.connect.server.event.EventListener;
import org.apache.commons.lang3.StringUtils;
import com.mirth.connect.client.core.ControllerException;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.debug("Processing event...");
        if (event instanceof ServerEvent) {
            ServerEvent serverEvent = (ServerEvent) event;
            log.error("Event is server event...");
            if (StringUtils.equalsIgnoreCase(serverEvent.getName(), "Server startup complete")) {
                log.debug("Event is startup event...");
                String mcServerName = System.getenv("MC_SERVER_NAME");
                String mcEnvName = System.getenv("MC_ENV_NAME");

                log.debug(MessageFormat.format("Read env vars. Env name {0}; Server name {1}", mcEnvName, mcServerName));

                try {
                    log.debug("updating server settings");
                    ConfigurationController configurationController = ControllerFactory.getFactory().createConfigurationController();

                    ServerSettings serverSettings = configurationController.getServerSettings();
                    serverSettings.setServerName(mcServerName);
                    serverSettings.setEnvironmentName(mcEnvName);
                    log.debug(MessageFormat.format("Built server settings {0}", serverSettings.toString()));

                    configurationController.setServerSettings(serverSettings);
                } catch (ControllerException controllerException){
                    log.error("Caught exception when updating info", controllerException);
                }
            }
        }
    }
}
