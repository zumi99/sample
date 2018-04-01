import zumi.util.ArchiveUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Start------->");

//        String str = "image/xxx/eps/1234567.eps";
//        System.out.println(str.replace(".eps", ".pdf"));

//        File tmpDir = new File("/Users/zumi/tmp");
//        File[] files = tmpDir.listFiles();
//        if (files == null) {
//            throw new FileNotFoundException("~/tmp");
//        }
//
//        HashSet<String> inputFiles = new HashSet<String>();
//        for (File file : files) {
//            inputFiles.add(file.getName());
//            System.out.println(file.getName());
//        }
//        String[] targetFiles = new String[] {"ccc.tsv", "aaa.tsv", "ddd.tsv","bbb.tsv"};
//        for (String target : targetFiles) {
//            if (!inputFiles.contains(target)) {
//                System.out.println("Error !! " + target);
//                return;
//            }
//        }

//        FilenameFilter filter = new FilenameFilter() {
//            public boolean accept(File file, String str) {
//                if (str.matches("db_update_[0-9]{8}.tar.gz")) {
//                    return true;
//                }
//                return false;
//            }
//        };
//
//        String WORK_ROOT_PATH = "/Users/zumi/tmp/work";
//        File dbUpdateDir = new File("/Users/zumi/tmp/db_update");
//        File[] files = dbUpdateDir.listFiles(filter);
//        for (File file : files) {
//            String regx = "[0-9]{8}";
//            Pattern pattern = Pattern.compile(regx);
//            Matcher matcher = pattern.matcher(file.getName());
//            if (matcher.find()) {
//                String day = matcher.group();
//
//                File workDir = new File(WORK_ROOT_PATH + "/" + day) ;
//                if (!workDir.exists()) {
//                    if (!workDir.mkdirs()) {
//                        System.out.println("Can't make directory!!");
//                    }
//                }
//
//                //ファイル移動&展開
//            }
//            System.out.println(file.getName());
//        }

        Path dbUpdateDir = Paths.get("/Users/zumi/tmp/db_update");
        DirectoryStream<Path> fileStream = Files.newDirectoryStream(dbUpdateDir, new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) {
                if (entry.getFileName().toString().matches("db_update_[0-9]{8}.tar.gz")) {
                    return true;
                }
                return false;
            }
        });

        String WORK_ROOT_PATH = "/Users/zumi/tmp/work";
        for (Path entry : fileStream) {
            System.out.println(entry.getFileName().toString());
            String regx = "[0-9]{8}";
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(entry.getFileName().toString());
            if (matcher.find()) {
                String day = matcher.group();

                Path workDir = Paths.get(WORK_ROOT_PATH + "/" + day);
                if (!Files.exists(workDir)) {
                    Files.createDirectories(workDir);
                }

                //ファイル移動
                Path dest = Paths.get(workDir.toString() + "/" + entry.getFileName().toString());
                Files.move(entry, dest);

                //ファイル解凍
                ArchiveUtils.extractTarGz(dest.toString(), workDir.toString());
            }
        }

    }
}