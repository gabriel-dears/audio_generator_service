package com.gabriel.audio_generator_service.infrastructure.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListUtilsTest {

    @Test
    void testEmptyListExists() {
        List<Object> list = ListUtils.getEmptyListFromNullList(null);
        assertThat(list).isNotNull();
    }

    @Test
    void testPopulatedListIsTheSame() {
        ArrayList<Object> objects = new ArrayList<>();
        List<Object> list = ListUtils.getEmptyListFromNullList(objects);
        assertThat(objects).isEqualTo(list);
    }

}