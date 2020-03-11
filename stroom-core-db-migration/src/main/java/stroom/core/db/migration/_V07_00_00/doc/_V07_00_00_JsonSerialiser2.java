package stroom.core.db.migration._V07_00_00.doc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class _V07_00_00_JsonSerialiser2<D> implements _V07_00_00_Serialiser2<D> {
    private static final String META = "meta";

    private final Class<D> clazz;
    private final ObjectMapper mapper;

    public _V07_00_00_JsonSerialiser2(final Class<D> clazz) {
        this.clazz = clazz;
        this.mapper = getMapper(true);
    }

    @Override
    public D read(final Map<String, byte[]> data) throws IOException {
        final byte[] meta = data.get(META);
        return mapper.readValue(new StringReader(_V07_00_00_EncodingUtil.asString(meta)), clazz);
    }

    @Override
    public Map<String, byte[]> write(final D document) throws IOException {
        final StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, document);
        final Map<String, byte[]> data = new HashMap<>();
        data.put(META, _V07_00_00_EncodingUtil.asBytes(stringWriter.toString()));
        return data;
    }

    protected ObjectMapper getMapper(final boolean indent) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, indent);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Enabling default typing adds type information where it would otherwise be ambiguous, i.e. for abstract classes
//        mapper.enableDefaultTyping();
        return mapper;
    }
}
