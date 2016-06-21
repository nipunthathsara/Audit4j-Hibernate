/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberanateplugin.interceptor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.audit4j.core.AuditManager;//nipun
import org.audit4j.core.dto.AuditEvent;
/**
 *
 * @author T-NipunT
 */
public class AuditLogInterceptor {
        Session session;
	private Set inserts = new HashSet();
	private Set updates = new HashSet();
	private Set deletes = new HashSet();
        
        private Set fields = new HashSet();//nipun
	
	public void setSession(Session session) {//set interceptor to the session
		this.session=session;
	}
		
	public boolean onSave(Object entity,Serializable id,
		Object[] state,String[] propertyNames,Type[] types)
		throws CallbackException {
		
		System.out.println("onSave");
                for(int i=0; i <= propertyNames.length;i++)//nipun
                    fields.add(new Field(String propertyNames[i], String entity.propertyNames[i], String types[i]))//nipun
		
		if (entity instanceof IAuditLog){
			inserts.add(entity);
		}
		return false;
			
	}
	
	public boolean onFlushDirty(Object entity,Serializable id,
		Object[] currentState,Object[] previousState,
		String[] propertyNames,Type[] types)
		throws CallbackException {
	
		System.out.println("onFlushDirty");
		
		if (entity instanceof IAuditLog){
			updates.add(entity);
		}
		return false;
		
	}
	
	public void onDelete(Object entity, Serializable id,
		Object[] state, String[] propertyNames, 
		Type[] types) {
		
		System.out.println("onDelete");
		
		if (entity instanceof IAuditLog){
			deletes.add(entity);
		}
	}

	//called before commit into database
	public void preFlush(Iterator iterator) {
		System.out.println("preFlush");
	}	
	
	//called after committed into database
	public void postFlush(Iterator iterator) {
		System.out.println("postFlush");
		
		try{
		
			for (Iterator it = inserts.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - insert");
				AuditLogUtil.LogIt("Saved",entity, session.connection());
                                AuditManager.audit(new AuditEvent(null,"Saved",null,))
			}	
			
			for (Iterator it = updates.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - update");
				AuditLogUtil.LogIt("Updated",entity, session.connection());
			}	
			
			for (Iterator it = deletes.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				System.out.println("postFlush - delete");
				AuditLogUtil.LogIt("Deleted",entity, session.connection());
			}	
			
		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}
}
