package compathon.org.logan_android.model;

import java.io.Serializable;

public class SerializableItem<T> implements Serializable {

    private T data;

    public SerializableItem(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
