package hr.fer.zemris.vhdllab.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A test case for {@link File} entity.
 * 
 * @author Miro Bezjak
 */
public class FileTest {

	private static final Long ID = Long.valueOf(123456);
	private static final String NAME = "file.name";
	private static final String TYPE = "file.type";
	private static final String CONTENT = "...file content...";
	private static final Date CREATED;
	private static final String NEW_NAME = "new." + NAME;
	private static final String NEW_TYPE = "new." + TYPE;
	private static final String NEW_CONTENT = "new." + CONTENT;
	private static final Date NEW_CREATED;
	private static final Project NEW_PROJECT;

	static {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		try {
			CREATED = df.parse("2008-01-02 13-45");
			NEW_CREATED = df.parse("2000-12-31 07-13");
		} catch (ParseException e) {
			// should never happen. but if pattern should change report it by
			// throwing exception.
			throw new IllegalStateException(e);
		}
		NEW_PROJECT = new Project();
		NEW_PROJECT.setId(Long.valueOf(5555));
		NEW_PROJECT.setName("new.project.name");
	}

	private Project project;
	private Project project2;
	private File file;
	private File file2;

	@Before
	public void initEachTest() {
		file = new File();
		file.setId(ID);
		file.setName(NAME);
		file.setType(TYPE);
		file.setContent(CONTENT);
		file.setCreated(CREATED);
		file2 = new File(file);

		project = new Project();
		project.setId(Long.valueOf(10));
		project.setName("library1.name");
		project.setCreated(Calendar.getInstance().getTime());
		project2 = new Project(project);

		project.addFile(file);
		project2.addFile(file2);
	}

	/**
	 * Test copy constructor
	 */
	@Test
	public void copyConstructor() {
		assertTrue("same reference.", file != file2);
		assertEquals("not equal.", file, file2);
		assertEquals("hashCode not same.", file.hashCode(), file2.hashCode());
		assertEquals("not equal by compareTo.", 0, file.compareTo(file2));
	}

	/**
	 * Test references to project
	 */
	@Test
	public void copyConstructor2() {
		file2 = new File(file);
		assertNull("reference to project is copied.", file2.getProject());
	}

	/**
	 * Test equals with self, null, and non-file object
	 */
	@Test
	public void equals() {
		assertEquals("not equal.", file, file);
		assertNotSame("file is equal to null.", file, null);
		assertNotSame("can compare with string object.", file,
				"a string object");
		assertNotSame("can compare with resource object.", file, new Resource());
	}

	/**
	 * Null object as parameter to compareTo method
	 */
	@Test(expected = NullPointerException.class)
	public void compareTo() {
		file.compareTo(null);
	}

	/**
	 * Non-file type
	 */
	@Test(expected = ClassCastException.class)
	public void compareTo2() {
		file.compareTo(new Resource());
	}

	/**
	 * Only ids (if set) are important in equals, hashcode and compareTo
	 */
	@Test
	public void equalsAndHashCodeAndCompareTo() {
		file2.setName(NEW_NAME);
		file2.setType(NEW_TYPE);
		file2.setContent(NEW_CONTENT);
		file2.setProject(NEW_PROJECT);
		assertEquals("not equal.", file, file2);
		assertEquals("hashCode not same.", file.hashCode(), file2.hashCode());
		assertEquals("not equal by compareTo.", 0, file.compareTo(file2));
	}

	/**
	 * Ids are null, then name and project is important
	 */
	@Test
	public void equalsHashCodeAndCompareTo2() {
		file.setId(null);
		file2.setId(null);
		file2.setType(NEW_TYPE);
		file2.setContent(NEW_CONTENT);
		file2.setCreated(NEW_CREATED);
		assertEquals("not equal.", file, file2);
		assertEquals("hashCode not same.", file.hashCode(), file2.hashCode());
		assertEquals("not equal by compareTo.", 0, file.compareTo(file2));
	}

	/**
	 * Ids are null and projects are not equal
	 */
	@Test
	public void equalsHashCodeAndCompareTo3() {
		file.setId(null);
		file2.setId(null);
		file2.setProject(NEW_PROJECT);
		assertNotSame("equal.", file, file2);
		assertNotSame("hashCode same.", file.hashCode(), file2.hashCode());
		assertEquals("not compared by project.", file.getProject().compareTo(
				file2.getProject()) < 0 ? -1 : 1, file.compareTo(file2));
	}

	/**
	 * Ids and names are null, then library is important
	 */
	@Test
	public void equalsHashCodeAndCompareTo4() {
		file.setId(null);
		file2.setId(null);
		file.setName(null);
		file2.setName(null);
		assertEquals("not equal.", file, file2);
		assertEquals("hashCode not same.", file.hashCode(), file2.hashCode());
		assertEquals("not equal by compareTo.", 0, file.compareTo(file2));
	}

	/**
	 * Ids, names and projects are null
	 */
	@Test
	public void equalsHashCodeAndCompareTo5() {
		file.setId(null);
		file2.setId(null);
		file.setName(null);
		file2.setName(null);
		file.setProject(null);
		file2.setProject(null);
		assertEquals("not equal.", file, file2);
		assertEquals("hashCode not same.", file.hashCode(), file2.hashCode());
		assertEquals("not equal by compareTo.", 0, file.compareTo(file2));
	}

	@Ignore("must be tested by a user and this has already been tested")
	@Test
	public void asString() {
		System.out.println(file);
	}

}
