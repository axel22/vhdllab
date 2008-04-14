/**
 * 
 */
package hr.fer.zemris.vhdllab.communicaton.methods;

import hr.fer.zemris.vhdllab.communicaton.AbstractIdParameterMethod;
import hr.fer.zemris.vhdllab.communicaton.results.Void;

/**
 * @author Miro Bezjak
 *
 */
public final class DeleteGlobalFileMethod extends AbstractIdParameterMethod<Void> {

	private static final long serialVersionUID = 1L;

	public DeleteGlobalFileMethod(Long id) {
		super("delete.global.file", id);
	}

}