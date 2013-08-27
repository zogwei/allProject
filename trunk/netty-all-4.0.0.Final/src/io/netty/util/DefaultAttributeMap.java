/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.util;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Default {@link AttributeMap} implementation which use simple synchronization to keep the memory overhead
 * as low as possible.
 */
public class DefaultAttributeMap implements AttributeMap {

	/**
	 * myOpinion map存储attributekey ,attributeValue,
	 *               每个key中都有一个静态map所有key,
	 *               attributeValue中包括key
	 * myDoubt 对于这种设计的目的未能理解？
	 */
    @SuppressWarnings("rawtypes")
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, Map> updater =
            AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, Map.class, "map");

    // Initialize lazily to reduce memory consumption; updated by AtomicReferenceFieldUpdater above.
    @SuppressWarnings("UnusedDeclaration")
    private volatile Map<AttributeKey<?>, Attribute<?>> map;

   
    @Override
    public <T> Attribute<T> attr(AttributeKey<T> key) {
        Map<AttributeKey<?>, Attribute<?>> map = this.map;
        if (map == null) {
            // Not using ConcurrentHashMap due to high memory consumption.
            map = new IdentityHashMap<AttributeKey<?>, Attribute<?>>(2);
            if (!updater.compareAndSet(this, null, map)) {
                map = this.map;
            }
        }

        /**
         * myDoubt 是否考虑使用普通读写锁，提高性能
         */
        synchronized (map) {
            @SuppressWarnings("unchecked")
            Attribute<T> attr = (Attribute<T>) map.get(key);
            if (attr == null) {
                attr = new DefaultAttribute<T>(map, key);
                map.put(key, attr);
            }
            return attr;
        }
    }

    /**
     * myOpinion 集成AtomicReference，说明基本AtomicReference的特性，
     *          即Attibute也可以拥有AtomicReference的功能
     *          从面向对象的思想看，DefaultAttribute 是AtomicReference的一个抽象
     */
    
    private static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T> {

        private static final long serialVersionUID = -2661411462200283011L;

        private final Map<AttributeKey<?>, Attribute<?>> map;
        private final AttributeKey<T> key;

        DefaultAttribute(Map<AttributeKey<?>, Attribute<?>> map, AttributeKey<T> key) {
            this.map = map;
            this.key = key;
        }

        @Override
        public AttributeKey<T> key() {
            return key;
        }

        @Override
        public T setIfAbsent(T value) {
            while (!compareAndSet(null, value)) {
                T old = get();
                if (old != null) {
                    return old;
                }
            }
            return null;
        }

        @Override
        public T getAndRemove() {
            T oldValue = getAndSet(null);
            remove0();
            return oldValue;
        }

        @Override
        public void remove() {
            set(null);
            remove0();
        }

        private void remove0() {
            synchronized (map) {
                map.remove(key);
            }
     }
    }
}
