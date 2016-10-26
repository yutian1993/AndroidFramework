package com.example;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wuwenchuan on 2016/10/20.
 */
public class GitGoolgePatch {


//    public static String filePath = "F:\\Task\\Oct\\1020\\2016#10#r3\\patches\\android-5.1.1_r37";
//    public static String gitURL = "git clone ssh://wuwenchuan@10.3.11.22:29418/qualcomm";
//    public static String codePath = "F:/Task/Oct/1020/code";
//    public static String remotebranch = "yl_3622a_ta";
//    public static String idsfilepath = "F:/Task/Oct/1020/cherrypickids.txt";

    public static String filePath = "/home/wuwenchuan/patch/201610/";
    public static String gitURL = "git clone ssh://wuwenchuan@10.3.11.22:29418/qualcomm/";
    public static String codePath = "/home/wuwenchuan/code/";
    public static String remotebranch = "yl_3320a_mr"; //"yl_3622a_mpcs_ta";
    public static String idsfilepath = "/home/wuwenchuan/patchcode/cherrypickids.txt";

    /**
     * 用于监控命令输出状况
     */
    public static class SyncPipe implements Runnable {
        public SyncPipe(InputStream istrm, OutputStream ostrm) {
            istrm_ = istrm;
            ostrm_ = ostrm;
        }

        public void run() {
            try {
                final byte[] buffer = new byte[1024];
                for (int length = 0; (length = istrm_.read(buffer)) != -1; ) {
                    ostrm_.write(buffer, 0, length);
                }
            } catch (Exception e) {
                throw new RuntimeException("处理命令出现错误：" + e.getMessage());
            }
        }

        private final OutputStream ostrm_;
        private final InputStream istrm_;
    }

    /**
     * 根据patch目录的内容来同步代码目录
     * @param filepath patch目录
     */
    public static Map<String, String> getAllGitPath(String filepath) {
        List<String> mGitPaths = isGitDir(new File(filepath));
        Map<String, String> mGitPath = new HashMap<String, String>();

        for (String gitpath :
                mGitPaths) {
            if (mGitPath.get(gitpath.replace(gitURL, "")) != null) {
                //Do nothing
            } else {
                mGitPath.put(codePath + gitpath.replace(gitURL, ""), gitpath + ".git");
            }
        }
        return mGitPath;
    }

    /**
     * 同步更新到服务器的代码
     * @param mGitBranchs 所有的分支
     * @return 是否成功运行
     */
    public static boolean syncBranches(Map<String, String> mGitBranchs)
    {
        Iterator<String> key = mGitBranchs.keySet().iterator();
        String keyval = "";

        //To mkdir
        while (key.hasNext()) {
            keyval = key.next();
            File tempFile = new File(keyval);
            if (tempFile.exists()) {
                //Sync code
                System.out.println("Sync ------------>" + mGitBranchs.get(keyval));
                syncCode(keyval);
            } else {
                if (mkdir(tempFile)) {
                    tempFile.delete();
                    System.out.println("Git ------------>" + mGitBranchs.get(keyval));
                    gitClone(tempFile.getParent(), mGitBranchs.get(keyval));
                } else {
                    //error
                    System.out.println("Mkdir error ------------>" + keyval);
                }
            }
        }

        return true;
    }

    /**
     *
     * @param mGitBranchs
     * @param checkcode
     * @return
     */
    public static String switchbranch(Map<String, String> mGitBranchs, boolean checkcode, boolean newbranch, String branchname)
    {
        Iterator<String> key = mGitBranchs.keySet().iterator();
        String keyval = "";

        if (checkcode || newbranch)
        {
            syncBranches(mGitBranchs);
        }

        if (newbranch || branchname == null)
            branchname = generateBranchName(remotebranch);
        File tempFile = null;
        mGitBranchs.keySet().iterator();
        while (key.hasNext())
        {
            keyval = key.next();
            tempFile = new File(keyval);
            if (tempFile.exists()) {
                System.out.println("Into " + keyval);
                if (newbranch)
                    gitcreatebranch(keyval, branchname , "remotes/origin/" + remotebranch);
                else
                    gitcheckoutbranch(keyval, branchname , "remotes/origin/" + remotebranch);
            }
        }

        return branchname;
    }

    /**
     * 同步代码
     * @param codepath 代码所在的路径
     */
    public static void syncCode(String codepath) {
        String[] command = {"sh",};
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + codepath);
            stdin.println("git reset --hard");
            stdin.println("git remote update");
            stdin.println("git pull");
//            stdin.println("git rebase");
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
        }
    }

    /**
     * Clone 远程代码到本地
     * @param codepath 代码路径
     * @param gerritpath gerrit 远程地址
     */
    public static void gitClone(String codepath, String gerritpath) {
        String[] command = {"sh",};
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + codepath);
            stdin.println(gerritpath);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
        }
    }

    /**
     * 切换到对应的branch来cherry pick code
     * @param codepath 代码所在的位置
     * @param branchname 分支名称
     * @param cherrypickid patch id
     */
    public static void gitcherrypick(String codepath, String branchname, String cherrypickid) {
        String[] command = {"sh",};
        Process p = null;
        ByteArrayOutputStream errorInformation = new ByteArrayOutputStream();
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), errorInformation)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + codepath);
            stdin.println("git checkout " + branchname);
//            stdin.println("git remote update");
//            stdin.println("git rebase");
            //stdin.println("git reset --hard");
