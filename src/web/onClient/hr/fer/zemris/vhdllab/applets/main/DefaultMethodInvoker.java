package hr.fer.zemris.vhdllab.applets.main;

import hr.fer.zemris.ajax.shared.AjaxMediator;
import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.ajax.shared.XMLUtil;
import hr.fer.zemris.vhdllab.applets.main.interfaces.MethodInvoker;
import hr.fer.zemris.vhdllab.vhdl.CompilationResult;
import hr.fer.zemris.vhdllab.vhdl.SimulationResult;
import hr.fer.zemris.vhdllab.vhdl.model.CircuitInterface;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultCircuitInterface;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultPort;
import hr.fer.zemris.vhdllab.vhdl.model.DefaultType;
import hr.fer.zemris.vhdllab.vhdl.model.Direction;
import hr.fer.zemris.vhdllab.vhdl.model.Hierarchy;
import hr.fer.zemris.vhdllab.vhdl.model.Port;
import hr.fer.zemris.vhdllab.vhdl.model.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Default implementation of <code>MethodInvoker</code> interface. Uses
 * <code>AjaxMediator</code> to initiate requests to server. It also uses
 * Properties class to create XML documents that are sent and then reassembled
 * on server. Server also returns XML documents that are reassembled here and
 * so forth.
 * @author Miro Bezjak
 */
public class DefaultMethodInvoker implements MethodInvoker {

	/** Mediator responsible for initiating requests to server */
	private AjaxMediator ajax;

	/**
	 * Constructor.
	 * @param ajax an <code>AjaxMediator</code> that is responsible for
	 * 		initiating requests to server
	 * @throws NullPointerException if <code>ajax</code> is <code>null</code>
	 */
	public DefaultMethodInvoker(AjaxMediator ajax) {
		if(ajax == null) throw new NullPointerException("Ajax mediator can not be null");
		this.ajax = ajax;
	}
	
