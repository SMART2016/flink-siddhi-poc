package flinksidhi.control.event.model;

/**
 * Control Stream Message Example:
 *  {
 *
 * 			"ruleId": "1",
 * 			"ruleversion": "1.0",
 * 			"disabled": false,
 * 		    "action":"add",
 * 			"ruleDefinition": "from inputStream select json:toObject(s3log) as obj insert into temp;
 * 			from temp select json:getString(obj,'$.s3log.requestId') as access_requester,
 * 			json:getString(obj,'$.s3log.httpStatus') as attempted_response,
 * 			json:getString(obj,'$.s3log.bucket') as accessed_bucket,
 * 			json:getString(obj,'$.s3log.operation') as attempted_action insert
 * 			into temp2;from every( e1 = temp2 ) -> e2 = temp2[ e1.access_requester == access_requester and
 * 			(e1.attempted_response == attempted_response) and attempted_response == '404'] within 30 sec
 * 			select e1.access_requester , e1.attempted_response, e1.accessed_bucket,e1.attempted_action
 * 			insert into outputStream;"
 *
 * }
 */

public class RuleControlEvent {
    private String ruleId;
    private String ruleversion;
    private String ruleDefinition;
    private boolean disabled;
    private String action;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleversion() {
        return ruleversion;
    }

    public void setRuleversion(String ruleversion) {
        this.ruleversion = ruleversion;
    }

    public String getRuleDefinition() {
        return ruleDefinition;
    }

    public void setRuleDefinition(String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
