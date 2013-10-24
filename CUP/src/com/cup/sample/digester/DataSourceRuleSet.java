package com.cup.sample.digester;

import com.cup.digester.Digester;
import com.cup.digester.ObjectCreateRule;
import com.cup.digester.RuleSet;

public class DataSourceRuleSet implements RuleSet {

    /**
     * The matching pattern prefix to use for recognizing our elements.
     */
    protected String prefix = null;
    
	 public DataSourceRuleSet(String prefix) {
	        super();
	        this.prefix = prefix;

	    }
	@Override
	public String getNamespaceURI() {
		// myOpinion Auto-generated method stub
		return null;
	}

	@Override
	public void addRuleInstances(Digester digester) {
		// myOpinion Auto-generated method stub
		 digester.addRule(prefix+"/data-source",new ObjectCreateRule("com.cup.sample.digester.bean.DataSource", "className"));
		 digester.addSetProperties(prefix+"/data-source");
		
	}

}
