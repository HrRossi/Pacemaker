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
package de.nomagic.printerController.printerConfigurationCreationWizard;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.nomagic.Translator.Translator;
import de.nomagic.WizardDialog.OneNextWizardSlide;

/** Welcomes the user and explains what this is about.
 *
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class WelcomeSlide extends OneNextWizardSlide
{
    private JPanel slide = new JPanel();
    private JTextArea welcomeMessage = new JTextArea();

    public WelcomeSlide(Translator t)
    {
        welcomeMessage.setText(t.t("Welcome_Headline") + "\n\n"
                             + t.t("Welcome_Text"));
        welcomeMessage.setEditable(false);
        slide.add(welcomeMessage);
    }

    @Override
    public String getName()
    {
        return "WelcomeSlide";
    }

    @Override
    public Component getComponent()
    {
        return slide;
    }
}
