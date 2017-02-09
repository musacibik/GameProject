/**
 * 
 */
package main.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author JusticeLeague
 *
 */
public class FileOperations {

	private static String userDir = System.getProperty("user.home");
	private String loadDialogTitle = "XML File Chooser";
	private String saveDialogTitle = "XML File Saver";

	private JFileChooser loadChooser;
	private JFileChooser saveChooser;

	public FileOperations(){
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Extensible Markup Language (.xml)", "xml");

		LookAndFeel previousLF = UIManager.getLookAndFeel();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			loadChooser = new JFileChooser(userDir + "/Desktop");
			loadChooser.setDialogTitle(loadDialogTitle);
			loadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			loadChooser.setMultiSelectionEnabled(false);
			loadChooser.setFileFilter(fileFilter);

			saveChooser = new JFileChooser(userDir + "/Desktop");
			saveChooser.setDialogTitle(saveDialogTitle);
			saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(System.getProperty("os.name").equals("Windows 7") || System.getProperty("os.name").equals("Windows 8") || System.getProperty("os.name").equals("Windows 10")){
				saveChooser.setSelectedFile(new File(System.getProperty("user.dir")+"/XMLFile.xml"));
			}else{
				saveChooser.setSelectedFile(new File("XMLFile.xml"));
			}
			saveChooser.addChoosableFileFilter(fileFilter);
			saveChooser.setFileFilter(fileFilter);	

			UIManager.setLookAndFeel(previousLF);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException er) {
			er.printStackTrace();
		}
	}

	public int showLoad(){
		return loadChooser.showOpenDialog(null);
	}

	public int showSave(){
		return saveChooser.showSaveDialog(null);
	}

	/**
	 * @return the loadChooser
	 */
	public JFileChooser getLoadChooser() {
		return loadChooser;
	}

	/**
	 * @param loadChooser the loadChooser to set
	 */
	public void setLoadChooser(JFileChooser loadChooser) {
		this.loadChooser = loadChooser;
	}

	/**
	 * @return the saveChooser
	 */
	public JFileChooser getSaveChooser() {
		return saveChooser;
	}

	/**
	 * @param saveChooser the saveChooser to set
	 */
	public void setSaveChooser(JFileChooser saveChooser) {
		this.saveChooser = saveChooser;
	}

}

