package ltd.penny.projects.dynamo.streams.consumer;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;

public class RecordProcessorFactory implements IRecordProcessorFactory {
    public IRecordProcessor createProcessor() {
        return new RecordProcessor();
    }
}
