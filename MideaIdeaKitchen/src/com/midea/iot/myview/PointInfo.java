package com.midea.iot.myview;


public class PointInfo {

	private int id;

	private int nextId;

	private boolean selected;

	private int left;

	private int top;

	private int right;

	private int bottom;

	public PointInfo(int id, int left, int top, int right,
			int bottom) {
		this.id = id;
		this.nextId = id;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public int getBottom() {
		return bottom;
	}

	public int getId() {
		return id;
	}



	public int getLeft() {
		return left;
	}

	public int getNextId() {
		return nextId;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public boolean hasNextId() {
		return nextId != id;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isInMyPlace(int x, int y) {
		boolean inX = x > left && x < right;
		boolean inY = y > top && y < bottom;

		return (inX && inY);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setTop(int top) {
		this.top = top;
	}

}
