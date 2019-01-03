import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
public class TextEditor extends JFrame {
	
	private JTextArea area= new JTextArea(20,120);
	private JFileChooser dialog= new JFileChooser(System.getProperty("user.dir"));
	private String currentfile= "untitled";
	private boolean changed= false;
	

	public TextEditor() {
		area.setFont(new Font("monospaced", Font.PLAIN,12));
		JScrollPane scroll= new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		JMenuBar JMB= new JMenuBar();
		setJMenuBar(JMB);
		JMenu file= new JMenu("File");
		JMenu edit= new JMenu("Edit");
		JMB.add(file);
		JMB.add(edit);
		file.add(New);
		file.add(Open);
		file.add(Save);
		file.add(SaveAs);
		file.add(Quit);
		file.addSeparator();
		
		/*for(int i=0;i<4;i++) {
			file.getItem(i).setIcon(null);	
		}*/
		
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		
		JToolBar tool= new JToolBar();
		add(tool,BorderLayout.NORTH);
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		
		JButton cut= tool.add(Cut);
		JButton cop= tool.add(Copy);
		JButton pas= tool.add(Paste);
		cut.setText(null);
		//cut.setIcon(new ImageIcon("cut.gif"));
		cop.setText(null);
		//cop.setIcon(new ImageIcon("copy.gif"));
		pas.setText(null);
		//pas.setIcon(new ImageIcon("paste.gif"));
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);
		setTitle(currentfile);
		setVisible(true);
						
	}
	
	private KeyListener k1= new KeyAdapter() {
		
		public void keyPressed(KeyEvent e) {
			changed= true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
			
		}
	};
	
	Action Open= new AbstractAction("Open") {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveOld();
			if(dialog.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);			
		}
	};
	
	Action Save= new AbstractAction("Save") {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!currentfile.equals("untitled")) {
				saveFile(currentfile);
			}else {
				SaveFileAs();
			}
			// TODO Auto-generated method stub
			
		}
	};
	
	Action SaveAs= new AbstractAction("Save As") {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveFileAs();
			// TODO Auto-generated method stub
			
		}
	};
	
	Action Quit= new AbstractAction("Quit") {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveOld();
			System.exit(0);
			// TODO Auto-generated method stub
			
		}
	};
	
	Action New= new AbstractAction("New") {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SaveOld();
			area.setText("");
			currentfile="untitled";
			setTitle(currentfile);
			Save.setEnabled(false);
			SaveAs.setEnabled(false);
			// TODO Auto-generated method stub
			
		}
	};
	
	ActionMap m= area.getActionMap();
	Action Cut= m.get(DefaultEditorKit.cutAction);
	Action Copy= m.get(DefaultEditorKit.copyAction);
	Action Paste= m.get(DefaultEditorKit.pasteAction);
	
	private void SaveFileAs() {
		
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
			saveFile(dialog.getSelectedFile().getAbsolutePath());
		}
		
	}
	
	private void SaveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentfile+" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
			saveFile(currentfile);	
			}     
		}
		
	}
	
	private void readInFile(String filename) {
		
		try {
			FileReader r= new FileReader(filename);
		}catch(IOException e){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Editor cannot open file named"+filename);
		}
		
	}
	
	private void saveFile(String filename) {
		
		try {
			FileWriter w= new FileWriter(filename);
			area.write(w);
			w.close();
			currentfile= filename;
			setTitle(currentfile);
			changed= false;
			Save.setEnabled(false);
			
		}catch(IOException e) {
			
		}
		
	}
	
	
	
	public static void main(String args[])
	{
		new TextEditor();
	}	
}
