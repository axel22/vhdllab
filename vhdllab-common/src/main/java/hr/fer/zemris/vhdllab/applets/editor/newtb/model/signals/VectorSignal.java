package hr.fer.zemris.vhdllab.applets.editor.newtb.model.signals;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hr.fer.zemris.vhdllab.applets.editor.newtb.enums.VectorDirection;
import hr.fer.zemris.vhdllab.applets.editor.newtb.exceptions.UniformSignalChangeException;
import hr.fer.zemris.vhdllab.applets.editor.newtb.exceptions.UniformSignalException;
import hr.fer.zemris.vhdllab.applets.editor.newtb.listeners.SignalChangeListener;

/**
 * 
 * @author Davor Jurisic
 *
 */
public class VectorSignal extends EditableSignal {
	
	protected VectorDirection direction = null;
	
	public VectorSignal(String name, short dimension, VectorDirection direction) throws UniformSignalException {
		this(name, dimension, direction, null);
	}
	
	public VectorSignal(String name, short dimension, VectorDirection direction, SignalChangeListener listener) throws UniformSignalException {
		super(name, dimension, listener);
		
		if(direction == null)
			throw new UniformSignalException("Direction ne smije biti null");
		
		this.direction = direction;
	}
	
	public VectorDirection getDirection() {
		return direction;
	}
	
	public void deleteSignalChangeValues(int componentIndex) throws UniformSignalException, UniformSignalChangeException {
		if(componentIndex < 0 || componentIndex >= this.dimension) {
			throw new UniformSignalException("componentIndex ne odgovara dimenziji signala.");
		}
		
		if(this.signalChangeListener != null) {
			this.signalChangeListener.suspendSignalChangedEvents();
		}
		for(SignalChange sc : this.changes.values()) {
			StringBuilder sb = new StringBuilder(sc.getSignalValue());
			sb.setCharAt(componentIndex, '0');
			sc.setSignalValue(sb.toString());
		}
		this.optimizeSignalChangeValues();
		if(this.signalChangeListener != null) {
			this.signalChangeListener.resumeSignalChangedEvents();
		}
	}
	
	public void setSignalChangeValue(long time, String signalValue, int componentIndex) throws UniformSignalChangeException, UniformSignalException {
		if(componentIndex < 0 || componentIndex >= this.dimension) {
			throw new UniformSignalException("componentIndex ne odgovara dimenziji signala.");
		}
		if(signalValue.length() != 1) {
			throw new UniformSignalException("Dimenzija signala mora biti 1.");
		}
		
		Map.Entry<Long, SignalChange> p = ((TreeMap <Long, SignalChange>)changes).floorEntry(time - 1);
		Map.Entry<Long, SignalChange> n = ((TreeMap <Long, SignalChange>)changes).ceilingEntry(time + 1);
		SignalChange prev = p == null ? null : p.getValue();
		SignalChange next = n == null ? null : n.getValue();
		String newSignalValue = null;
		
		if (this.changes.containsKey(new Long(time))) {
			SignalChange sc = this.changes.get(new Long(time));
			StringBuilder sb = new StringBuilder(sc.getSignalValue());
			sb.setCharAt(componentIndex, signalValue.charAt(0));
			newSignalValue = sb.toString();
			sc.setSignalValue(newSignalValue);
		} else {
			if(p == null) {
				StringBuilder sb = new StringBuilder(this.dimension);
				for(int i = 0; i < this.dimension; i++) {
					if(i == componentIndex)
						sb.append(signalValue.charAt(0));
					else
						sb.append('0');
				}
				newSignalValue = sb.toString();
			} else {
				StringBuilder sb = new StringBuilder(p.getValue().getSignalValue());
				sb.setCharAt(componentIndex, signalValue.charAt(0));
				newSignalValue = sb.toString();
			}
			this.changes.put(new Long(time), new SignalChange(this.dimension, newSignalValue, time, this));
			if (this.signalChangeListener != null) {
				this.signalChangeListener.signalChanged(this);
			}
		}
		if (prev != null && prev.getSignalValue().equals(newSignalValue)){
			this.changes.remove(time);
		}
		if (next != null && newSignalValue.equals(next.getSignalValue())) {
			this.changes.remove(next.getTime());
		}
	}
	
	public void setSignalChangeValue(List<SignalChange> signalChangeList, int componentIndex)
			throws UniformSignalChangeException, UniformSignalException {
		if(signalChangeList == null)
			throw new UniformSignalException("signalChangeList ne smije biti null!");
		if(componentIndex < 0 || componentIndex >= this.dimension) {
			throw new UniformSignalException("componentIndex ne odgovara dimenziji signala.");
		}
		try
		{
			if(this.signalChangeListener != null) {
				this.signalChangeListener.suspendSignalChangedEvents();
			}
			for(SignalChange sc : signalChangeList) {
				this.setSignalChangeValue(sc.getTime(), sc.getSignalValue(), componentIndex);
			}
		}
		catch (UniformSignalChangeException e) {
			throw e;
		}
		finally {
			if(this.signalChangeListener != null) {
				this.signalChangeListener.resumeSignalChangedEvents();
			}
		}
	}
	
	public ScalarSignal[] getSignalComponents() throws UniformSignalException, UniformSignalChangeException {
		ScalarSignal[] resultList = new ScalarSignal[this.dimension];
		char[] lastValue = new char[this.dimension];
		String sn = null;
		
		for(int i = 0; i < this.dimension; i++) {
			if(this.direction == VectorDirection.to) {
				sn = this.name + " [" + i + "]"; 
			} else {
				sn = this.name + " [" + (this.dimension - i - 1) + "]";
			}
			resultList[i] = new ScalarSignal(sn);
			lastValue[i] = 0;
		}
		
		for(SignalChange sc : this) {
			for(int i = 0; i < this.dimension; i++) {
				char c = sc.getSignalValue().charAt(i);
				if(lastValue[i] == 0) {
					// Sve je to zbog xilinixa
					//resultList[this.dimension - i - 1].setSignalChangeValue(sc.getTime(), Character.toString(c));
					resultList[i].setSignalChangeValue(sc.getTime(), Character.toString(c));
					lastValue[i] = c;
				}
				else if(lastValue[i] != c) {
					// Sve je to zbog xilinixa
					//resultList[this.dimension - i - 1].setSignalChangeValue(sc.getTime(), Character.toString(c));
					resultList[i].setSignalChangeValue(sc.getTime(), Character.toString(c));
					lastValue[i] = c;
				}
					
			}
		}
		
		return resultList;
	}
}