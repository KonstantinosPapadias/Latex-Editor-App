package model;

import javax.swing.JOptionPane;
import model.strategies.StableVersionsStrategy;
import model.strategies.VersionsStrategy;
import model.strategies.VolatileVersionsStrategy;

public class VersionsManager {
	private boolean enabled;
	private VersionsStrategy strategy;
	private String strategyType;
	private static VersionsManager versionsManagerInstance = null;
	
	public VersionsManager(VersionsStrategy versionsStrategy) {
		this.strategy = versionsStrategy;
		versionsManagerInstance = this;
	}
	
	public static VersionsManager getInstance() {
		if (versionsManagerInstance == null)
			versionsManagerInstance = new VersionsManager(new VolatileVersionsStrategy());
		return versionsManagerInstance;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}
	
	public void enableStrategy() {
		//String strategyType = latexEditorView.getStrategy();
		if(strategyType.equals("volatile") && strategy instanceof VolatileVersionsStrategy) {
			enable();
		}
		else if(strategyType.equals("stable") && strategy instanceof VolatileVersionsStrategy) {
			//allagh apo to ena sto allo
			VersionsStrategy newStrategy = new StableVersionsStrategy();
			newStrategy.setEntireHistory(strategy.getEntireHistory());
			strategy = newStrategy;
			enable();
		}
		else if(strategyType.equals("volatile") && strategy instanceof StableVersionsStrategy) {
			VersionsStrategy newStrategy = new VolatileVersionsStrategy();
			newStrategy.setEntireHistory(strategy.getEntireHistory());
			strategy = newStrategy;
			enable();
		}
		else if(strategyType.equals("stable") && strategy instanceof StableVersionsStrategy) {
			enable();
		}
	}

	public void changeStrategy() {
		//String strategyType = latexEditorView.getStrategy();
		if(strategyType.equals("stable") && strategy instanceof VolatileVersionsStrategy) {
			VersionsStrategy newStrategy = new StableVersionsStrategy();
			newStrategy.setEntireHistory(strategy.getEntireHistory());
			strategy = newStrategy;
			enable();
		}
		else if(strategyType.equals("volatile") && strategy instanceof StableVersionsStrategy) {
			VersionsStrategy newStrategy = new VolatileVersionsStrategy();
			newStrategy.setEntireHistory(strategy.getEntireHistory());
			strategy = newStrategy;
			enable();
		}
	}

	public void rollback() {
		if(isEnabled() == false) {
			JOptionPane.showMessageDialog(null, "Strategy is not enabled", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			Document doc = strategy.getVersion();
			if(doc == null) {
				JOptionPane.showMessageDialog(null, "No version available", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				strategy.removeVersion();
				//latexEditorView.setCurrentDocument(doc);
				DocumentManager.getInstance().setCurrentDocument(doc);
			}
		}
		
	}

	public VersionsStrategy getStrategy() {
		return strategy;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
}