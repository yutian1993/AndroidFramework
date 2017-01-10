package com.yutian.utillib.CommonUtil;

import android.os.Environment;

import com.jcraft.jzlib.GZIPInputStream;
import com.jcraft.jzlib.GZIPOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created by wuwenchuan on 16-7-18.
 */
public class CompressUtil {
    private static final Logger LOG = Logger.getLogger("CompressUtil");

    /**
     * Use to compress a byte array
     * @param data need compress bytes
     * @return compressed bytes
     */
//    @Deprecated
//    public static byte[] compress(byte[] data)
//    {
//        Deflater deflater = new Deflater();
//        deflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        deflater.finish();
//        byte[] buffer = new byte[1024];
//        while (!deflater.finished()) {
//            int count = deflater.deflate(buffer); // returns the generated code... index
//            outputStream.write(buffer, 0, count);
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            LOG.info("Compress: Stream close error!");
//            e.printStackTrace();
//        }
//        byte[] output = outputStream.toByteArray();
//        LOG.info("Original: " + data.length / 1024 + " Kb");
//        LOG.info("Compressed: " + output.length / 1024 + " Kb");
//        return output;
//    }

    /**
     *
     * @param data
     * @return
     */
//    @Deprecated
//    public static byte[] decompress(byte[] data)
//    {
//        Inflater inflater = new Inflater();
//        inflater.setInput(data);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
//        byte[] buffer = new byte[1024];
//        try {
//            while (!inflater.finished()) {
//                int count = inflater.inflate(buffer);
//                outputStream.write(buffer, 0, count);
//            }
//        } catch (DataFormatException e) {
//            LOG.info("Decompress: Data format error!");
//            e.printStackTrace();
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            LOG.info("Decompress: Stream close error!");
//            e.printStackTrace();
//        }
//        byte[] output = outputStream.toByteArray();
//        LOG.info("Original: " + data.length);
//        LOG.info("Compressed: " + output.length);
//        return output;
//    }

    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
            showDataSize(data, b, SizeKindUtil.KB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 压缩Zip
     *
     * @param data
     * @return
     */
//    public static byte[] zip(byte[] data) {
//        byte[] b = null;
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ZipOutputStream zip = new ZipOutputStream(bos);
//            ZipEntry entry = new ZipEntry("zip");
//            entry.setSize(data.length);
//            zip.putNextEntry(entry);
//            zip.write(data);
//            zip.closeEntry();
//            zip.close();
//            b = bos.toByteArray();
//            bos.close();
//            showDataSize(data, b, SizeKindUtil.B);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return b;
//    }

    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
//    public static byte[] unZip(byte[] data) {
//        byte[] b = null;
//        try {
//            ByteArrayInputStream bis = new ByteArrayInputStream(data);
//            ZipInputStream zip = new ZipInputStream(bis);
//            while (zip.getNextEntry() != null) {
//                byte[] buf = new byte[1024];
//                int num = -1;
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                while ((num = zip.read(buf, 0, buf.length)) != -1) {
//                    baos.write(buf, 0, num);
//                }
//                b = baos.toByteArray();
//                baos.flush();
//                baos.close();
//            }
//            zip.close();
//            bis.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return b;
//    }

    /***
     * 压缩BZip2
     *
     * @param data
     * @return
     */
    public static byte[] bZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            BZip2CompressorOutputStream bzip2 = new BZip2CompressorOutputStream(bos);
            bzip2.write(data);
            bzip2.flush();
            bzip2.close();
            b = bos.toByteArray();
            bos.close();
            showDataSize(data, b, SizeKindUtil.KB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压BZip2
     *
     * @param data
     * @return
     */
    public static byte[] unBZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BZip2CompressorInputStream bzip2 = new BZip2CompressorInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            bzip2.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 显示数据压缩前后的大小
     * @param original
     * @param output
     */
    public static void showDataSize(byte[] original, byte[] output, SizeKindUtil sizekind)
    {
        LOG.info("Original: " + calculateDataSize(original.length, sizekind) + sizekind.getKind());
        LOG.info("Compressed: " + calculateDataSize(output.length, sizekind) + sizekind.getKind());
    }

    public static int calculateDataSize(int length, SizeKindUtil sizekind)
    {
        return length/sizekind.getStandardSize();
    }

    public static byte[] zip4j(byte[] data)
    {
        try {
            //String filename = DirUtil.getSDCardDir() + "/temp.zip";
            final File tempZipFile = File.createTempFile("temp",".zip");
            tempZipFile.delete();
            System.out.println(tempZipFile.getAbsolutePath());
            ZipFile temp = new ZipFile(tempZipFile.getAbsolutePath());
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
            parameters.setFileNameInZip("1.png");
            parameters.setSourceExternalStream(true);

            InputStream is = new FileInputStream(Environment.getExternalStorageDirectory().toString()+"/1.png");
            temp.addStream(is, parameters);
            is.close();

            byte[] temp1 = InputStreamUtil.InputStreamTOByte(new FileInputStream(tempZipFile.getAbsolutePath()));

            showDataSize(data, temp1, SizeKindUtil.KB);

            //uncompress
            ZipFile newtemp = new ZipFile(tempZipFile.getAbsolutePath());
            newtemp.extractAll(tempZipFile.getParent());


        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] zip4jCompressByte(byte[] data)
    {
        ZipParameters zipParam = new ZipParameters();
        zipParam.setSourceExternalStream(true);
        zipParam.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParam.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bo, new ZipModel());
        zipParam.setFileNameInZip("1.png");
        try {
            zout.putNextEntry(null, zipParam);
            zout.write(data);
            zout.closeEntry();
            zout.finish();
            zout.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showDataSize(data, bo.toByteArray(), SizeKindUtil.KB);

        return null;
    }

    public static byte[] zip4jUncompressByte(byte[] data)
    {
        ZipParameters zipParam = new ZipParameters();
        zipParam.setSourceExternalStream(true);
        zipParam.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParam.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zout = new ZipOutputStream(bo, new ZipModel());
        zipParam.setFileNameInZip("1.png");
        try {
            zout.putNextEntry(null, zipParam);
            zout.write(data);
            zout.closeEntry();
            zout.finish();
            zout.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showDataSize(data, bo.toByteArray(), SizeKindUtil.KB);

        return null;
    }

//    /**
//     *jzlib 压缩数据
//     *
//     * @param object
//     * @return
//     * @throws IOException
//     */
//    public static byte[] jzlib(byte[] object) {
//
////        byte[] data = http://www.cnblogs.com/zhangyukun/p/null;
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ZOutputStream zOut = new ZOutputStream(out,
//                    JZlib.Z_DEFAULT_COMPRESSION);
//            DataOutputStream objOut = new DataOutputStream(zOut);
//            objOut.write(object);
//            objOut.flush();
//            zOut.close();
//            data = http://www.cnblogs.com/zhangyukun/p/out.toByteArray();
//            out.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//    /**
//     *jzLib压缩的数据
//     *
//     * @param object
//     * @return
//     * @throws IOException
//     */
//    public static byte[] unjzlib(byte[] object) {
//
//        byte[] data = http://www.cnblogs.com/zhangyukun/p/null;
//        try {
//            ByteArrayInputStream in = new ByteArrayInputStream(object);
//            ZInputStream zIn = new ZInputStream(in);
//            byte[] buf = new byte[1024];
//            int num = -1;
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            while ((num = zIn.read(buf, 0, buf.length)) != -1) {
//                baos.write(buf, 0, num);
//            }
//            data = http://www.cnblogs.com/zhangyukun/p/baos.toByteArray();
//            baos.flush();
//            baos.close();
//            zIn.close();
//            in.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }


}
