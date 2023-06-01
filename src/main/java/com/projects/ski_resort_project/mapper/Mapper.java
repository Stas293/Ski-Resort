package com.projects.ski_resort_project.mapper;

import java.util.List;

public interface Mapper<F, T> {
    T map(F from);

    default T map(F from, T to){
        return to;
    }

    default List<T> map(List<F> fromList){
        return fromList.parallelStream()
                .map(this::map)
                .toList();
    }
}
