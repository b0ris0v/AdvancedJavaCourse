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
            Object object = ReadXMLFileSAX.readXML(fullPath);
            if (object.getClass() == SerializationObject.class) {
                System.out.println(object);
            }
        }
    }

}
