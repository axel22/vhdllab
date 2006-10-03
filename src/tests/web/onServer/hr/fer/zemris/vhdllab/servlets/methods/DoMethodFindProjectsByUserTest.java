package hr.fer.zemris.vhdllab.servlets.methods;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.vhdllab.model.File;
import hr.fer.zemris.vhdllab.model.Project;
import hr.fer.zemris.vhdllab.service.ServiceException;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.servlets.ManagerProvider;
import hr.fer.zemris.vhdllab.servlets.RegisteredMethod;
import hr.fer.zemris.vhdllab.servlets.manprovs.SampleManagerProvider;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DoMethodFindProjectsByUserTest {
	
	private static ManagerProvider mprov;
	private static RegisteredMethod regMethod;
	private static String method;
	private Properties prop;
	
	private static Project project;
	private static File file1;
	private static File file2;
	private static File file3;
	
	@BeforeClass
	public static void init() throws ServiceException {
		mprov = new SampleManagerProvider();
		VHDLLabManager labman = (VHDLLabManager)mprov.get("vhdlLabManager");
		project = labman.createNewProject("TestProjectName", Long.valueOf(1111));
		file1 = labman.createNewFile(project, "TestFileName_1", File.FT_VHDLSOURCE);
		file2 = labman.createNewFile(project, "TestFileName_2", File.FT_VHDLSOURCE);
		file3 = labman.createNewFile(project, "TestFileName_3", File.FT_VHDLTB);
		Set<File> files = new TreeSet<File>();
		files.add(file1);
		files.add(file2);
		files.add(file3);
		project.setFiles(files);
		labman.saveProject(project);
		regMethod = new DoMethodFindProjectsByUser();
		method = MethodConstants.MTD_FIND_PROJECTS_BY_USER;
	}
	
	@Before
	public void initEachTest() {
		prop = new Properties();
		prop.setProperty(MethodConstants.PROP_METHOD, method);
	}
	
	/**
	 * There is no project owner ID argument.
	 */
	@Test
	public void run() {
		Properties p = regMethod.run(prop, mprov);
		assertEquals(3, p.keySet().size());
		assertEquals(method, p.getProperty(MethodConstants.PROP_METHOD, ""));
		assertEquals(MethodConstants.SE_METHOD_ARGUMENT_ERROR, p.getProperty(MethodConstants.PROP_STATUS, ""));
	}
	
	/**
	 * Project owner ID argument is not number.
	 */
	@Test
	public void run2() {
		prop.setProperty(MethodConstants.PROP_PROJECT_OWNER_ID, "n");

		Properties p = regMethod.run(prop, mprov);
		assertEquals(3, p.keySet().size());
		assertEquals(method, p.getProperty(MethodConstants.PROP_METHOD, ""));
		assertEquals(MethodConstants.SE_PARSE_ERROR, p.getProperty(MethodConstants.PROP_STATUS, ""));
	}
	
	/**
	 * Test should pass without errors.
	 */
	@Test
	public void run3() {
		prop.setProperty(MethodConstants.PROP_PROJECT_OWNER_ID, String.valueOf(project.getOwnerID()));

		Properties p = regMethod.run(prop, mprov);
		assertEquals(3, p.keySet().size());
		assertEquals(method, p.getProperty(MethodConstants.PROP_METHOD, ""));
		assertEquals(MethodConstants.STATUS_OK, p.getProperty(MethodConstants.PROP_STATUS, ""));
		assertEquals(String.valueOf(project.getId()), p.getProperty(MethodConstants.PROP_PROJECT_ID+".1"));
	}
}