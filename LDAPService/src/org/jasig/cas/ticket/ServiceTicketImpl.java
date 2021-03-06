/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/
 */
package org.jasig.cas.ticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Service;
import org.springframework.util.Assert;

/**
 * Domain object representing a Service Ticket. A service ticket grants specific
 * access to a particular service. It will only work for a particular service.
 * Generally, it is a one time use Ticket, but the specific expiration policy
 * can be anything.
 * 
 * @author Scott Battaglia
 * @version $Revision: 43947 $ $Date: 2008-07-10 16:31:57 -0400 (Thu, 10 Jul 2008) $
 * @since 3.0
 */
@Entity
@Table(name="SERVICETICKET")
public final class ServiceTicketImpl extends AbstractTicket implements
    ServiceTicket {

    /** Unique Id for serialization. */
    private static final long serialVersionUID = -4223319704861765405L;

    /** The service this ticket is valid for. */
    @Lob
    @Column(name="SERVICE",nullable=false)
    private Service service;

    /** Is this service ticket the result of a new login. */
    @Column(name="FROM_NEW_LOGIN",nullable=false)
    private boolean fromNewLogin;

    @Column(name="TICKET_ALREADY_GRANTED",nullable=false)
    private Boolean grantedTicketAlready = false;
    
    public ServiceTicketImpl() {
        // exists for JPA purposes
    }

    /**
     * Constructs a new ServiceTicket with a Unique Id, a TicketGrantingTicket,
     * a Service, Expiration Policy and a flag to determine if the ticket
     * creation was from a new Login or not.
     * 
     * @param id the unique identifier for the ticket.
     * @param ticket the TicketGrantingTicket parent.
     * @param service the service this ticket is for.
     * @param fromNewLogin is it from a new login.
     * @param policy the expiration policy for the Ticket.
     * @throws IllegalArgumentException if the TicketGrantingTicket or the
     * Service are null.
     */
    protected ServiceTicketImpl(final String id,
        final TicketGrantingTicketImpl ticket, final Service service,
        final boolean fromNewLogin, final ExpirationPolicy policy) {
        super(id, ticket, policy);

        Assert.notNull(ticket, "ticket cannot be null");
        Assert.notNull(service, "service cannot be null");

        this.service = service;
        this.fromNewLogin = fromNewLogin;
    }

    public boolean isFromNewLogin() {
        return this.fromNewLogin;
    }

    public Service getService() {
        return this.service;
    }

    public boolean isValidFor(final Service serviceToValidate) {
        updateState();
        return serviceToValidate.matches(this.service);
    }

    public TicketGrantingTicket grantTicketGrantingTicket(
        final String id, final Authentication authentication,
        final ExpirationPolicy expirationPolicy) {
        synchronized (this) {
            if(this.grantedTicketAlready) {
                throw new IllegalStateException(
                    "TicketGrantingTicket already generated for this ServiceTicket.  Cannot grant more than one TGT for ServiceTicket");
            }
            this.grantedTicketAlready = true;
        }

        return new TicketGrantingTicketImpl(id, (TicketGrantingTicketImpl) this.getGrantingTicket(),
            authentication, expirationPolicy);
    }
    
    public Authentication getAuthentication() {
        return null;
    }
    
    public final boolean equals(final Object object) {
        if (object == null
            || !(object instanceof ServiceTicket)) {
            return false;
        }

        final Ticket serviceTicket = (Ticket) object;
        
        return serviceTicket.getId().equals(this.getId());
    }
}
