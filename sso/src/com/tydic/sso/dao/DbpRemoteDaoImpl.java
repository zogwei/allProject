package com.tydic.sso.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.tydic.dbp.utils.Constants;
import com.tydic.sso.Util.Constant;
import com.tydic.sso.service.base.DbpSysLog;
import com.tydic.sso.service.base.InterOrg;
import com.tydic.sso.service.base.Privilege;
import com.tydic.sso.service.base.RemoteLog;
import com.tydic.sso.service.base.StaffInfo;
import com.tydic.sso.service.base.StaffRole;
import com.tydic.sso.service.base.impl.InterOrgImpl;
import com.tydic.sso.service.base.impl.PrivilegeImpl;
import com.tydic.sso.service.base.impl.StaffInfoImpl;
import com.tydic.sso.service.base.impl.StaffRoleImpl;

/**
 * @author houdc
 */

public class DbpRemoteDaoImpl implements DbpRemoteDao {

    public DbpRemoteDaoImpl(DataSource ds) {
        jdbcTemplate = new SimpleJdbcTemplate(ds);
    }

    public boolean checkApp(String appId) throws DbpRemoteDaoException {

        List<Map<String, Object>> list = jdbcTemplate.queryForList(
                this.checkAppSql, appId);
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }
    
    public boolean isExistObject(String sqlId, Object param){
        List<Map<String, Object>> list = jdbcTemplate.queryForList(
        		sqlId, param);
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }

    public List<Privilege> findButtonPrivilege(String staffAcct, String appId)
            throws DbpRemoteDaoException {

        List<Privilege> list = this.jdbcTemplate.query(this.buttonSql,
                new ParameterizedRowMapper<Privilege>() {

                    public Privilege mapRow(ResultSet rs, int i)
                            throws SQLException {
                        PrivilegeImpl p = new PrivilegeImpl();
                        p.setParentPrivilegeCode(rs.getLong("PARENTCODE"));
                        p.setPrivilegeCode(rs.getLong("CODE"));
                        p.setPrivilegeDesc(rs.getString("DES"));
                        p.setPrivilegeName(rs.getString("NAME"));
                        return p;
                    }

                }, staffAcct, appId);

        return list;
    }

