package com.zjj.excel.component.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:04
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseHeaderUtils {

	/**
	 * 下载文件名重新编码
	 * @param response 响应对象
	 * @param realFileName 真实文件名
	 */
	public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) {
		String percentEncodedFileName = percentEncode(realFileName);
		String contentDispositionValue = "attachment; filename=%s;filename*=utf-8''%s".formatted(percentEncodedFileName,
				percentEncodedFileName);
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
		response.setHeader("Content-disposition", contentDispositionValue);
		response.setHeader("download-filename", percentEncodedFileName);
	}

	/**
	 * 百分号编码工具方法
	 * @param s 需要百分号编码的字符串
	 * @return 百分号编码后的字符串
	 */
	public static String percentEncode(String s) {
		String encode = URLEncoder.encode(s, StandardCharsets.UTF_8);
		return encode.replace("\\+", "%20");
	}

}
