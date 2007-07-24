package hr.fer.zemris.vhdllab.servlets.methods;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.vhdllab.constants.FileTypes;
import hr.fer.zemris.vhdllab.model.GlobalFile;
import hr.fer.zemris.vhdllab.service.ServiceException;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.servlets.ManagerProvider;
import hr.fer.zemris.vhdllab.servlets.RegisteredMethod;
import hr.fer.zemris.vhdllab.servlets.manprovs.SampleManagerProvider;

import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DoMethodFindGlobalFilesByTypeTest {
	
	private static ManagerProvider mprov;
	private static RegisteredMethod regMethod;
	private static String method;
	private Properties prop;
	
	private static GlobalFile file;
	private static GlobalFile file2;
	
	@BeforeClass
	public static void init() throws ServiceException {
		mprov = new SampleManagerProvider();
		VHDLLabManager labman = (VHDLLabManager)mprov.get(ManagerProvider.VHDL_LAB_MANAGER);
		file = labman.createNewGlobalFile("TestFileName_1", FileTypes.FT_SYSTEM);
		file2 = labman.createNewGlobalFile("TestFileName_2", FileTypes.FT_SYSTEM);
		labman.createNewGlobalFile("TestFileName_3", FileTypes.FT_THEME);
		regMethod = new DoMethodFindGlobalFilesByType();
		method = MethodConstants.MTD_FIND_GLOBAL_FILES_BY_TYPE;
	}
	
	@Before
	public void initEachTest() {
		prop = new Properties();
		prop.setProperty(MethodConstants.PROP_METHOD, method);
	}
	
	/**
	 * There is no global file type argument.
	 */
	@Test
	public void run() {
		Properties p = regMethod.run(prop, mprov);
		assertEquals(3, p.keySet().size());
		assertEquals(method, p.getProperty(MethodConstants.PROP_METHOD, ""));
		assertEquals(MethodConstants.SE_METHOD_ARGUMENT_ERROR, p.getProperty(MethodConstants.PROP_STATUS, ""));
	}
	
	/**
	 * Test should pass without errors.
	 */
	@Test
	public void run2() {
		prop.setProperty(MethodConstants.PROP_FILE_TYPE, String.valueOf(file.getType()));

		Properties p = regMethod.run(prop, mprov);
		assertEquals(4, p.keySet().size());
		assertEquals(method, p.getProperty(MethodConstants.PROP_METHOD, ""));
		assertEquals(MethodConstants.STATUS_OK, p.getProperty(MethodConstants.PROP_STATUS, ""));
		assertEquals(String.valueOf(file.getId()), p.getProperty(MethodConstants.PROP_FILE_ID+".1"));
		assertEquals(String.valueOf(file2.getId()), p.getProperty(MethodConstants.PROP_FILE_ID+".2"));
	}
}