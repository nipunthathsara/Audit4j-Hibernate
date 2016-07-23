package org.audit4j.integration.hibernate.bootstrap;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.spi.ServiceContributor;

public class AuditServiceContributor implements ServiceContributor{

    @Override
    public void contribute(StandardServiceRegistryBuilder serviceRegistryBuilder) {
        serviceRegistryBuilder.addInitiator( AuditServiceInitiator.INSTANCE );
    }

}
