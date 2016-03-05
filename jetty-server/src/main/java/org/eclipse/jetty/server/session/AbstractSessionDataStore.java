//
//  ========================================================================
//  Copyright (c) 1995-2016 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//


package org.eclipse.jetty.server.session;


import org.eclipse.jetty.util.component.AbstractLifeCycle;

/**
 * AbstractSessionDataStore
 *
 *
 */
public abstract class AbstractSessionDataStore extends AbstractLifeCycle implements SessionDataStore
{
    protected SessionContext _context; //context associated with this session data store


    /**
     * Store the session data persistently.
     * 
     * @param id identity of session to store
     * @param data info of the session
     * @param lastSaveTime time of previous save or 0 if never saved
     * @throws Exception
     */
    public abstract void doStore(String id, SessionData data, long lastSaveTime) throws Exception;

   

    
    /** 
     * @see org.eclipse.jetty.server.session.SessionDataStore#initialize(org.eclipse.jetty.server.session.SessionContext)
     */
    public void initialize (SessionContext context)
    {
        if (isStarted())
            throw new IllegalStateException("Context set after SessionDataStore started");
        _context = context;
    }

    /** 
     * @see org.eclipse.jetty.server.session.SessionDataStore#store(java.lang.String, org.eclipse.jetty.server.session.SessionData)
     */
    @Override
    public void store(String id, SessionData data) throws Exception
    {
        long lastSave = data.getLastSaved();
        
        //set the last saved time to now
        data.setLastSaved(System.currentTimeMillis());
        try
        {
            //call the specific store method, passing in previous save time
            doStore(id, data, lastSave);
            data.setDirty(false); //only undo the dirty setting if we saved it
        }
        catch (Exception e)
        {
            //reset last save time if save failed
            data.setLastSaved(lastSave);
            throw e;
        }
    }
    


    /** 
     * @see org.eclipse.jetty.server.session.SessionDataStore#newSessionData(java.lang.String, long, long, long, long)
     */
    @Override
    public SessionData newSessionData(String id, long created, long accessed, long lastAccessed, long maxInactiveMs)
    {
        return new SessionData(id, _context.getCanonicalContextPath(), _context.getVhost(), created, accessed, lastAccessed, maxInactiveMs);
    }
 
    /**
     * @throws IllegalStateException
     */
    protected void checkStarted () throws IllegalStateException
    {
        if (isStarted())
            throw new IllegalStateException("Already started");
    }




    @Override
    protected void doStart() throws Exception
    {
        if (_context == null)
            throw new IllegalStateException ("No SessionContext");
        
        super.doStart();
    }
    
    
    
}
