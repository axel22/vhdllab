package hr.fer.zemris.vhdllab.platform.ui.wizard.schema;

import hr.fer.zemris.vhdllab.platform.ui.wizard.AbstractShowWizardDialogCommand;

import org.springframework.richclient.wizard.Wizard;

public class NewSchemaCommand extends AbstractShowWizardDialogCommand {

    @Override
    protected Class<? extends Wizard> getWizardClass() {
        return NewSchemaWizard.class;
    }

}