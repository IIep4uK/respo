package kursa4;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.QueueForTransactions;
import widgets.ChooseRandom;

public class GeneratorAvto extends Actor {

	
	private Timer timer;
	private ChooseRandom rndDay;
	private ChooseRandom rndNight;
	private QueueForTransactions queue;
	private BooleanSupplier isAvto;
	private double finishTime;
     /////////////////
	//konstructor
   /////////////////
	public GeneratorAvto(String string, Main gui, Model model) {
		// TODO Auto-generated constructor stub
		timer = model.getTimer();
		rndDay = gui.getChooseRandomIntervalDay();
		rndNight = gui.getChooseRandomIntervalNight();
		queue = model.getQueueAvto();
		finishTime = gui.getChooseDataModelingTime().getDouble();
	}
	   /////////////////
	  //rule
	 //////////////////
	@Override
	protected void rule() {
		ChooseRandom rnd = null;
		isAvto = ()->queue.size()>0;
		 while(getDispatcher().getCurrentTime() <= finishTime){
		 if(timer.getTime()>=8 && timer.getTime()<=16){
			 rnd = rndDay;}
		 
			 else{
				 rnd = rndNight;
			 }
		 holdForTime(rnd.next());
		 getDispatcher().printToProtocol("  " + getNameForProtocol() + " додає час");
		 queue.add(getDispatcher().getCurrentTime());
			 
		 }
		 }
	public void setFinishTime(double finishTime2) {
		// TODO Auto-generated method stub
		this.finishTime =finishTime;
	}
 
	}


