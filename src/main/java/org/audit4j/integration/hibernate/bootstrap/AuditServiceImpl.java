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

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.IAuditManager;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Startable;
import org.hibernate.service.spi.Stoppable;

/**
 * The Class AuditServiceImpl.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditServiceImpl implements AuditService, Startable, Stoppable, Configurable {

    /** The initialized. */
    private boolean initialized;
    
    /** The is configuration available. */
    private boolean isConfigurationAvailable;
    
    /* (non-Javadoc)
     * @see org.hibernate.service.spi.Configurable#configure(java.util.Map)
     */
    @Override
    public void configure(Map configurationValues) {
    }

    /* (non-Javadoc)
     * @see org.audit4j.integration.hibernate.bootstrap.AuditService#init()
     */
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
    
    /* (non-Javadoc)
     * @see org.hibernate.service.spi.Startable#start()
     */
    @Override
    public void start() {
        init();
    }

    /* (non-Javadoc)
     * @see org.hibernate.service.spi.Stoppable#stop()
     */
    @Override
    public void stop() {
        initialized = false;
        AuditManager.shutdown();
    }

    /* (non-Javadoc)
     * @see org.audit4j.integration.hibernate.bootstrap.AuditService#isInitialized()
     */
    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
