/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.config;

import de.rub.nds.tlsattacker.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.tlsattacker.modifiablevariable.VariableModification;
import de.rub.nds.tlsattacker.modifiablevariable.integer.IntegerModificationFactory;
import de.rub.nds.tlsattacker.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.tlsattacker.modifiablevariable.singlebyte.ByteExplicitValueModification;
import de.rub.nds.tlsattacker.tls.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.tls.protocol.message.ProtocolMessage;
import de.rub.nds.tlsattacker.tls.protocol.message.ClientHelloMessage;
import de.rub.nds.tlsattacker.tls.record.Record;
import de.rub.nds.tlsattacker.tls.util.WorkflowTraceSerializer;
import de.rub.nds.tlsattacker.tls.workflow.TlsConfig;
import de.rub.nds.tlsattacker.tls.workflow.WorkflowTrace;
import de.rub.nds.tlsattacker.tls.workflow.action.SendAction;
import de.rub.nds.tlsattacker.tls.workflow.action.TLSAction;
import de.rub.nds.tlsattacker.tls.workflow.factory.WorkflowConfigurationFactory;
import de.rub.nds.tlsattacker.util.RandomHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public class WorkflowTraceSerializerTest {

    private static final Logger LOGGER = LogManager.getLogger(WorkflowTraceSerializerTest.class);

    /**
     * Test of write method, of class WorkflowTraceSerializer.
     * 
     * @throws java.lang.Exception
     */
    // TODO Test all messages with all modifiable variables
    @Test
    public void testWriteRead() throws Exception {
        WorkflowConfigurationFactory factory = new WorkflowConfigurationFactory(new TlsConfig());
        WorkflowTrace trace = factory.createFullWorkflow();
        // pick random protocol message and initialize a record with modifiable
        // variable
        List<Record> records = new LinkedList<Record>();
        Record record = new Record();
        record.getContentType().setModification(new ByteExplicitValueModification(Byte.MIN_VALUE));
        record.setMaxRecordLengthConfig(5);
        records.add(record);
        SendAction action = new SendAction(new ClientHelloMessage());
        action.setConfiguredRecords(records);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WorkflowTraceSerializer.write(os, trace);

        String serializedWorkflow = new String(os.toByteArray());

        LOGGER.debug(serializedWorkflow);

        ByteArrayInputStream bis = new ByteArrayInputStream(serializedWorkflow.getBytes());
        WorkflowTrace wt = WorkflowTraceSerializer.read(bis);

        os = new ByteArrayOutputStream();
        WorkflowTraceSerializer.write(os, wt);

        LOGGER.info(new String(os.toByteArray()));

        Assert.assertArrayEquals("The serialized workflows have to be equal", serializedWorkflow.getBytes(),
                os.toByteArray());
    }

    @Test
    public void TestWrite() {
        try {
            WorkflowTrace trace = new WorkflowTrace();
            trace.add(new SendAction(new ClientHelloMessage(new TlsConfig())));
            File f = new File("workflowtrace.unittest");
            WorkflowTraceSerializer.write(f, trace);
            Assert.assertTrue(f.exists());
            f.delete();
        } catch (JAXBException | IOException ex) {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            Assert.fail();
        }
    }

}
