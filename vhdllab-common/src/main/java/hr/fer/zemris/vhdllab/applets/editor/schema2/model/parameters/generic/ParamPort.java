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
package hr.fer.zemris.vhdllab.applets.editor.schema2.model.parameters.generic;

import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.IGenericValue;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.serialization.PortFactory;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PortWrapper;
import hr.fer.zemris.vhdllab.service.ci.Port;







public class ParamPort implements IGenericValue {
	
	/* static fields */
	private static final String DELIMITER = "#";
	
	/* private fields */
	private Port port;
	
	
	
	/* ctors */
	
	public ParamPort() { }
	public ParamPort(Port p) { port = p; }
	
	
	
	/* methods */
	
	public Port getPort() {
		return port;
	}
	
	public IGenericValue copyCtor() {
		return new ParamPort(new Port(port));
	}
	
	public void deserialize(String code) {
		this.port = PortFactory.createPort(createPortWrapper(code));
	}
	
	public String serialize() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(port.getName()).append(DELIMITER);
		sb.append(port.getDirection().toString()).append(DELIMITER);
		sb.append(port.getTypeName()).append(DELIMITER);
		if (port.isScalar()) {
			sb.append(DELIMITER).append(DELIMITER);
		} else {
			sb.append(fromVecDir(port)).append(DELIMITER);
			sb.append(Integer.toString(port.getFrom())).append(DELIMITER);
			sb.append(Integer.toString(port.getTo()));
		}
		
		return sb.toString();
	}
	
	private String fromVecDir(Port p) {
	    return p.isTO() ? PortWrapper.ASCEND : PortWrapper.DESCEND;
	}
	
	private PortWrapper createPortWrapper(String code) {
		String[] sf = code.split(DELIMITER);
		
		PortWrapper pw = new PortWrapper();
		pw.setName(sf[0]);
		pw.setDirection(sf[1]);
		pw.setType(sf[2]);
		if (sf[2].equalsIgnoreCase("std_logic_vector")) {
			pw.setVectorAscension(sf[3]);
			pw.setLowerBound(sf[4]);
			pw.setUpperBound(sf[5]);
		}
		
		return pw;
	}
	@Override
	public String toString() {
		return port.toString();
	}
	
	
	
}


















