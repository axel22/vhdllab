package hr.fer.zemris.vhdllab.server.conf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A server configuration.
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since 6/2/2008
 */
public final class ServerConf {

	/**
	 * All defined file type mappings.
	 */
	private final Map<String, FileTypeMapping> mappings;

	/**
	 * Default constructor.
	 */
	public ServerConf() {
		mappings = new HashMap<String, FileTypeMapping>();
	}

	/**
	 * Adds a specified file type mapping.
	 * 
	 * @param mapping
	 *            a file type mapping to add
	 * @throws NullPointerException
	 *             if <code>mapping</code> is <code>null</code>
	 */
	public void addFileTypeMapping(FileTypeMapping mapping) {
		if (mapping == null) {
			throw new NullPointerException("File type mapping cant be null");
		}
		mappings.put(mapping.getType().toLowerCase(), mapping);
	}

	/**
	 * Returns a file type mapping for specified file type of <code>null</code>
	 * if mapping for specified type doesn't exist.
	 * 
	 * @param type
	 *            a type of a file for whom to return file type mapping
	 * @return a file type mapping for specified file type
	 * @throws NullPointerException
	 *             if <code>type</code> is <code>null</code>
	 */
	public FileTypeMapping getFileTypeMapping(String type) {
		return mappings.get(type.toLowerCase());
	}

	/**
	 * Returns <code>true</code> if <code>type</code> is specified in server
	 * configuration or <code>false</code> otherwise.
	 * 
	 * @param type
	 *            a file type to check
	 * @return <code>true</code> if <code>type</code> is specified in server
	 *         configuration or <code>false</code> otherwise
	 */
	public boolean containsFileType(String type) {
		return getFileTypeMapping(type) != null;
	}

	/**
	 * Returns all defined file types. Return value will never be
	 * <code>null</code>.
	 * 
	 * @return all defined file types
	 */
	public Set<String> getFileTypes() {
		return new HashSet<String>(mappings.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mappings.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ServerConf))
			return false;
		final ServerConf other = (ServerConf) obj;
		return mappings.equals(other.mappings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(mappings.size() * 20);
		sb.append("Server configuration: {\n");
		for (FileTypeMapping m : mappings.values()) {
			sb.append(m).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

}