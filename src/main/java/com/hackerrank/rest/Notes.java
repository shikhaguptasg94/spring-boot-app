package com.hackerrank.rest;

public class Notes {
	private int noteId;
	private String noteName;
	private String noteMessage;
	public Notes(int noteId, String noteName, String noteMessage) {
		super();
		this.noteId = noteId;
		this.noteName = noteName;
		this.noteMessage = noteMessage;
	}
	public int getNoteId() {
		return noteId;
	}
	public String getNoteName() {
		return noteName;
	}
	public String getNoteMessage() {
		return noteMessage;
	}
	@Override
	public String toString() {
		return "Notes [noteId=" + noteId + ", noteName=" + noteName + ", noteMessage=" + noteMessage + "]";
	}
	
}
