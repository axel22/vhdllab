package hr.fer.zemris.vhdllab.dao.impl;

import hr.fer.zemris.vhdllab.dao.DAOException;
import hr.fer.zemris.vhdllab.dao.LibraryDAO;
import hr.fer.zemris.vhdllab.entities.Library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a default implementation of {@link LibraryDAO}.
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since 6/2/2008
 */
public final class LibraryDAOImpl extends AbstractEntityDAO<Library> implements
		LibraryDAO {

	/**
	 * Sole constructor.
	 */
	public LibraryDAOImpl() {
		super(Library.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.vhdllab.dao.LibraryDAO#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String name) throws DAOException {
		checkParameters(name);
		String namedQuery = Library.FIND_BY_NAME_QUERY;
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("name", name);
		return existsEntity(namedQuery, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.vhdllab.dao.LibraryDAO#findByName(java.lang.String)
	 */
	@Override
	public Library findByName(String name) throws DAOException {
		checkParameters(name);
		String namedQuery = Library.FIND_BY_NAME_QUERY;
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put("name", name);
		return findSingleEntity(namedQuery, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.vhdllab.dao.LibraryDAO#getAll()
	 */
	@Override
	public List<Library> getAll() throws DAOException {
		String namedQuery = Library.GET_ALL_QUERY;
		Map<String, Object> params = new HashMap<String, Object>(0);
		return findEntityList(namedQuery, params);
	}

	/**
	 * Throws {@link NullPointerException} is <code>name</code> is
	 * <code>null</code>.
	 */
	private void checkParameters(String name) {
		if (name == null) {
			throw new NullPointerException("Name cant be null");
		}
	}

}
