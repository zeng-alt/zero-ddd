package com.zjj.excel.component.utils;

import com.zjj.excel.component.processor.DemoData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 14:41
 */
@SpringBootTest
public class TestExcelHelper {

    @Test
    public void testImportExcel() throws FileNotFoundException {
        // 读取resources下的文件
        File file = ResourceUtils.getFile("classpath:demo/demo.xlsx");
        // 拿到文件流
        FileInputStream fileInputStream = new FileInputStream(file);
        List<DemoData> demoData = ExcelHelper.importExcel(fileInputStream, DemoData.class);
        System.out.println();
    }
}
