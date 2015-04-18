package kursa4;



import java.awt.Color;

import paint.Painter;
import process.Actor;
import process.Dispatcher;

public class Timer extends Actor {
	
	
	private double finishTime;
	private int nbrigad;
	private Model model;
	private Main gui;
	private  Painter p;


	   /////////////////
		//konstructor
	   /////////////////
	public Timer(String string, Main gui, Model model) {
		// TODO Auto-generated constructor stub
		this.nbrigad = gui.getChooseDatacountBrigad().getInt();
		setNameForProtocol(string);
		finishTime = gui.getChooseDataModelingTime().getDouble();
		this.model = model;
		this.gui = gui;
		p = new Painter(gui.getDiagram());
		p.placeToXY(0, 0);
		p.setColor(Color.gray);
	}

	
	   /////////////////
		//rule
	   /////////////////
	
	@Override
	protected void rule() {
		// TODO Auto-generated method stub
  while(getDispatcher().getCurrentTime() <= finishTime){
	  float m = Float.parseFloat(p.getDiagram().getVerticalMaxText());
	  p.fillRectAtXY( 0, m, 8, 2);
	  for (int i = 0; i < 3; i++) {
		holdForTime(8);
		getDispatcher().printToProtocol("×àñ = " + getTime());
		for (int j = 0; j <nbrigad; j++) {
			Brigada b = new Brigada("Brigada", gui, model);
			getDispatcher().addStartingActor(b);
		}	
		if(getDispatcher().getCurrentTime()%24==16){
			
			p.fillRectAtXY((float) getDispatcher().getCurrentTime(), m, 16, 2);
		}
		
	} 
	  
	  
  }
	}
	
	
	public double getTime(){
		
		return getDispatcher().getCurrentTime()%24;
	}


	public void setFinishTime(double finishTime2) {
		// TODO Auto-generated method stub
		this.finishTime = finishTime;
	}

}
