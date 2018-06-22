package ltd.penny.projects.dynamo.streams.consumer;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;


public class RecordProcessor implements IRecordProcessor {
    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public void initialize(InitializationInput initializationInput) {
/*
the starting sequence number from which
records would be provided to the record processor instance.
This is the sequence number that was last check pointed by the record processor
instance previously processing the same shard.

the pending checkpoint sequence number (if any) that could not be committed
before the previous record processor instance stopped.

 */
        System.out.println("initialising record processor");
        String shardId = initializationInput.getShardId();
        System.out.println("shard id: "  + shardId);
    }

    public void processRecords(ProcessRecordsInput processRecordsInput) {
        System.out.println("processing records");
        List<Record> records = processRecordsInput.getRecords();
        for (Record record : records) {
            System.out.println("record is " + record);
            ByteBuffer data = record.getData();
            try {
                String s = decoder.decode(data).toString();
                System.out.println("uuid is : " + s);
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown(ShutdownInput shutdownInput) {
        System.out.println("shutting down record processor");
        try {
            shutdownInput.getCheckpointer().checkpoint();
            System.out.println("SHUTDOWN ok");
        } catch (InvalidStateException | ShutdownException e) {
            e.printStackTrace();
        }
    }
}
