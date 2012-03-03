package gui;
import input.Event;
import input.InputParser;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import output.GenerateCalendar;
import javax.swing.JLabel;

import processor.EndTimeSorter;
import processor.KeyWordFinder;
import processor.NameSorter;
import processor.Processor;
import processor.StartTimeSorter;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Button;
import java.awt.TextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class MainWindow {

	private JFrame frame;
	private List<String> inputFiles;
	private Processor processor;
	private TextField keywordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		inputFiles = new ArrayList<String>();
		processor = new Processor();		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 633, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnChooseInputFile = new JButton("Load Input File");
		btnChooseInputFile.setBounds(179, 0, 171, 25);
		btnChooseInputFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
			    // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
			    int returnVal = chooser.showOpenDialog(frame);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("You chose to open this file: " +
			            chooser.getSelectedFile().getName());
			       
			    inputFiles.add(chooser.getSelectedFile().getName());
			    }
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnChooseInputFile);
		
		keywordField = new TextField();
		keywordField.setBounds(202, 90, 186, 20);
		frame.getContentPane().add(keywordField);
		
		JButton btnSortByName = new JButton("Sort By Name");
		btnSortByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processor.addSorter(new NameSorter (true)); 				
			}
		});
		btnSortByName.setBounds(10, 156, 202, 25);
		frame.getContentPane().add(btnSortByName);
		
		JButton btnKeyWordFilter = new JButton("Search for Keyword");
		btnKeyWordFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				List <String> s = new ArrayList<String> ();
				s.add(keywordField.getText());
				processor.addFinder(new KeyWordFinder (s, true));
			}
		});
		btnKeyWordFilter.setBounds(10, 85, 186, 25);
		frame.getContentPane().add(btnKeyWordFilter);
		
		JButton btnReverseNameSort = new JButton("Reverse Name Sort");
		btnReverseNameSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processor.addSorter(new NameSorter (false)); 
			}
		});
		btnReverseNameSort.setBounds(12, 193, 200, 25);
		frame.getContentPane().add(btnReverseNameSort);
		
		JButton btnSortByStart = new JButton("Sort By Start Time");
		btnSortByStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processor.addSorter(new StartTimeSorter (true)); 
			}
		});
		btnSortByStart.setBounds(10, 230, 202, 25);
		frame.getContentPane().add(btnSortByStart);
		
		JButton btnReverseStartTime = new JButton("Reverse Start Time Sort");
		btnReverseStartTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processor.addSorter(new StartTimeSorter (false)); 
			}
		});
		btnReverseStartTime.setBounds(10, 269, 202, 25);
		frame.getContentPane().add(btnReverseStartTime);
		
		JButton btnNewButton = new JButton("Sort By End Time");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				processor.addSorter(new EndTimeSorter (true)); 
			}
		});
		btnNewButton.setBounds(249, 156, 213, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Reverse Sort By End Time");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processor.addSorter(new EndTimeSorter (false)); 
			}
		});
		btnNewButton_1.setBounds(249, 193, 213, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Search Without Keyword");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List <String> s = new ArrayList<String> ();
				s.add(keywordField.getText());
				processor.addFinder(new KeyWordFinder (s, false));
			}
		});
		btnNewButton_2.setBounds(396, 85, 235, 25);
		frame.getContentPane().add(btnNewButton_2);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Preview Web Page");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Read in input files
				List<InputParser> inputParsers = new ArrayList<InputParser>();
				for(String filename : inputFiles){
					inputParsers.add(InputParser.ParserFactory.generate(filename));
				}
				List<Event> eventList = new ArrayList<Event>();
				for(InputParser p : inputParsers){
					eventList.addAll(p.getListOfEvents());
				}
				
				 //output to calendar
		        int year = 2;
		        int month = 0;
		        int date = 1;
		        GregorianCalendar start = new GregorianCalendar(year, month, date);
		        int eYear = 2012;
		        int eMonth = 1;
		        int eDate = 30;
		        GregorianCalendar end = new GregorianCalendar(eYear, eMonth, eDate);
		        
		        processor.initializeEventList(eventList);
		        eventList = processor.process(); 
				for (Event e: eventList){
					System.out.println (e.toString());
				}
		        
		        GenerateCalendar o = new GenerateCalendar(eventList);
		        //o.dayWeekMonth(start, end);
		        //o.sortedList();
		        //o.conflictList();
		        o.generate(start,end);
				
				//Display HTML preview of Calendar
		        File file = new File("Calendar.html");
		            try {
						HTMLPreview foo = new HTMLPreview(file.toURI().toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		});
		mnNewMenu.add(mntmNewMenuItem);		
	}
}
