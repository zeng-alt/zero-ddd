package com.zjj.excel.component.processor;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.ListUtils;
import com.zjj.excel.component.configuration.ExcelAutoConfiguration;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 14:02
 */
@WebMvcTest({ExcelController.class})
@Import(ExcelAutoConfiguration.class)
public class TestHttpExcelMethodProcessor {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testExcelImport() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload")
                        .file(createFile("test.xlsx"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()) // 期望返回 200
                .andExpect(content().string("Uploaded: 10 users"));
    }

    @Test
    public void testMultiExcelImport() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/uploadMultiple")
                        .file(createFile("test.xlsx"))
                        .file(createFile("test.xlsx"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()) // 期望返回 200
                .andExpect(content().string("Uploaded: 2 users"));
    }

    @Test
    public void testExcelImportRxjava() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/uploadRxjava")
                        .file(createFile("test.xlsx"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk()) // 期望返回 200
                .andExpect(content().string("Uploaded: 2 users"));
    }

    @Test
    public void testExcelImportStream() throws FileNotFoundException {
        BlockingQueue<DemoData> queue = new LinkedBlockingQueue<>();
        DemoData POISON_PILL = new DemoData();
        File file = ResourceUtils.getFile("classpath:demo/demo.xlsx");
        // 拿到文件流
        FileInputStream fileInputStream = new FileInputStream(file);
        Flowable<DemoData> flowable = Flowable.create(emitter -> {
            FastExcelFactory.read(fileInputStream, DemoData.class, new AnalysisEventListener<DemoData>() {
                @Override
                public void invoke(DemoData data, AnalysisContext context) {

                    if (data.getString().equals("字符串3")) {
                        emitter.onError(new RuntimeException("抛出异常"));
                        return;
                    }
                    if (!emitter.isCancelled()) {
                        emitter.onNext(data); // **向 Rx 流发送数据**
                    }

                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    emitter.onComplete(); // **数据读取完毕**
                }

            }).sheet().doRead();
        }, io.reactivex.rxjava3.core.BackpressureStrategy.BUFFER);

//        demoDataStream.forEach(System.out::println);

        flowable
//                .onExceptionResumeNext(new Function<Throwable, Publisher<? extends DemoData>>() {
//                    @Override
//                    public Publisher<? extends DemoData> apply(Throwable throwable) throws Throwable {
//                        System.err.println("Error: " + throwable);
//                        return Flowable.just(new DemoData());
//                    }
//                })
//                .doOnError(e -> System.err.println("❌ 发生异常（但继续读取）：" + e.getMessage()))
                .subscribe(
                        data -> System.out.println("Received: " + data),
                        throwable -> System.err.println("Error: " + throwable),
                        () -> System.out.println("Completed!")
                );
    }


    @Test
    public void testRxjava() {
        // 模拟异步读取 Excel 流并抛出异常
        Flowable.<String>create(emitter -> {
                    try {
                        // 读取数据并发出
                        emitter.onNext("Data1");
                        emitter.onNext("Data2");

                        // 模拟发生异常
                        emitter.onError(new RuntimeException("Something went wrong"));

                        // 继续发出数据
                        emitter.onNext("Data3");  // This will be emitted after onErrorContinue
                        emitter.onNext("Data3");  // This will be emitted after onErrorContinue
                        emitter.onNext("Data3");  // This will be emitted after onErrorContinue
                        emitter.onNext("Data3");  // This will be emitted after onErrorContinue
                        emitter.onNext("Data3");  // This will be emitted after onErrorContinue
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }, BackpressureStrategy.BUFFER)
//                .onErrorReturn(throwable -> "123")
                .onErrorResumeNext(throwable -> {
                    // 捕获错误，并继续执行后续数据
                    System.out.println("Error encountered, continuing...");
                    return Flowable.empty();  // 返回一个默认值，继续处理后续数据
                })
                .subscribe(
                        data -> System.out.println("Received: " + data)
                );

    }


    public MockMultipartFile createFile(String name) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastExcelFactory.write(outputStream, DemoData.class).sheet("Sheet1").doWrite(data());
        return new MockMultipartFile(
                "file", // 参数名
                name, // 文件名
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // MIME 类型
                outputStream.toByteArray() // Excel 文件内容
        );
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
