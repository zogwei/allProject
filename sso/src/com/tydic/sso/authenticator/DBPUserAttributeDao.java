package com.tydic.sso.authenticator;

import org.apache.commons.lang.Validate;
import org.jasig.services.persondir.support.AbstractFlatteningPersonAttributeDao;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * 此DAO用来获取用户相关的信息，如最后密码修改时间，角色
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 12:53:37
 * To change this template use File | Settings | File Templates.
 */
public class DBPUserAttributeDao extends AbstractFlatteningPersonAttributeDao {
    private String sql;

    private String roleSql;

    private DataSource dataSource;

    private final SimpleJdbcTemplate simpleJdbcTemplate;

    public DBPUserAttributeDao(DataSource _dataSource,String _sql,String _roleSql){
        sql = _sql;
        dataSource = _dataSource;
        roleSql = _roleSql;
         this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public Map<String, List<Object>> getMultivaluedUserAttributes(Map<String, List<Object>> stringListMap) {
        //此方法SSO尚未用到，不实现
        return null;
    }

    public Map<String, List<Object>> getMultivaluedUserAttributes(String s) {
        Validate.notEmpty(s,"用户名不能为空!");

        List<Map<String,Object>> list = getResult(s);
        if(list==null || list.size()<1){
            return new HashMap<String,List<Object>>();
        }
        Map<String,Object> map = list.get(0);

        Map<String,List<Object>> reMap = new HashMap<String,List<Object>>();
        List<Object> temp = null;
        for(Map.Entry entry:map.entrySet()){
            temp = new ArrayList<Object>();
            temp.add(entry.getValue());

            reMap.put(entry.getKey().toString(),temp);
            System.out.println(entry.getKey().toString()+"："+entry.getValue());
        }

        temp = new ArrayList<Object>();
        temp.add(getResultForRole(s));
        reMap.put("ROLES",temp);
        return reMap;
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

    public Set<String> getPossibleUserAttributeNames() {
        //此方法SSO尚未用到，不实现
        return null;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
