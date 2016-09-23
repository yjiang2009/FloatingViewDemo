package com.ftd2009.floatingviewdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

public class Utils {

	private static final String SUFFIX = "_belongto2345";

	private static long lastClickTime;

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 拷贝到剪切板
	 *
	 * @param content
	 */
	@SuppressLint("NewApi")
	public static void copy(Context context,CharSequence content) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// 得到剪贴板管理器
			ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			cmb.setPrimaryClip(ClipData.newPlainText(null, content));
		} else {
			android.text.ClipboardManager cmb = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			cmb.setText(content);
		}

	}

	public  static boolean notInstalled( Context context, String packageName )
	{
		PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for ( int i = 0; i < pinfo.size(); i++ )
		{
			if(pinfo.get(i).packageName.equalsIgnoreCase(packageName))
				return false;
		}
		return true;
	}
	
	public static boolean isMobileNO(String mobiles) {
        String str="^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern
                .compile(str);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
	
	public static void openUrlWithBrowser(String url,Context context){
		Intent intent= new Intent();        
	    intent.setAction(Intent.ACTION_VIEW);    
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    Uri content_url = Uri.parse(url);   
	    intent.setData(content_url);  
	    context.startActivity(intent);
	   
	}
	
	public static int getDrawable(Context context,String name){
//		int i = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
//		Log.i("janan", "drawable类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "drawable", context.getPackageName());
	}
	
	public static int getDimens(Context context ,String name){
//		int i = context.getResources().getIdentifier(name, "dimen", context.getPackageName());
//		Log.i("janan", "dimen类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "dimen", context.getPackageName());
	}
	
	public static int getId(Context context ,String name){
//		int i = context.getResources().getIdentifier(name, "id", context.getPackageName());
//		Log.i("janan", "id类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "id", context.getPackageName());
	}
	
	public static int getLayout(Context context ,String name){
//		int i = context.getResources().getIdentifier(name, "layout", context.getPackageName());
//		Log.i("janan", "layout类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "layout", context.getPackageName());
	}
	
	public static int getStyle(Context context ,String name){
		int i = context.getResources().getIdentifier(name, "style", context.getPackageName());
		Log.i("janan", "style类型:"+name+"--"+i);
		return i;
//		return context.getResources().getIdentifier(name, "style", context.getPackageName());
	}
	
	public static int getString(Context context,String name){
//		int i = context.getResources().getIdentifier(name, "string", context.getPackageName());
//		Log.i("janan", "string类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "string", context.getPackageName());
	}
	
	public static int getColor(Context context ,String name){
//		int i = context.getResources().getIdentifier(name, "color", context.getPackageName());
//		Log.i("janan", "color类型:"+name+"--"+i);
//		return i;
		return context.getResources().getIdentifier(name+SUFFIX, "color", context.getPackageName());
	}
	
	   /**
     * 加密
     *
     * @param str
     */
    public static String strCode(String str) {

        String hash = "N#gK3OgTw#eRUI8+8bZsti78P==4s.5";
        String key = Md5(hash);

        byte[] bstr = str.getBytes();

        int keylen = key.length();
        int strlen = bstr.length;

        byte resultByte[] = new byte[bstr.length];
        for (int i = 0; i < strlen; i++) {
            int k = i % keylen;
            int x = bstr[i];
            int y = key.charAt(k);
            byte z = (byte) (x ^ y);
            resultByte[i] = z;
        }

        try {
            String r = new String(Base64.encode(resultByte, Base64.DEFAULT), "utf-8");
            return r;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    /**
     * MD5的算法在RFC1321 中定义 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： MD5 ("") =
     * d41d8cd98f00b204e9800998ecf8427e MD5 ("a") =
     * 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") =
     * 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") =
     * f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") =
     * c3fcd3d76192e4007dfb496cca67e13b
     * 
     * @author haogj
     * 
     *         传入参数：一个字节数组 传出参数：字节数组的 MD5 结果字符串
     */
    public static String Md5(String plainText)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Returns whether the network is available
     * 
     * @param context
     *            Context
     * @return 网络是否可用
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
        }
        else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    
    public static String getLocalFileMd5(String mFileName) {
        if (TextUtils.isEmpty(mFileName)) {
            return "";
        }
        File file = new File(mFileName);
        if (!file.isFile()) {
            return "";
        }
        return getLocalFileMd5(file);
    }

    public static String getLocalFileMd5(File file) {
        if (file != null && file.exists()) {
            MessageDigest digest = null;
            FileInputStream in = null;
            byte buffer[] = new byte[1024];
            int len;
            try {
                digest = MessageDigest.getInstance("MD5");
                in = new FileInputStream(file);
                while ((len = in.read(buffer, 0, 1024)) != -1) {
                    digest.update(buffer, 0, len);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return convertToHex(digest.digest());
        }
        return "";
    }
    
    
    /**
     * Convert byte array to Hex string
     */
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    
	/**
	 * 全局单位统一
	 * 
	 * @param fileSize
	 * @return
	 */
	public static String formatFileSizeToString(long fileSize) {// 转换文件大小
		String fileSizeString = "";

		if (fileSize <= 1 * 1024) {
			fileSizeString = "1" + "KB";
		} else if (fileSize <= (800 * 1024)) {
			fileSizeString = fileSize / 1024 + "KB";
		} else if (fileSize <= (800 * 1024 * 1024)) {
			fileSizeString = String.format("%.2f",
					((float) fileSize / (1 * 1024 * 1024))) + "MB";
		} else {
			// 直接除以 800 * 1024 * 1024 * 1024 会出错，乘积太大
			double size = (float) fileSize / (1 * 1024 * 1024 * 1024);
			if (size <= 800) {
				fileSizeString = String.format("%.2f", size) + "GB";
			} else {
				fileSizeString = String.format("%.2f", size / 1024) + "TB";
			}
		}

		return fileSizeString;
	}
	
	public static  long parseFileLength(String fileLength) {
        if (!TextUtils.isEmpty(fileLength)) {
            String fileEnd = fileLength.substring(fileLength.length() - 2, fileLength.length());
            String fileNum = fileLength.substring(0, fileLength.length() - 2);
            if ("MB".equalsIgnoreCase(fileEnd)) {
                BigDecimal bd = new BigDecimal(Float.parseFloat(fileNum) * 1024 * 1024);
                bd = bd.setScale(2, 5);
                return bd.longValue();
            } else if ("KB".equalsIgnoreCase(fileEnd)) {
                BigDecimal bd = new BigDecimal(Float.parseFloat(fileNum) * 1024);
                bd = bd.setScale(2, 5);
                return bd.longValue();
            } else if ("GB".equalsIgnoreCase(fileEnd)) {
                BigDecimal bd = new BigDecimal(Float.parseFloat(fileNum) * 1024 * 1024 * 1024);
                bd = bd.setScale(2, 5);
                return bd.longValue();
            }
        }
        return -1;
    }
	
	
    
    /**
     * Android 安装应用
     *
     * @param context Application Context
     */
    public static void installApk(final Context context, File file, String sid) {
        if (file.exists()) {
            try {
                String[] args1 = {"chmod", "771", file.getPath().substring(0, file.getPath().lastIndexOf("/"))};
                Process p1 = Runtime.getRuntime().exec(args1);
                p1.waitFor();
                p1.destroy();
                String[] args2 = {"chmod", "777", file.getPath()};
                Process p2 = Runtime.getRuntime().exec(args2);
                p2.waitFor();
                p2.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

            startInstall(context, file.getAbsolutePath());
        }
    }
	
    
    public static void startInstall(Context context, String filePath) {
        if (null == context || null == filePath) {
            return;
        }

        Intent i = new Intent();
        String uriStr = "file://" + filePath;
        // v3.3暂时不加
//        if (Utils.isMiUi() && isClzExist() && !Utils.isNonMarketAppsAllowed(context)) {
//            i.setClass(context, InstallTipDialog.class);
//            i.putExtra(URI_KEY, uriStr);
//            i.putExtra(SHOW_KEY, true);
//        } else {
            i.setAction(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse(uriStr), "application/vnd.android.package-archive");
//        }

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (!context.getPackageManager().queryIntentActivities(i, 0).isEmpty()) {
            try {
                context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
    
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    
    // 内存剩余空间
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    // 内存总空间
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    // SD卡剩余空间
    public static long getAvailableExternalMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            long fstab = -1;
            try {
                File mountFile = new File("/etc/vold.fstab");
                if (mountFile.exists()) {
                    Scanner scanner = new Scanner(mountFile);
                    try {
                        while (scanner.hasNext()) {
                            String line = scanner.nextLine();
                            if (line.startsWith("dev_mount")) {
                                String[] lineElements = line.split(" ");
                                if (lineElements.length > 3) {
                                    String element = lineElements[1];
                                    if (element.equals("sdcard")) {
                                        File f = new File(lineElements[2]);
                                        if (null != f.listFiles()) {
                                            StatFs stat = new StatFs(f.getPath());
                                            long blockSize = stat.getBlockSize();
                                            long totalBlocks = stat.getBlockCount();
                                            return totalBlocks * blockSize;
                                        }
                                    }
                                }
                            }
                        }
                    } finally {
                        scanner.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fstab;
        }
    }

    // SD卡总空间
    public static long getTotalExternalMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }
    
    /**
     * 递归删除目录
     *
     * @param file
     */
    public static void deleteDir(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File subFile : files) {
                        if (subFile.isDirectory())
                            deleteDir(subFile);
                        else
                            subFile.delete();
                    }
                }
            }
            file.delete();
        }
    }
    
    
}
