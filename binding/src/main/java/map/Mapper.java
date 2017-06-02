package map;

import core.utils.Page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ebiz on 24/04/17.
 */
public interface Mapper<T, U> {
    /**
     * T from U.
     * @param u in
     * @return T
     */
    T from(U u);

    /**
     * T to U.
     * @param t in
     * @return U
     */
    U to(T t);

    /**
     * Collection of T to Collection of U.
     * @param collectionIn in
     * @return Collection of U
     */
    default Collection<U> toList(Collection<T> collectionIn) {
        Collection<U> collectionOut = new ArrayList<>();
        for (T t:collectionIn) {
            collectionOut.add(to(t));
        }
        return collectionOut;
    }

    /**
     * Collection of T from Collection of U.
     * @param collectionIn in
     * @return Collection of T
     */
    default Collection<T> fromList(Collection<U> collectionIn) {
        Collection<T> collectionOut = new ArrayList<>();
        for (U u:collectionIn) {
            collectionOut.add(from(u));
        }
        return collectionOut;
    }

    /**
     * Page of T to Page of U.
     * @param pageIn in
     * @return Page of U
     */
    default Page<U> toPage(Page<T> pageIn) {
        return new Page<>((new ArrayList<>(toList(pageIn.getListPage()))), pageIn.getCurrentPage(), pageIn.getPageSize());
    }

    /**
     * Page of T from Page of U.
     * @param pageIn in
     * @return Page of T
     */
    default Page<T> fromPage(Page<U> pageIn) {
        return new Page<>((new ArrayList<>(fromList(pageIn.getListPage()))), pageIn.getCurrentPage(), pageIn.getPageSize());
    }
}
