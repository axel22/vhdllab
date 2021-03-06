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
package hr.fer.zemris.vhdllab.applets.editor.schema2.misc;

import java.io.Serializable;




// TODO rijesiti problem razmaka i posebnih znakova

/**
 * U principu String za koji je case nebitan.
 * Pozivom toString() dobiva se originalni string na temelju
 * kojeg je Caseless stvoren - case je ocuvan. Usporedba pomocu
 * equals(), s druge strane, ignorira case.
 * 
 * @author brijest
 *
 */
public final class Caseless implements Serializable {
	
    private static final long serialVersionUID = 5107135366712264850L;

    public static final Caseless Empty = new Caseless("");
	
	public static boolean isNullOrEmpty(Caseless caseless) {
		return (caseless == null || caseless.equals(Empty));
	}
	
	
	
	private String inner;
	
	public Caseless() {
		inner = "";
	}
	
	/**
	 * 
	 * @param val
	 * Ako se preda null, Caseless
	 * ce sadrzavati string "".
	 */
	public Caseless(String val) {
		if (val == null) inner = "";
		else {
			this.inner = val;
		}
	}
	
	/**
	 * 
	 * @param other
	 * Ako se preda null, Caseless
	 * ce sadrzavati string "".
	 */
	public Caseless(Caseless other) {
		if (other == null) inner = "";
		else {
			this.inner = other.inner;
		}
	}
	

	/**
	 * Moguce je obaviti equals provjeru
	 * i sa stringom, ne iskljucivo s drugim
	 * Caselessom. U oba slucaja se casing
	 * ignorira.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof Caseless) {
			Caseless cas = (Caseless)obj;
			if (this.inner.equalsIgnoreCase(cas.inner)) return true;
		} else if (obj instanceof String) {
			String str = (String)obj;
			return (this.inner.equalsIgnoreCase(str));
		}
		return false;
	}

	@Override
	public int hashCode() {
//		int hash = 0;
//		char ch;
//		for (int i = 0; i < inner.length(); i++) {
//			ch = inner.charAt(i);
//			if (ch >= 'a' && ch <= 'z') ch -= 32;
//			hash = hash * 41 + (int)(ch);
//		}
//		return hash;
		
		// neefikasnije, ali sigurnije
		return inner.toLowerCase().hashCode();
	}

	@Override
	public String toString() {
		return inner;
	}

	
}






