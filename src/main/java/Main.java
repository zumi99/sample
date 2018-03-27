import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Start------->");

        String str = "image/xxx/eps/1234567.eps";
        System.out.println(str.replace(".eps", ".pdf"));

        File tmpDir = new File("/Users/zumi/tmp");
        File[] files = tmpDir.listFiles();
        if (files == null) {
            throw new FileNotFoundException("~/tmp");
        }

        HashSet<String> inputFiles = new HashSet<String>();
        for (File file : files) {
            inputFiles.add(file.getName());
            System.out.println(file.getName());
        }
        String[] targetFiles = new String[] {"ccc.tsv", "aaa.tsv", "ddd.tsv","bbb.tsv"};
        for (String target : targetFiles) {
            if (!inputFiles.contains(target)) {
                System.out.println("Error !! " + target);
                return;
            }
        }
    }
}
