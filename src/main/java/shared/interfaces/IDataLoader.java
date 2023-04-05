package shared.interfaces;

/**
 * Contains logic-declaration for loading models.
 */
public interface IDataLoader {
    /**
     * loads data
     * @param clazz data-class
     * @return
     * @param <T>
     */
    <T> T load(Class<T> clazz);

    boolean getIsDataLoading();

    void setIsDataLoading(boolean status);
}
