package cg.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import cg.draw2D.Model;
import cg.draw2D.View;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;

/**
 * Der SaveButtonListener speichert auf Knopfdruck eine SVG-Repraesentation der Objekte ab
 * 
 * @author rkunze
 * @version 1.0 (ML 17.05.2004)
 */
public class SaveButtonListener implements ActionListener {

    private Model m;
    private View view;
    /**
     * Erzeugt einen Listener, der Resetable Objekte in den Ursprungszustand
     * versetzt.
     * @param m Model der Applikation
     * @param view View der Applikation, an der der FileDialog gehaengt wird.
     */
    public SaveButtonListener(Model m, View view) {
        this.m = m;
        this.view = view;
    }

    /**
     * Wird aufgerufen, wenn der User auf den SaveButton gedrueckt hat.
     * @param e der vom Button ausgeloeste Event
     */
    public void actionPerformed(ActionEvent e) {
 		JFileChooser fc = new JFileChooser();
 		fc.setFileFilter(new SVGFileFilter());
 		int result = fc.showSaveDialog(view);
 		if (result == JFileChooser.APPROVE_OPTION) {
 			try{
				java.io.FileWriter out = new java.io.FileWriter(fc.getSelectedFile());
				out.write(m.toSVG(640, 500));
				out.close();
 			} catch (IOException ex)
 			{
 				ex.printStackTrace();
 			}
 		}
 		
    }
    
    private class SVGFileFilter extends FileFilter{
    	
    	public boolean accept(File f) {
		String fname = f.getName();
		String x = fname.substring(fname.lastIndexOf('.')+1).toLowerCase();
    		if (f.isDirectory()) return true;
    		else
    		  if (x != null){
    		  	if (x.equals("svg")) return true;	
    		  }
    		  return false;
    	}
    	
    	public String getDescription(){
    		return "SVG";
    	}
    }
}
