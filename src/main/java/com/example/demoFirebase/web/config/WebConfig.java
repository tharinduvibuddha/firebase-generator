package com.example.demoFirebase.web.config;

import com.example.demoFirebase.domain.model.TestEntity;
import com.example.demoFirebase.web.dto.test.TestJson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.modelmapper.spi.MappingContext;;

public class WebConfig {
    public final static String MODEL_MAPPER = "ModelMapperWeb";
   // test that properties are mapped as expected:
    @Bean(name= MODEL_MAPPER)
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(new Converter<TestEntity, TestJson>(){

            public TestJson convert (MappingContext<TestEntity,TestJson>context){
                TestEntity entity = context.getSource();
                TestJson testJson = context.getDestination();
                testJson.setOutId(entity.getId());
                testJson.setName(entity.getName());

                return testJson;

            }


        });
    return mapper;
}


}
