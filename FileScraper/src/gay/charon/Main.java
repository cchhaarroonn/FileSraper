package gay.charon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class Main {

    public static BufferedWriter fileWriter;
    public static List<String> allowedFiles;
    public static List<String> avoidDir;

    public static void main(String[] args) throws Exception{
        String home = System.getProperty("user.home");
        Writer writer = new FileWriter(System.getProperty("user.home") + File.separator + "output.txt", false);
        fileWriter = new BufferedWriter(writer);

        //Add dirs and file extensions to list
        allowedFiles = new FileTypes().AllowedTypes;
        avoidDir = new AvoidedDir().avoidDir;

        finder(home);
        fileWriter.close();
        System.out.println("File reading done!\nYou can see your file at: " + home + "\\output.txt");
    }

    public static void finder(String path) throws Exception {
        File root = new File(path);
        File[] list = root.listFiles();

        if(list == null) return;

        for(File f: list) {
            if (f.isDirectory()) {
                String name = f.getName();
                if (avoidDir.contains(name.toLowerCase())) return; //If you want to read files from those folders too just remove this or remove folder from class
                finder(f.getAbsolutePath());

            } else {
                int index = f.getName().lastIndexOf(".");
                String fileType = f.getName().substring(index + 1);
                if(allowedFiles.contains("." + fileType.toUpperCase())) {
                    fileWriter.write(f.getAbsolutePath());
                    fileWriter.newLine();
                }
            }
        }
    }
}
