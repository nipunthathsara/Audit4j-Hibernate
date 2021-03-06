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

package org.audit4j.integration.hibernate.listener;

import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;
import org.audit4j.integration.hibernate.bootstrap.AuditService;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 * The Class AuditPostDeleteEventListenerImpl.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditPostDeleteEventListenerImpl extends BaseAuditEventListener implements PostDeleteEventListener{

    /**
     * Instantiates a new audit post delete event listener impl.
     *
     * @param auditService the audit service
     */
    public AuditPostDeleteEventListenerImpl(AuditService auditService) {
        super(auditService);
    }

    /* (non-Javadoc)
     * @see org.hibernate.event.spi.PostDeleteEventListener#onPostDelete(org.hibernate.event.spi.PostDeleteEvent)
     */
    @Override
    public void onPostDelete(PostDeleteEvent event) {
        AuditManager.getInstance().audit(new EventBuilder()
                .addAction("delete " + event.getEntity().getClass().toString())
                .addField(event.getEntity().getClass().toString(), event.getEntity()).build());
    }

    /* (non-Javadoc)
     * @see org.hibernate.event.spi.PostDeleteEventListener#requiresPostCommitHanding(org.hibernate.persister.entity.EntityPersister)
     */
    @Override
    public boolean requiresPostCommitHanding(EntityPersister arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
