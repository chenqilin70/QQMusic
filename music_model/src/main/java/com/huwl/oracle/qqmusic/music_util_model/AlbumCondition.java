package com.huwl.oracle.qqmusic.music_util_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class AlbumCondition {
	private String language;
	private String genres;
	private Boolean isfree;
	private String category;
	private Date minYear;
	private Date maxYear;
	private Integer albumCompany;
	public static Properties conditionMapping;
	static{
		conditionMapping=new Properties();
		try {
			InputStream in=AlbumCondition.class.getClassLoader().getResourceAsStream("album_condition_mapping.properties");
			conditionMapping.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	
	public Boolean getIsIsfree() {
		return isfree;
	}
	public void setIsfree(Boolean isfree) {
		this.isfree = isfree;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getMinYear() {
		return minYear;
	}
	public void setMinYear(Date minYear) {
		this.minYear = minYear;
	}
	public Date getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(Date maxYear) {
		this.maxYear = maxYear;
	}
	public Integer getAlbumCompany() {
		return albumCompany;
	}
	public void setAlbumCompany(Integer albumCompany) {
		this.albumCompany = albumCompany;
	}
	@Override
	public String toString() {
		return "AlbumCondition [language=" + language + ", genres=" + genres
				+ ", isfree=" + isfree + ", category=" + category
				+ ", minYear=" + minYear + ", maxYear=" + maxYear
				+ ", albumCompany=" + albumCompany + "]";
	}
	
	
	
}
