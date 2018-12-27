package com.project.mgr.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.mgr.constants.ProjConstants;

public class CommonUtil {

	private final static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	public static String dateToString(Date date) {
		String dateInStr = ProjConstants.EMPTY;
		try {
			if(null != date) {
				SimpleDateFormat sdf = new SimpleDateFormat(ProjConstants.FORMATYMD);
				dateInStr = sdf.format(date);
			}
		} catch(Exception e) {
			logger.error("Error - ProjectManagerUtil : " + e);
		}
		return dateInStr;
	}
	
	public static Date stringToDate(String dateInStr) {
		Date date = null;
		try {
			if(null != dateInStr) {
				SimpleDateFormat sdf = new SimpleDateFormat(ProjConstants.FORMATYMD);
				date = sdf.parse(dateInStr);
			}
		} catch(Exception e) {
			logger.error("Error - ProjectManagerUtil : " + e);
		}
		return date;
	}
	
}