//            stdin.println("git pull");
            stdin.println("git cherry-pick " + cherrypickid);
            //stdin.close();
            //p.waitFor();
            //System.out.println("");
            //if (!errorInformation.toString().contains("error:"))
            //{
                stdin.println("git push origin HEAD:refs/for/"+remotebranch);
                stdin.close();
                p.waitFor();
                if (errorInformation.toString().contains("error:")) {
                    System.out.println(errorInformation.toString());
                    System.out.println("Push code to server error!");
                } else {
                    System.out.println("Push code to server success!");
                }
                errorInformation.close();
            //} else {
            //    System.out.println("Cherry-pick code error!");
            //}
        } catch (Exception e) {
        }
    }

    /**
     * 生成一个branch
     * @param codepath 代码位置
     * @param branchname 新建的分支名称
     * @param remotebranchname 远程分支名称
     */
    public static void gitcreatebranch(String codepath,
                                       String branchname,
                                       String remotebranchname)
    {
        String[] command = {"sh",};
        Process p = null;
        try {
            //System.out.println("gitcreatebranch");
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + codepath);
            stdin.println("git remote update");
            stdin.println("git checkout -b " + branchname + " " + remotebranchname);
            stdin.println("git checkout " + branchname);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
        }
    }


    public static void gitcheckoutbranch(String codepath,
                                       String branchname,
                                       String remotebranchname)
    {   
        String[] command = {"sh",};
        Process p = null;
        try {
            //System.out.println("gitcreatebranch");
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + codepath);
            stdin.println("git remote update");
            stdin.println("git checkout " + branchname);
            stdin.println("git reset --hard " + remotebranchname);
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
        }   
    }
    /**
     * 生成一个目录中的所有git目录，只针对patch有效
     * @param childpath 目录路径
     * @return
     */
    public static List<String> isGitDir(File childpath) {
        List<String> mGitPath = new ArrayList<String>();
        if (childpath.isDirectory()) {
            for (File childFile : childpath.listFiles()) {
                mGitPath.addAll(isGitDir(childFile));
            }
        } else {
            mGitPath.add(childpath.getParent().replace(filePath, gitURL).replace("\\", "/"));
        }
        return mGitPath;
    }

    /**
     * 根据输入的目录路径，递归生成所需要的目录
     * @param targetfile 需要生成的目录
     * @return 是否成功生成
     */
    public static boolean mkdir(File targetfile) {
        if (targetfile.exists()) {
            //Do nothing
            return true;
        } else {
            if (mkdir(targetfile.getParentFile())) {
                return targetfile.mkdir();
            } else {
                //mkdir error
                return false;
            }
        }
    }


    /**
     * 读取cherry pick ids
     * @param filepath cherry pick ids file
     * @return
     */
    public static Map<String, List<String>> getBranchPatchs(String filepath)
    {
        Map<String, List<String>> mCherryPickIDs = new HashMap<String, List<String>>();
        try {
            String encoding="utf-8";
            File file=new File(filepath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String codepath = null;
                List<String> patches = new ArrayList<String>();
                while((lineTxt = bufferedReader.readLine()) != null){
                    if (lineTxt.startsWith("#"))
                        continue;
                    if (lineTxt.contains("CODEPATH:")) {
                        if (codepath != null) {
                            List<String> ids = new ArrayList<String>();
                            ids.addAll(patches);
                            mCherryPickIDs.put(codepath, ids);
                            patches.clear();
                            patches = new ArrayList<String>();
                        }
                        codepath = lineTxt.replace("CODEPATH:", "");
                    } else {
                        patches.add(lineTxt);
                    }
                }

                //Add last values
                if (patches.size() > 0) {
                    List<String> ids = new ArrayList<String>();
                    ids.addAll(patches);
                    mCherryPickIDs.put(codepath, ids);
                    patches.clear();
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return mCherryPickIDs;
    }

    public static boolean beginApplyPatchs(Map<String, List<String>> patchandcodepath, String branchname)
    {
        Iterator<String> keyIterator = patchandcodepath.keySet().iterator();
        while (keyIterator.hasNext()) {
            String codepath = keyIterator.next();

            System.out.println("CodePath: " + codepath);
            for (String val :
                    patchandcodepath.get(codepath)) {
                System.out.println("Gerrit ID: " + val);
                System.out.println("");
                gitcherrypick(codepath, branchname, val);
            }

            System.out.println();
        }

        return true;
    }

    /**
     * 生成branch唯一name
     * @param remotebranch
     * @return
     */
    public static String generateBranchName(String remotebranch)
    {
        Date temp = new Date();
        SimpleDateFormat simpletime = new SimpleDateFormat("yyyyMMddhhmmss");
        return remotebranch + "_" + simpletime.format(temp);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //根据patch目录生成patch code代码
        Map<String, String> mGitBranches = getAllGitPath(filePath);

        //判断指定目录的代码是否是最新，如果没有代码就去下载，如果有代码，就同步到最新
        syncBranches(mGitBranches);

        //创建一个新branch
        String branchname = switchbranch(mGitBranches, false, true, null);

        //String branchname = "yl_3622a_mpcs_ta_20161024095555";
        System.out.println(branchname);

        //解析cherrypick文件
        Map<String, List<String>> mCherryPickIDs = getBranchPatchs(idsfilepath);

        //开始cherrypick代码到指定的branch name
        beginApplyPatchs(mCherryPickIDs, branchname);

    }

}
