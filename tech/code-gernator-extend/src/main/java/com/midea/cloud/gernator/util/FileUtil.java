package com.midea.cloud.gernator.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    public static int compress(List<String> filePaths, String zipFilePath, Boolean keepDirStructure) throws IOException {
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);//zip文件不存在，则创建文件，用于压缩
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        int fileCount = 0;//记录压缩了几个文件？
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < filePaths.size(); i++) {
                String relativePath = filePaths.get(i);

                if (StringUtils.isEmpty(relativePath)) {
                    continue;
                }
                File sourceFile = new File(relativePath);//绝对路径找到file
                if (sourceFile == null || !sourceFile.exists()) {
                    continue;
                }
                FileInputStream fis = new FileInputStream(sourceFile);
                if (keepDirStructure != null && keepDirStructure) {
                    //保持目录结构 只取src部分
                    relativePath="project"+relativePath.split("project")[1];
                    zos.putNextEntry(new ZipEntry(relativePath));
                } else {//直接放到压缩包的根目录
                    zos.putNextEntry(new ZipEntry(sourceFile.getName()));
                }//System.out.println("压缩当前文件："+sourceFile.getName());
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                fis.close();
                fileCount++;
            }
            zos.close();//System.out.println("压缩完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileCount;
    }

    public static void folderMethod(String path,List<String> filePath) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        folderMethod(file2.getAbsolutePath(),filePath);
                    } else {
                        filePath.add(file2.getAbsolutePath());
                        //System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("folderMethod2文件不存在!");
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir：要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName：要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}