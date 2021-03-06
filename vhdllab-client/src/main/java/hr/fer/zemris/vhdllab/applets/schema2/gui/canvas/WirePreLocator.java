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
package hr.fer.zemris.vhdllab.applets.schema2.gui.canvas;

import hr.fer.zemris.vhdllab.applets.editor.schema2.constants.Constants;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.ICommand;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.ICommandResponse;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.ISchemaController;
import hr.fer.zemris.vhdllab.applets.editor.schema2.misc.Caseless;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.commands.AddWireCommand;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.commands.BindWireCommand;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.commands.ExpandWireCommand;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.commands.PlugWireCommand;

import java.awt.Graphics2D;

public class WirePreLocator implements IWirePreLocator {

	public static int HORIZ_FIRST = 0;
	public static int VERT_FIRST = 1;
	
	private int x1;
	private int y1;
	
	private int x2;
	private int y2;
	
	private int orientation;
	
	private int devition1;
	private int deviation2;
	
	private boolean wireInstantiable = true;
	
	public WirePreLocator(int x1, int y1, int x2, int y2, int orientation, int odm1, int odm2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		this.devition1 = odm1;
		this.deviation2 = odm2;
		
		this.orientation = orientation;
	}
	
	public WirePreLocator() {
		this(0,0,0,0,0,0,0);
	}
	
