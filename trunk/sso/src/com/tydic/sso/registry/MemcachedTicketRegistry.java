
package com.tydic.sso.registry;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.AbstractDistributedTicketRegistry;
import org.springframework.beans.factory.DisposableBean;

/**
 * Memcache (or Repcache) backed ticket registry.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2005/08/19 18:27:17 $
 * @since 3.3
 *
 */
public final class MemcachedTicketRegistry extends AbstractDistributedTicketRegistry implements DisposableBean {
	

	private MemcachedClient client;
	
	private int stTimeOut;
	
	private int tgtTimeOut;
	
	/**
	 * Host names should be given in a list of the format: &lt;hostname&gt;:&lt;port&gt;
	 * 
	 * @param hostnames
	 * @param ticketGrantingTicketTimeOut
	 * @param serviceTicketTimeOut
	 */
	public MemcachedTicketRegistry(MemcachedClient _client,int timeOut,int _stTimeOut,int _tgtTimeOut) {
		this.client = _client;
		this.client.setOpTimeout(timeOut);
		this.stTimeOut = _stTimeOut;
		this.tgtTimeOut = _tgtTimeOut;
	}
	

	protected void updateTicket(final Ticket ticket) {
		if (ticket instanceof TicketGrantingTicket) {
			try {
				client.replace(ticket.getId(), 3000, ticket);
			} catch (TimeoutException e) {
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		if (ticket instanceof ServiceTicket) {
			try {
				client.replace(ticket.getId(), 3000, ticket);
			} catch (TimeoutException e) {
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	public void addTicket(final Ticket ticket) {
		if (ticket instanceof TicketGrantingTicket) {
		    try {
				this.client.add(ticket.getId(), this.tgtTimeOut, ticket);
			} catch (TimeoutException e) {
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		if (ticket instanceof ServiceTicket) {
			try {
				this.client.add(ticket.getId(), this.stTimeOut, ticket);
			} catch (TimeoutException e) {
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public boolean deleteTicket(final String ticketId) {
		boolean flag = false;
		try {
			flag = this.client.delete(ticketId);
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		return flag;
	}

	public Ticket getTicket(final String ticketId) {
		Ticket t = null;
		try {
			t = (Ticket) this.client.get(ticketId);
		} catch (TimeoutException e) {
			log.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			log.error(e.getMessage(), e);
		}
		if (t == null) {
			return null;
		}
		return getProxiedTicketInstance(t);
	}

	/**
	 * This operation is not supported.
	 * 
	 * @throws UnsupportedOperationException if you try and call this operation.
	 */
	public Collection<Ticket> getTickets() {
		throw new UnsupportedOperationException("GetTickets not supported.");
	}

	public void destroy() throws Exception {
		this.client.shutdown();
	}
}
