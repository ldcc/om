package org.ldccc.om3.dto;

import java.util.HashMap;

public class DTO {
	public static final String ID = "ID";

	public HashMap<String, Object> map = new HashMap<>();

	public DTO(HashMap<String, Object> map) {
		this.map = map;
	}

	public DTO() {
	}

	@Override
	public String toString() {
		return map.toString();
	}

	@Override
	public int hashCode() {
		int keyCode = map.keySet().stream().map(String::hashCode).reduce(Integer::compareTo).orElse(0);
		int valueCode = map.values().stream().map(Object::hashCode).reduce(Integer::compareTo).orElse(0);
		return (keyCode + valueCode) * 31;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(this.getClass()) && this.hashCode() == obj.hashCode();
	}
}
