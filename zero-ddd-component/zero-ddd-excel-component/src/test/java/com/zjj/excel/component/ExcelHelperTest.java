// package com.zjj.excel.component;
//
// import com.zjj.excel.component.utils.ExcelHelper;
// import com.zjj.excel.component.utils.ImportExcelHelper;
// import org.junit.jupiter.api.Test;
//
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.util.ArrayList;
// import java.util.List;
//
/// **
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年07月05日 10:26
// */
// public class ExcelHelperTest {
//
// @Test
// public void testExportExcel() throws FileNotFoundException {
// List<ExportDemoVo> list = new ArrayList<>();
// for (int i = 0; i < 20; i++) {
//
// list.add(new ExportDemoVo(i + "nickname", i + "null", "男", "123456", "aaa@qq.com", "a",
// i, "aa", i,"123",i));
// }
//
//
// ExcelHelper.exportExcel(list, "abc", ExportDemoVo.class, new
// FileOutputStream("abc.xlsx"));
// }
//
// @Test
// public void testImportExcelHelper() throws FileNotFoundException {
// ImportExcelHelper
// .importExcel(new FileInputStream("abc.xlsx"), ExportDemoVo.class)
// .invoke(
// invoke -> invoke
// .test1()
// .test1()
// .test2()
// .test3()
// )
// .success(System.out::println)
// .fail(System.out::println);
// }
// }
