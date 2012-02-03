package com.impetus.kundera.examples.crossdatastore.useraddress;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * The Class CassandraCli.
 */
public final class CassandraCli
{

    /** The client. */
    private static Cassandra.Client client;

    /** the log used by this class. */
    private static Log log = LogFactory.getLog(CassandraCli.class);
    
    private static TTransport tr;
     
//     public static void main(String[] args) throws Exception
//     {
//         CassandraCli.truncate("KunderaExamples", "localhost", 9160,"ADDRESS","PERSONNEL");
//      
//     }
     

    /**
     * Gets the client.
     * 
     * @param host
     *            the host
     * @param port
     *            the port
     * @return the client
     * @throws TTransportException
     *             the t transport exception
     */
    private static Cassandra.Client getClient(String host, int port) throws TTransportException
    {
        if (client == null)
        {
            tr = new TFramedTransport(new TSocket(host, port));
            TProtocol proto = new TBinaryProtocol(tr);
            client = new Cassandra.Client(proto);
            tr.open();
        }
        return client;
    }

    /**
     * Truncate.
     * 
     * @param colFamilies
     *            the col family
     * @throws SchemaDisagreementException 
     * @throws InvalidRequestException 
     */
    public static void truncate(String keyspace, String host, int port, String...colFamilies) throws InvalidRequestException, SchemaDisagreementException
    {
        try
        {
            for(String colFamily:colFamilies)
            {
                client = getClient(host, port);
                client.send_set_keyspace(keyspace);
                log.info("truncate " + colFamily);
                client.send_truncate(colFamily);

            client = null;
            tr.close();
            }
        }
        catch (TTransportException e)
        {
            log.error(e.getMessage());
        }
        catch (TException e)
        {
            log.error(e.getMessage());
        }
    }
}
