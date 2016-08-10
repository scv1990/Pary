package com.yisa.pray.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Log;

public final class BitmapUtil {
	private static final Size ZERO_SIZE = new Size(0, 0);
	private static final Options OPTIONS_GET_SIZE = new Options();
	private static final Options OPTIONS_DECODE = new Options();
	private static final byte[] LOCKED = new byte[0];
	// 此对象用来保持Bitmap的回收顺序,保证最后使用的图片被回收
	private static final LinkedList<String> CACHE_ENTRIES = new LinkedList<String>();
	// 线程请求创建图片的队列
	private static final Queue<QueueEntry> TASK_QUEUE = new LinkedList<QueueEntry>();
	// 保存队列中正在处理的图片的key,有效防止重复添加到请求创建队列
	private static final Set<String> TASK_QUEUE_INDEX = new HashSet<String>();
	// 缓存Bitmap
	private static final Map<String, Bitmap> IMG_CACHE_INDEX = new HashMap<String, Bitmap>(); // 通过图片路径,图片大小
	private static int CACHE_SIZE = 20; // 缓存图片数量
	static {
		OPTIONS_GET_SIZE.inJustDecodeBounds = true;
		// 初始化创建图片线程,并等待处理
		new Thread() {
			{
				setDaemon(true);
			}

			public void run() {
				while (true) {
					synchronized (TASK_QUEUE) {
						if (TASK_QUEUE.isEmpty()) {
							try {
								TASK_QUEUE.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					QueueEntry entry = (QueueEntry) TASK_QUEUE.poll();
					String key = createKey(entry.path, entry.width, entry.height);
					TASK_QUEUE_INDEX.remove(key);
					createBitmap(entry.path, entry.width, entry.height);
				}
			}
		}.start();
	}

	public static Bitmap getBitmap(String path, int width, int height) {
		if (path == null) {
			return null;
		}
		Bitmap bitMap = null;
		try {
			if (CACHE_ENTRIES.size() >= CACHE_SIZE) {
				destoryLast();
			}
			bitMap = useBitmap(path, width, height);
			if (bitMap != null && !bitMap.isRecycled()) {
				return bitMap;
			}
			bitMap = createBitmap(path, width, height);
			String key = createKey(path, width, height);
			synchronized (LOCKED) {
				IMG_CACHE_INDEX.put(key, bitMap);
				CACHE_ENTRIES.addFirst(key);
			}
		} catch (OutOfMemoryError err) {
			destoryLast();
			System.out.println(CACHE_SIZE);
			return createBitmap(path, width, height);
		}
		return bitMap;
	}

	/**
	 * @Title: getBitmapByResId
	 * @Description: TODO(加载drawable下图片)
	 * @param @param context
	 * @param @param resId
	 * @param @return 设定文件
	 * @return Bitmap 返回类型
	 * @throws
	 */
	public static Bitmap getBitmapByResId(Context context, int resId) {
		InputStream is = context.getResources().openRawResource(resId);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2; // width，hight设为原来的十分一
		Bitmap btp = BitmapFactory.decodeStream(is, null, options);
		return btp;
	}

	public static Size getBitMapSize(String path) {
		File file = new File(path);
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				BitmapFactory.decodeStream(in, null, OPTIONS_GET_SIZE);
				return new Size(OPTIONS_GET_SIZE.outWidth, OPTIONS_GET_SIZE.outHeight);
			} catch (FileNotFoundException e) {
				return ZERO_SIZE;
			} finally {
				closeInputStream(in);
			}
		}
		return ZERO_SIZE;
	}

	// ------------------------------------------------------------------
	// private Methods
	// 将图片加入队列头
	private static Bitmap useBitmap(String path, int width, int height) {
		Bitmap bitMap = null;
		String key = createKey(path, width, height);
		synchronized (LOCKED) {
			bitMap = (Bitmap) IMG_CACHE_INDEX.get(key);
			if (null != bitMap) {
				if (CACHE_ENTRIES.remove(key)) {
					CACHE_ENTRIES.addFirst(key);
				}
			}
		}
		return bitMap;
	}

	// 回收最后一张图片
	private static void destoryLast() {
		synchronized (LOCKED) {
			String key = (String) CACHE_ENTRIES.removeLast();
			if (key.length() > 0) {
				Bitmap bitMap = (Bitmap) IMG_CACHE_INDEX.remove(key);
				if (bitMap != null && !bitMap.isRecycled()) {
					bitMap.recycle();
					bitMap = null;
				}
			}
		}
	}

	// 创建键
	private static String createKey(String path, int width, int height) {
		if (null == path || path.length() == 0) {
			return "";
		}
		return path + "_" + width + "_" + height;
	}

	// 通过图片路径,宽度高度创建一个Bitmap对象
	private static Bitmap createBitmap(String path, int width, int height) {
		File file = new File(path);
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
				Size size = getBitMapSize(path);
				if (size.equals(ZERO_SIZE)) {
					return null;
				}
				int scale = 1;
				int a = size.getWidth() / width;
				int b = size.getHeight() / height;
				scale = Math.max(a, b);
				synchronized (OPTIONS_DECODE) {
					OPTIONS_DECODE.inSampleSize = scale;
					Bitmap bitMap = BitmapFactory.decodeStream(in, null, OPTIONS_DECODE);
					return bitMap;
				}
			} catch (FileNotFoundException e) {
				Log.v("BitMapUtil", "createBitmap==" + e.toString());
			} finally {
				closeInputStream(in);
			}
		}
		return null;
	}

	// 关闭输入流
	private static void closeInputStream(InputStream in) {
		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				Log.v("BitMapUtil", "closeInputStream==" + e.toString());
			}
		}
	}
	
	// 图片大小
	static class Size {
		private int width, height;

		Size(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}

	// 队列缓存参数对象
	static class QueueEntry {
		public String path;
		public int width;
		public int height;
	}
	
	public static void downLoad(String urlStr, DownLoadListener listener) {
		// 新建视频播放的路径
		makeRootDirectory();
		String dirName = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.sportq/user.pub.video/";
		String newFilename = urlStr.substring(urlStr.lastIndexOf("/") + 1);
		newFilename = dirName + newFilename;
		try {
			File file = new File(newFilename);
			if (file != null && file.exists()) {
				file.delete();
			}
			// pBar.setVisibility(View.VISIBLE);
			// 构造URL
			URL url = new URL(urlStr);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			// int contentLength = con.getContentLength();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(newFilename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
			listener.downloadFinish(newFilename);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建视频播放的路径
	 */
	private static void makeRootDirectory() {
		String dirName = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.qiqi/";
		File file = new File(dirName);
		try {
			if (!file.exists()) {
				file.mkdir();
			}

			dirName = Environment.getExternalStorageDirectory()
					+ "/Android/data/com.qiqi/user.pub.video/";
			file = new File(dirName);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface DownLoadListener {
		public void downloadFinish(String path);
	}

}