package web.main;

import java.io.*;
import java.util.*;

public class VFSImpl implements VFS {

    private String root;

    public VFSImpl(String root) throws Exception {
        if (isExist(root) && isDirectory(root))
            this.root = root;
    }

    public boolean isExist(String path) throws Exception {
        try {
            if (root != null) {
                File tmpDir = new File(root+path);
                return tmpDir.exists();
            } else {
                File tmpDir = new File(path);
                return tmpDir.exists();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isDirectory(String path) throws Exception {
        try {
            if (root != null) {
                File tmpDir = new File(root+path);
                return tmpDir.isDirectory();
            } else {
                File tmpDir = new File(path);
                return tmpDir.isDirectory();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String getAbsolutePath(String file) throws Exception {
        try {
            File f = new File(root+file);
            return f.getAbsolutePath();
        } catch (Exception e) {
            throw e;
        }
    }

    public byte[] getBytes(String path) {
        byte[] getBytes = {};
        try {
            File file = new File(root+path);
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }

    public String getUTF8Text(String file) {
        StringBuilder output = null;
        try {
            File fileDir = new File(root+file);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String str;
            while ((str = in.readLine()) != null) {
                output.append(str);
            }
            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return output.toString();
    }

    public Iterator<String> getIterator(String startDir) {
        File folder = new File(root+startDir);
        File[] listOfFiles = folder.listFiles();
        Set<String> fileNames = new HashSet<String>();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                fileNames.add(listOfFile.getName());
            }
        }

        return fileNames.iterator();
    }
}
