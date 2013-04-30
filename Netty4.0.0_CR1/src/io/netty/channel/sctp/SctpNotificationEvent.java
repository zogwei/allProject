/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.channel.sctp;

import com.sun.nio.sctp.Notification;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

/**
 * A Notification event which carries a {@link Notification} from the SCTP stack to a SCTP {@link ChannelPipeline}.
 * <p>
 * Following notifications may be supported by a {@link SctpChannel};
 * AssociationChangeNotification, PeerAddressChangeNotification, SendFailedNotification, ShutdownNotification and
 * additional implementation specific notifications.
 *</p>
 *
 * <p>
 * User can handle the notification events of a {@link SctpChannel} by override the following method
 * {@link ChannelHandler#userEventTriggered(ChannelHandlerContext, Object)}.
 * </p>
 */
public final class SctpNotificationEvent {
    private final Notification notification;
    private final Object attachment;

    /**
     * Create a new instance
     *
     * @param notification  the {@link Notification} which triggered this event
     * @param attachment    the attachment or {@code null} if non is attached.
     */
    public SctpNotificationEvent(Notification notification, Object attachment) {
        if (notification == null) {
            throw new NullPointerException("notification");
        }
        this.notification = notification;
        this.attachment = attachment;
    }

    /**
     * Return the {@link Notification}
     */
    public Notification notification() {
        return notification;
    }

    /**
     * Return the attachment of this {@link SctpNotificationEvent}, or
     * {@code null} if no attachment was provided
     */
    public Object attachment() {
        return attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SctpNotificationEvent that = (SctpNotificationEvent) o;

        if (!attachment.equals(that.attachment)) {
            return false;
        }

        if (!notification.equals(that.notification)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = notification.hashCode();
        result = 31 * result + attachment.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SctpNotification{" +
                "notification=" + notification +
                ", attachment=" + attachment +
                '}';
    }
}
