package hr.fer.zemris.vhdllab.platform.manager.workspace.support;

import hr.fer.zemris.vhdllab.api.workspace.Workspace;
import hr.fer.zemris.vhdllab.platform.listener.AbstractEventPublisher;
import hr.fer.zemris.vhdllab.platform.manager.workspace.WorkspaceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceInitializer extends
        AbstractEventPublisher<WorkspaceInitializationListener> {

    @Autowired
    private WorkspaceManager workspaceManager;

    public WorkspaceInitializer() {
        super(WorkspaceInitializationListener.class);
    }

    public void initializeWorkspace() {
        Workspace workspace = workspaceManager.getWorkspace();
        fireInitialize(workspace);
        logger.debug("Workspace initialized with "
                + workspace.getProjectCount() + " projects");
    }

    private void fireInitialize(Workspace workspace) {
        for (WorkspaceInitializationListener l : getListeners()) {
            l.initialize(workspace);
        }
    }

}