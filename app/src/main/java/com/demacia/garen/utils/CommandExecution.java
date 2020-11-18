package com.demacia.garen.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandExecution {
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_SU = "su";

    public static class CommandResult {
        public String errorMsg;
        public int result = -1;
        public String successMsg;
    }

    private static class PrintStream extends Thread {
        private BufferedReader bufferedReader = null;
        private InputStream inputStream = null;
        private boolean isError = false;
        /* access modifiers changed from: private */
        public StringBuffer stringBuffer = new StringBuffer();

        public PrintStream(InputStream inputStream2, boolean z) {
            this.inputStream = inputStream2;
            this.isError = z;
        }

        public void run() {
            InputStream inputStream2;
            try {
                if (this.inputStream == null) {
                    LOG.m10e("--- 读取输出流出错！因为当前输出流为空！---");
                }
                this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
                while (true) {
                    String readLine = this.bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    StringBuffer stringBuffer2 = this.stringBuffer;
                    stringBuffer2.append(readLine + "\r\n");
                }
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                }
                if (this.inputStream != null) {
                    inputStream2 = this.inputStream;
                    inputStream2.close();
                }
            } catch (Exception e) {
                LOG.m10e("--- 读取输入流出错了！--- 错误信息：" + e.getMessage());
                try {
                    if (this.bufferedReader != null) {
                        this.bufferedReader.close();
                    }
                    if (this.inputStream != null) {
                        inputStream2 = this.inputStream;
                    }
                } catch (Exception e2) {
                    LOG.m10e("--- 调用PrintStream读取输出流后，关闭流时出错！---");
                }
            } catch (Throwable th) {
                try {
                    if (this.bufferedReader != null) {
                        this.bufferedReader.close();
                    }
                    if (this.inputStream != null) {
                        this.inputStream.close();
                    }
                } catch (Exception e3) {
                    LOG.m10e("--- 调用PrintStream读取输出流后，关闭流时出错！---");
                }
                throw th;
            }
        }
    }

    private static class ProcessKiller extends Thread {
        private Process process;

        public ProcessKiller(Process process2) {
            this.process = process2;
        }

        public void run() {
            this.process.destroy();
        }
    }

    public static CommandResult execCommand(String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        return execCommand((List<String>) arrayList, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004d A[SYNTHETIC, Splitter:B:23:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0052 A[SYNTHETIC, Splitter:B:26:0x0052] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00bd A[SYNTHETIC, Splitter:B:44:0x00bd] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c2 A[SYNTHETIC, Splitter:B:47:0x00c2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static CommandExecution.CommandResult execCommand(List<String> r8, boolean r9) {
        /*
            r2 = 0
            com.lisyx.tap.utils.CommandExecution$CommandResult r4 = new com.lisyx.tap.utils.CommandExecution$CommandResult
            r4.<init>()
            if (r8 == 0) goto L_0x00ed
            int r0 = r8.size()
            if (r0 != 0) goto L_0x0010
            r0 = r4
        L_0x000f:
            return r0
        L_0x0010:
            java.lang.Runtime r5 = java.lang.Runtime.getRuntime()
            if (r9 == 0) goto L_0x00f0
            java.lang.String r0 = "su"
        L_0x0018:
            java.lang.Process r1 = r5.exec(r0)     // Catch:{ Exception -> 0x00d4, all -> 0x00cf }
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00cb, all -> 0x00df }
            java.io.OutputStream r0 = r1.getOutputStream()     // Catch:{ Exception -> 0x00cb, all -> 0x00df }
            r3.<init>(r0)     // Catch:{ Exception -> 0x00cb, all -> 0x00df }
            java.util.Iterator r2 = r8.iterator()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
        L_0x0029:
            boolean r0 = r2.hasNext()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            if (r0 == 0) goto L_0x005f
            java.lang.Object r0 = r2.next()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            if (r0 == 0) goto L_0x0029
            byte[] r0 = r0.getBytes()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r3.write(r0)     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.String r0 = "\n"
            r3.writeBytes(r0)     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r3.flush()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            goto L_0x0029
        L_0x0047:
            r0 = move-exception
        L_0x0048:
            r0.printStackTrace()     // Catch:{ all -> 0x00f4 }
            if (r3 == 0) goto L_0x0050
            r3.close()     // Catch:{ Exception -> 0x00d9 }
        L_0x0050:
            if (r1 == 0) goto L_0x005a
            com.lisyx.tap.utils.CommandExecution$ProcessKiller r0 = new com.lisyx.tap.utils.CommandExecution$ProcessKiller     // Catch:{ Exception -> 0x00af }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00af }
            r5.addShutdownHook(r0)     // Catch:{ Exception -> 0x00af }
        L_0x005a:
            java.lang.System.gc()
            r0 = r4
            goto L_0x000f
        L_0x005f:
            java.lang.String r0 = "exit\n"
            r3.writeBytes(r0)     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r3.flush()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            com.lisyx.tap.utils.CommandExecution$PrintStream r0 = new com.lisyx.tap.utils.CommandExecution$PrintStream     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.io.InputStream r2 = r1.getErrorStream()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r6 = 1
            r0.<init>(r2, r6)     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            com.lisyx.tap.utils.CommandExecution$PrintStream r2 = new com.lisyx.tap.utils.CommandExecution$PrintStream     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.io.InputStream r6 = r1.getInputStream()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r7 = 0
            r2.<init>(r6, r7)     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r0.start()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r2.start()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            int r6 = r1.waitFor()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r4.result = r6     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r0.join()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r2.join()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.StringBuffer r2 = r2.stringBuffer     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r4.successMsg = r2     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.StringBuffer r0 = r0.stringBuffer     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r4.errorMsg = r0     // Catch:{ Exception -> 0x0047, all -> 0x00b9 }
            r3.close()     // Catch:{ Exception -> 0x00b4 }
        L_0x00a4:
            if (r1 == 0) goto L_0x005a
            com.lisyx.tap.utils.CommandExecution$ProcessKiller r0 = new com.lisyx.tap.utils.CommandExecution$ProcessKiller     // Catch:{ Exception -> 0x00af }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00af }
            r5.addShutdownHook(r0)     // Catch:{ Exception -> 0x00af }
            goto L_0x005a
        L_0x00af:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x005a
        L_0x00b4:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00a4
        L_0x00b9:
            r0 = move-exception
            r4 = r0
        L_0x00bb:
            if (r3 == 0) goto L_0x00c0
            r3.close()     // Catch:{ Exception -> 0x00e3 }
        L_0x00c0:
            if (r1 == 0) goto L_0x00ca
            com.lisyx.tap.utils.CommandExecution$ProcessKiller r0 = new com.lisyx.tap.utils.CommandExecution$ProcessKiller     // Catch:{ Exception -> 0x00e8 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x00e8 }
            r5.addShutdownHook(r0)     // Catch:{ Exception -> 0x00e8 }
        L_0x00ca:
            throw r4
        L_0x00cb:
            r0 = move-exception
            r3 = r2
            goto L_0x0048
        L_0x00cf:
            r0 = move-exception
            r3 = r2
            r1 = r2
            r4 = r0
            goto L_0x00bb
        L_0x00d4:
            r0 = move-exception
            r1 = r2
            r3 = r2
            goto L_0x0048
        L_0x00d9:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0050
        L_0x00df:
            r0 = move-exception
        L_0x00e0:
            r3 = r2
            r4 = r0
            goto L_0x00bb
        L_0x00e3:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00c0
        L_0x00e8:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00ca
        L_0x00ed:
            r0 = r4
            goto L_0x000f
        L_0x00f0:
            java.lang.String r0 = "sh"
            goto L_0x0018
        L_0x00f4:
            r0 = move-exception
            r2 = r3
            goto L_0x00e0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lisyx.tap.utils.CommandExecution.execCommand(java.util.List, boolean):com.lisyx.tap.utils.CommandExecution$CommandResult");
    }
}
