package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import generator.Generator;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import utilities.Tools;

public class HuffmanGUI extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final double version = 0.2;
	
	public void init() {
		
		try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

		
		System.out.println("Applet Initialising");
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
		});
	}
	
	public void start() {
		System.out.println("Applet starting");
		}
		public void stop() {
		System.out.println("Applet stopping");
		}
		public void destroy() {
		System.out.println("Applet destroyed");
		}
    
    private void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        
        DisplayPanel displayPanel = new DisplayPanel();
        InputPanel inputPanel = new InputPanel(displayPanel);
        
        getContentPane().add(inputPanel, BorderLayout.WEST);
        getContentPane().add(displayPanel, BorderLayout.EAST);

    }

}

class InputPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Generator generator = new Generator();
	
	private JLabel baseInputLabel;
	private JTextArea baseInput;
	private JTextArea textInput;
	private JButton submitButton;
	private DisplayPanel displayPanel;
	
	GridBagConstraints c;
	
	public InputPanel(DisplayPanel displayPanel) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		this.displayPanel = displayPanel;
		
		initComponents();
	}
	
	private void initComponents() {
		baseInputLabel = new JLabel("Choose base for encryption:");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		this.add(baseInputLabel,c);
		baseInput = new JTextArea("2",1,1);
		baseInput.setFont(new Font("Arial",Font.BOLD,20));
		c.gridx = 1;
		c.gridy = 0;
		this.add(baseInput, c);
		
		
		textInput = new JTextArea("Enter a sample using the alphabet to be converted here.",10,80);
		textInput.setLineWrap(true);
		textInput.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane( textInput );
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 5;
		this.add(scrollPane, c);
		
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridheight = 2;
		this.add(submitButton, c);
	}
	
	private int getAlphabet() {
		String inputTextValue = textInput.getText();
		
		if (inputTextValue.isEmpty()) {
			displayPanel.displayOutput("Insert a non-empty string.");
			return 0;
		}
		
		Map<String, Double> alphabet = generator.generateAlphabet(inputTextValue);
		Map<String, String> hCode = generator.createHuffmanCode(alphabet);
		displayPanel.displayOutput(hCode);
		return 1;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		//Draw Text
		g.drawString("Huffman Code Generator v"+HuffmanGUI.version, 10, 20);
		g.drawString("Author: James Lucas",10,40);		
	}
	
	public void actionPerformed(ActionEvent e) {
		if ("submit".equals(e.getActionCommand())) {
			int newBase = 0;
			
			try {
				newBase = Integer.parseInt(baseInput.getText());
			} catch(NumberFormatException e1) {
				displayPanel.displayOutput("Insert a valid base.");
			}
			// Check for valid base
			if (newBase <= 1 || newBase >= 10) {
				displayPanel.displayOutput("Insert a valid base. (Only values 2,...,9 currently supported).");
			}
			else {
				generator.setBaseD(newBase);
				getAlphabet();
			}
		}
	}
}

class DisplayPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextArea textDisplay;
	public DisplayPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		initComponents();
	}
	
	private void initComponents() {
		textDisplay = new JTextArea("Huffman code map will appear here.",30,40);
		textDisplay.setLineWrap(true);
		textDisplay.setWrapStyleWord(true);
		textDisplay.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textDisplay);
		this.add(scrollPane);
	}
	
	public void displayOutput(Map<String,String> hCode) {
		String outputString = Tools.GenerateOutputString(hCode);
		
		textDisplay.setText(outputString);
	}
	
	public void displayOutput(String outputString) {
		textDisplay.setText(outputString);
	}
}
