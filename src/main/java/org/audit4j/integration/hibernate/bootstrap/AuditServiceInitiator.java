package org.audit4j.integration.hibernate.bootstrap;

import java.util.Map;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import sun.audio.AudioDevice;

/**
 * The Class AuditServiceInitiator.
 */
public class AuditServiceInitiator implements StandardServiceInitiator<AuditService> {

    public static final AuditServiceInitiator INSTANCE = new AuditServiceInitiator();
    
    /* (non-Javadoc)
     * @see org.hibernate.service.spi.ServiceInitiator#getServiceInitiated()
     */
    @Override
    public Class<AuditService> getServiceInitiated() {
        return AuditService.class;
    }

    /* (non-Javadoc)
     * @see org.hibernate.boot.registry.StandardServiceInitiator#initiateService(java.util.Map, org.hibernate.service.spi.ServiceRegistryImplementor)
     */
    @Override
    public AuditService initiateService(Map arg0, ServiceRegistryImplementor arg1) {
        return new AuditServiceImpl();
    }
}
