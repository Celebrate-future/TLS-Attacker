/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.extension.HRRKeyShareExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.HRRKeyShareExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

/**
 * @author Nurullah Erinola <nurullah.erinola@rub.de>
 */
public class HRRKeyShareExtensionPreparator extends ExtensionPreparator<HRRKeyShareExtensionMessage> {

    private final HRRKeyShareExtensionMessage msg;

    public HRRKeyShareExtensionPreparator(TlsContext context, HRRKeyShareExtensionMessage message,
            HRRKeyShareExtensionSerializer serializer) {
        super(context, message, serializer);
        this.msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        LOGGER.debug("Preparing HRRKeyShareExtensionMessage");
        prepareSelectedGroup(msg);
    }

    private void prepareSelectedGroup(HRRKeyShareExtensionMessage msg) {
        msg.setSelectedGroup(context.getConfig().getKeyShareType().getValue());
        LOGGER.debug("SelectedGroup: " + ArrayConverter.bytesToHexString(msg.getSelectedGroup().getValue()));
    }

}