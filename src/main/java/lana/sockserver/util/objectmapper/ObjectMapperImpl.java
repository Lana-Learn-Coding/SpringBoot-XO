package lana.sockserver.util.objectmapper;

import org.modelmapper.ModelMapper;

//Singleton wrapper for modelMapper
public class ObjectMapperImpl implements ObjectMapper {
    private static ObjectMapperImpl ourInstance = new ObjectMapperImpl();
    private final ModelMapper modelMapper = new ModelMapper();

    private ObjectMapperImpl() {
    }

    public static ObjectMapperImpl getInstance() {
        return ourInstance;
    }

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}
