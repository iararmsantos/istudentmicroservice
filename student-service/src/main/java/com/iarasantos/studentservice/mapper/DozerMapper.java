package com.iarasantos.studentservice.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
//import org.modelmapper.ModelMapper;

public class DozerMapper {
    //Dozer is deprecated
    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
//    private static ModelMapper mapper = new ModelMapper();

    public static <O, D> D parseObject(O origin, Class<D> destiny) {
        return mapper.map(origin, destiny);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destiny) {
        List<D> destinyObjects = new ArrayList<>();
        for(O o : origin) {
            destinyObjects.add(mapper.map(o, destiny));
        }
        return destinyObjects;
    }
}
