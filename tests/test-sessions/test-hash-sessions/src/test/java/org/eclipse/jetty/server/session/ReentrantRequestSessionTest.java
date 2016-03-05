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

import org.junit.Test;

/**
 * ReentrantRequestSessionTest
 */
public class ReentrantRequestSessionTest extends AbstractReentrantRequestSessionTest
{

    /** 
     * @see org.eclipse.jetty.server.session.AbstractReentrantRequestSessionTest#createServer(int, int, int, int, int)
     */
    @Override
    public AbstractTestServer createServer(int port, int max, int scavenge, int inspectionPeriod, int idlePassivatePeriod)
    {
        return new HashTestServer(port, max, scavenge, inspectionPeriod, idlePassivatePeriod);
    }

    @Test
    public void testReentrantRequestSession() throws Exception
    {
        super.testReentrantRequestSession();
    }

}
