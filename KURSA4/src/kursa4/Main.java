package kursa4;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import widgets.ChooseData;
import widgets.ChooseRandom;
import widgets.experiments.ExperimentManager;
import widgets.stat.StatisticsManager;
import widgets.trans.TransProcessManager;
import widgets.Diagram;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import process.Dispatcher;
import process.IModelFactory;
import rnd.Negexp;
import rnd.Uniform;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btnStart;
	private JTextPane textPane;
	private Diagram diagramQueueForRozvantazh;
	
	private Diagram diagramCountWorkBrigad;
	private JCheckBox Protokolnakonsol;
	private Diagram diagramCountWorkNadurochno;
	private ChooseRandom chooseRandomIntervalDay;
	private ChooseRandom chooseRandomIntervalNight;
	private ChooseRandom chooseRandomChasRozvantazh;
	private ChooseData countBrigad;
	private ChooseData modelingTime;
	private JPanel panel;
	private StatisticsManager statisticsManager;
	private JPanel panel_2;
	private JTabbedPane tabbedPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    /////////////////////////////
	//TZ
	////////////////////////////
	private void TZ() {

		String str = "tz.html";
		URL url = getClass().getResource(str);
		try {
			textPane.setPage(url);
			textPane.setEditable(false);
			textPane.setHighlighter(null);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Problems with file " + str);
		}
	}
	////////////////////////////////////////////
	//startTest
	////////////////////////////////////////////
	private void startTest() {
		
	
		Dispatcher dispatcher = new Dispatcher();			
		dispatcher.addDispatcherFinishListener(
				()->btnStart.setEnabled(true));
		IModelFactory factory = (d)-> new Model(d, this);
		Model model = (Model) factory.createModel(dispatcher);
		model.initForTest();
		dispatcher.start();
		btnStart.setEnabled(false);
		diagramQueueForRozvantazh.clear();
		diagramCountWorkNadurochno.clear();
		diagramCountWorkBrigad.clear();
			

	}
		
	
	

	
	/**
	 * Create the frame.
	 * 
	 */
	public Main() throws IOException,HeadlessException {
	
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 272, 539);
		contentPane.add(panel);
		panel.setLayout(null);
		
		modelingTime = new ChooseData();
		modelingTime.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (getPanel().isShowing()){
					getDiagram().setHorizontalMaxText(modelingTime.getText());
					getDiagram_2().setHorizontalMaxText(modelingTime.getText());
					getDiagram_3().setHorizontalMaxText(modelingTime.getText());
				}
			}
			
		});
		modelingTime.setTitle("\u0427\u0430\u0441 \u043C\u043E\u0434\u0435\u043B\u044E\u0432\u0430\u043D\u043D\u044F");
		modelingTime.setText("300");
		modelingTime.setBounds(10, 247, 252, 46);
		
		panel.add(modelingTime);
		
		chooseRandomIntervalDay = new ChooseRandom();
		chooseRandomIntervalDay.setRandom(new Negexp(1));
		chooseRandomIntervalDay.setTitle("\u0406\u043D\u0442\u0435\u0440\u0432\u0430\u043B \u043F\u0440\u0438\u0431\u0443\u0442\u0442\u044F \u043C\u0430\u0448\u0438\u043D \u0432\u0434\u0435\u043D\u044C ");
		chooseRandomIntervalDay.setBounds(10, 11, 252, 46);
		panel.add(chooseRandomIntervalDay);
		
		chooseRandomIntervalNight = new ChooseRandom();
		chooseRandomIntervalNight.setRandom(new Negexp(3));
		chooseRandomIntervalNight.setTitle("\u0406\u043D\u0442\u0435\u0440\u0432\u0430\u043B \u043F\u0440\u0438\u0431\u0443\u0442\u0442\u044F \u043C\u0430\u0448\u0438\u043D \u0432\u043D\u043E\u0447\u0456");
		chooseRandomIntervalNight.setBounds(10, 68, 252, 46);
		panel.add(chooseRandomIntervalNight);
		
		chooseRandomChasRozvantazh = new ChooseRandom();
		chooseRandomChasRozvantazh.setRandom(new Uniform(1,3));
		chooseRandomChasRozvantazh.setTitle("\u0427\u0430\u0441 \u0440\u043E\u0437\u0432\u0430\u043D\u0442\u0430\u0436\u0435\u043D\u043D\u044F");
		chooseRandomChasRozvantazh.setBounds(10, 125, 252, 46);
		panel.add(chooseRandomChasRozvantazh);
		
		countBrigad = new ChooseData();
		countBrigad.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (getPanel().isShowing()){
					getDiagram().setVerticalMaxText(countBrigad.getText());
					getDiagram_2().setVerticalMaxText(countBrigad.getText());
					getDiagram_3().setVerticalMaxText(countBrigad.getText());
				}
			}
			
		});
		countBrigad.setTitle("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u0431\u0440\u0438\u0433\u0430\u0434");
		countBrigad.setBounds(10, 190, 252, 46);
		countBrigad.setText("5");
		panel.add(countBrigad);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(292, 11, 539, 539);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u0422\u0435\u0441\u0442 \u041C\u043E\u0434\u0435\u043B\u0456", null, panel_1, null);
		panel_1.setLayout(null);
		
		diagramCountWorkBrigad = new Diagram();
		diagramCountWorkBrigad.setPainterColor(Color.BLUE);
		diagramCountWorkBrigad.setTitleText("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u043F\u0440\u0430\u0446\u044E\u044E\u0447\u0438\u0445 \u0431\u0440\u0438\u0433\u0430\u0434");
		diagramCountWorkBrigad.setBounds(10, 174, 514, 140);
		diagramCountWorkBrigad.setVerticalMaxText(countBrigad.getText());
		diagramCountWorkBrigad.setHorizontalMaxText(modelingTime.getText());
		panel_1.add(diagramCountWorkBrigad);
				
		diagramQueueForRozvantazh = new Diagram();
		diagramQueueForRozvantazh.setPainterColor(Color.RED);
		diagramQueueForRozvantazh.setTitleText("\u0427\u0435\u0440\u0433\u0430 \u0430\u0432\u0442\u043E\u043C\u043E\u0431\u0456\u043B\u0456\u0432 \u043D\u0430 \u0440\u043E\u0437\u0432\u0430\u043D\u0442\u0430\u0436\u0435\u043D\u043D\u044F");
		diagramQueueForRozvantazh.setBounds(10, 11, 514, 152);
		diagramQueueForRozvantazh.setHorizontalMaxText(modelingTime.getText());
		diagramQueueForRozvantazh.setVerticalMaxText(countBrigad.getText());
		panel_1.add(diagramQueueForRozvantazh);
		
		diagramCountWorkNadurochno = new Diagram();
		diagramCountWorkNadurochno.setPainterColor(Color.BLUE);
		diagramCountWorkNadurochno.setTitleText("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u043F\u0440\u0430\u0446\u044E\u044E\u0447\u0438\u0445 \u043D\u0430\u0434\u0443\u0440\u043E\u0447\u043D\u043E");
		diagramCountWorkNadurochno.setHorizontalMaxText(modelingTime.getText());
		diagramCountWorkNadurochno.setVerticalMaxText(countBrigad.getText());
		GridBagLayout gbl_diagramCountWorkNadurochno = (GridBagLayout) diagramCountWorkNadurochno.getLayout();
		gbl_diagramCountWorkNadurochno.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_diagramCountWorkNadurochno.rowHeights = new int[]{0, 0, 0, 0};
		gbl_diagramCountWorkNadurochno.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_diagramCountWorkNadurochno.columnWidths = new int[]{0, 0, 0};
		diagramCountWorkNadurochno.setBounds(10, 325, 514, 153);
		panel_1.add(diagramCountWorkNadurochno);
		
		Diagram diagram_4 = new Diagram();
		GridBagLayout gridBagLayout = (GridBagLayout) diagram_4.getLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		diagram_4.setBounds(-42, 152, 247, 149);
		diagramCountWorkNadurochno.getDiagramPanel().add(diagram_4);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startTest();
				}
		});
		btnStart.setBounds(435, 488, 89, 23);
		panel_1.add(btnStart);
		
		Protokolnakonsol = new JCheckBox("\u041F\u0440\u043E\u0442\u043E\u043A\u043E\u043B \u043D\u0430 \u043A\u043E\u043D\u0441\u043E\u043B\u044C");
		Protokolnakonsol.setBounds(10, 488, 211, 23);
		panel_1.add(Protokolnakonsol);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430", null, panel_2, null);
		panel_2.setLayout(new CardLayout(0, 0));
		
		statisticsManager = new StatisticsManager();
		statisticsManager.setFactory((d)-> new Model(d, this));
		panel_2.add(statisticsManager, "name_190032480611010");
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("\u0417\u0430\u043B\u0435\u0436\u043D\u043E\u0441\u0442\u0456", null, panel_3, null);
		panel_3.setLayout(new CardLayout(0, 0));
		
		ExperimentManager experimentManager = new ExperimentManager();
		experimentManager.setFactory((d)-> new Model(d, this));
		panel_3.add(experimentManager, "name_267290904423649");
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("\u041F\u0435\u0440\u0435\u0445. \u041F\u0440\u043E\u0446\u0435\u0441\u0438", null, panel_4, null);
		panel_4.setLayout(new CardLayout(0, 0));
		
		TransProcessManager transProcessManager = new TransProcessManager();
		transProcessManager.setFactory((d)-> new Model(d, this));
		panel_4.add(transProcessManager, "name_190312513958933");
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("T\u0417", null, panel_5, null);
		panel_5.setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane, "name_191458976318107");
		
		 textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		TZ();
		
		
	}
	public JCheckBox getCheckBox() {
		return Protokolnakonsol;
	}
	public Diagram getDiagram_3() {
		return diagramCountWorkNadurochno;
	}
	public Diagram getDiagram_2() {
		return diagramCountWorkBrigad;
	}
	public Diagram getDiagram() {
		return diagramQueueForRozvantazh;
	}
	public ChooseRandom getChooseRandomIntervalDay() {
		return chooseRandomIntervalDay;
	}
	public ChooseRandom getChooseRandomIntervalNight() {
		return chooseRandomIntervalNight;
	}
	public ChooseRandom getChooseRandomChasRozvantazh() {
		return chooseRandomChasRozvantazh;
	}
	public ChooseData getChooseDatacountBrigad() {
		return countBrigad;
	}
	public ChooseData getChooseDataModelingTime() {
		return modelingTime;
	}
	public JPanel getPanel() {
		return panel;
	}

	
}
