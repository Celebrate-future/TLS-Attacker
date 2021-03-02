package de.rub.nds.tlsattacker.core.protocol;

import de.rub.nds.tlsattacker.core.protocol.serializer.ProtocolMessageSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ProtocolMessageHandler<MessageT extends ProtocolMessage> extends Handler<MessageT> {
    protected static final Logger LOGGER = LogManager.getLogger();
    /**
     * tls context
     */
    protected final TlsContext tlsContext;

    public ProtocolMessageHandler(TlsContext tlsContext) {
        this.tlsContext = tlsContext;
    }

    @Override
    public abstract ProtocolMessageParser<MessageT> getParser(byte[] message, int pointer);

    @Override
    public abstract ProtocolMessagePreparator<MessageT> getPreparator(MessageT message);

    @Override
    public abstract ProtocolMessageSerializer<MessageT> getSerializer(MessageT message);

    /**
     * Adjusts the TLS Context according to the received or sending ProtocolMessage
     *
     * @param message
     * The Message for which this context should be adjusted
     */
    public abstract void adjustTLSContext(MessageT message);

    public void adjustTlsContextAfterSerialize(MessageT message) {
    }

    /**
     * Performs additional preparations after parsing the message (e.g. ESNI decryption/parsing).
     *
     * @param message
     */
    public void prepareAfterParse(MessageT message) {
    }

    @Override
    public final void adjustContext(MessageT message) {
        adjustTLSContext(message);
    }
}
