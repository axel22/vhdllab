package hr.fer.zemris.vhdllab.simulations;

import java.awt.Graphics;


/**
 * Predstavlja cisti heksagon.  Dolazi na pocetku ili iza heksagona
 *
 * @author Boris Ozegovic
 */
class Shape7 implements Shape
{
	public void draw (Graphics g, int x1, int y1, int x2)
	{
        if (x2 - 3 > x1 + 3)
        {
            g.drawLine(x1, y1 + 10, x1 + 3, y1);
            g.drawLine(x1, y1 + 10, x1 + 3, y1 + 20);
            g.drawLine(x2, y1 + 10, x2 - 3, y1);
            g.drawLine(x2, y1 + 10, x2 - 3, y1 + 20);
            g.drawLine(x1 + 3, y1, x2 - 3, y1);
            g.drawLine(x1 + 3, y1 + 20, x2 - 3, y1 + 20);
        }

        /* 
         * kada je trajanje heksagona premalo, i postaje sve manje kako se
         * srolla, heksagon ostaje spojen
         */
        else
        {
            g.drawLine(x1, y1 + 10, x2 - 3, y1);
            g.drawLine(x1, y1 + 10, x2 - 3, y1 + 20);
            g.drawLine(x2, y1 + 10, x2 - 3, y1);
            g.drawLine(x2, y1 + 10, x2 - 3, y1 + 20);
        }
            
    }


	public void putLabel (Graphics g, String string, int x1, int y1, int x2)
	{
		int length = x2 - x1;
		int middle = length / 2;
		int startPoint = x1 + (middle - (string.length() * 7) / 2);

        /* ako je trajanje premaleno, ne crtaj string unutar heksagona */
		if (length > string.length() * 8 && length > 10)
        {
            g.drawString(string, startPoint, y1 + 14);
        }
	}
}
