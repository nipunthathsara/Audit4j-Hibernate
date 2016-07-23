package org.audit4j.integration.hibernate.bootstrap;

import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

public interface AuditService extends Service{

    String INTEGRATION_ENABLED = "hibernate.integration.audit4j.enabled";

    boolean isEnabled();

    boolean isInitialized();
    
    ServiceRegistry getServiceRegistry();

    void init();
}
