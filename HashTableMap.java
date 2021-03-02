// --== CS400 File Header Information ==--
// Name: Maaz Amin
// Email: mamin6@wisc.edu
// Team: Red
// Group: DD
// TA: Dan
// Lecturer: Gary
// Notes to Grader: N/A
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

	private LinkedList<KeyType>[] keyArray;
	private LinkedList<ValueType>[] valueArray;
	private int size;

	@SuppressWarnings("unchecked")
	public HashTableMap() {
			this.keyArray  = (LinkedList<KeyType>[]) new LinkedList[10];
			this.valueArray  = (LinkedList<ValueType>[]) new LinkedList[10];
	}

	@SuppressWarnings("unchecked")
	public HashTableMap(int capacity) {
			this.keyArray  = (LinkedList<KeyType>[]) new LinkedList[capacity];
			this.valueArray  = (LinkedList<ValueType>[]) new LinkedList[capacity];
	}

	@Override
	public boolean put(KeyType key, ValueType value) {
		if (key == null) {
			return false;
		}
		int index = Math.abs(key.hashCode()) % this.keyArray.length;
		if (keyArray[index] != null) {
			for (int a = 0; a < keyArray[index].size(); a++) {
				if (keyArray[index].get(a).equals(key)) {
					return false;
				}
			}
		}

		if (keyArray[index] == null) {
			keyArray[index] = new LinkedList<KeyType>();
			valueArray[index] = new LinkedList<ValueType>();
		}
		keyArray[index].add(key);
		valueArray[index].add(value);
		size++;
		checkResize();
		return true;
	}

	@Override
	public ValueType get(KeyType key) throws NoSuchElementException {
		int index = Math.abs(key.hashCode()) % this.keyArray.length;
		int linkedListOrder = -1;
		//Important to make sure that the proper value is being returned,
		//As each list can have multiple values
		LinkedList<KeyType> keys = this.keyArray[index];
		if (keys != null) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					linkedListOrder = i;
					break;
				}
			}
			if (linkedListOrder == -1) {
				throw new NoSuchElementException("Not Found");
			}
			else {
				return this.valueArray[index].get(linkedListOrder);
			}
		}
		else {
			throw new NoSuchElementException("Not Found");
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean containsKey(KeyType key) {
		int index = Math.abs(key.hashCode()) % this.keyArray.length;
		int linkedListOrder = -1;
		LinkedList<KeyType> keys = this.keyArray[index];
		if (keys != null) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					linkedListOrder = i;
					break;
				}
			}
			if (linkedListOrder == -1) {
				return false;
			}
		}
		else {
			return false;
		}
		return true;
	}

	@Override
	public ValueType remove(KeyType key) {
		int index = Math.abs(key.hashCode()) % this.keyArray.length;
		int linkedListOrder = -1;
		LinkedList<KeyType> keys = this.keyArray[index];
		if (keys != null) {
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).equals(key)) {
					linkedListOrder = i;
					break;
				}
			}
			if (linkedListOrder == -1) {
				return null;
			}
			this.size--;
			this.keyArray[index].remove(linkedListOrder);
			return this.valueArray[index].remove(linkedListOrder);
		}
		return null;
	}

	@Override
	public void clear() {
		this.keyArray  = (LinkedList<KeyType>[]) new LinkedList[keyArray.length];
		this.valueArray  = (LinkedList<ValueType>[]) new LinkedList[keyArray.length];

	}

	private void checkResize() {
		if (((double)this.size/keyArray.length)*100 >= 85){
			LinkedList<KeyType>[] tempK = keyArray;
			LinkedList<ValueType>[] tempV = valueArray;
			this.keyArray = (LinkedList<KeyType>[]) new LinkedList[this.keyArray.length*2];
			this.valueArray = (LinkedList<ValueType>[]) new LinkedList[this.keyArray.length*2];
			this.size = 0;
			for (int i = 0; i < tempK.length; i++) {
				if (tempK[i] != null) {
					for (int a = 0; a < tempK[i].size(); a++) {
						this.put(tempK[i].get(a), tempV[i].get(a));
					}
				}
			}
		};
	}

}
