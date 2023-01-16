package im.conversations.android.xmpp.model.disco.info;

import im.conversations.android.annotation.XmlElement;
import im.conversations.android.xmpp.model.Extension;

@XmlElement
public class Identity extends Extension {
    public Identity() {
        super(Identity.class);
    }

    public String getCategory() {
        return this.getAttribute("category");
    }

    public String getType() {
        return this.getAttribute("type");
    }

    public String getLang() {
        return this.getAttribute("xml:lang");
    }

    public String getIdentityName() {
        return this.getAttribute("name");
    }
}
