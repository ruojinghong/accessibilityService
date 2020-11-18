package com.demacia.garen.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FsUtils {
    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static void appendBytes(File file, byte[] bArr) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(bArr);
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendText(File file, String str) {
        appendText(file, str, Charset.forName("utf-8"));
    }

    public static void appendText(File file, String str, Charset charset) {
        appendBytes(file, str.getBytes(charset));
    }

    public static void copy(File file, File file2) {
        if (file.exists() && file.isFile()) {
            createFolder(file2.getParentFile());
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void copyPath(File file, File file2, Map<String, File> map) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            copy(file, file2);
            return;
        }
        int length = file.getAbsolutePath().length();
        for (File next : searchFiles(file)) {
            File file3 = new File(file2.getAbsolutePath() + next.getAbsolutePath().substring(length));
            if (map == null || !map.containsKey(next.getName())) {
                copy(next, file3);
            }
        }
    }

    public static void createFolder(File file) {
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(File file) {
        try {
            if (!file.exists()) {
                return;
            }
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File delete : listFiles) {
                    delete(delete);
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirectoryIfEmpty(File file) {
        if (!file.getAbsolutePath().equals(SD_CARD) && file.exists() && file.isDirectory()) {
            String[] list = file.list();
            if (list == null || list.length == 0) {
                file.delete();
                deleteDirectoryIfEmpty(file.getParentFile());
            }
        }
    }

    public static long getSize(File file) {
        if (!file.exists()) {
            return 0;
        }
        if (!file.isDirectory()) {
            return file.length();
        }
        final long[] jArr = {0};
        file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    // TODO: 2020/11/17  
                    long[] jArr = new long[1];
                    jArr[0] = jArr[0] + FsUtils.getSize(file);
                } else {
                    long[] jArr2 = jArr;
                    jArr2[0] = jArr2[0] + file.length();
                }
                return false;
            }
        });
        return jArr[0];
    }

    public static byte[] readBytes(File file) {
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[fileInputStream.available()];
                fileInputStream.read(bArr);
                fileInputStream.close();
                return bArr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String readText(File file) {
        return readText(file, Charset.forName("utf-8"));
    }

    public static String readText(File file, Charset charset) {
        byte[] readBytes = readBytes(file);
        if (readBytes != null) {
            return new String(readBytes, charset);
        }
        return null;
    }

    public static List<File> searchFiles(File file) {
        ArrayList arrayList = new ArrayList();
        if (file.exists()) {
            if (file.isFile()) {
                arrayList.add(file);
            } else {
                searchFilesImpl(arrayList, file);
            }
        }
        return arrayList;
    }

    public static List<String> searchFiles(File file, String str) {
        ArrayList arrayList = new ArrayList();
        searchFilesImpl(arrayList, file, str);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public static void searchFilesImpl(final List<File> list, File file) {
        file.listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isFile()) {
                    list.add(file);
                    return false;
                }
                FsUtils.searchFilesImpl(list, file);
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public static void searchFilesImpl(final List<String> list, File file, final String str) {
        file.list(new FilenameFilter() {
            public boolean accept(File file, String str) {
                boolean z;
                File file2 = new File(file.getAbsoluteFile().toString() + "/" + str);
                if (file2.isDirectory()) {
                    FsUtils.searchFilesImpl(list, file2, str);
                } else if (file2.isFile()) {
                    String str2 = str;
                    if (str2 != null) {
                        String[] split = str2.split(";");
                        int length = split.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                z = false;
                                break;
                            }
                            String str3 = split[i];
                            if (str3.length() > 0) {
                                if (str3.startsWith("*")) {
                                    str3 = str3.substring(1);
                                }
                                if (str3.equals(".*")) {
                                    z = true;
                                    break;
                                } else if (str.toLowerCase().endsWith(str3.toLowerCase())) {
                                    z = true;
                                    break;
                                }
                            }
                            i++;
                        }
                    } else {
                        z = true;
                    }
                    if (z) {
                        list.add(file2.getAbsoluteFile().toString());
                    }
                }
                return false;
            }
        });
    }

    public static void writeBytes(File file, byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeBytes(File file, byte[] bArr, int i, int i2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr, i, i2);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeText(File file, String str) {
        writeText(file, str, Charset.forName("utf-8"));
    }

    public static void writeText(File file, String str, Charset charset) {
        writeBytes(file, str.getBytes(charset));
    }
}
