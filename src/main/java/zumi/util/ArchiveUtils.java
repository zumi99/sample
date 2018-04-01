package zumi.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;


public class ArchiveUtils {
    public static void extractTarGz(String file, String dest) throws IOException {
        File target = new File(file);
        if(!target.exists())
            throw new IllegalArgumentException("Archive doesnt exist");
        FileInputStream fin = new FileInputStream(target);
        int BUFFER = 2048;
        byte data[] = new byte[BUFFER];

        BufferedInputStream in = new BufferedInputStream(fin);
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                File f = new File(dest +File.separator +  entry.getName());
                f.mkdirs();
            } else {
                int count;
                FileOutputStream fos = new FileOutputStream(dest + File.separator +  entry.getName());
                BufferedOutputStream destStream = new BufferedOutputStream(fos, BUFFER);
                while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
                    destStream.write(data, 0, count);
                }
                destStream.flush();

                IOUtils.closeQuietly(destStream);
            }
        }
        tarIn.close();
    }
}
