package tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileTool {
	private static List<String> orgPath;// 原图路径
	private static List<String> thumbPath;// 缩略图路径
	private static final String ROOT_URL = "127.0.0.1/Test";

	/** 上传文件到服务器 */
	public List<String> addFiles(HttpServletRequest request) {
		return addFiles(request, "files", "images/");
	}

	/** 上传文件到服务器 */
	public List<String> addFiles(HttpServletRequest request, String formName,
			String relaRootPath) {
		initPath(orgPath, thumbPath);

		String path = request.getSession().getServletContext().getRealPath("/")
				+ relaRootPath;// 上传文件目录

		List<String> fileNameList = new ArrayList<String>();

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		multipartResolver.setDefaultEncoding("utf-8");

		if (multipartResolver.isMultipart(request)) {

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Iterator<MultipartFile> iter = multiRequest.getFiles(formName)
					.iterator();

			while (iter.hasNext()) {
				try {
					MultipartFile file = iter.next();

					if (file != null) {
						String myFileName = file.getOriginalFilename();
						if (myFileName.trim() != "") {

							// 时间戳命名--数据库增加文件名称
							String fileName = file.getOriginalFilename();

							/** 文件后缀 */
							fileName = System.currentTimeMillis()
									+ fileName.substring(fileName
											.lastIndexOf("."));

							File localFile = new File(path + fileName);

							if (!localFile.exists()) {
								localFile.mkdirs();
							}
							file.transferTo(localFile);

							// 根路径可以更改
							fileNameList.add(ROOT_URL
									+ request.getSession().getServletContext()
											.getContextPath() + relaRootPath
									+ localFile.getName());

							orgPath.add(relaRootPath + localFile.getName());
							thumbPath.add(relaRootPath + "thumb/"
									+ localFile.getName());

						}
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fileNameList;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param request
	 *            请求
	 * @param formName
	 *            表单file的name值
	 * @param relaRootPath
	 *            相对根目录的文件存储位置，如 images/,videos/
	 * @return 文件相对路径
	 */

	public static String addFile(HttpServletRequest request, String formName,
			String relaRootPath) {

		String resultPath = null;

		String path = request.getSession().getServletContext().getRealPath("/")
				+ relaRootPath;// 上传文件目录

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		multipartResolver.setDefaultEncoding("utf-8");

		if (multipartResolver.isMultipart(request)) {

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			try {
				MultipartFile file = multiRequest.getFile(formName);

				if (file != null) {

					String myFileName = file.getOriginalFilename();
					if (myFileName.trim() != "") {

						// 时间戳命名--数据库增加文件名称
						String fileName = file.getOriginalFilename();

						/** 文件后缀 */
						fileName = System.currentTimeMillis()
								+ fileName.substring(fileName.lastIndexOf("."));

						File localFile = new File(path + fileName);

						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						file.transferTo(localFile);

						resultPath = relaRootPath + localFile.getName();
					}
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultPath;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param request
	 *            请求
	 * @param strURL
	 *            相对根目录的文件路径
	 * @return
	 */

	public static boolean delFile(HttpServletRequest request, String strURL) {
		String path = request.getSession().getServletContext().getRealPath("/")
				+ strURL;// 上传文件目录
		File file = new File(path);

		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 将数据写入到指定文件中
	 */
	public static void writeTofile(String data, String resultPath,
			boolean isAppend) {
		BufferedWriter bw = null;
		File writefile = null;
		FileWriter fw = null;
		try {
			// 通过这个对象来判断是否向文本文件中追加内容
			writefile = new File(resultPath);
			// 如果文本文件不存在则创建它
			if (writefile.exists() == false) {
				writefile.createNewFile();
				writefile = new File(resultPath); // 重新实例化
			}
			fw = new FileWriter(writefile, isAppend);
			fw.write(data);
			fw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void initPath(List<String> orgPath2, List<String> thumbPath2) {
		setOrgPath(new ArrayList<String>());
		setThumbPath(new ArrayList<String>());
	}

	public static List<String> getOrgPath() {
		return orgPath;
	}

	public static void setOrgPath(List<String> orgPath) {
		FileTool.orgPath = orgPath;
	}

	public static List<String> getThumbPath() {
		return thumbPath;
	}

	public static void setThumbPath(List<String> thumbPath) {
		FileTool.thumbPath = thumbPath;
	}

}
