package hr.fer.zemris.vhdllab.vhdl;

import java.util.List;

public class CompilationResult extends Result {

	public CompilationResult(Integer status, boolean isSuccessful, List<? extends CompilationMessage> messages) {
		super(status, isSuccessful, messages);
	}
}