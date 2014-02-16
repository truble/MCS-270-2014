/*
 * Persistence class (saving to file) for the TicTacToe game. 
 * 
 * Mike Hvidsten
 * Feb. 10, 2014
 */

package edu.gac.mcs270.hvidsten.tictactoe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

// Class does not ned to be instantiated, just has static 
//   methods to save the state of a game to a file. 
public class DBManager {
	//Create a file chooser
	private final static JFileChooser fc = new JFileChooser();
	// The output stream we will write to
	private static FileOutputStream fop = null;

	public static void saveGame(JPanel p, Position[][] board){
		File file = null;
		// Pop up the file dialog 
		//  returnVal is used to see if the user hit "okay" or
		//  "cancel"
		int returnVal = fc.showSaveDialog(p);
		if (returnVal == JFileChooser.APPROVE_OPTION) 
            file = fc.getSelectedFile();
		
		try {
			// Create an output stream to 
			//  write to the File. Could throw an exception
			//  (IOException) which must be caught.  
			fop = new FileOutputStream(file);

			// get the content in bytes and write to file
			for (Position[] boardRow: board){
				for (Position rowVal : boardRow){
					byte[] valInBytes = rowVal.getValue().getBytes();
					fop.write(valInBytes);
				}
			}
			// Flush the output buffers that remain to write to file
			//  and then close file.  
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			// Should probably do something more relevant 
			//  to the program here - like pop up a warning
			//  that program cannot write to the file. 
			e.printStackTrace();
		} finally {
			try { // make sure we close the file connection!!
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

