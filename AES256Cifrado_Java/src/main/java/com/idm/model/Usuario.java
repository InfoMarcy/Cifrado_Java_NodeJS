package com.idm.model;

public class Usuario {

	private int numEmpleado;
	private String authorization;
	
	public int getNumEmpleado() {
		return numEmpleado;
	}
	public void setNumEmpleado(int numEmpleado) {
		this.numEmpleado = numEmpleado;
	}
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	@Override
	public String toString() {
		return "Usuario [numEmpleado=" + numEmpleado + ", authorization=" + authorization + "]";
	}

}
