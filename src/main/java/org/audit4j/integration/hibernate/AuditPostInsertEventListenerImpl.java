package org.audit4j.integration.hibernate;

import org.audit4j.core.dto.EventBuilder;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

public class AuditPostInsertEventListenerImpl implements PostInsertEventListener{

    @Override
    public void onPostInsert(PostInsertEvent event) {
        manager.audit(new EventBuilder().addAction("save " + event.getEntity().getClass().toString())
                .addField(event.getEntity().getClass().toString(), event.getEntity()).build());
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
