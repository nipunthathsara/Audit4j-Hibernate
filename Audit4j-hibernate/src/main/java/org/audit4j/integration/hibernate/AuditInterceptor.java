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

package org.audit4j.integration.hibernate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.audit4j.core.AuditManager;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.dto.EventBuilder;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * User should configure AuditInterceptor at the Session level or Session
 * Factory level.
 * 
 * @author T-NipunT
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = 2993840809041948451L;

    private Set<Object> inserts = new HashSet<>();
    private Set<Object> updates = new HashSet<>();
    private Set<Object> deletes = new HashSet<>();

    // called on save
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
            Type[] types) {
        if (entity.getClass().isAnnotationPresent(Audit.class)) {
            inserts.add(entity);
        }
        return false;
    }

    // call on update
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
            Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity.getClass().isAnnotationPresent(Audit.class)) {
            updates.add(entity);
        }
        return false;
    }

    // call on delete
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
            Type[] types) {
        if (entity.getClass().isAnnotationPresent(Audit.class)) {
            deletes.add(entity);
        }
    }

    // call after commiting
    public void postFlush(Iterator iterator) {
        IAuditManager manager = AuditManager.getInstance();
        try {
            for (Iterator it = inserts.iterator(); it.hasNext();) {
                manager.audit(new EventBuilder().addAction("save " + it.next().getClass().toString())
                        .addField(it.next().getClass().toString(), it.next()).build());
            }
            for (Iterator it = updates.iterator(); it.hasNext();) {
                manager.audit(
                        new EventBuilder().addAction("update " + it.next().getClass().toString())
                                .addField(it.next().getClass().toString(), it.next()).build());
            }
            for (Iterator it = deletes.iterator(); it.hasNext();) {
                manager.audit(
                        new EventBuilder().addAction("delete " + it.next().getClass().toString())
                                .addField(it.next().getClass().toString(), it.next()).build());
            }
        } finally {
            inserts.clear();
            updates.clear();
            deletes.clear();
        }
    }

}
