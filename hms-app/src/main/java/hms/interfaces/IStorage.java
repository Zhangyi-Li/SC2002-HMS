package interfaces;

import java.util.List;

public interface IStorage<T> {
    // Method to import data into the storage
    void importData();

    // Method to get the list of data of type T
    List<T> getData();
}
