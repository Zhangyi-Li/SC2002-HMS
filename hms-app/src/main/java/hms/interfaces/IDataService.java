package interfaces;

import java.util.List;

public interface IDataService<T> {
    void importData();

    List<T> getData();
}
