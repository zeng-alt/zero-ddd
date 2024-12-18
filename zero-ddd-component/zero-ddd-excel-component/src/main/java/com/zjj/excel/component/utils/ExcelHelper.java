package com.zjj.excel.component.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.zjj.excel.component.domain.DefaultExcelListenerSuccess;
import com.zjj.excel.component.domain.ExcelSuccessListener;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:32
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelHelper {

	/**
	 * 同步导入(适用于小数据量)
	 * @param is 输入流
	 * @return 转换后集合
	 */
	public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
		return EasyExcelFactory.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
	}

	/**
	 * 使用校验监听器 异步导入 同步返回
	 * @param is 输入流
	 * @param clazz 对象类型
	 * @param isValidate 是否 Validator 检验 默认为是
	 * @return 转换后集合
	 */
	public static <T> List<T> importExcel(InputStream is, Class<T> clazz, boolean isValidate) {
		DefaultExcelListenerSuccess<T> listener = new DefaultExcelListenerSuccess<>();
		EasyExcelFactory.read(is, clazz, listener).sheet().doRead();
		return listener.getResult();
	}

	/**
	 * 使用自定义监听器 异步导入 自定义返回
	 * @param is 输入流
	 * @param clazz 对象类型
	 * @param listener 自定义监听器
	 * @return 转换后集合
	 */
	public static <T> List<T> importExcel(InputStream is, Class<T> clazz, ExcelSuccessListener<T> listener) {
		EasyExcelFactory.read(is, clazz, listener).sheet().doRead();
		return listener.getResult();
	}

	/**
	 * 导出excel
	 * @param list 导出数据集合
	 * @param clazz 实体类
	 * @param os 输出流
	 */
	public static <T> void exportExcel(List<T> list, Class<T> clazz, OutputStream os) {
		exportExcel(list, null, clazz, os);
	}

	/**
	 * 导出excel
	 * @param list 导出数据集合
	 * @param sheetName 工作表的名称
	 * @param clazz 实体类
	 * @param os 输出流
	 */
	public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, OutputStream os) {
		EasyExcelFactory.write(os, clazz).autoCloseStream(false)
				// 自动适配
				.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
				// 大数值自动转换 防止失真
				// .registerConverter(new ExcelBigNumberConvert())
				.sheet(sheetName).doWrite(list);
	}

	/**
	 * 单表多数据模板导出 模板格式为 {.属性}
	 * @param filename 文件名
	 * @param templatePath 模板路径 resource 目录下的路径包括模板文件名 例如: excel/temp.xlsx 重点:
	 * 模板文件必须放置到启动类对应的 resource 目录下
	 * @param data 模板需要的数据
	 * @param response 响应体
	 */
	public static void exportTemplate(Collection<Object> data, String filename, String templatePath,
			HttpServletResponse response) {
		try {
			resetResponse(filename, response);
			ServletOutputStream os = response.getOutputStream();
			exportTemplate(data, templatePath, os);
		}
		catch (IOException e) {
			throw new RuntimeException("导出Excel异常");
		}
	}

	/**
	 * 单表多数据模板导出 模板格式为 {.属性}
	 * @param templatePath 模板路径 resource 目录下的路径包括模板文件名 例如: excel/temp.xlsx 重点:
	 * 模板文件必须放置到启动类对应的 resource 目录下
	 * @param data 模板需要的数据
	 * @param os 输出流
	 */
	public static void exportTemplate(Collection<Object> data, String templatePath, OutputStream os)
			throws IOException {
		ClassPathResource templateResource = new ClassPathResource(templatePath);
		ExcelWriter excelWriter = EasyExcelFactory.write(os).withTemplate(templateResource.getInputStream())
				.autoCloseStream(false)
				// 大数值自动转换 防止失真
				// .registerConverter(new ExcelBigNumberConvert())
				.build();
		WriteSheet writeSheet = EasyExcelFactory.writerSheet().build();
		if (CollectionUtils.isEmpty(data)) {
			throw new IllegalArgumentException("数据为空");
		}
		// 单表多数据导出 模板格式为 {.属性}
		for (Object d : data) {
			excelWriter.fill(d, writeSheet);
		}
		excelWriter.finish();
	}

	/**
	 * 编码文件名
	 */
	public static String encodingFilename(String filename) {
		return RandomStringUtils.random(36) + "_" + filename + ".xlsx";
	}

	/**
	 * 重置响应体
	 */
	private static void resetResponse(String sheetName, HttpServletResponse response)
			throws UnsupportedEncodingException {
		String filename = encodingFilename(sheetName);
		ResponseHeaderUtils.setAttachmentResponseHeader(response, filename);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
	}

}
