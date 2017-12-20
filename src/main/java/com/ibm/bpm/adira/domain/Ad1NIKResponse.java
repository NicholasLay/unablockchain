package com.ibm.bpm.adira.domain;

import java.util.List;

/*
 * Class to handling the mapping of response from Ad1 to API.
 */

public class Ad1NIKResponse 
{
	public List<UsersAdira> user;
	
	public List<UsersAdira> getUser() {
		return user;
	}
	
	public void setUser(List<UsersAdira> user) {
		this.user = user;
	}

public class UsersAdira {
		
		public String NIK;
		public String Nama;
		
		public String getNIK() {
			return NIK;
		}
		public void setNIK(String nIK) {
			NIK = nIK;
		}
		public String getNama() {
			return Nama;
		}
		public void setNama(String nama) {
			Nama = nama;
		}
	}
	
}
