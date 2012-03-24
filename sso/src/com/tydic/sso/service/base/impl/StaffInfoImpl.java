package com.tydic.sso.service.base.impl;

import java.util.Date;
import java.util.List;

import com.tydic.sso.service.base.InterOrg;
import com.tydic.sso.service.base.StaffInfo;
import com.tydic.sso.service.base.StaffRole;

/**
 * 用户信息
 * @author houdc
 *
 */
public class StaffInfoImpl implements StaffInfo {

	private String cerNumb;
	
	private Date crtDate;
	
	private List<Long> dataPrivilege;
	
	private int depType;
	
	private String email;
	
	private String ipAddr;
	
	private String latnId;
	
	private String macAddr;
	
	private String mobile;
	
	private long orderNumb;
	
	private List<InterOrg> orgs;
	
	private List<StaffRole> roleList;
	
	private String staffAcct;
	
	private String staffAddr;
	
	private String staffCode;
	
	private long staffId;
	
	private int staffLevel;
	
	private String staffName;
	
	private String staffType;
	
	private String teleNo;
	
	private String areaId;
	
	private String areaLevel;

    private String crmAcct;
	
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getCerNumb() {
		return this.cerNumb;
	}

	public Date getCrtDate() {
		return this.crtDate;
	}

	public List<Long> getDataPrivilege() {
		return this.dataPrivilege;
	}

	public int getDepType() {
		return this.depType;
	}

	public String getEmail() {
		return this.email;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public String getLatnId() {
		return this.latnId;
	}

	public String getMacAddr() {
		return this.macAddr;
	}

	public String getMobile() {
		return this.mobile;
	}

	public long getOrderNumb() {
		return this.orderNumb;
	}

	public List<InterOrg> getOrgs() {
		return this.orgs;
	}

	public List<StaffRole> getRoleList() {
		return this.roleList;
	}

	public String getStaffAcct() {
		return this.staffAcct;
	}

	public String getStaffAddr() {
		return this.staffAddr;
	}

	public String getStaffCode() {
		return this.staffCode;
	}

	public long getStaffId() {
		return this.staffId;
	}

	public int getStaffLevel() {
		return this.staffLevel;
	}

	public void setCerNumb(String cerNumb) {
		this.cerNumb = cerNumb;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public void setDataPrivilege(List<Long> dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}

	public void setDepType(int depType) {
		this.depType = depType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setLatnId(String latnId) {
		this.latnId = latnId;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setOrderNumb(long orderNumb) {
		this.orderNumb = orderNumb;
	}

	public void setOrgs(List<InterOrg> orgs) {
		this.orgs = orgs;
	}

	public void setRoleList(List<StaffRole> roleList) {
		this.roleList = roleList;
	}

	public void setStaffAcct(String staffAcct) {
		this.staffAcct = staffAcct;
	}

	public void setStaffAddr(String staffAddr) {
		this.staffAddr = staffAddr;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public void setStaffLevel(int staffLevel) {
		this.staffLevel = staffLevel;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public void setTeleNo(String teleNo) {
		this.teleNo = teleNo;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public String getStaffType() {
		return this.staffType;
	}

	public String getTeleNo() {
		return this.teleNo;
	}

    public String getCrmAcct() {
        return crmAcct;
    }

    public void setCrmAcct(String crmAcct) {
        this.crmAcct = crmAcct;
    }
}
