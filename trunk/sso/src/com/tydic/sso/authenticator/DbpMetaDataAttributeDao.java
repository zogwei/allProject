package com.tydic.sso.authenticator;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.AuthenticationMetaDataPopulator;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-31
 * Time: 16:33:00
 * To change this template use File | Settings | File Templates.
 */
public class DbpMetaDataAttributeDao implements AuthenticationMetaDataPopulator {

    private String sql;

    private String roleSql;

    private DataSource dataSource;

    private final SimpleJdbcTemplate simpleJdbcTemplate;

    public DbpMetaDataAttributeDao(DataSource _dataSource,String _sql,String _roleSql) {
        this.sql = _sql;
        this.roleSql = _roleSql;
        this.dataSource = _dataSource;
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Authentication populateAttributes(final Authentication authentication, final Credentials credentials) {
       // credentials.
        if(!(credentials instanceof UsernamePasswordCredentials)){
            return authentication;
        }
        String userName = ((UsernamePasswordCredentials) credentials).getUsername();

        List<Map<String,Object>> list = getResult(userName);

        if(list==null || list.size()<1){
            return authentication;
        }

        Map<String,Object> map = list.get(0);

        authentication.getAttributes().putAll(map);
        authentication.getAttributes().put("ROLES",getResultForRole(userName));
        return authentication;
    }

     private List getResult(String userAccount){
        String[] strs = new String[1];
        strs[0]=userAccount;
        List<Map<String,Object>> list = this.simpleJdbcTemplate.queryForList(sql,strs);
        return list;
    }

    private String getResultForRole(String userAccount){
        String[] strs = new String[1];
        strs[0]=userAccount;
        List<Map<String,Object>> list = this.simpleJdbcTemplate.queryForList(roleSql,strs);
        StringBuffer sb = new StringBuffer();
        if(list ==null || list.size() ==0){
            return "";
        }
        for(Map map:list){
            sb.append(map.get("ROLE_ID")).append(",");
        }
        return sb.substring(0,sb.length()-1);
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setRoleSql(String roleSql) {
        this.roleSql = roleSql;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
