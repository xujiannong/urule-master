/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.urule.model.rule.lhs;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.rete.EvaluationContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2015年5月29日
 */
public class CollectLeftPart extends AbstractLeftPart {
    private String property;
    private CollectPurpose purpose;

    public Object evaluate(EvaluationContext context, Object obj, List<Object> allMatchedObjects) {
        ExprValue value = computeValue(context, obj, allMatchedObjects);
        int match = value.getMatch();
        List<Object> facts = value.getFacts();
        if (purpose == (CollectPurpose.count)) {
            return match;
        }
        if (purpose == (CollectPurpose.avg)) {
            BigDecimal total = new BigDecimal(0);
            for (Object fact : facts) {
                Object propertyValue = Utils.getObjectProperty(fact, property);
                total = total.add(Utils.toBigDecimal(propertyValue));
            }
            return total.divide(new BigDecimal(match), 4, BigDecimal.ROUND_HALF_UP);
        } else if (purpose == (CollectPurpose.sum)) {
            BigDecimal total = new BigDecimal(0);
            for (Object fact : facts) {
                Object propertyValue = Utils.getObjectProperty(fact, property);
                total = total.add(Utils.toBigDecimal(propertyValue));
            }
            return total;
        } else if (purpose == (CollectPurpose.max)) {
            BigDecimal max = new BigDecimal(0);
            for (Object fact : facts) {
                Object propertyValue = Utils.getObjectProperty(fact, property);
                BigDecimal decValue = Utils.toBigDecimal(propertyValue);
                int result = decValue.compareTo(max);
                if (result > 0) {
                    max = decValue;
                }
            }
            return max;
        } else if (purpose == (CollectPurpose.min)) {
            BigDecimal min = null;
            for (Object fact : facts) {
                Object propertyValue = Utils.getObjectProperty(fact, property);
                BigDecimal decValue = Utils.toBigDecimal(propertyValue);
                if (min != null) {
                    int result = decValue.compareTo(min);
                    if (result < 0) {
                        min = decValue;
                    }
                } else {
                    min = decValue;
                }
            }
            return min;
        }
        return 0;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public CollectPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(CollectPurpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = "collect(" + variableCategory + "." + variableLabel + ",";
            if (multiCondition != null) {
                id += multiCondition.getId() + ")";
            } else {
                id += ")";
            }
            if (purpose == (CollectPurpose.count)) {
                id += "." + purpose.name();
            } else {
                id += "." + property + "." + purpose.name();
            }
        }
        return id;
    }
}
