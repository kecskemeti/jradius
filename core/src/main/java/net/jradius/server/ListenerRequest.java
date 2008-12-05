/**
 * JRadius - A Radius Server Java Adapter
 * Copyright (C) 2004-2006 PicoPoint, B.V.
 * Copyright (C) 2006-2007 David Bird <david@coova.com>
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package net.jradius.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.jradius.exception.RadiusException;

/**
 * @author David Bird
 */
public abstract class ListenerRequest
{
    protected JRadiusEvent event;
    protected Listener listener;
    
    public ListenerRequest(Listener listener)
    {
        this.listener = listener;
    }
    
	public abstract InputStream getInputStream() throws IOException;

    public abstract OutputStream getOutputStream() throws IOException;

    public abstract Map<String, String> getServerVariables();

    public Listener getListener()
    {
        return listener;
    }

    public JRadiusEvent getEventFromListener() throws IOException, RadiusException
    {
        JRadiusEvent e = listener.parseRequest(this, getInputStream());
        if (e == null) return null;
        e.setListener(listener);
        return e;
    }
    
    public JRadiusEvent getRequestEvent() throws IOException, RadiusException
    {
        if (event == null)
            event = getEventFromListener();

        return event;
    }
}
