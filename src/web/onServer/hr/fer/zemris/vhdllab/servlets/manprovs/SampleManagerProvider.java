package hr.fer.zemris.vhdllab.servlets.manprovs;

import hr.fer.zemris.vhdllab.dao.FileDAO;
import hr.fer.zemris.vhdllab.dao.GlobalFileDAO;
import hr.fer.zemris.vhdllab.dao.ProjectDAO;
import hr.fer.zemris.vhdllab.dao.UserFileDAO;
import hr.fer.zemris.vhdllab.dao.impl.dummy.FileDAOMemoryImpl;
import hr.fer.zemris.vhdllab.dao.impl.dummy.GlobalFileDAOMemoryImpl;
import hr.fer.zemris.vhdllab.dao.impl.dummy.ProjectDAOMemoryImpl;
import hr.fer.zemris.vhdllab.dao.impl.dummy.UserFileDAOMemoryImpl;
import hr.fer.zemris.vhdllab.service.impl.dummy.VHDLLabManagerImpl;
import hr.fer.zemris.vhdllab.servlets.ManagerProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides infomation regarding which implementations will
 * be used for these interfaces:
 * <ul>
 * <li>FileDAO, ProjectDAO : data access objects</li>
 * <li>VHDLLabManager : service manager</li>
 * </ul>
 */
public class SampleManagerProvider implements ManagerProvider {
	
	private Map<String,Object> beans = new HashMap<String,Object>();
	
	/**
	 * Constructor that sets implemetation that will be used.
	 */
	public SampleManagerProvider() {
		// Create all data access objects.
		FileDAO fileDAO = new FileDAOMemoryImpl();
		ProjectDAO projectDAO = new ProjectDAOMemoryImpl();
		GlobalFileDAO globalFileDAO = new GlobalFileDAOMemoryImpl();
		UserFileDAO userFileDAO = new UserFileDAOMemoryImpl();
		
		// Create all service managers, and configure them
		// with appropriate DAO objects.
		VHDLLabManagerImpl labManImpl = new VHDLLabManagerImpl();
		labManImpl.setFileDAO(fileDAO);
		labManImpl.setProjectDAO(projectDAO);
		labManImpl.setGlobalFileDAO(globalFileDAO);
		labManImpl.setUserFileDAO(userFileDAO);
		
		// Remember created managers, so we can later return
		// a reference to them.
		beans.put("vhdlLabManager",labManImpl);
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.servlets.ManagerProvider#get(java.lang.String)
	 */
	public Object get(String name) {
		return beans.get(name);
	}
}