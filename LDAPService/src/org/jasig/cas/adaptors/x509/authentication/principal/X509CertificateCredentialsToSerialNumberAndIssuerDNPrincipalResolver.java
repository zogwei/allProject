/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.adaptors.x509.authentication.principal;

import java.security.cert.X509Certificate;

import org.inspektr.common.ioc.annotation.NotNull;

/**
 * This class is targeted at usage for mapping to an existing user record. It
 * can construct a highly-likely unique DN based on a certificate's serialnumber
 * and its issuerDN. example:
 * SERIALNUMBER=20267647332258882251479793556682961758, SERIALNUMBER=200301,
 * CN=Citizen CA, C=BE see RFC3280 The combination of a certificate serial
 * number and the issuerDN *should* be unique: - The certificate serialNumber is
 * by its nature unique for a certain issuer. - The issuerDN is RECOMMENDED to
 * be unique. Both the serial number and the issuerDN are REQUIRED in a
 * certificate. Note: comparison rules state the compare should be
 * case-insensitive. LDAP value description: EQUALITY distinguishedNameMatch
 * SYNTAX 1.3.6.1.4.1.1466.115.121.1.12 [=distinguishedName]
 * 
 * @author Jan Van der Velpen
 * @version $Revision: 42776 $ $Date: 2008-01-04 09:15:42 -0500 (Fri, 04 Jan 2008) $
 * @since 3.1
 */
public final class X509CertificateCredentialsToSerialNumberAndIssuerDNPrincipalResolver
    extends AbstractX509CertificateCredentialsToPrincipalResolver {

    /** Prefix for Certificate Serial Number. */
    @NotNull
    private String serialNumberPrefix = "SERIALNUMBER=";

    /** Prefix for Value Delimiter. */
    @NotNull
    private String valueDelimiter = ", ";

    /**
     * Sets a prefix for the certificate serialnumber (default: "SERIALNUMBER=")
     * 
     * @param serialNumberPrefix The serialNumberPrefix to set.
     */
    public void setSerialNumberPrefix(final String serialNumberPrefix) {
        this.serialNumberPrefix = serialNumberPrefix;
    }

    /**
     * Sets a delimiter to separate the two certificate properties in the string
     * (default: ", ")
     * 
     * @param valueDelimiter The valueDelimiter to set.
     */
    public void setValueDelimiter(final String valueDelimiter) {
        this.valueDelimiter = valueDelimiter;
    }

    protected String resolvePrincipalInternal(
        final X509Certificate certificate) {
        return this.serialNumberPrefix
            + certificate.getSerialNumber().toString() + this.valueDelimiter
            + certificate.getIssuerDN().getName();
    }
}
