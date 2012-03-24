package com.jw.ess.entity;

public class StorageInfo {
	
	private Order order;
	
	private Storage storage;
	
	private int countInStorage;
	
	private int countOrder;
	
	private int countOrderCancel;

	public int getCountInStorage() {
		return countInStorage;
	}

	public void setCountInStorage(int countInStorage) {
		this.countInStorage = countInStorage;
	}

	public int getCountOrder() {
		return countOrder;
	}

	public void setCountOrder(int countOrder) {
		this.countOrder = countOrder;
	}

	public int getCountOrderCancel() {
		return countOrderCancel;
	}

	public void setCountOrderCancel(int countOrderCancel) {
		this.countOrderCancel = countOrderCancel;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