    public InterOrg findInterOrg(long orgId) throws DbpRemoteDaoException {
    	InterOrg org = null;
    	try{
    		org = this.jdbcTemplate.queryForObject(this.orgByIdSql,
                new ParameterizedRowMapper<InterOrg>() {
                    public InterOrg mapRow(ResultSet rs, int i)
                            throws SQLException {
                        InterOrgImpl org = new InterOrgImpl();
                        org.setOrgCode(rs.getString("ORGCODE"));
                        org.setOrgDesc(rs.getString("DES"));
                        org.setOrgId(rs.getLong("ORGID"));
                        org.setOrgIndex(rs.getString("ORGINDEX"));
                        //org.setOrgLatnId(rs.getString("LATNID"));
                        org.setOrgName(rs.getString("ORGNAME"));
                        org.setOrgOrderNumb(rs.getInt("ORDERNUMB"));
                        org.setOrgParentId(rs.getLong("PARENTID"));
                        org.setOrgShortName(rs.getString("SHORTNAME"));
                        org.setOrgState(rs.getString("STATE"));
                        org.setOrgStrutId(rs.getString("STRUTID"));
                        org.setOrgType(rs.getInt("TYPE"));
                        try {
                            org.setOrgLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return org;
                    }

                }, orgId);
		}catch(EmptyResultDataAccessException e){//spring bug; EmptyResultDataAccessException will be throw 
												//when the size of ResultSet is 0
			return null;
		}        
        
        return org;
    }

    public InterOrg findInterOrg(String orgCode) throws DbpRemoteDaoException {
    	InterOrg org = null;
    	try{
        	
    	 org = this.jdbcTemplate.queryForObject(this.orgByCodeSql,
                new ParameterizedRowMapper<InterOrg>() {
                    public InterOrg mapRow(ResultSet rs, int i)
                            throws SQLException {
                        InterOrgImpl org = new InterOrgImpl();
                        org.setOrgCode(rs.getString("ORGCODE"));
                        org.setOrgDesc(rs.getString("DES"));
                        org.setOrgId(rs.getLong("ORGID"));
                        org.setOrgIndex(rs.getString("ORGINDEX"));
                        //org.setOrgLatnId(rs.getString("LATNID"));
                        org.setOrgName(rs.getString("ORGNAME"));
                        org.setOrgOrderNumb(rs.getInt("ORDERNUMB"));
                        org.setOrgParentId(rs.getLong("PARENTID"));
                        org.setOrgShortName(rs.getString("SHORTNAME"));
                        org.setOrgState(rs.getString("STATE"));
                        org.setOrgStrutId(rs.getString("STRUTID"));
                        org.setOrgType(rs.getInt("TYPE"));
                        try {
                            org.setOrgLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return org;
                    }

                }, orgCode);
		}catch(EmptyResultDataAccessException e){//spring bug; EmptyResultDataAccessException will be throw 
												//when the size of ResultSet is 0
		    	return null;
		}  
        return org;
    }

    public StaffInfo findStaff(long staffId, String appId) throws DbpRemoteDaoException {
    	StaffInfo staff = null;
    	try{
    		staff = this.jdbcTemplate.queryForObject(this.staffByIdSql,
                new ParameterizedRowMapper<StaffInfo>() {
                    public StaffInfo mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        StaffInfoImpl staff = new StaffInfoImpl();
                        staff.setCerNumb(rs.getString("CERNUMB"));
                        staff.setCrtDate(rs.getDate("CRTDATE"));
                        staff.setDepType(rs.getInt("DEPTYPE"));
                        staff.setEmail(rs.getString("EMAIL"));
                        staff.setIpAddr(rs.getString("IPADDR"));
                        staff.setMacAddr(rs.getString("MACADDR"));
                        staff.setMobile(rs.getString("MOBILE"));
                        staff.setOrderNumb(rs.getLong("ORDERNUMB"));
                        staff.setStaffAcct(rs.getString("ACCT"));
                        staff.setStaffAddr(rs.getString("ADDR"));
                        staff.setStaffCode(rs.getString("STAFFCODE"));
                        staff.setStaffId(rs.getLong("STAFFID"));
                        staff.setStaffLevel(rs.getInt("STAFFLEVEL"));
                        staff.setStaffName(rs.getString("STAFFNAME"));
                        staff.setStaffType(rs.getString("STAFFTYPE"));
                        staff.setTeleNo(rs.getString("TELENO"));
                        staff.setCrmAcct(rs.getString("CRMACCT"));
                        try {
                            staff.setLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return staff;
                    }
                }, staffId);
		}catch(EmptyResultDataAccessException e){//spring bug; EmptyResultDataAccessException will be throw 
				//when the size of ResultSet is 0
			return null;
		}  
        /**
         * 组装角色信息
         */
        ((StaffInfoImpl) staff).setRoleList(this.jdbcTemplate.query(
                this.rolesSql, new ParameterizedRowMapper<StaffRole>() {

                    public StaffRole mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        StaffRoleImpl role = new StaffRoleImpl();
                        role.setRoleId(rs.getLong("ROLEID"));
                        role.setRoleName(rs.getString("ROLENAME"));
                        return role;
                    }

                }, staffId));
        ((StaffInfoImpl) staff).setOrgs(this.jdbcTemplate.query(
                this.staffOrgSql, new ParameterizedRowMapper<InterOrg>() {

                    public InterOrg mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        InterOrgImpl org = new InterOrgImpl();
                        org.setOrgCode(rs.getString("ORGCODE"));
                        org.setOrgDesc(rs.getString("DES"));
                        org.setOrgId(rs.getLong("ORGID"));
                        org.setOrgIndex(rs.getString("ORGINDEX"));
                        //org.setOrgLatnId(rs.getString("LATNID"));
                        org.setOrgName(rs.getString("ORGNAME"));
                        org.setOrgOrderNumb(rs.getInt("ORDERNUMB"));
                        org.setOrgParentId(rs.getLong("PARENTID"));
                        org.setOrgShortName(rs.getString("SHORTNAME"));
                        org.setOrgState(rs.getString("STATE"));
                        org.setOrgStrutId(rs.getString("STRUTID"));
                        org.setOrgType(rs.getInt("TYPE"));
                        try {
                            org.setOrgLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return org;
                    }

                }, staffId));
        ((StaffInfoImpl) staff).setDataPrivilege(this.jdbcTemplate.query(
                this.staffDataPSql, new ParameterizedRowMapper<Long>() {

                    public Long mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        Long p = new Long(rs.getLong("CODE"));
                        return p;
                    }

                }, staffId, appId));
        return staff;
    }

