/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>
 *
 */
package de.nomagic.printerController.Interface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 *
 */
public class TcpInterface extends InteractiveInterface
{

    public TcpInterface()
    {
    }

    public void run()
    {
        ServerSocket welcomeSocket;
        try
        {
            welcomeSocket = new ServerSocket(2342);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        while(false == isInterrupted())
        {
            try
            {
                Socket connectionSocket = welcomeSocket.accept();
                in = connectionSocket.getInputStream();
                out = connectionSocket.getOutputStream();
                readFromStreams();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            welcomeSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
