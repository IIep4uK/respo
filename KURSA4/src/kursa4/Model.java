package kursa4;

import java.util.HashMap;
import java.util.Map;




import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import widgets.experiments.IExperimentable;
import widgets.stat.IStatisticsable;
import widgets.trans.ITransProcesable;



public class Model implements IStatisticsable, IExperimentable,
ITransProcesable {

	private Dispatcher dispatcher;
	private Main gui;
	private Timer timer;
	private GeneratorAvto avto;
	private QueueForTransactions<Double> queueAvto;
	private QueueForTransactions<Brigada> queueBrigad;
	private QueueForTransactions<Brigada> queueBrigadNadurochno;
	private Histo histoForBrigadWait;
	private DiscretHisto dHistoAvtoServiceTime;
	private double finishTime;

	
	public Model(Dispatcher d, Main g) {
		// TODO Auto-generated constructor stub
		if (d == null || g == null) {
			System.out.println("Не визначено диспетчера або GUI для RgrModel");
			System.out.println("Подальша робота неможлива");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		// Передаємо акторів до стартового списку диспетчера
		componentsToStartList();
	}

	private void componentsToStartList() {
		// TODO Auto-generated method stub
			// Передаємо акторів диспетчеру
			dispatcher.addStartingActor(getTimer());
			dispatcher.addStartingActor(getAvto());
				
			}

	
	//черга авто
	public QueueForTransactions getQueueAvto() {
		if(queueAvto == null){
			queueAvto = new QueueForTransactions("QueueAvto",dispatcher);
		}
		return queueAvto;
	}
	
	public QueueForTransactions getQueueBrigad() {
		if(queueBrigad == null){
			queueBrigad = new QueueForTransactions<Brigada>("QueueBrigad",dispatcher);
		}
		return queueBrigad;
	}
	
	public QueueForTransactions getQueueBrigadNadurochno() {
		if(queueBrigadNadurochno == null){
			queueBrigadNadurochno = new QueueForTransactions<Brigada>("QueueBrigadNadurochno",dispatcher);
		}
		return queueBrigadNadurochno;
	}
	
	
	public GeneratorAvto getAvto(){
		if( avto ==null){
			avto = new GeneratorAvto("Avto", gui, this);
		}
		return avto;
	}
	
	public Timer getTimer() {
		// TODO Auto-generated method stub
		if (timer==null){
			timer = new Timer("Timer", gui, this);
		}
		return timer;
	}
	   /////////////////
		//initForTest
	   /////////////////
	public void initForTest() {
		// TODO Auto-generated method stub
		if(gui.getCheckBox().isSelected()){
			dispatcher.setProtocolFileName("Console");
		}
		getQueueAvto().setPainter(gui.getDiagram().getPainter());
		getQueueBrigad().setPainter(gui.getDiagram_2().getPainter());
		getQueueBrigadNadurochno().setPainter(gui.getDiagram_3().getPainter());
	}

	public Histo getHistoBrigadWait() {
		// TODO Auto-generated method stub
		if (histoForBrigadWait == null) {
			histoForBrigadWait = new Histo();
		}
		return  histoForBrigadWait;
	}
	

	public DiscretHisto getHistoAvtoServiceTime() {
		// TODO Auto-generated method stub
		if (dHistoAvtoServiceTime == null) {
			dHistoAvtoServiceTime = new DiscretHisto();
		}
		return dHistoAvtoServiceTime;
	}

	@Override
	public Map<String, Double> getTransResult() {
		// TODO Auto-generated method stub
		Map<String, Double> transMap = new HashMap<>();
		transMap.put("Черга Авто", getQueueAvto().getAccumAverage());
		transMap.put("Кількість бригад", getQueueBrigad().getAccumAverage());
		transMap.put("Бригади працюючі надурочно", getQueueBrigadNadurochno().getAccumAverage());
		return transMap;
	}

	@Override
	public void initForTrans(double finishTime) {
		// TODO Auto-generated method stub
		getAvto().setFinishTime(finishTime);
		getTimer().setFinishTime(finishTime);/////////////////////////////////////////////////////////
		gui.getChooseDataModelingTime().setDouble(finishTime);
	}

	@Override
	public void resetTransAccum() {
		// TODO Auto-generated method stub
		getQueueAvto().resetAccum();
		getQueueBrigad().resetAccum();
		getQueueBrigadNadurochno().resetAccum();
	}

	@Override
	public Map<String, Double> getResultOfExperiment() {
		// TODO Auto-generated method stub
		Map<String, Double> resultMap = new HashMap<>();
		resultMap.put("Час простою авто від їх кількості", getHistoAvtoServiceTime()
				.getAverage());
		resultMap.put("Час простою бригад від кількості авто", getHistoBrigadWait()
				.average());
		
		return resultMap;
	}
	
	
	

	@Override
	public void initForExperiment(double factory) {
		// TODO Auto-generated method stub
	//	queueBrigad.setNumberOfClones((int) factory);
	}

	@Override
	public Map<String, IHisto> getStatistics() {
		// TODO Auto-generated method stub
		Map<String, IHisto> map = new HashMap<>();
		map.put("Гістограма для часу очікування бригад",getHistoBrigadWait());
		map.put("Гістограма для часу обслуговування Авто", getHistoAvtoServiceTime());
		
		return map;
	}

	@Override
	public void initForStatistics() {
		// TODO Auto-generated method stub
		
	}
	}