	public WirePreLocator(int x1, int y1, int x2, int y2){
		this(x1,y1,x2,y2,0,0,0);
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getOdmak1()
	 */
	public int getOdmak1() {
		return devition1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setOdmak1(int)
	 */
	public void setOdmak1(int odmak1) {
		this.devition1 = odmak1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getOdmak2()
	 */
	public int getOdmak2() {
		return deviation2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setOdmak2(int)
	 */
	public void setOdmak2(int odmak2) {
		this.deviation2 = odmak2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getOrientation()
	 */
	public int getOrientation() {
		return orientation;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setOrientation(int)
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getX1()
	 */
	public int getX1() {
		return x1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setX1(int)
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getX2()
	 */
	public int getX2() {
		return x2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setX2(int)
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getY1()
	 */
	public int getY1() {
		return y1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setY1(int)
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#getY2()
	 */
	public int getY2() {
		return y2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setY2(int)
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#instantiateWire(hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.ISchemaController, hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.CriticalPoint, hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.CriticalPoint)
	 */
	public void instantiateWire(ISchemaController controller, CriticalPoint wireBeginning, CriticalPoint wireEnding) {
		//TODO srediti za razlicit orientation i odmak ako ce to uopce postojati
		if((wireBeginning == null || wireBeginning.getType()==CriticalPoint.ON_COMPONENT_PLUG) &&
				(wireEnding == null || wireEnding.getType() == CriticalPoint.ON_COMPONENT_PLUG)){
			addWire(controller, wireBeginning, wireEnding);
		}else{
			if(wireBeginning == null || wireBeginning.getType() == CriticalPoint.ON_COMPONENT_PLUG)
				expandWire(controller, wireEnding, wireBeginning);
			else
				expandWire(controller, wireBeginning, wireEnding);
		}
	}

	private void expandWire(ISchemaController controller, CriticalPoint wireBeginning, CriticalPoint wireEnding) {
		Caseless wireName = wireBeginning.getName();
		if(orientation == VERT_FIRST){
			if (x1 != x2 && y1 != y2) {
				ICommand instantiate = new ExpandWireCommand(wireName,x1,y1,x1,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());

				instantiate = new ExpandWireCommand(wireName,x1,y2,x2,y2);
				response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
			else{
				ICommand instantiate = new ExpandWireCommand(wireName,x1,y1,x2,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
		}else{
			if (x1 != x2 && y1 != y2) {
				ICommand instantiate = new ExpandWireCommand(wireName,x1,y1,x2,y1);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());

				instantiate = new ExpandWireCommand(wireName,x2,y1,x2,y2);
				response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
			else{
				ICommand instantiate = new ExpandWireCommand(wireName,x1,y1,x2,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
		}
		plugToPoint(wireBeginning,controller,wireName);
		plugToPoint(wireEnding,controller,wireName);		
	}

	private void addWire(ISchemaController controller, CriticalPoint wireBeginning, CriticalPoint wireEnding) {
		Caseless wireName = null;
		if(orientation == VERT_FIRST){
			if (x1 != x2 && y1 != y2) {
				wireName = createName(x1,y1,x1,y2);
				ICommand instantiate = new AddWireCommand(wireName,x1,y1,x1,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());

				instantiate = new ExpandWireCommand(wireName,x1,y2,x2,y2);
				response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
			else{
				wireName = createName(x1,y1,x1,y2);
				ICommand instantiate = new AddWireCommand(createName(x1, y1, x2, y2),x1,y1,x2,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
		}else{
			if (x1 != x2 && y1 != y2) {
				wireName = createName(x1,y1,x1,y2);
				ICommand instantiate = new AddWireCommand(wireName,x1,y1,x2,y1);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());

				instantiate = new ExpandWireCommand(wireName,x2,y1,x2,y2);
				response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
			else{
				wireName = createName(x1, y1, x2, y2);
				ICommand instantiate = new AddWireCommand(wireName,x1,y1,x2,y2);
				ICommandResponse response = controller.send(instantiate);
				System.out.println ("canvas report| wire instantiate succesful: "+response.isSuccessful());
			}
		}
		plugToPoint(wireBeginning,controller,wireName);
		plugToPoint(wireEnding,controller,wireName);
	}
	
	private void plugToPoint(CriticalPoint point, ISchemaController controller, Caseless wireName) {
		//TODO napraviti plug wire!!!
		if (point!=null){
			if(point.getType()==CriticalPoint.ON_COMPONENT_PLUG){
				Caseless componentName = point.getName();
				ICommand plug = new PlugWireCommand(componentName, wireName, point.getPortName());
				ICommandResponse response = controller.send(plug);
				String message = "";
				try{
					message = response.getError().getMessage(); 
				}catch (NullPointerException e) {}
				System.out.println ("canvas report| wire instantiate & plug succesful: "+response.isSuccessful()+" "+message);
			}else{
				if(point.getType() == CriticalPoint.ON_WIRE_PLUG && !point.getName().equals(wireName)){
					Caseless wireToBindName = point.getName();
					ICommand plug = new BindWireCommand(wireToBindName, wireName);
					ICommandResponse response = controller.send(plug);
					String message = "";
					try{
						message = response.getError().getMessage(); 
					}catch (NullPointerException e) {}
					System.out.println ("canvas report| wire instantiate & plug succesful: "+response.isSuccessful()+" "+message);
				}else{
					System.out.println("canvas report| wire instantiate & plug expand wire");
				}
			}
		}
	}

	private Caseless createName(int x1, int y1, int x2, int y2) {
		StringBuilder build = new StringBuilder("WIRE_");
		build.append(normalize(x1)).append("_").append(normalize(y1)).append("_")
			.append(normalize(x2)).append("_").append(normalize(y2));
		return new Caseless(build.toString());
	}

	private String normalize(int x) {
		return x<0?"M"+String.valueOf(Math.abs(x)):String.valueOf(x);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g) {
		if(orientation == VERT_FIRST){
			g.drawLine(x1, y1, x1, y2);
			g.drawLine(x1, y2, x2, y2);
		}else{
			g.drawLine(x1, y1, x2, y1);
			g.drawLine(x2, y1, x2, y2);
		}
		
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#isWireInstance()
	 */
	public boolean isWireInstance() {
		return (Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))>10) && wireInstantiable;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#isWireInstantiable()
	 */
	public boolean isWireInstantiable() {
		return wireInstantiable;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setWireInstantiable(boolean)
	 */
	public void setWireInstantiable(boolean wireInstantiable) {
		this.wireInstantiable = wireInstantiable;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setWireInstantiable(hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.CriticalPoint, hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.CriticalPoint)
	 */
	public void setWireInstantiable(CriticalPoint wireBeginning, CriticalPoint wireEnding) {
		if(wireBeginning != null && wireEnding != null){
			if(wireBeginning.getType()==CriticalPoint.ON_WIRE_PLUG && wireEnding.getType()==CriticalPoint.ON_WIRE_PLUG){
				if(wireBeginning.getName().equals(wireEnding.getName())){
					wireInstantiable = false;
				}else{
					wireInstantiable = true;
				}
			}else{
				wireInstantiable = true;
			}
		}else{
			wireInstantiable = true;
		}
		
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.schema2.gui.canvas.IWirePreLocator#setWireOrientation()
	 */
	public void revalidateWire() {
		double d = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		if(d>Constants.GRID_SIZE-1&&d<Constants.GRID_SIZE+1){
			if(Math.abs(x1-x2)>Math.abs(y1-y2)) 
				orientation = WirePreLocator.HORIZ_FIRST;
			else 
				orientation = WirePreLocator.VERT_FIRST;
		}
	}


}
