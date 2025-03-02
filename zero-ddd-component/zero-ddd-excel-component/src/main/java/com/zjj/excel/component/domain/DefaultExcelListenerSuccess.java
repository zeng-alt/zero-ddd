package com.zjj.excel.component.domain;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelAnalysisException;
import cn.idev.excel.exception.ExcelDataConvertException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:42
 */
@Slf4j
public class DefaultExcelListenerSuccess<T> extends ExcelSuccessListener<T> {

	public DefaultExcelListenerSuccess() {
	}

	/**
	 * 处理异常
	 * @param exception ExcelDataConvertException
	 * @param context Excel 上下文
	 */
	@Override
	public void onException(Exception exception, AnalysisContext context) throws Exception {
		String errMsg = null;
		if (exception instanceof ExcelDataConvertException excelDataConvertException) {
			// 如果是某一个单元格的转换异常 能获取到具体行号
			Integer rowIndex = excelDataConvertException.getRowIndex();
			Integer columnIndex = excelDataConvertException.getColumnIndex();
			errMsg = "第%d行-第%d列-表头%s: 解析异常<br/>".formatted(rowIndex + 1, columnIndex + 1, headMap.get(columnIndex));

			if (log.isDebugEnabled()) {
				log.error(errMsg);
			}
		}
		if (exception instanceof ConstraintViolationException constraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
			String constraintViolationsMsg = constraintViolations.stream().map(ConstraintViolation::getMessage)
					.filter(Objects::nonNull).collect(Collectors.joining(", "));
			errMsg = "第%d行数据校验异常: %s".formatted(context.readRowHolder().getRowIndex() + 1, constraintViolationsMsg);
			if (log.isDebugEnabled()) {
				log.error(errMsg);
			}
		}
		errorList.add(errMsg);
		throw new ExcelAnalysisException(errMsg);
	}

	@Override
	public void invoke(T data, AnalysisContext context) {
		list.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.debug("解析完成！");
	}

}
