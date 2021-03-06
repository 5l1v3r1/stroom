package stroom.processor.impl;

import stroom.entity.shared.ExpressionCriteria;
import stroom.processor.shared.ProcessorFilter;
import stroom.util.shared.ResultPage;
import stroom.util.shared.HasIntCrud;

public interface ProcessorFilterDao extends HasIntCrud<ProcessorFilter> {
    ResultPage<ProcessorFilter> find(ExpressionCriteria criteria);
}
