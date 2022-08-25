package com.boss.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class ImperialDateConverter {
	
	private final static double MAKR_CONSTANT = 0.11407955;
	
	public Integer yearFraction(DateTime dt) {		 
		int dayOfYear = dt.getDayOfYear(); 		
		int hour = dt.getHourOfDay();		
		Integer fraction = (int) Math.floor((dayOfYear*24+hour)*MAKR_CONSTANT);			
		return fraction;
	}
	
	public String currentImperialDate() {
		DateTime dt = new DateTime();
		Integer fraction = yearFraction(dt);
		String year = String.valueOf(dt.getYear());			
		String imperialDate = "**"+fraction+" "+year.substring(year.length()-3)+".M"+year.substring(0, year.length()-3)+"**";
		return imperialDate;
	}

}
