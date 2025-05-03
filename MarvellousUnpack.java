import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MarvellousUnpack {
    FileOutputStream outstream = null;

    public MarvellousUnpack(String src) throws Exception {
        unpack(src);
    }

    public void unpack(String filePath) throws Exception {
        try (FileInputStream instream = new FileInputStream(filePath)) {
            byte[] Magic = new byte[12];
            instream.read(Magic, 0, Magic.length);

            String Magicstr = new String(Magic);
            if (!Magicstr.equals("Marvellous11")) {
                throw new InvalidFileException("Invalid packed file format");
            }

            byte[] header = new byte[100];
            int length;

            while ((length = instream.read(header)) > 0) {
                String headerStr = new String(header).trim();
                String[] parts = headerStr.split(" ");

                if (parts.length < 2) {
                    System.err.println("Invalid file header: " + headerStr);
                    break;
                }

                String fullPath = parts[0];
                String filename = new File(fullPath).getName();  // Safe extraction
                int size = Integer.parseInt(parts[1]);

                byte[] fileData = new byte[size];
                instream.read(fileData, 0, size);

                try (FileOutputStream fout = new FileOutputStream(filename)) {
                    fout.write(fileData);
                }

                System.out.println("Unpacked: " + filename);
            }

        } catch (InvalidFileException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Unpacking error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
