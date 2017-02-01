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

import java.util.Map;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

/**
 * The Class AuditServiceInitiator.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
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
