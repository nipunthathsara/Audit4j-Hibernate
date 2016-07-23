package org.audit4j.integration.hibernate.bootstrap;

import java.util.Map;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Startable;
import org.hibernate.service.spi.Stoppable;

public class AuditServiceImpl implements AuditService, Startable, Stoppable, Configurable {

    private boolean initialized;

    private boolean integrationEnabled;

    private boolean isConfigurationAvailable;

    @Override
    public void configure(Map configurationValues) {
        this.integrationEnabled = ConfigurationHelper.getBoolean(INTEGRATION_ENABLED,
                configurationValues, true);
    }

    @Override
    public void init() {
        if (initialized) {
            throw new UnsupportedOperationException("AuditService#init should be called only once");
        }
        initialized = true;
        if (isConfigurationAvailable) {
            AuditManager.startWithConfiguration(new Configuration());
        } else {
            AuditManager.start();
        }

    }
    
    @Override
    public void start() {
        init();
    }

    @Override
    public void stop() {
        initialized = false;
        AuditManager.shutdown();
    }

    @Override
    public boolean isEnabled() {
        return integrationEnabled;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public ServiceRegistry getServiceRegistry() {
        // TODO Auto-generated method stub
        return null;
    }
}
