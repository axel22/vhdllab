/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.applets.simulations;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollBar;


/**
 * Panel sadrzi trenutne vrijednosti signala, ovisno o polozaju kursora
 *
 * @author Boris Ozegovic
 */
class SignalValuesPanel extends JPanel
{
	/** Prva vrijednosti pocinje od 30-tog piksela */
    private static final int YAXIS_START_POINT = 30;

    /** Svaka se vrijednost nalazi u springu (elasticnom pretincu) koji je visine 45 piksela */
	private static final int SIGNAL_NAME_SPRING_HEIGHT = 45;

    /** Maksimalna duljina koju panel moze poprimiti iznosi 150 piksela */
	private static final int PANEL_MAX_WIDTH = 650;

	/** Trenutni indeks na kojem je kursor, vrijednost na tom indeksu crta se u panelu */
    private int valueIndex;

	/** Sadrzi rezultate simulacije */
    private GhdlResults results; 
	
	/** Polozaj trenutnog springa */
    private int yAxis;
    
    /** Trenutni offset po Y-osi */
    private int offsetYAxis;

    /** Trenutni offset po X-osi */
    private int offsetXAxis;	

	/** Sirina panela s imenima signala */
    private int panelWidth;

	/** Makimalna duljina vektora, ukoliko postoje vektori */
    private int maximumVectorSize;

	/** Varijabla koja sadrzi informaciju je li trenutni signal oznacen misem */
    private boolean isClicked = false;

    /** Sadrzi indeks trenutno oznacenog signala misem */
    private int index = -1;

    /** Boje */
    private ThemeColor themeColor;

	/** ScrollBar */
	private JScrollBar scrollbar;

    /** SerialVersionUID */ 
    private static final long serialVersionUID = 9;


	 /**
     * Constructor
     *
	 * @param themeColor Sve raspolozive boje
     */
    public SignalValuesPanel (ThemeColor themeColor, JScrollBar scrollbar)
    {
		super();
		this.scrollbar = scrollbar;
		this.themeColor = themeColor;
	}


	/**
	 * Metoda postavlja novo stanje panela s vrijednostima signala u ovisnosti o 
	 * rezultatu kojeg vraca GhdlResult
	 *
	 * @param results rezultati koje parsira GhdlResult
	 */
	public void setContent(GhdlResults results) {
		this.results = results;
		this.maximumVectorSize = results.getMaximumVectorSize();
		this.panelWidth = this.maximumVectorSize * 6;
	}


	/**
     * Getter koji vraca preferirane dimenzije
     */
    @Override
    public Dimension getPreferredSize() 
    { 
        return new Dimension(panelWidth + 4, 
				results.getSignalNames().size() * SIGNAL_NAME_SPRING_HEIGHT); 
    } 


    /**
     * Getter koji vraca preferirane dimenzije ako je ime najvece duljine manje
     * od 650 piksela, inace vraca 650 piksela
     */
    @Override
    public Dimension getMaximumSize()
    {
        if (panelWidth < PANEL_MAX_WIDTH)
        {
            return new Dimension(panelWidth, 
					results.getSignalNames().size() * SIGNAL_NAME_SPRING_HEIGHT); 
        }

        return new Dimension(PANEL_MAX_WIDTH, results.getSignalNames().size() * 
				SIGNAL_NAME_SPRING_HEIGHT);
    }

	/**
     * Setter koji postavlja vertikalni offset
     *
     * @param offset Zeljeni novi offset
     */
	public void setVerticalOffset (int offset) 
    {
        this.offsetYAxis = offset;
    }


    /**
     * Vraca trenutni vertikalni offset
     */
    public int getVerticalOffset ()
    {
        return offsetYAxis;
    }


    /**
     * Setter koji postavlja horizontalni offset
     *
     *@param offset Zeljeni novi offset
     */
    public void setHorizontalOffset (int offset)
    {
        this.offsetXAxis = offset;
    }


	/**
     * Postavlja informaciju treba li oznaciti signal
     *
     * @param isClicked true or false
     */
    public void setIsClicked (boolean isClicked)
    {
        this.isClicked = isClicked;
    }


    /**
     * Postavlja trenutni indeks oznacenog signala 
     *
     * @param index indeks oznacenog signala
     */
    public void setIndex (int index)
    {
        this.index = index;
    }


    /**
     * Vraca informaciju je li signal oznacen
     *
     * @return true ako je kliknut, inace false
     */
    public boolean getIsClicked ()
    {
        return isClicked;
    }


    /**
     * Vraca indeks trenutno oznacenog signala
     *
     * @return Index kliknutog signala, inace vraca -1
     */
    public int getIndex ()
    {
        return index;
    }


    /**
     * Vraca visinu springa u kojoj je smjesteno ime signala 
     */
    public int getSignalNameSpringHeight ()
    {
        return SIGNAL_NAME_SPRING_HEIGHT;
    }


	/**
	 * Postavlja indeks vrijednosti na kojoj se trenutno nalazi kursor 
	 */
	public void setValueIndex (int valueIndex)
	{
		this.valueIndex = valueIndex;
	}


	/**
     * Postavlja novu vrijednosti sirine panela
     *
     * @param panelWidth nova vrijednost
     */
    public void setPanelWidth (int panelWidth)
    {
        this.panelWidth = panelWidth;
    }


    /**
     * Vraca sirinu panela
     */
    public int getPanelWidth ()
    {
        return panelWidth;
    }
 


	 /**
     * Crta komponentu
     *
     * @param g Graphics objekt
     */
    @Override
    public void paintComponent (Graphics g)
	{
		super.paintComponent(g);

		/* postavi vrijednost scrollbara */
		scrollbar.setMaximum(this.getMaximumSize().width);

        setBackground(themeColor.getSignalNames());
		/* ako treba oznaciti neki od signala */
        if (isClicked)
        {
            g.setColor(themeColor.getApplet());
            g.fillRect(0, index * SIGNAL_NAME_SPRING_HEIGHT + 15 - offsetYAxis, 
                    getMaximumSize().width, SIGNAL_NAME_SPRING_HEIGHT / 2 + 5);
        }
        g.setColor(themeColor.getLetters());
		yAxis = YAXIS_START_POINT - offsetYAxis;
		for (int i = 0; i < results.getSignalValues().size(); i++)
		{
			g.drawString(results.getSignalValues().get(i)[valueIndex], 5 - 
					offsetXAxis, yAxis);
			yAxis += SIGNAL_NAME_SPRING_HEIGHT;
		}
	}
}
