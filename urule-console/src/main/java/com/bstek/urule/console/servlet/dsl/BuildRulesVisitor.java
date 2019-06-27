package com.bstek.urule.console.servlet.dsl;

import com.bstek.urule.Configure;
import com.bstek.urule.RuleException;
import com.bstek.urule.action.Action;
import com.bstek.urule.dsl.RuleParserBaseVisitor;
import com.bstek.urule.dsl.RuleParserParser.*;
import com.bstek.urule.dsl.builder.BuildUtils;
import com.bstek.urule.dsl.builder.ContextBuilder;
import com.bstek.urule.dsl.builder.NamedConditionBuilder;
import com.bstek.urule.model.rule.Library;
import com.bstek.urule.model.rule.Other;
import com.bstek.urule.model.rule.Rhs;
import com.bstek.urule.model.rule.Rule;
import com.bstek.urule.model.rule.lhs.*;
import com.bstek.urule.model.rule.loop.LoopEnd;
import com.bstek.urule.model.rule.loop.LoopRule;
import com.bstek.urule.model.rule.loop.LoopStart;
import com.bstek.urule.model.rule.loop.LoopTarget;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BuildRulesVisitor extends RuleParserBaseVisitor<Object> {
    private Map<ParseTree, Junction> a = new HashMap();
    private Collection<ContextBuilder> b;
    private NamedConditionBuilder c = new NamedConditionBuilder();
    private CommonTokenStream d;

    public BuildRulesVisitor(Collection<ContextBuilder> var1, CommonTokenStream var2) {
        this.b = var1;
        this.d = var2;
    }

    private String a(ExpressionBodyContext var1) {
        StringBuffer var2 = new StringBuffer();
        Iterator var3 = var1.children.iterator();

        while(var3.hasNext()) {
            ParseTree var4 = (ParseTree)var3.next();
            Interval var5 = var4.getSourceInterval();
            int var6 = var5.a;
            List var7 = this.d.getHiddenTokensToLeft(var6);
            if (var7 != null) {
                Token var8 = (Token)var7.get(0);
                String var9 = var8.getText();
                var2.append(var9);
            }

            var2.append(var4.getText());
            List var11 = this.d.getHiddenTokensToRight(var6);
            if (var11 != null) {
                Token var12 = (Token)var11.get(0);
                String var10 = var12.getText();
                var2.append(var10);
            }
        }

        return var2.toString();
    }

    @Override
    public Library visitResource(ResourceContext var1) {
        return (Library)this.a((ParserRuleContext)var1);
    }

    public LoopRule visitLoopRuleDef(LoopRuleDefContext var1) {
        SimpleDateFormat var2 = new SimpleDateFormat(Configure.getDateFormat());
        LoopRule var3 = new LoopRule();
        String var4 = var1.STRING().getText();
        var4 = var4.substring(1, var4.length() - 1);
        var3.setName(var4);
        LoopTargetContext var5 = var1.loopTarget();
        ComplexValueContext var6 = var5.complexValue();
        LoopTarget var7 = new LoopTarget();
        var7.setValue(BuildUtils.buildValue(var6));
        var3.setLoopTarget(var7);
        LoopStartContext var8 = var1.loopStart();
        if (var8 != null) {
            List var9 = var8.action();
            if (var9 != null) {
                LoopStart var10 = new LoopStart();
                var10.setActions(this.a(var9));
                var3.setLoopStart(var10);
            }
        }

        LoopEndContext var19 = var1.loopEnd();
        List var20;
        if (var19 != null) {
            var20 = var19.action();
            if (var20 != null) {
                LoopEnd var11 = new LoopEnd();
                var11.setActions(this.a(var20));
                var3.setLoopEnd(var11);
            }
        }

        var20 = var1.attribute();
        if (var20 != null) {
            Iterator var21 = var20.iterator();

            while(var21.hasNext()) {
                AttributeContext var12 = (AttributeContext)var21.next();
                if (var12.salienceAttribute() != null) {
                    var3.setSalience(Integer.valueOf(var12.salienceAttribute().NUMBER().getText()));
                } else if (var12.loopAttribute() != null) {
                    var3.setLoop(Boolean.valueOf(var12.loopAttribute().Boolean().getText()));
                } else {
                    String var13;
                    if (var12.effectiveDateAttribute() != null) {
                        try {
                            var13 = var12.effectiveDateAttribute().STRING().getText();
                            var13 = var13.substring(1, var13.length() - 1);
                            var3.setEffectiveDate(var2.parse(var13));
                        } catch (ParseException var18) {
                            throw new RuleException(var18);
                        }
                    } else if (var12.expiresDateAttribute() != null) {
                        try {
                            var13 = var12.expiresDateAttribute().STRING().getText();
                            var13 = var13.substring(1, var13.length() - 1);
                            var3.setExpiresDate(var2.parse(var13));
                        } catch (ParseException var17) {
                            throw new RuleException(var17);
                        }
                    } else if (var12.enabledAttribute() != null) {
                        var3.setEnabled(Boolean.valueOf(var12.enabledAttribute().Boolean().getText()));
                    } else if (var12.debugAttribute() != null) {
                        var3.setDebug(Boolean.valueOf(var12.debugAttribute().Boolean().getText()));
                    } else if (var12.activationGroupAttribute() != null) {
                        var13 = var12.activationGroupAttribute().STRING().getText();
                        var13 = var13.substring(1, var13.length() - 1);
                        var3.setActivationGroup(var13);
                    } else if (var12.agendaGroupAttribute() != null) {
                        var13 = var12.agendaGroupAttribute().STRING().getText();
                        var13 = var13.substring(1, var13.length() - 1);
                        var3.setAgendaGroup(var13);
                    } else if (var12.autoFocusAttribute() != null) {
                        var3.setAutoFocus(Boolean.valueOf(var12.autoFocusAttribute().Boolean().getText()));
                    } else if (var12.ruleflowGroupAttribute() != null) {
                        var13 = var12.ruleflowGroupAttribute().STRING().getText();
                        var13 = var13.substring(1, var13.length() - 1);
                        var3.setRuleflowGroup(var13);
                    }
                }
            }
        }

        LeftContext var22 = var1.left();
        ParseTree var23 = var22.getChild(1);
        Lhs var24 = new Lhs();
        var3.setLhs(var24);
        Criterion var14 = this.buildCriterion(var23);
        var24.setCriterion(var14);
        Rhs var15 = new Rhs();
        var15.setActions(this.visitRight(var1.right()));
        var3.setRhs(var15);
        Other var16 = new Other();
        var16.setActions(this.visitOther(var1.other()));
        var3.setOther(var16);
        return var3;
    }

    @Override
    public Rule visitRuleDef(RuleDefContext var1) {
        SimpleDateFormat var2 = new SimpleDateFormat(Configure.getDateFormat());
        Rule var3 = new Rule();
        String var4 = var1.STRING().getText();
        var4 = var4.substring(1, var4.length() - 1);
        var3.setName(var4);
        List var5 = var1.attribute();
        if (var5 != null) {
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
                AttributeContext var7 = (AttributeContext)var6.next();
                if (var7.salienceAttribute() != null) {
                    var3.setSalience(Integer.valueOf(var7.salienceAttribute().NUMBER().getText()));
                } else if (var7.loopAttribute() != null) {
                    var3.setLoop(Boolean.valueOf(var7.loopAttribute().Boolean().getText()));
                } else {
                    String var8;
                    if (var7.effectiveDateAttribute() != null) {
                        try {
                            var8 = var7.effectiveDateAttribute().STRING().getText();
                            var8 = var8.substring(1, var8.length() - 1);
                            var3.setEffectiveDate(var2.parse(var8));
                        } catch (ParseException var13) {
                            throw new RuleException(var13);
                        }
                    } else if (var7.expiresDateAttribute() != null) {
                        try {
                            var8 = var7.expiresDateAttribute().STRING().getText();
                            var8 = var8.substring(1, var8.length() - 1);
                            var3.setExpiresDate(var2.parse(var8));
                        } catch (ParseException var12) {
                            throw new RuleException(var12);
                        }
                    } else if (var7.enabledAttribute() != null) {
                        var3.setEnabled(Boolean.valueOf(var7.enabledAttribute().Boolean().getText()));
                    } else if (var7.debugAttribute() != null) {
                        var3.setDebug(Boolean.valueOf(var7.debugAttribute().Boolean().getText()));
                    } else if (var7.activationGroupAttribute() != null) {
                        var8 = var7.activationGroupAttribute().STRING().getText();
                        var8 = var8.substring(1, var8.length() - 1);
                        var3.setActivationGroup(var8);
                    } else if (var7.agendaGroupAttribute() != null) {
                        var8 = var7.agendaGroupAttribute().STRING().getText();
                        var8 = var8.substring(1, var8.length() - 1);
                        var3.setAgendaGroup(var8);
                    } else if (var7.autoFocusAttribute() != null) {
                        var3.setAutoFocus(Boolean.valueOf(var7.autoFocusAttribute().Boolean().getText()));
                    } else if (var7.ruleflowGroupAttribute() != null) {
                        var8 = var7.ruleflowGroupAttribute().STRING().getText();
                        var8 = var8.substring(1, var8.length() - 1);
                        var3.setRuleflowGroup(var8);
                    }
                }
            }
        }

        LeftContext var14 = var1.left();
        ParseTree var15 = var14.getChild(1);
        Lhs var16 = new Lhs();
        var3.setLhs(var16);
        Criterion var9 = this.buildCriterion(var15);
        var16.setCriterion(var9);
        Rhs var10 = new Rhs();
        var10.setActions(this.visitRight(var1.right()));
        var3.setRhs(var10);
        Other var11 = new Other();
        var11.setActions(this.visitOther(var1.other()));
        var3.setOther(var11);
        return var3;
    }

    @Override
    public Criteria visitSingleCondition(SingleConditionContext var1) {
        return (Criteria)this.a((ParserRuleContext)var1);
    }

    @Override
    public Criterion visitParenConditions(ParenConditionsContext var1) {
        ParseTree var2 = var1.getChild(1);
        return this.buildCriterion(var2);
    }

    @Override
    public Criterion visitSingleNamedConditionSet(SingleNamedConditionSetContext var1) {
        NamedCriteria var2 = new NamedCriteria();
        NamedConditionSetContext var3 = var1.namedConditionSet();
        if (var3.refName() != null) {
            var2.setReferenceName(var3.refName().getText());
        }

        var2.setVariableCategory(var3.refObject().getText());
        NamedConditionContext var4 = var3.namedCondition();
        CriteriaUnit var5 = this.c.buildNamedCriteria(var4, var2.getVariableCategory());
        var2.setUnit(var5);
        return var2;
    }

    @Override
    public Criterion visitMultiConditions(MultiConditionsContext var1) {
        Object var2 = null;
        Criterion var3 = null;
        Object var4 = (Junction)this.a.get(var1);
        int var5 = var1.getChildCount();

        for(int var6 = 0; var6 < var5; ++var6) {
            ParseTree var7 = var1.getChild(var6);
            if (var7 instanceof JoinContext) {
                JoinContext var8 = (JoinContext)var7;
                if (var8.AND() != null) {
                    if (var4 == null) {
                        var4 = new And();
                        var2 = var4;
                        ((Junction)var4).addCriterion(var3);
                    } else if (!(var4 instanceof And)) {
                        And var9 = new And();
                        ((Junction)var4).addCriterion(var9);
                        var4 = var9;
                    }
                } else if (var4 == null) {
                    var4 = new Or();
                    var2 = var4;
                    ((Junction)var4).addCriterion(var3);
                } else if (!(var4 instanceof Or)) {
                    Or var11 = new Or();
                    ((Junction)var4).addCriterion(var11);
                    var4 = var11;
                }
            } else {
                boolean var10 = false;
                if (var7 instanceof MultiConditionsContext) {
                    var10 = true;
                }

                if (var4 != null && var10) {
                    this.a.put(var7, (Junction) var4);
                }

                var3 = this.buildCriterion(var7);
                if (var4 != null && !var10) {
                    ((Junction)var4).addCriterion(var3);
                }
            }
        }

        if (var2 != null) {
            return (Criterion)var2;
        } else {
            return var3;
        }
    }

    public List<Action> visitRight(RightContext var1) {
        if (var1 != null && var1.action() != null) {
            List var2 = var1.action();
            return this.a(var2);
        } else {
            return null;
        }
    }

    private List<Action> a(List<ActionContext> var1) {
        ArrayList var2 = new ArrayList();
        Iterator var3 = var1.iterator();

        while(var3.hasNext()) {
            ActionContext var4 = (ActionContext)var3.next();
            Action var5 = (Action)this.a((ParserRuleContext)var4);
            var2.add(var5);
        }

        return var2;
    }

    @Override
    public List<Action> visitOther(OtherContext var1) {
        if (var1 != null && var1.action() != null) {
            ArrayList var2 = new ArrayList();
            Iterator var3 = var1.action().iterator();

            while(var3.hasNext()) {
                ActionContext var4 = (ActionContext)var3.next();
                Action var5 = (Action)this.a((ParserRuleContext)var4);
                var2.add(var5);
            }

            return var2;
        } else {
            return null;
        }
    }

    public Criterion buildCriterion(ParseTree var1) {
        Object var2 = null;
        if (var1 instanceof ParenConditionsContext) {
            var2 = this.visitParenConditions((ParenConditionsContext)var1);
        } else if (var1 instanceof SingleConditionContext) {
            var2 = this.visitSingleCondition((SingleConditionContext)var1);
        } else if (var1 instanceof MultiConditionsContext) {
            var2 = this.visitMultiConditions((MultiConditionsContext)var1);
        } else if (var1 instanceof SingleNamedConditionSetContext) {
            var2 = this.visitSingleNamedConditionSet((SingleNamedConditionSetContext)var1);
        }

        return (Criterion)var2;
    }

    private Object a(ParserRuleContext var1) {
        Iterator var2 = this.b.iterator();

        ContextBuilder var3;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            var3 = (ContextBuilder)var2.next();
        } while(!var3.support(var1));

        return var3.build(var1);
    }
}

