package com.example.Blog_Application2.Service.mappers;

import org.mapstruct.Mapper;


public interface MapperClass<E,D,C> {

    E toEntity(D d);

    D toDto(E e);

    D toDtoTwo(C c);

    D toDtoThree(D d);


}
