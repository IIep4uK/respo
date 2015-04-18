package kursa4;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;
import widgets.ChooseRandom;

public class Brigada extends Actor{
	
	
	private BooleanSupplier isAvtoOrFinishWork;
	private BooleanSupplier finishWork;
	private Randomable rnd;
	private Main gui;
	private Model model;
	private QueueForTransactions<Double> quequeToBrigada;
	
	
	
	
	public Brigada(String string, Main gui, Model model) {
		// TODO Auto-generated constructor stub
		this.gui = gui;
		this.model = model;
		quequeToBrigada = model.getQueueAvto();
		rnd = gui.getChooseRandomChasRozvantazh();
		setHistoForActorWaitingTime(model.getHistoBrigadWait());
	
	}



	

	@Override
	protected void rule() {
		double nowTime = getDispatcher().getCurrentTime();
		finishWork = ()->nowTime+8==getDispatcher().getCurrentTime();
		 isAvtoOrFinishWork = ()-> quequeToBrigada.size()>0||finishWork.getAsBoolean();
		// TODO Auto-generated method stub
		
			//	стартувати генератор бригад
			try {
				waitForCondition(isAvtoOrFinishWork, "Має бути вантажівка в черзі");
				//працюючих
			} catch (DispatcherFinishException e) {
				// TODO Auto-generated catch block
				return;
			}
			if(finishWork.getAsBoolean()){
				return;
			}
			// Забираємо вантажівку на обслуговування
			double bt =	quequeToBrigada.removeFirst();
				
				model.getQueueBrigad().add(this);
				double holdTime = rnd.next();
				holdForTimeOrWaitForCondition(holdTime, finishWork, "чекаємо кінця зміни");
				model.getQueueBrigad().remove(this);
			if(finishWork.getAsBoolean()){
				
				//стати в чергу надурочно
				model.getQueueBrigadNadurochno().add(this);
				getDispatcher().printToProtocol(getNameForProtocol() + " стає в чергу надурочно");
				double nadTime = getActivateTime()-getDispatcher().getCurrentTime();
				
				holdForTime(nadTime);
				model.getQueueBrigadNadurochno().remove(this);	
			}
		
			double time = getDispatcher().getCurrentTime()-bt;
			model.getHistoAvtoServiceTime().add(time);
	}
	
}
