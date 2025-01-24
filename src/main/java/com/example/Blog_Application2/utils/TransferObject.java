package com.example.Blog_Application2.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransferObject {


    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T,D> T convert(D object, Class<T> toClass){
        return Optional.ofNullable(object).map(o-> modelMapper.map(o, toClass)).orElse(null);
    }


    public static <T, D> List<T> convert(List<D> objects, Class<T> toClass) {
        List<T> objectsList = new ArrayList<>();
        objects.forEach(obj -> {
            objectsList.add(Optional.ofNullable(obj).map(o -> modelMapper.map(o, toClass)).orElse(null));
        });
        return objectsList;
    }



}
