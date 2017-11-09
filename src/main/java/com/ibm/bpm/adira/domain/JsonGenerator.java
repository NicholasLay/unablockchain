package com.ibm.bpm.adira.domain;

import com.google.gson.Gson;

import java.util.*;
import java.util.function.*;
import java.lang.*;


public class JsonGenerator {
	
	public static void main(String[] args) {
		Gson json = new Gson();
		
		StartProcessRequestBean spro = new StartProcessRequestBean();
		
		spro.setOrderID("1231231231");
		spro.setProcessID(12318293);
		spro.setTaskID(1982391823);
		spro.setMayor(true);
		spro.setBrmsScoring(1);
		
		System.out.println(json.toJson(spro));
		 
	}
}
