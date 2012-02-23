/*
 * ====================================================================
 *
 * This code is subject to the freebxml License, Version 1.1
 *
 * Copyright (c) 2001 - 2003 freebxml.org.  All rights reserved.
 *
 * $Header: /cvsroot/ebxmlrr/omar/src/java/org/freebxml/omar/server/persistence/rdb/DescriptionDAO.java,v 1.14 2005/11/21 04:27:52 farrukh_najmi Exp $
 * ====================================================================
 */
package org.freebxml.omar.server.persistence.rdb;

import javax.xml.bind.JAXBException;

import javax.xml.registry.RegistryException;
import org.freebxml.omar.server.common.ServerRequestContext;
import org.oasis.ebxml.registry.bindings.rim.Description;


class DescriptionDAO extends InternationalStringDAO {
    
    /**
     * Use this constructor only.
     */
    DescriptionDAO(ServerRequestContext context) {
        super(context);
    }
    
    public String getTableName() {
        return "Description";
    }

    Description getDescriptionByParent(String parentId)
        throws RegistryException {
        return ((Description) super.getInternationalStringByParent(parentId));
    }
    
    /**
     * Creates an unitialized binding object for the type supported by this DAO.
     */
    Object createObject() throws JAXBException {
        Description obj = bu.rimFac.createDescription();
        
        return obj;
    }
    
}
