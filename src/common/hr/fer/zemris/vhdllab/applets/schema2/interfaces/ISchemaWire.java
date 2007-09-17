package hr.fer.zemris.vhdllab.applets.schema2.interfaces;

import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Rect2d;
import hr.fer.zemris.vhdllab.applets.schema2.misc.WireSegment;
import hr.fer.zemris.vhdllab.applets.schema2.misc.XYLocation;

import java.util.List;
import java.util.Set;



/**
 * Sucelje koje opisuje bilo koju zicu (signal)
 * na shemi.
 * 
 * @author Axel
 *
 */
public interface ISchemaWire {
	
	/**
	 * Kljuc koji bi se trebao naci u svakoj kolekciji
	 * parametara.
	 */
	public static final String KEY_NAME = "Name";
	
	
	/**
	 * Virtualni copy konstruktor.
	 * Koristi se u ISchemaPrototypeCollection.
	 * 
	 * @returns
	 * Deep copy zadane zice.
	 * 
	 */
	ISchemaWire copyCtor();
	
	
	/**
	 * Vraca ime zice.
	 * 
	 * @return
	 * Jedinstveno ime zice za
	 * koje casing nije vazan.
	 */
	Caseless getName();
	

	/**
	 * Za dohvat parametara komponente,
	 * npr. imena komponente, kasnjenja
	 * komponente, itd.
	 * 
	 * @return
	 * Objekt navedenog tipa koji se ponasa kao
	 * string-object bazirana kolekcija.
	 */
	IParameterCollection getParameters();
	
	
	
	/**
	 * Vraca listu svih racvalista zice.
	 * 
	 * @return
	 * Lista koordinata svih racvalista.
	 * 
	 */
	List<XYLocation> getNodes();
	
	
	/**
	 * Vraca listu svih segmenata zice.
	 * 
	 * 
	 * @return
	 * Lista segmenata.
	 * 
	 */
	List<WireSegment> getSegments();
	
	
	/**
	 * Vraca objekt za
	 * iscrtavanje komponente.
	 * 
	 * @return
	 * @see IWireDrawer
	 */
	IWireDrawer getDrawer();
	
	
	/**
	 * Vraca minimalni pravokutnik unutar
	 * kojeg stane zica.
	 * 
	 * @return
	 * Bounding box, pravokutnik!
	 */
	Rect2d getBounds();
	
	
	/**
	 * Dodaje segment zice. Pritom
	 * po potrebi dodaje cvorove na
	 * mjesta gdje je to potrebno.
	 * 
	 * @param segment
	 * @return
	 * Vraca true ako je segment uspjesno dodan,
	 * a false u protivnom (npr. radi overlapa).
	 */
	boolean insertSegment(WireSegment segment);
	
	/**
	 * Brise iz liste segmenata navedeni segment.
	 * Pritom po potrebi mice cvorove koji postaju
	 * suvisni.
	 * 
	 * @param segment
	 * @return
	 * Vraca false ako segment nije naden.
	 */
	boolean removeSegment(WireSegment segment);
	
	/**
	 * Vraca segmente zice na danoj koordinati.
	 * @param x
	 * @param y
	 * @return
	 * Null ako nema segmenata na toj koordinati.
	 */
	Set<WireSegment> segmentsAt(int x, int y);
	
}







