package web.main;

import java.io.Serializable;

public class SerializationObject extends  Object implements Serializable {
    private static final long serialVersionUID = -1;
    private String name;
    private int age;

    private  Object test = new Object();

    public SerializationObject() {
        this.name = "Nobody";
        this.age = 0;
    }

    public SerializationObject(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SerializationObject{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }

    private void setName(String name) {
        this.name = name;
    }
}
