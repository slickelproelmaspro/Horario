package com.horario.pe.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	private Integer id;
	private String title;
	private Date start;
	private Date end;

	public Event(Integer id,String title,Date start,Date end) {
		this.id=id;
		this.title=title;
		this.start=start;
		this.end=end;
	}
	public Event() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
