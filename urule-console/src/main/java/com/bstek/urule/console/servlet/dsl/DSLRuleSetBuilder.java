package com.bstek.urule.console.servlet.dsl;

import com.bstek.urule.RuleException;
import com.bstek.urule.dsl.RuleParserLexer;
import com.bstek.urule.dsl.RuleParserParser;
import com.bstek.urule.dsl.ScriptDecisionTableErrorListener;
import com.bstek.urule.dsl.builder.ContextBuilder;
import com.bstek.urule.model.rule.lhs.Criterion;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.Collection;

/**
 * @program: xygl
 * @description:
 * @author: xujiannong
 * @create: 2019-06-09 14:06
 **/
public class DSLRuleSetBuilder {

    private Collection<ContextBuilder> a;

    public Criterion buildCriterion(String paramString) throws IOException {
        ANTLRInputStream localANTLRInputStream = new ANTLRInputStream(paramString);
        RuleParserLexer localRuleParserLexer = new RuleParserLexer(localANTLRInputStream);
        CommonTokenStream localCommonTokenStream = new CommonTokenStream(localRuleParserLexer);
        RuleParserParser localRuleParserParser = new RuleParserParser(localCommonTokenStream);
        ScriptDecisionTableErrorListener localScriptDecisionTableErrorListener = new ScriptDecisionTableErrorListener();
        localRuleParserParser.addErrorListener(localScriptDecisionTableErrorListener);
        BuildRulesVisitor localBuildRulesVisitor = new BuildRulesVisitor(this.a, localCommonTokenStream);
        Criterion localCriterion = localBuildRulesVisitor.buildCriterion(localRuleParserParser.condition());
        String str = localScriptDecisionTableErrorListener.getErrorMessage();
        if (str != null) {
            throw new RuleException("Script parse error:" + str);
        }
        return localCriterion; }

}
