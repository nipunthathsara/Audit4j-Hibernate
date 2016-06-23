/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberanateplugin.interceptor;

import java.io.Serializable;
import java.util.Iterator;

import org.audit4j.core.AuditManager;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.type.Type;

/**
 *User should configure AuditInterceptor at the Session level or Session Factory level.
 * @author T-NipunT
 */
public class AuditInterceptor {

    Session session;//should this be private?

    public void setSession(Session session) {
        this.session = session;
    }

    //method to create array of Field objects
    private static Field[] fieldSetter(String[] propertyNames, Object[] state, Type[] types) {
        Field[] fields = new Field[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            fields[i] = new Field(propertyNames[i], state[i].toString(), types[i].toString());
        }
        return fields;
    }

//    called on save
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
//        if (entity instanceof Auditable) {
//            
//        }
        System.out.println("onSave");
        Field[] fields = fieldSetter(propertyNames, state, types);
        IAuditManager manager = AuditManager.getInstance();
        manager.audit(new AuditEvent(null, "Saved", null, fields));

        return false;
    }

    //call on update
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        System.out.println("onFlushDirty");
        Field[] fields = fieldSetter(propertyNames, currentState, types);
        IAuditManager manager = AuditManager.getInstance();
        manager.audit(new AuditEvent(null, "updated", null, fields));
        return false;
    }

    //call on delete
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("onDelete");
        Field[] fields = fieldSetter(propertyNames, state, types);
        IAuditManager manager = AuditManager.getInstance();
        manager.audit(new AuditEvent(null, "deleted", null, fields));
    }

    //call on load
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Field[] fields = fieldSetter(propertyNames, state, types);
        IAuditManager manager = AuditManager.getInstance();
        manager.audit(new AuditEvent(null, "loaded", null, fields));
        return false;
    }

    //call before commiting
    public void preFlush(Iterator iterator) {
        System.out.println("preFlush");
    }

    //call after commiting
    public void postFlush(Iterator iterator) {
        System.out.println("postFlush");
    }

}
