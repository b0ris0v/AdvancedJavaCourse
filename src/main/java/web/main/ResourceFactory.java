package web.main;

import java.util.Iterator;

public class ResourceFactory {
    VFS vfs;

    {
        try {
            vfs = new VFSImpl("/Volumes/Macbook/Users/vladislavborisov/IdeaProjects/jspserver/vfs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator iterator = vfs.getIterator("");
        while (iterator.hasNext()) {
            String fullPath = (String) iterator.next();
            System.out.println(fullPath);
            ReadXMLFileSAX.readXML(fullPath);
        }
    }

}
