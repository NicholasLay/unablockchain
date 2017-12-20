package com.ibm.bpm.adira.domain;

/*
 * Class for mapping UserAdira (NIK,Nama) in response NIK from Ad1 to API. 
 */

public class UserAdira 
{
	private String NIK;
	private String nama;
	
	public String getNIK() {
		return NIK;
	}
	public void setNIK(String nIK) {
		NIK = nIK;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	
}
