package com.ruxuanwo.fm.client.utils;

import java.util.Map;

/**
 * 集合操作类
 *
 * @author ruxuanwo
 */
public final class CollectionUtils {
    /**
     * 当在一个列表或数组里找不到元素时返回的索引值。
     */

    public static final int INDEX_NOT_FOUND = -1;

    private CollectionUtils() {
    }

    /**
     * Null-safe check if the specified map is empty.
     * <p>
     * Null returns true.
     *
     * @param map the map to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map the map to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !CollectionUtils.isEmpty(map);
    }

    /**
     * 判断某个对象是否存在于特定的数组里。
     */

    public static boolean contains(final Object[] array, final Object objectToFind) {

        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;

    }

    /**
     * 从给定的索引位置开始查找特定对象在特定数组中的索引位置。
     */

    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {

        if (array == null) {

            return INDEX_NOT_FOUND;

        }

        if (startIndex < 0) {

            startIndex = 0;

        }

        if (objectToFind == null) {

            for (int i = startIndex; i < array.length; i++) {

                if (array[i] == null) {

                    return i;

                }

            }

        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {

            for (int i = startIndex; i < array.length; i++) {

                if (objectToFind.equals(array[i])) {

                    return i;

                }

            }

        }

        return INDEX_NOT_FOUND;

    }

    /**
     * 根据给定的对象来查找它在特定数组的索引位置。
     */

    public static int indexOf(final Object[] array, final Object objectToFind) {

        return indexOf(array, objectToFind, 0);

    }
}
