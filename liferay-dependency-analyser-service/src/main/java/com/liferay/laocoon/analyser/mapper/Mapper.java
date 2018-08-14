package com.liferay.laocoon.analyser.mapper;

import java.util.Optional;

public interface Mapper<T, U> {

    Optional<T> map(U model);

}
