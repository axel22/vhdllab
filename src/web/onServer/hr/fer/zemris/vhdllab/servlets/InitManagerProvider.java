package hr.fer.zemris.vhdllab.servlets;

import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.servlets.initialize.InitServerData;
import hr.fer.zemris.vhdllab.servlets.manprovs.SampleManagerProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes a servlet.
 */
public class InitManagerProvider implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent ev) {
		ev.getServletContext().log("InitManagerProvider listener started.");
		// create new manager provider
		ManagerProvider prov = new SampleManagerProvider();
		// store it in application context
		ev.getServletContext().setAttribute("managerProvider",prov);
		
		// initialize server data
		VHDLLabManager labman = (VHDLLabManager) prov.get(ManagerProvider.VHDL_LAB_MANAGER);
		InitServerData initData = new InitServerData(labman);
		initData.initGlobalFiles();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent ev) {
		//InitServerData.cleanServerData();
		ev.getServletContext().removeAttribute("managerProvider");
		ev.getServletContext().log("InitManagerProvider listener finished.");
	}

}