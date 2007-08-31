package hr.fer.zemris.vhdllab.applets.schema2.model.queries;

import hr.fer.zemris.vhdllab.applets.schema2.constants.Constants;
import hr.fer.zemris.vhdllab.applets.schema2.enums.EPropertyChange;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IQuery;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IQueryResult;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaInfo;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaWire;
import hr.fer.zemris.vhdllab.applets.schema2.misc.PlacedComponent;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Rect2d;
import hr.fer.zemris.vhdllab.applets.schema2.misc.WireSegment;
import hr.fer.zemris.vhdllab.applets.schema2.misc.XYLocation;
import hr.fer.zemris.vhdllab.applets.schema2.model.QueryResult;
import hr.fer.zemris.vhdllab.applets.schema2.model.queries.misc.WalkabilityMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 * Analizira citav schematic i stvara mapu prolaznosti, koja se kasnije
 * moze koristiti npr. u algoritmu za trazenje najkracih puteva za zice.
 * 
 * Prolaznost je pohranjena kao WalkabilityMap pod kljucem KEY_WALKABILITY.
 * 
 * @author Axel
 *
 */
public class InspectWalkability implements IQuery {
	
	/* static fields */
	public static final String KEY_WALKABILITY = "walkability_map";
	private static final String QUERY_NAME = InspectWalkability.class.getSimpleName();
	private static final int STEP = Constants.GRID_SIZE;
	private static final Integer ALLUNWALKABLE = 0;
	private static final Integer HORIZONTALLY_WALKABLE = WalkabilityMap.FROM_EAST | WalkabilityMap.FROM_WEST;
	private static final Integer VERTICALLY_WALKABLE = WalkabilityMap.FROM_NORTH | WalkabilityMap.FROM_SOUTH;
	private static final List<EPropertyChange> propdepend = new ArrayList<EPropertyChange>();
	private static final List<EPropertyChange> ro_pd = Collections.unmodifiableList(propdepend);	
	{
		propdepend.add(EPropertyChange.CANVAS_CHANGE);
		propdepend.add(EPropertyChange.PROPERTY_CHANGE);
	}
	
	
	/* private fields */
	private boolean quick;
	
	
	/* ctors */

	/**
	 * Standardni konstruktor.
	 * @param quickinspect
	 * Ako se ovaj parametar postavi u true,
	 * query nece postavljati sve cvorove na mapi prolaznosti
	 * u false za pojedinu komponentu, vec samo za njene rubove.
	 * Ovo je prihvatljivo za algoritme trazenja puteva koji idu
	 * cvor po cvor, a uvelike smanjuje slozenost.
	 */
	public InspectWalkability(boolean quickinspect) {
		quick = quickinspect;
	}
	
	
	
	/* methods */	

	public List<EPropertyChange> getPropertyDependency() {
		return ro_pd;
	}

	public String getQueryName() {
		return QUERY_NAME;
	}

	public boolean isCacheable() {
		return true;
	}

	public IQueryResult performQuery(ISchemaInfo info) {
		int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE;
		int maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
		WalkabilityMap walkability = new WalkabilityMap();
		
		/* iterate through components and map unwalkable grid points */
		for (PlacedComponent placed : info.getComponents()) {
			/* update bounds */
			int tx = placed.pos.x + placed.comp.getWidth(), ty = placed.pos.y + placed.comp.getHeight();
			if (placed.pos.x < minx) minx = placed.pos.x;
			if (placed.pos.y < miny) miny = placed.pos.y;
			if (tx > maxx) maxx = tx;
			if (ty > maxy) maxy = ty;
			
			/* handle walkability */
			if (quick) quickHandleCmp(placed, tx, ty, walkability);
			else handleCmp(placed, tx, ty, walkability);
		}
		
		/* iterate through wires and map unwalkable grid points */
		for (ISchemaWire wire : info.getWires()) {
			/* update bounds */
			Rect2d wb = wire.getBounds();
			if (wb.left < minx) minx = wb.left;
			if (wb.top < miny) miny = wb.top;
			if ((wb.left + wb.width) > maxx) maxx = wb.left + wb.width;
			if ((wb.top + wb.height) > maxy) maxy = wb.top + wb.height;
			
			/* handle walkability */
			handleWire(wire, walkability);
		}
		
		/* set bounds to walkability map */
		walkability.width = ((maxx - minx) / STEP + 1) * STEP;
		walkability.height = ((maxy - miny) / STEP + 1) * STEP;
		
		return new QueryResult(KEY_WALKABILITY, walkability);
	}
	
