/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.parser;

import de.rub.nds.tlsattacker.tls.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.tls.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.protocol.message.CertificateVerifyMessage;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class CertificateVerifyMessageParser extends HandshakeMessageParser<CertificateVerifyMessage> {

    public CertificateVerifyMessageParser(int pointer, byte[] array) {
        super(pointer, array, HandshakeMessageType.CERTIFICATE_VERIFY);
    }

    @Override
    protected void parseHandshakeMessageContent(CertificateVerifyMessage msg) {
        msg.setSignatureHashAlgorithm(parseByteArrayField(HandshakeByteLength.SIGNATURE_HASH_ALGORITHM));
        msg.setSignatureLength(parseIntField(HandshakeByteLength.SIGNATURE_LENGTH));
        msg.setSignature(parseByteArrayField(msg.getSignatureLength().getValue()));
    }

    @Override
    protected CertificateVerifyMessage createHandshakeMessage() {
        return new CertificateVerifyMessage();
    }

}