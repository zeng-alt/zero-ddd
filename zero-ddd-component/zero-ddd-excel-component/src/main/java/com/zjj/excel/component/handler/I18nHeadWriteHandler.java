package com.zjj.excel.component.handler;

import cn.idev.excel.metadata.Head;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.write.handler.CellWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import com.zjj.i18n.component.MessageSourceHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月03日 09:43
 */
public class I18nHeadWriteHandler implements CellWriteHandler {

    private static final Pattern pattern = Pattern.compile("^\\{.*\\}$");

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        if (Boolean.FALSE.equals(isHead)) {
            return;
        }

        String originalHead = cell.getStringCellValue();
        if (!StringUtils.hasText(originalHead)) {
            return;
        }

        if (pattern.matcher(originalHead).matches()) {
            String headName = MessageSourceHelper.getMessage(originalHead, originalHead);
            cell.setCellValue(headName);
        }

    }
}