	private static void handleCmp(PlacedComponent placed, int tox, int toy, WalkabilityMap walkability) {
		int fromx = placed.pos.x, fromy = placed.pos.y;
		fromx = (fromx / STEP + (((fromx % STEP) == 0 || fromx < 0) ? (0) : (1))) * STEP;
		fromy = (fromy / STEP + (((fromy % STEP) == 0 || fromy < 0) ? (0) : (1))) * STEP;
		
		for (int i = fromx; i <= tox; i += STEP) {
			for (int j = fromy; j <= toy; j += STEP) {
				walkability.walkmap.put(new XYLocation(i, j), ALLUNWALKABLE);
			}
		}
	}
	
	private static void quickHandleCmp(PlacedComponent placed, int tox, int toy, WalkabilityMap walkability) {
		int fromx = placed.pos.x, fromy = placed.pos.y;
		fromx = (fromx / STEP + (((fromx % STEP) == 0 || fromx < 0) ? (0) : (1))) * STEP;
		fromy = (fromy / STEP + (((fromy % STEP) == 0 || fromy < 0) ? (0) : (1))) * STEP;
		tox = (tox / STEP + (((tox % STEP) == 0 || tox > 0) ? (0) : (-1))) * STEP;
		toy = (toy / STEP + (((toy % STEP) == 0 || toy > 0) ? (0) : (-1))) * STEP;
		
		for (int i = fromx; i <= tox; i += STEP) {
			walkability.walkmap.put(new XYLocation(i, fromy), ALLUNWALKABLE);
			walkability.walkmap.put(new XYLocation(i, toy), ALLUNWALKABLE);
		}
		toy--; fromy++;
		for (int j = fromy; j <= toy; j += STEP) {
			walkability.walkmap.put(new XYLocation(j, fromx), ALLUNWALKABLE);
			walkability.walkmap.put(new XYLocation(j, tox), ALLUNWALKABLE);
		}
	}
	
	private static void handleWire(ISchemaWire wire, WalkabilityMap walkability) {
		for (WireSegment segment : wire.getSegments()) {
			XYLocation start = segment.getStart(), end;
			
			/* check if vertical */
			if (segment.isVertical()) {
				/* check if it is aligned to grid */
				if (start.x % STEP != 0) continue;
				
				/* handle it */
				end = segment.getEnd();
				int fromy = (start.y / STEP + (((start.y % STEP) == 0 || start.y < 0) ? (0) : (1))) * STEP;
				int toy = (end.y / STEP + (((end.y % STEP) == 0 || end.y > 0) ? (0) : (-1))) * STEP;
				for (int j = fromy; j <= toy; j += STEP) {
					walkability.walkmap.put(new XYLocation(start.x, j), HORIZONTALLY_WALKABLE);
				}
			} else {
				/* check if it is aligned to grid */
				if (start.y % STEP != 0) continue;
				
				/* handle it */
				end = segment.getEnd();
				int fromx = (start.x / STEP + (((start.x % STEP) == 0 || start.x < 0) ? (0) : (1))) * STEP;
				int tox = (end.x / STEP + (((end.x % STEP) == 0 || end.x > 0) ? (0) : (-1))) * STEP;
				for (int i = fromx; i <= tox; i += STEP) {
					walkability.walkmap.put(new XYLocation(i, start.y), VERTICALLY_WALKABLE);
				}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof InspectWalkability)) return false;
		InspectWalkability other = (InspectWalkability)obj;
		return other.quick == this.quick;
	}

	@Override
	public int hashCode() {
		return (quick) ? (2) : (3);
	}
	
	
	
	

}














