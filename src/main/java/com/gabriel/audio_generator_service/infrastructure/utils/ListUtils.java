package com.gabriel.audio_generator_service.infrastructure.utils;

import java.util.Collections;
import java.util.List;

public class ListUtils {

    public static <T> List<T> getEmptyListFromNullList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

}
