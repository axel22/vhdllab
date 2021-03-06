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
package hr.fer.zemris.vhdllab.applets.editor.schema2.model.parameters.constraints;

import hr.fer.zemris.vhdllab.applets.editor.schema2.enums.EConstraintExplanation;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.IParameterConstraint;
import hr.fer.zemris.vhdllab.applets.editor.schema2.misc.Time;

import java.util.Set;


public class TimeConstraint implements IParameterConstraint {
	
	/* static fields */
	

	/* private fields */
	private Set<Object> validset;
	

	/* ctors */
	
	public TimeConstraint() {
		validset = null;
	}
	
	public TimeConstraint(Set<Object> validValues) {
		validset = validValues;
	}
	

	
	
	/* methods */

	public boolean checkValue(Object object) {
		if (object == null) return false;
		if (!(object instanceof Time)) return false;
		if (validset == null) return true;
		Time val = (Time)object;
		if (!(validset.contains(val))) return false;
		return true;
	}

	public EConstraintExplanation getExplanation(Object object) {
		if (object == null) return EConstraintExplanation.NULL_PASSED;
		if (!(object instanceof Time)) return EConstraintExplanation.WRONG_TYPE;
		if (validset == null) return EConstraintExplanation.ALLOWED;
		Time val = (Time)object;
		if (!(validset.contains(val))) return EConstraintExplanation.CONSTRAINT_BANNED;
		return EConstraintExplanation.ALLOWED;
	}

	public Set<Object> getPossibleValues() {
		return validset;
	}

	public void setPossibleValues(Set<Object> possibleValues) {
		validset = possibleValues;
	}
	
}












