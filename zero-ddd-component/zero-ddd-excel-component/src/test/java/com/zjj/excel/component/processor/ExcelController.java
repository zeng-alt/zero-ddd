package com.zjj.excel.component.processor;

import com.zjj.excel.component.annotation.ExcelExport;
import com.zjj.excel.component.annotation.ExcelImport;
import io.reactivex.rxjava3.core.Flowable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 13:55
 */
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    /**
     * 上传并解析 Excel 文件
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@ExcelImport("file") List<DemoData> file) {
        return ResponseEntity.ok("Uploaded: " + file.size() + " users");
    }


    @ExcelExport("file.xls")
    @PostMapping("/uploadMultiple")
    public ResponseEntity<String> uploadMultipleExcel(@ExcelImport("file") List<List<DemoData>> file) {
        return ResponseEntity.ok("Uploaded: " + file.size() + " users");
    }

    @ExcelExport("file.xls")
    @PostMapping("/uploadRxjava")
    public ResponseEntity<String> uploadRxjavaExcel(@ExcelImport("file") Flowable<DemoData> file) {
        file.subscribe(
                item -> System.out.println("Item: " + item),
                throwable -> System.err.println("Error: " + throwable),  // 错误捕获
                () -> System.out.println("Completed!")  // 流完成时
        );
        return ResponseEntity.ok("Uploaded: " + 2 + " users");
    }
}
