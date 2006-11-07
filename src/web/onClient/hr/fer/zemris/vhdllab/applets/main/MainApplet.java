package hr.fer.zemris.vhdllab.applets.main;

import hr.fer.zemris.ajax.shared.AjaxMediator;
import hr.fer.zemris.ajax.shared.DefaultAjaxMediator;
import hr.fer.zemris.vhdllab.applets.main.dummy.ProjectExplorer;
import hr.fer.zemris.vhdllab.applets.main.dummy.SideBar;
import hr.fer.zemris.vhdllab.applets.main.dummy.StatusBar;
import hr.fer.zemris.vhdllab.applets.main.dummy.StatusExplorer;
import hr.fer.zemris.vhdllab.applets.main.dummy.Writer;
import hr.fer.zemris.vhdllab.i18n.CachedResourceBundles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * 
 * 
 * @author Miro Bezjak
 */
public class MainApplet
		extends JApplet
		 {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 4037604752375048576L;
	
	private AjaxMediator ajax;
	private ResourceBundle bundle;
	
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private StatusBar statusBar;
	
	private ProjectExplorer projectExplorer;
	private Writer writer;
	private StatusExplorer statusExplorer;
	private SideBar sideBar;
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		super.init();
		ajax = new DefaultAjaxMediator(this);
		bundle = CachedResourceBundles.getBundle(LanguageConstants.APPLICATION_RESOURCES_NAME_MAIN,
				LanguageConstants.LANGUAGE_EN, null);
		initGUI();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start() {
		super.start();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void stop() {
		super.stop();
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#destroy()
	 */
	@Override
	public void destroy() {
		//ajax.initiateAbort();
		ajax = null;
		this.setJMenuBar(null);
		this.getContentPane().removeAll();
		this.repaint();
		super.destroy();
	}
	
	protected void initGUI() {
		JPanel topContainerPanel = new JPanel(new BorderLayout());
		menuBar = new PrivateMenuBar(bundle).setup();
		this.setJMenuBar( menuBar );
		
		toolBar = new PrivateToolBar(bundle).setup();
		topContainerPanel.add(toolBar, BorderLayout.NORTH);
		topContainerPanel.add(setupStatusBar(), BorderLayout.SOUTH);
		topContainerPanel.add(setupCenterPanel(), BorderLayout.CENTER);
		
		this.add(topContainerPanel, BorderLayout.CENTER);
	}
	
	private JPanel setupStatusBar() {
		statusBar = new StatusBar();
		JPanel statusBarPanel = new JPanel(new BorderLayout());
		statusBarPanel.add(statusBar, BorderLayout.CENTER);
		statusBarPanel.setPreferredSize(new Dimension(0, 24));

		return statusBarPanel;
	}
	
	private JPanel setupCenterPanel() {
		projectExplorer = new ProjectExplorer();
		JPanel projectExplorerPanel = new JPanel(new BorderLayout());
		projectExplorerPanel.add(projectExplorer, BorderLayout.CENTER);
		projectExplorerPanel.setPreferredSize(new Dimension(this.getWidth()/3, 0));
		
		JPanel writerPanel = new JPanel(new BorderLayout());
		writer = new Writer();
		writerPanel.add(writer, BorderLayout.CENTER);
		
		JPanel statusExplorerPanel = new JPanel(new BorderLayout());
		statusExplorer = new StatusExplorer();
		statusExplorerPanel.add(statusExplorer, BorderLayout.CENTER);
		statusExplorerPanel.setPreferredSize(new Dimension(0, this.getHeight()/3));
		
		JPanel sideBarPanel = new JPanel(new BorderLayout());
		sideBar = new SideBar();
		sideBarPanel.add(sideBar, BorderLayout.CENTER);
		sideBarPanel.setPreferredSize(new Dimension(this.getWidth()/3, 0));
		
		JSplitPane sideBarSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, writerPanel, sideBarPanel);
		JSplitPane projectExporerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectExplorerPanel, sideBarSplitPane);
		JSplitPane statusExplorerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, projectExporerSplitPane, statusExplorerPanel);
		
		JPanel centerComponentsPanel = new JPanel(new BorderLayout());
		centerComponentsPanel.add(statusExplorerSplitPane);
		
		return centerComponentsPanel;
		
	}
	
	/**
	 * Stop all internet traffic and destroy application.
	 */
	protected void exitApplication() {
		stop();
		destroy();
	}
	
	/**
	 * Private class for creating menu bar. Created menu bar will be localized.
	 * 
	 * @author Miro Bezjak
	 */
	private class PrivateMenuBar {

		private ResourceBundle bundle;
		
		/**
		 * Constructor.
		 * 
		 * @param bundle {@link ResourceBundle} that contains information about menus
		 * 		that will be created.
		 */
		public PrivateMenuBar(ResourceBundle bundle) {
			this.bundle = bundle;
		}
		
		/**
		 * Creates and instantiates menu bar.
		 * 
		 * @return created menu bar
		 */
		public JMenuBar setup() {

			JMenuBar menuBar = new JMenuBar();
			JMenu menu;
			JMenu submenu;
			JMenuItem menuItem;
			String key;

			// File menu
			key = LanguageConstants.MENU_FILE;
			menu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(menu, key);
			
			// New sub menu
			key = LanguageConstants.MENU_FILE_NEW;
			submenu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(submenu, key);

			// New Project menu item
			key = LanguageConstants.MENU_FILE_NEW_PROJECT;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			submenu.add(menuItem);
			submenu.addSeparator();
			
			// New File menu item
			key = LanguageConstants.MENU_FILE_NEW_FILE;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			submenu.add(menuItem);
			
			// New Testbench menu item
			key = LanguageConstants.MENU_FILE_NEW_TESTBENCH;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			submenu.add(menuItem);
			
			// New Schema menu item
			key = LanguageConstants.MENU_FILE_NEW_SCHEMA;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			submenu.add(menuItem);
			
			// New Automat menu item
			key = LanguageConstants.MENU_FILE_NEW_AUTOMAT;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			submenu.add(menuItem);
			
			menu.add(submenu);
			
			// Open menu item
			key = LanguageConstants.MENU_FILE_OPEN;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menu.add(menuItem);
			menu.addSeparator();
			
			// Save menu item
			key = LanguageConstants.MENU_FILE_SAVE;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menu.add(menuItem);

			// Save All menu item
			key = LanguageConstants.MENU_FILE_SAVE_ALL;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menu.add(menuItem);
			menu.addSeparator();
			
			// Close menu item
			key = LanguageConstants.MENU_FILE_CLOSE;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menu.add(menuItem);

			// Close All menu item
			key = LanguageConstants.MENU_FILE_CLOSE_ALL;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menu.add(menuItem);
			menu.addSeparator();

			// Exit menu item
			key = LanguageConstants.MENU_FILE_EXIT;
			menuItem = new JMenuItem(bundle.getString(key));
			setMnemonicAndAccelerator(menuItem, key);
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					exitApplication();
				}
			});
			menu.add(menuItem);
			
			menuBar.add(menu);
			
			// Edit menu
			key = LanguageConstants.MENU_EDIT;
			menu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(menu, key);
			menuBar.add(menu);
			
			// View menu
			key = LanguageConstants.MENU_VIEW;
			menu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(menu, key);
			menuBar.add(menu);

			// Tool menu
			key = LanguageConstants.MENU_TOOLS;
			menu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(menu, key);
			menuBar.add(menu);

			// Help menu
			key = LanguageConstants.MENU_HELP;
			menu = new JMenu(bundle.getString(key));
			setMnemonicAndAccelerator(menu, key);
			menuBar.add(menu);
			
			return menuBar;
		}

		/**
		 * Sets mnemonic and accelerator for a given menu. If keys for mnemonic
		 * or accelerator (or both) does not exists then they will simply be ignored.
		 * 
		 * @param menu a menu where to set mnemonic and accelerator
		 * @param key a key containing menu's name 
		 */
		private void setMnemonicAndAccelerator(JMenuItem menu, String key) {
			/**
			 * For locating mnemonic or accelerator of a <code>menu</code> this method
			 * will simply append appropriate string to <code>key</code>.
			 * Information regarding such strings that will be appended can be found here:
			 * <ul>
			 * <li>LanguageConstants.MNEMONIC_APPEND</li>
			 * <li>LanguageConstants.ACCELERATOR_APPEND</li>
			 * </ul>
			 */
			
			// Set mnemonic
			try {
				String temp = bundle.getString(key + LanguageConstants.MNEMONIC_APPEND);
				if(temp.length() == 1) {
					menu.setMnemonic(temp.codePointAt(0));
				}
			} catch (RuntimeException e) {}
			
			// Set accelerator
			try {
				String temp = bundle.getString(key + LanguageConstants.ACCELERATOR_APPEND);
				String[] keys = temp.split("[+]");
				if(keys.length != 0) {
					int mask = 0;
					int keyCode = 0;
					for(String k : keys) {
						k = k.trim();
						if(k.equals("ctrl")) mask += KeyEvent.CTRL_DOWN_MASK;
						else if(k.equals("alt")) mask += KeyEvent.ALT_DOWN_MASK;
						else if(k.equals("shift")) mask += KeyEvent.SHIFT_DOWN_MASK;
						else if(k.length() == 1) keyCode = k.toUpperCase().codePointAt(0);
					}
					menu.setAccelerator(KeyStroke.getKeyStroke(keyCode, mask));
				}
			} catch (RuntimeException e) {}
		}
	}

	/**
	 * Private class for creating tool bar. Created toll bar will be localized.
	 * 
	 * @author Miro Bezjak
	 */
	private class PrivateToolBar {
		
		private ResourceBundle bundle;

		/**
		 * Constructor.
		 * 
		 * @param bundle {@link ResourceBundle} that contains information about menus
		 * 		that will be created.
		 */
		public PrivateToolBar(ResourceBundle bundle) {
			this.bundle = bundle;
		}
		
		/**
		 * Creates and instantiates tool bar.
		 * 
		 * @return created tool bar
		 */
		public JToolBar setup() {
			JToolBar toolBar = new JToolBar();
			toolBar.setPreferredSize(new Dimension(0, 24));
			
			return toolBar;
		}
	}

}