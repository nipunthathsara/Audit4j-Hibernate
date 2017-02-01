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

import javax.persistence.Table;

import org.audit4j.core.AuditManager;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.dto.EventBuilder;
import org.audit4j.integration.hibernate.bootstrap.AuditService;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 * The Class AuditPostInsertEventListenerImpl.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditPostInsertEventListenerImpl extends BaseAuditEventListener
        implements PostInsertEventListener {

    /**
     * Instantiates a new audit post insert event listener impl.
     *
     * @param auditService the audit service
     */
    public AuditPostInsertEventListenerImpl(AuditService auditService) {
        super(auditService);
        System.out.println("listerner inoit");
    }

    public void init(){}
    /* (non-Javadoc)
     * @see org.hibernate.event.spi.PostInsertEventListener#onPostInsert(org.hibernate.event.spi.PostInsertEvent)
     */
    @Override
    public void onPostInsert(PostInsertEvent event) {
        // TODO
        // Repository check, get from Audit annotation, get from entoity table
        // name.

        if (event.getEntity().getClass().isAnnotationPresent(Audit.class)) {
            if (event.getEntity().getClass().isAnnotationPresent(Table.class)) {
                
            }
            AuditManager.getInstance().audit(new EventBuilder()
                    .addAction("save " + event.getEntity().getClass().toString())
                    .addField(event.getEntity().getClass().toString(), event.getEntity()).build());
        }
    }

    /* (non-Javadoc)
     * @see org.hibernate.event.spi.PostInsertEventListener#requiresPostCommitHanding(org.hibernate.persister.entity.EntityPersister)
     */
    @Override
    public boolean requiresPostCommitHanding(EntityPersister arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
