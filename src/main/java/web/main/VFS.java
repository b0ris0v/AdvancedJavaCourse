package web.main;

import java.util.Iterator;

public interface VFS {
    boolean isExist(String path) throws Exception;
    boolean isDirectory(String path) throws Exception;
    String getAbsolutePath(String file) throws Exception;
    byte[] getBytes(String file);
    String getUTF8Text(String file);
    Iterator<String> getIterator(String startDir);
}
