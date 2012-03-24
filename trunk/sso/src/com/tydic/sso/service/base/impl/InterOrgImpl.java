package com.tydic.sso.service.base.impl;

import com.tydic.sso.service.base.InterOrg;

/**
 * 组织机构实体
 * @author houdc
 *
 */
public class InterOrgImpl implements InterOrg {

	private String orgCode;

	private String orgDesc;

	private long orgId;

	private String orgIndex;

	private String orgLatnId;

	private String orgName;

	private int orgOrderNumb;

	private long orgParentId;

	private String orgShortName;

	private String orgState;

	private String orgStrutId;

	private int orgType;

    private int orgContainChilds;

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public void setOrgIndex(String orgIndex) {
		this.orgIndex = orgIndex;
	}

	public void setOrgLatnId(String orgLatnId) {
		this.orgLatnId = orgLatnId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setOrgOrderNumb(int orgOrderNumb) {
		this.orgOrderNumb = orgOrderNumb;
	}

	public void setOrgParentId(long orgParentId) {
		this.orgParentId = orgParentId;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	public void setOrgStrutId(String orgStrutId) {
		this.orgStrutId = orgStrutId;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

    public void setOrgContainChilds(int orgContainChilds) {
        this.orgContainChilds = orgContainChilds;
    }
	public String getOrgCode() {
		return this.orgCode;
	}

	public String getOrgDesc() {
		return this.orgDesc;
	}

	public long getOrgId() {
		return this.orgId;
	}

	public String getOrgIndex() {
		return this.orgIndex;
	}

	public String getOrgLatnId() {
		return this.orgLatnId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public int getOrgOrderNumb() {
		return this.orgOrderNumb;
	}

	public long getOrgParentId() {
		return this.orgParentId;
	}

	public String getOrgShortName() {
		return this.orgShortName;
	}

	public String getOrgState() {
		return this.orgState;
	}

	public String getOrgStrutId() {
		return this.orgStrutId;
	}

	public int getOrgType() {
		return this.orgType;
	}

    public int getOrgContainChilds() {
        return orgContainChilds;
    }
}
