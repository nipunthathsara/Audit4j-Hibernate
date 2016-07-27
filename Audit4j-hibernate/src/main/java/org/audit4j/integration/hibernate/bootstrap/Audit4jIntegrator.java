/*
 * This source is a part of Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.integration.hibernate.bootstrap;

import org.audit4j.core.exception.InitializationException;
import org.audit4j.integration.hibernate.listener.AuditPostDeleteEventListenerImpl;
import org.audit4j.integration.hibernate.listener.AuditPostInsertEventListenerImpl;
import org.audit4j.integration.hibernate.listener.AuditPostUpdateEventListenerImpl;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 * The Class Audit4jIntegrator.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jIntegrator implements Integrator {

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        final AuditService auditService = serviceRegistry.getService(AuditService.class);

        if (!auditService.isInitialized()) {
            throw new InitializationException(
                    "Audit4j hibernate integration can not be initialized..");
        }

        // Registor listeners..
        final EventListenerRegistry listenerRegistry = serviceRegistry
                .getService(EventListenerRegistry.class);

        listenerRegistry.appendListeners(EventType.POST_INSERT,
                new AuditPostInsertEventListenerImpl(auditService));
        listenerRegistry.appendListeners(EventType.POST_UPDATE,
                new AuditPostUpdateEventListenerImpl(auditService));
        listenerRegistry.appendListeners(EventType.POST_DELETE,
                new AuditPostDeleteEventListenerImpl(auditService));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hibernate.integrator.spi.Integrator#disintegrate(org.hibernate.engine
     * .spi.SessionFactoryImplementor,
     * org.hibernate.service.spi.SessionFactoryServiceRegistry)
     */
    @Override
    public void disintegrate(SessionFactoryImplementor arg0, SessionFactoryServiceRegistry arg1) {

    }

}
