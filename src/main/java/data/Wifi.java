package data;

import java.util.Date;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Wifi {
	
	double distance = 0.0;
	private String wifiId;
	private String wrdofc;
	private String wifiName;
	private String adres1;
	private String adres2;
	private String floor;
	private String instlTy;
	private String instlMby;
	private String svcSe;
	private String cmcwr;
	private String cnstcYear;
	private String inOut;
	private String remars3;
	private double lat;
	private double lnt;
	private String workDt;
	
	public Wifi(){}
	
	public Wifi(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public String getWifiId() {
		return wifiId;
	}
	public void setWifiId(String wifiId) {
		this.wifiId = wifiId;
	}
	public String getWrdofc() {
		return wrdofc;
	}
	public void setWrdofc(String wrdofc) {
		this.wrdofc = wrdofc;
	}
	public String getWifiName() {
		return wifiName;
	}
	public void setWifiName(String wifiName) {
		this.wifiName = wifiName;
	}
	public String getAdres1() {
		return adres1;
	}
	public void setAdres1(String adres1) {
		this.adres1 = adres1;
	}
	public String getAdres2() {
		return adres2;
	}
	public void setAdres2(String adres2) {
		this.adres2 = adres2;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getInstlTy() {
		return instlTy;
	}
	public void setInstlTy(String instlTy) {
		this.instlTy = instlTy;
	}
	public String getInstlMby() {
		return instlMby;
	}
	public void setInstlMby(String instlMby) {
		this.instlMby = instlMby;
	}
	public String getSvcSe() {
		return svcSe;
	}
	public void setSvcSe(String svcSe) {
		this.svcSe = svcSe;
	}
	public String getCmcwr() {
		return cmcwr;
	}
	public void setCmcwr(String cmcwr) {
		this.cmcwr = cmcwr;
	}
	public String getCnstcYear() {
		return cnstcYear;
	}
	public void setCnstcYear(String cnstcYear) {
		this.cnstcYear = cnstcYear;
	}
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getRemars3() {
		return remars3;
	}
	public void setRemars3(String remars3) {
		this.remars3 = remars3;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLat(String lat) {
		this.lat = Double.parseDouble(lat);
	}
	public double getLnt() {
		return lnt;
	}
	public void setLnt(double lnt) {
		this.lnt = lnt;
	}
	public void setLnt(String lnt) {
		this.lnt = Double.parseDouble(lnt);
	}
	public String getWorkDt() {
		return workDt;
	}
	public void setWorkDt(String workDt) {
		this.workDt = workDt;
	}

}
