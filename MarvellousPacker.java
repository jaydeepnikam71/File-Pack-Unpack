import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class MarvellousPacker {
    FileOutputStream outstream = null;
    String ValidExt[] = {".txt", ".c", ".java", ".cpp"};

    public MarvellousPacker(String src, String Dest) throws Exception {
        String Magic = "Marvellous11";
        byte arr[] = Magic.getBytes();
        outstream = new FileOutputStream(Dest);
        outstream.write(arr, 0, arr.length);
        listAllFiles(src);
    }

    public void listAllFiles(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        String name = filePath.getFileName().toString();
                        String ext = name.substring(name.lastIndexOf("."));
                        if (Arrays.asList(ValidExt).contains(ext)) {
                            Pack(filePath.toString());
                        }
                    } catch (Exception e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Error walking directory: " + e.getMessage());
        }
    }

    public void Pack(String filePath) {
        try (FileInputStream instream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[1024];
            File fobj = new File(filePath);
            String Header = filePath + " " + fobj.length();
            Header = String.format("%-100s", Header); // Pad to 100 bytes
            outstream.write(Header.getBytes());

            int length;
            while ((length = instream.read(buffer)) > 0) {
                outstream.write(buffer, 0, length);
            }

            System.out.println("Packed: " + filePath);
        } catch (Exception e) {
            System.err.println("Error packing file: " + filePath + " -> " + e.getMessage());
        }
    }
}
