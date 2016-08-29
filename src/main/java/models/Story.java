package models;

/**
 * Story class. Holds information about a user story.
 *
 * Created by juan on 29/08/16.
 */



public class Story {
	private int number;
	private String text, info;

	// CONSTRUCTORS

	public Story() {
	}

	public Story(int number, String text) {
		this.number = number;
		this.text = text;
	}

	public Story(int number, String text, String info) {
		this.number = number;
		this.text = text;
		this.info = info;
	}

	// GETTERS & SETTERS

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