    public StaffInfo findStaff(String staffAcct, String appId) throws DbpRemoteDaoException {
    	StaffInfo staff = null;
    	try{
    		 staff = this.jdbcTemplate.queryForObject(this.staffByAcctSql,
                    new ParameterizedRowMapper<StaffInfo>() {
                        public StaffInfo mapRow(ResultSet rs, int arg1)
                                throws SQLException {
                            StaffInfoImpl staff = new StaffInfoImpl();
                            staff.setCerNumb(rs.getString("CERNUMB"));
                            staff.setCrtDate(rs.getDate("CRTDATE"));
                            staff.setDepType(rs.getInt("DEPTYPE"));
                            staff.setEmail(rs.getString("EMAIL"));
                            staff.setIpAddr(rs.getString("IPADDR"));
                            //staff.setLatnId(rs.getString("LATNID"));
                            staff.setMacAddr(rs.getString("MACADDR"));
                            staff.setMobile(rs.getString("MOBILE"));
                            staff.setOrderNumb(rs.getLong("ORDERNUMB"));
                            staff.setStaffAcct(rs.getString("ACCT"));
                            staff.setStaffAddr(rs.getString("ADDR"));
                            staff.setStaffCode(rs.getString("STAFFCODE"));
                            staff.setStaffId(rs.getLong("STAFFID"));
                            staff.setStaffLevel(rs.getInt("STAFFLEVEL"));
                            staff.setStaffName(rs.getString("STAFFNAME"));
                            staff.setStaffType(rs.getString("STAFFTYPE"));
                            staff.setTeleNo(rs.getString("TELENO"));
                            staff.setCrmAcct(rs.getString("CRMACCT"));
                            try {
                                staff.setLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                            } catch (DbpRemoteDaoException e) {
                                e.printStackTrace();
                            }
                            return staff;
                        }
                    }, staffAcct);
    	}catch(EmptyResultDataAccessException e){//spring bug; EmptyResultDataAccessException will be throw 
    											//when the size of ResultSet is 0
    		return null;
    	}
    	
        /**
         * 组装角色信息
         */
        ((StaffInfoImpl) staff).setRoleList(this.jdbcTemplate.query(
                this.rolesSql, new ParameterizedRowMapper<StaffRole>() {

                    public StaffRole mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        StaffRoleImpl role = new StaffRoleImpl();
                        role.setRoleId(rs.getLong("ROLEID"));
                        role.setRoleName(rs.getString("ROLENAME"));
                        return role;
                    }

                }, staff.getStaffId()));
        ((StaffInfoImpl) staff).setOrgs(this.jdbcTemplate.query(
                this.staffOrgSql, new ParameterizedRowMapper<InterOrg>() {

                    public InterOrg mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        InterOrgImpl org = new InterOrgImpl();
                        org.setOrgCode(rs.getString("ORGCODE"));
                        org.setOrgDesc(rs.getString("DES"));
                        org.setOrgId(rs.getLong("ORGID"));
                        org.setOrgIndex(rs.getString("ORGINDEX"));
                        //org.setOrgLatnId(rs.getString("LATNID"));
                        org.setOrgName(rs.getString("ORGNAME"));
                        org.setOrgOrderNumb(rs.getInt("ORDERNUMB"));
                        org.setOrgParentId(rs.getLong("PARENTID"));
                        org.setOrgShortName(rs.getString("SHORTNAME"));
                        org.setOrgState(rs.getString("STATE"));
                        org.setOrgStrutId(rs.getString("STRUTID"));
                        org.setOrgType(rs.getInt("TYPE"));
                        try {
                            org.setOrgLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return org;
                    }

                }, staff.getStaffId()));
        ((StaffInfoImpl) staff).setDataPrivilege(this.jdbcTemplate.query(
                this.staffDataPSql, new ParameterizedRowMapper<Long>() {

                    public Long mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        Long p = new Long(rs.getLong("CODE"));
                        return p;
                    }

                }, staff.getStaffId(), appId));
        return staff;
    }

    public List<Privilege> findStaffPrivilege(long staffId, String appId)
            throws DbpRemoteDaoException {
        List<Privilege> privileges = this.jdbcTemplate.query(
                this.privilegeByIdSql, new ParameterizedRowMapper<Privilege>() {

                    public Privilege mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        PrivilegeImpl p = new PrivilegeImpl();
                        p.setParentPrivilegeCode(rs.getLong("PARENTCODE"));
                        p.setPostion(rs.getInt("POSTION"));
                        p.setPrivilegeCode(rs.getLong("CODE"));
                        p.setPrivilegeDesc(rs.getString("DES"));
                        p.setPrivilegeId(rs.getLong("PRIVILEGEID"));
                        p.setPrivilegeName(rs.getString("NAME"));
                        p.setPrivilegeType(rs.getString("TYPE"));
                        p.setState(rs.getString("STATE"));
                        p.setUrl(rs.getString("URL"));
                        p.setLayer(rs.getInt("LAYER"));
                        p.setAliasName(rs.getString("ALIAS"));
                        p.setExtPropertis(rs.getString("EXT"));
                        return p;
                    }

                }, staffId, appId);
        return privileges;
    }

    public List<Privilege> findStaffPrivilege(String staffAcct, String appId)
            throws DbpRemoteDaoException {
        List<Privilege> privileges = this.jdbcTemplate.query(
                this.privilegeByAcctSql,
                new ParameterizedRowMapper<Privilege>() {

                    public Privilege mapRow(ResultSet rs, int arg1)
                            throws SQLException {
                        PrivilegeImpl p = new PrivilegeImpl();
                        p.setParentPrivilegeCode(rs.getLong("PARENTCODE"));
                        p.setPostion(rs.getInt("POSTION"));
                        p.setPrivilegeCode(rs.getLong("CODE"));
                        p.setPrivilegeDesc(rs.getString("DES"));
                        p.setPrivilegeId(rs.getLong("PRIVILEGEID"));
                        p.setPrivilegeName(rs.getString("NAME"));
                        p.setPrivilegeType(rs.getString("TYPE"));
                        p.setState(rs.getString("STATE"));
                        p.setUrl(rs.getString("URL"));
                        p.setLayer(rs.getInt("LAYER"));
                        p.setAliasName(rs.getString("ALIAS"));
                        p.setExtPropertis(rs.getString("EXT"));
                        return p;
                    }

                }, staffAcct, appId);
        return privileges;
    }

    public List<InterOrg> findStaffDataPrivilege(String staffAcct, String appId)
            throws DbpRemoteDaoException {
        List<InterOrg> list = this.jdbcTemplate.query(
                this.staffDataPrivilegeSql,
                new ParameterizedRowMapper<InterOrg>() {
                    public InterOrg mapRow(ResultSet rs, int i)
                            throws SQLException {
                        InterOrgImpl org = new InterOrgImpl();
                        org.setOrgCode(rs.getString("ORGCODE"));
                        org.setOrgDesc(rs.getString("DES"));
                        org.setOrgId(rs.getLong("ORGID"));
                        org.setOrgIndex(rs.getString("ORGINDEX"));
                        //org.setOrgLatnId(rs.getString("LATNID"));
                        org.setOrgName(rs.getString("ORGNAME"));
                        org.setOrgOrderNumb(rs.getInt("ORDERNUMB"));
                        org.setOrgParentId(rs.getLong("PARENTID"));
                        org.setOrgShortName(rs.getString("SHORTNAME"));
                        org.setOrgState(rs.getString("STATE"));
                        org.setOrgStrutId(rs.getString("STRUTID"));
                        org.setOrgType(rs.getInt("TYPE"));
                        org.setOrgContainChilds(rs.getInt("CONTAINCHILDS"));
                        try {
                            org.setOrgLatnId(conversionStaffLatnId(rs.getString("LATNID")));
                        } catch (DbpRemoteDaoException e) {
                            e.printStackTrace();
                        }
                        return org;
                    }

                }, staffAcct, appId);
        return list;
    }

    public String conversionStaffLatnId(String latnId) throws DbpRemoteDaoException {

        String localId = latnId;

        if (Constant.SSO_CURRENT_PROVINCE_SC.equals(Constants.reportProperties.getProperty("currentProvince").toUpperCase())) {
            Map map = this.jdbcTemplate.queryForMap(this.getLocalIdSql(), new Object[]{latnId});
            localId = (String) map.get(Constant.SSO_GET_LOCAL_ID);
        }
        return localId;
    }

    private SimpleJdbcTemplate jdbcTemplate;

    private String staffByIdSql;

    private String staffByAcctSql;

    private String checkAppSql;

    private String privilegeByIdSql;

    private String privilegeByAcctSql;

    private String orgByIdSql;

    private String orgByCodeSql;

    private String buttonSql;

    private String rolesSql;

    private String staffOrgSql;

    private String staffDataPSql;

    private String staffDataPrivilegeSql;

    private String logSql;

    private String remoteLogSql;

    private String localIdSql;

    public String getRemoteLogSql() {
        return remoteLogSql;
    }

    public void setRemoteLogSql(String remoteLogSql) {
        this.remoteLogSql = remoteLogSql;
    }

    public void setLogSql(String logSql) {
        this.logSql = logSql;
    }

    public void setStaffDataPrivilegeSql(String staffDataPrivilegeSql) {
        this.staffDataPrivilegeSql = staffDataPrivilegeSql;
    }

    public void setRolesSql(String rolesSql) {
        this.rolesSql = rolesSql;
    }

    public void setStaffOrgSql(String staffOrgSql) {
        this.staffOrgSql = staffOrgSql;
    }

    public void setStaffDataPSql(String staffDataPSql) {
        this.staffDataPSql = staffDataPSql;
    }

    public void setButtonSql(String buttonSql) {
        this.buttonSql = buttonSql;
    }

    public void setStaffByIdSql(String staffByIdSql) {
        this.staffByIdSql = staffByIdSql;
    }

    public void setStaffByAcctSql(String staffByAcctSql) {
        this.staffByAcctSql = staffByAcctSql;
    }

    public void setCheckAppSql(String checkAppSql) {
        this.checkAppSql = checkAppSql;
    }

    public void setPrivilegeByIdSql(String privilegeByIdSql) {
        this.privilegeByIdSql = privilegeByIdSql;
    }

    public void setPrivilegeByAcctSql(String privilegeByAcctSql) {
        this.privilegeByAcctSql = privilegeByAcctSql;
    }

    public void setOrgByIdSql(String orgByIdSql) {
        this.orgByIdSql = orgByIdSql;
    }

    public void setOrgByCodeSql(String orgByCodeSql) {
        this.orgByCodeSql = orgByCodeSql;
    }

    public String getLocalIdSql() {
        return localIdSql;
    }

    public void setLocalIdSql(String localIdSql) {
        this.localIdSql = localIdSql;
    }

    public void logDbpSys(DbpSysLog log) throws DbpRemoteDaoException {
        String localId = log.getLatnId();
        try {
            localId = conversionStaffLatnId(localId);
        } catch (DbpRemoteDaoException e) {
            e.printStackTrace();
        }

        this.jdbcTemplate.update(this.logSql, new Object[]{
                log.getStaffName(), log.getState(), log.getLogInfo(),
                log.getIp(), localId, log.getStaffId(),
                log.getOprObjId(), log.getOprType(), log.getSysLatnId(),
                log.getAppId()});
    }

    public void logRemoteSys(RemoteLog log) throws DbpRemoteDaoException {
        this.jdbcTemplate.update(this.remoteLogSql, new Object[]{
                log.getAppId(), log.getOperator(), log.getObjectType(),
                log.getObjectKey(), log.getOpResult(), log.getException(), log.getIp()});
    }

}
