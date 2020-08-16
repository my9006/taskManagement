package com.kgb.tasker.persistence.model;

import javax.persistence.Entity;

@Entity
public class User extends AbstractBaseEntity{
	private String name;
	private String surname;

	public User(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
