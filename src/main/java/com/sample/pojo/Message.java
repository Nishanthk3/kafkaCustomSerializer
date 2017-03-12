package com.sample.pojo;

import java.util.Date;

public class Message<T> {
    private int id;
    private String message;
    private Date date;
    T entity;
    Class<T> clazz;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public Class<T> getClazz() {
		return clazz;
	}
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", date=" + date
				+ ", entity=" + entity + ", clazz=" + clazz + "]";
	}
    
}