	public CompilationResult compileFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_COMPILE_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String data = resProperties.getProperty(MethodConstants.PROP_RESULT_COMPILATION_SERIALIZATION);
		CompilationResult result = CompilationResult.deserialize(data);
		return result;
	}

	public Long createFile(Long projectId, String name, String type) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		if(name == null) throw new NullPointerException("File name can not be null.");
		if(type == null) throw new NullPointerException("File type can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_CREATE_NEW_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		reqProperties.setProperty(MethodConstants.PROP_FILE_TYPE, type);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String fileId = resProperties.getProperty(MethodConstants.PROP_FILE_ID);
		Long id = Long.parseLong(fileId);
		return id;
	}

	public Long createGlobalFile(String name, String type) throws UniformAppletException {
		if(name == null) throw new NullPointerException("Global file name can not be null.");
		if(type == null) throw new NullPointerException("Global file type can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_CREATE_NEW_GLOBAL_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		reqProperties.setProperty(MethodConstants.PROP_FILE_TYPE, type);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String fileId = resProperties.getProperty(MethodConstants.PROP_FILE_ID);
		Long id = Long.parseLong(fileId);
		return id;
	}

	public Long createProject(String name, String ownerId) throws UniformAppletException {
		if(name == null) throw new NullPointerException("Project name can not be null.");
		if(ownerId == null) throw new NullPointerException("Project owner identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_CREATE_NEW_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_NAME, name);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_OWNER_ID, ownerId);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String projectId = resProperties.getProperty(MethodConstants.PROP_PROJECT_ID);
		Long id = Long.parseLong(projectId);
		return id;
	}

	public Long createUserFile(String ownerId, String type) throws UniformAppletException {
		if(ownerId == null) throw new NullPointerException("User owner identifier can not be null.");
		if(type == null) throw new NullPointerException("User file type can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_CREATE_NEW_USER_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_OWNER_ID, ownerId);
		reqProperties.setProperty(MethodConstants.PROP_FILE_TYPE, type);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String fileId = resProperties.getProperty(MethodConstants.PROP_FILE_ID);
		Long id = Long.parseLong(fileId);
		return id;
	}

	public void deleteFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_DELETE_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		initiateAjax(reqProperties);
	}

	public void deleteGlobalFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_DELETE_GLOBAL_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		initiateAjax(reqProperties);
	}

	public void deleteProject(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_DELETE_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		initiateAjax(reqProperties);
	}

	public void deleteUserFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_DELETE_USER_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		initiateAjax(reqProperties);
	}

	public boolean existsFile(Long projectId, String name) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		if(name == null) throw new NullPointerException("File name can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXISTS_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String exists = resProperties.getProperty(MethodConstants.PROP_FILE_EXISTS);
		return exists.equals("1");
	}

	public boolean existsGlobalFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXISTS_GLOBAL_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String exists = resProperties.getProperty(MethodConstants.PROP_FILE_EXISTS);
		return exists.equals("1");
	}

	public boolean existsProject(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXISTS_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String exists = resProperties.getProperty(MethodConstants.PROP_PROJECT_EXISTS);
		return exists.equals("1");
	}

	public boolean existsUserFile(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXISTS_USER_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String exists = resProperties.getProperty(MethodConstants.PROP_FILE_EXISTS);
		return exists.equals("1");
	}

	public CircuitInterface extractCircuitInterface(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXTRACT_CIRCUIT_INTERFACE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String ciEntityName = resProperties.getProperty(MethodConstants.PROP_CI_ENTITY_NAME);
		List<Port> ports = new ArrayList<Port>();
		int i = 1;
		while(true) {
			String portName = resProperties.getProperty(MethodConstants.PROP_CI_PORT_NAME+"."+i);
			if(portName == null) break;
			String portDirection = resProperties.getProperty(MethodConstants.PROP_CI_PORT_DIRECTION+"."+i);
			String typeName = resProperties.getProperty(MethodConstants.PROP_CI_PORT_TYPE_NAME+"."+i);
			String typeRangeFrom = resProperties.getProperty(MethodConstants.PROP_CI_PORT_TYPE_RANGE_FROM+"."+i);
			String typeRangeTo = resProperties.getProperty(MethodConstants.PROP_CI_PORT_TYPE_RANGE_TO+"."+i);
			String vectorDirection = resProperties.getProperty(MethodConstants.PROP_CI_PORT_TYPE_VECTOR_DIRECTION+"."+i);
			
			Direction direction;
			if(portDirection.equalsIgnoreCase("IN")) {
				direction = Direction.IN;
			} else if(portDirection.equalsIgnoreCase("OUT")) {
				direction = Direction.OUT;
			} else if(portDirection.equalsIgnoreCase("INOUT")) {
				direction = Direction.INOUT;
			} else {
				direction = Direction.BUFFER;
			}
			
			int[] range;
			if(typeRangeFrom == null && typeRangeTo == null) {
				range = null;
			} else {
				int rangeFrom = Integer.parseInt(typeRangeFrom);
				int rangeTo = Integer.parseInt(typeRangeTo);
				range = new int[] {rangeFrom, rangeTo};
			} 
			
			Type type = new DefaultType(typeName, range, vectorDirection);
			ports.add(new DefaultPort(portName, direction, type));
			i++;
		}
		
		return new DefaultCircuitInterface(ciEntityName, ports);
	}

	public List<Long> extractDependencies(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXTRACT_DEPENDENCIES;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> files = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_FILE_ID+"."+i);
			if(id == null) break;
			files.add(Long.parseLong(id));
			i++;
		}
		return files;
	}
	
	public Hierarchy extractHierarchy(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_EXTRACT_HIERARCHY;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String data = resProperties.getProperty(MethodConstants.PROP_HIERARCHY_SERIALIZATION);
		Hierarchy h = Hierarchy.deserialize(data);
		return h;
	}
	
	public Long findFileByName(Long projectId, String name) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		if(name == null) throw new NullPointerException("File name can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_FIND_FILE_BY_NAME;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String fileId = resProperties.getProperty(MethodConstants.PROP_FILE_ID);
		Long id = Long.parseLong(fileId);
		return id;
	}
	
	public List<Long> findFileByProject(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_FIND_FILES_BY_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> files = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_FILE_ID+"."+i);
			if(id == null) break;
			files.add(Long.parseLong(id));
			i++;
		}
		return files;
	}

	public List<Long> findGlobalFilesByType(String type) throws UniformAppletException {
		if(type == null) throw new NullPointerException("Global file type can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_FIND_GLOBAL_FILES_BY_TYPE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_TYPE, type);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> files = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_FILE_ID+"."+i);
			if(id == null) break;
			files.add(Long.parseLong(id));
			i++;
		}
		return files;
	}

	public List<Long> findProjectsByUser(String ownerId) throws UniformAppletException {
		if(ownerId == null) throw new NullPointerException("Project owner identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_FIND_PROJECTS_BY_USER;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_OWNER_ID, ownerId);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> projects = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_PROJECT_ID+"."+i);
			if(id == null) break;
			projects.add(Long.parseLong(id));
			i++;
		}
		return projects;
	}

	public List<Long> findUserFilesByOwner(String ownerId) throws UniformAppletException {
		if(ownerId == null) throw new NullPointerException("User file owner identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_FIND_USER_FILES_BY_USER;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_OWNER_ID, ownerId);
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> files = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_FILE_ID+"."+i);
			if(id == null) break;
			files.add(Long.parseLong(id));
			i++;
		}
		return files;
	}

	public String generateVHDL(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_GENERATE_VHDL;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String vhdl = resProperties.getProperty(MethodConstants.PROP_GENERATED_VHDL);
		return vhdl;
	}

	public String loadFileContent(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_FILE_CONTENT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String content = resProperties.getProperty(MethodConstants.PROP_FILE_CONTENT);
		return content;
	}

	public String loadFileName(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_FILE_NAME;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String name = resProperties.getProperty(MethodConstants.PROP_FILE_NAME);
		return name;
	}

	public Long loadFileProjectId(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_FILE_BELONGS_TO_PROJECT_ID;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String projectId = resProperties.getProperty(MethodConstants.PROP_PROJECT_ID);
		Long id = Long.parseLong(projectId);
		return id;

	}

	public String loadFileType(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_FILE_TYPE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String type = resProperties.getProperty(MethodConstants.PROP_FILE_TYPE);
		return type;
	}

	public String loadGlobalFileContent(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_GLOBAL_FILE_CONTENT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String content = resProperties.getProperty(MethodConstants.PROP_FILE_CONTENT);
		return content;
	}

	public String loadGlobalFileName(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_GLOBAL_FILE_NAME;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String name = resProperties.getProperty(MethodConstants.PROP_FILE_NAME);
		return name;
	}

	public String loadGlobalFileType(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_GLOBAL_FILE_TYPE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String type = resProperties.getProperty(MethodConstants.PROP_FILE_TYPE);
		return type;
	}

	public List<Long> loadProjectFilesId(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_PROJECT_FILES_ID;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		List<Long> files = new ArrayList<Long>();
		int i = 1;
		while(true) {
			String id = resProperties.getProperty(MethodConstants.PROP_FILE_ID+"."+i);
			if(id == null) break;
			files.add(Long.parseLong(id));
			i++;
		}
		return files;
	}

	public String loadProjectName(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_PROJECT_NAME;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String name = resProperties.getProperty(MethodConstants.PROP_PROJECT_NAME);
		return name;
	}

	public Long loadProjectNumberFiles(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_PROJECT_NMBR_FILES;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String numberOfFiles = resProperties.getProperty(MethodConstants.PROP_PROJECT_NMBR_FILES);
		Long count = Long.parseLong(numberOfFiles);
		return count;
	}

	public String loadProjectOwnerId(Long projectId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_PROJECT_OWNER_ID;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String ownerId = resProperties.getProperty(MethodConstants.PROP_PROJECT_OWNER_ID);
		return ownerId;
	}

	public String loadUserFileContent(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_USER_FILE_CONTENT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String content = resProperties.getProperty(MethodConstants.PROP_FILE_CONTENT);
		return content;
	}

	public String loadUserFileOwnerId(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_USER_FILE_OWNER_ID;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String ownerId = resProperties.getProperty(MethodConstants.PROP_FILE_OWNER_ID);
		return ownerId;
	}
	
	public String loadUserFileName(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_USER_FILE_NAME;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String name = resProperties.getProperty(MethodConstants.PROP_FILE_NAME);
		return name;
	}


	public String loadUserFileType(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("User file identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_LOAD_USER_FILE_TYPE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String type = resProperties.getProperty(MethodConstants.PROP_FILE_TYPE);
		return type;
	}

	public void renameFile(Long fileId, String name) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		if(name == null) throw new NullPointerException("File name can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_RENAME_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		
		initiateAjax(reqProperties);
	}

	public void renameGlobalFile(Long fileId, String name) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		if(name == null) throw new NullPointerException("Global file name can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_RENAME_GLOBAL_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_NAME, name);
		
		initiateAjax(reqProperties);
	}

	public void renameProject(Long projectId, String name) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		if(name == null) throw new NullPointerException("Project name can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_RENAME_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_NAME, name);
		
		initiateAjax(reqProperties);
	}

	public SimulationResult runSimulation(Long fileId) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_RUN_SIMULATION;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		
		Properties resProperties = initiateAjax(reqProperties);
		
		String data = resProperties.getProperty(MethodConstants.PROP_RESULT_SIMULATION_SERIALIZATION);
		SimulationResult result = SimulationResult.deserialize(data);
		return result;
	}

	public void saveFile(Long fileId, String content) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		if(content == null) throw new NullPointerException("File content can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_SAVE_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_CONTENT, content);
		
		initiateAjax(reqProperties);
	}

	public void saveGlobalFile(Long fileId, String content) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("Global file identifier can not be null.");
		if(content == null) throw new NullPointerException("Global file content can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_SAVE_GLOBAL_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_CONTENT, content);
		
		initiateAjax(reqProperties);
	}

	public void saveProject(Long projectId, List<Long> filesId) throws UniformAppletException {
		if(projectId == null) throw new NullPointerException("Project identifier can not be null.");
		if(filesId == null) throw new NullPointerException("List of File identifiers can not be null.");
		if(filesId.size() == 0) throw new IllegalArgumentException("List of File identifiers can not be empty.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_SAVE_PROJECT;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_PROJECT_ID, String.valueOf(projectId));
		int i = 1;
		for(Long id : filesId) {
			reqProperties.setProperty(MethodConstants.PROP_FILE_ID+"."+i, String.valueOf(id));
			i++;
		}
		
		initiateAjax(reqProperties);
	}

	public void saveUserFile(Long fileId, String content) throws UniformAppletException {
		if(fileId == null) throw new NullPointerException("File identifier can not be null.");
		if(content == null) throw new NullPointerException("User file content can not be null.");
		Properties reqProperties = new Properties();
		String method = MethodConstants.MTD_SAVE_USER_FILE;
		reqProperties.setProperty(MethodConstants.PROP_METHOD, method);
		reqProperties.setProperty(MethodConstants.PROP_FILE_ID, String.valueOf(fileId));
		reqProperties.setProperty(MethodConstants.PROP_FILE_CONTENT, content);
		
		initiateAjax(reqProperties);
	}

	private Properties initiateAjax(Properties p) throws UniformAppletException {
		String method = p.getProperty(MethodConstants.PROP_METHOD, "");
		String response = null;
		try {
			response = ajax.initiateSynchronousCall(XMLUtil.serializeProperties(p));
		} catch (Exception e) {
			response = null;
		}
		if(response == null) throw new UniformAppletException("AJAX connection problems");
		
		Properties resProperties = XMLUtil.deserializeProperties(response);
		String resMethod = resProperties.getProperty(MethodConstants.PROP_METHOD);
		if(!method.equalsIgnoreCase(resMethod)) {
			throw new UniformAppletException("Wrong method returned! Expected: " + method + " but was: " + resMethod);
		}
		String status = resProperties.getProperty(MethodConstants.PROP_STATUS, "");
		if(!status.equals(MethodConstants.STATUS_OK)) {
			throw new UniformAppletException(resProperties.getProperty(MethodConstants.PROP_STATUS_CONTENT, "Unknown error."));
		}
		
		return resProperties;
	}

}
