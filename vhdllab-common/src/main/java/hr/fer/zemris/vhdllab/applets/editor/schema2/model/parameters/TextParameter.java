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
package hr.fer.zemris.vhdllab.applets.editor.schema2.model.parameters;

import hr.fer.zemris.vhdllab.applets.editor.schema2.enums.EParamTypes;
import hr.fer.zemris.vhdllab.applets.editor.schema2.exceptions.InvalidParameterValueException;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.IParameter;
import hr.fer.zemris.vhdllab.applets.editor.schema2.interfaces.IParameterConstraint;
import hr.fer.zemris.vhdllab.applets.editor.schema2.misc.Time;
import hr.fer.zemris.vhdllab.applets.editor.schema2.model.parameters.constraints.TextConstraint;

import java.util.HashSet;
import java.util.Set;

public class TextParameter extends AbstractParameter {
	
	/* static fields */
	
	

	/* private fields */
	private String name;
	private String value;
	private boolean generic;
	private TextConstraint constraint;
	

	/* ctors */
	
	public TextParameter(String parameterName, boolean isGeneric) {
		name = parameterName;
		value = "";
		generic = isGeneric;
		constraint = new TextConstraint();
	}
	
	public TextParameter(String parameterName, boolean isGeneric, String initialValue) {
		name = parameterName;
		value = initialValue;
		generic = isGeneric;
		constraint = new TextConstraint();
	}
	

	/* methods */
	
	public IParameter copyCtor() {
		TextParameter tp = new TextParameter(this.name, this.generic);
		Set<Object> allowed = this.constraint.getPossibleValues();
		allowed = (allowed != null) ? (new HashSet<Object>(allowed)) : (null);
		
		tp.value = this.value;
		tp.constraint = new TextConstraint(allowed);
		tp.paramevent = (this.paramevent != null) ? (this.paramevent.copyCtor()) : (null);
		
		return tp;
	}

	public boolean checkStringValue(String stringValue) {
		return true;
	}

	public IParameterConstraint getConstraint() {
		return constraint;
	}

	public String getName() {
		return name;
	}

	public EParamTypes getType() {
		return EParamTypes.TEXT;
	}

	public Object getValue() {
		return value;
	}

	public Boolean getValueAsBoolean() throws ClassCastException {
		throw new ClassCastException();
	}

	public Double getValueAsDouble() throws ClassCastException {
		throw new ClassCastException();
	}

	public Integer getValueAsInteger() throws ClassCastException {
		throw new ClassCastException();
	}

	public String getValueAsString() throws ClassCastException {
		return value;
	}

	public Time getValueAsTime() throws ClassCastException {
		throw new ClassCastException();
	}

	public String getVHDLGenericEntry() {
		return value;
	}

	public boolean isGeneric() {
		return generic;
	}

	public boolean isParsable() {
		return true;
	}

	public void setAsString(String stringValue) throws InvalidParameterValueException {
		if (stringValue == null) throw new InvalidParameterValueException("Null passed.");
		value = stringValue;
	}

	public void setValue(Object val) throws InvalidParameterValueException {
		if (val == null || !(val instanceof String))
			throw new InvalidParameterValueException("Non-string passed.");
		
		if (!(constraint.checkValue(val)))
			throw new InvalidParameterValueException("Not allowed by constraint.");
		
		value = (String)val;
	}

	public String getValueClassName() {
		return String.class.getName();
	}

	
	
	

}
