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
package de.nomagic.printerController.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class PrinterStatusPanel
{
    private final static String OFFLINE_MESSAGE = "Status Information not available !";

    private final JPanel myPanel = new JPanel();
    private final JLabel statusLabel = new JLabel(OFFLINE_MESSAGE);

    public PrinterStatusPanel()
    {
        myPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Printer Status"));
        myPanel.add(statusLabel, BorderLayout.CENTER);
    }

    public Component getPanel()
    {
        return myPanel;
    }

    public void setToOffline()
    {
        statusLabel.setText(OFFLINE_MESSAGE);
    }

    public void setToOnline()
    {
        statusLabel.setText("Connected to Pacemaker client !");
    }

}
