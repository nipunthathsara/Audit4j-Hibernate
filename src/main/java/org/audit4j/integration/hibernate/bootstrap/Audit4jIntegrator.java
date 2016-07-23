package org.audit4j.integration.hibernate.bootstrap;

import org.audit4j.core.exception.InitializationException;
import org.audit4j.integration.hibernate.AuditPostInsertEventListenerImpl;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class Audit4jIntegrator implements Integrator {

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        final AuditService auditService = serviceRegistry.getService(AuditService.class);

        if (!auditService.isEnabled()) {
            return;
        }

        if (!auditService.isInitialized()) {
            throw new InitializationException(
                    "Audit4j hibernate integration can not be initialized..");
        }

        // Registor listeners..
        final EventListenerRegistry listenerRegistry = serviceRegistry
                .getService(EventListenerRegistry.class);

        listenerRegistry.appendListeners(EventType.POST_INSERT,
                new AuditPostInsertEventListenerImpl());

    }

    @Override
    public void disintegrate(SessionFactoryImplementor arg0, SessionFactoryServiceRegistry arg1) {
        // TODO Auto-generated method stub

    }

}
