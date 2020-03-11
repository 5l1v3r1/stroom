/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.core.db.migration._V07_00_00.entity.shared;

import stroom.core.db.migration._V07_00_00.docref._V07_00_00_DocRef;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_SharedObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "docs")
public class _V07_00_00_DocRefs implements Iterable<_V07_00_00_DocRef>, _V07_00_00_SharedObject {
    private static final long serialVersionUID = 8637215303311013483L;
    private Set<_V07_00_00_DocRef> set = new TreeSet<>();

    public _V07_00_00_DocRefs() {
    }

    public boolean add(_V07_00_00_DocRef docRef) {
        return set.add(docRef);
    }

    public boolean remove(_V07_00_00_DocRef docRef) {
        return set.remove(docRef);
    }

    @Override
    public Iterator<_V07_00_00_DocRef> iterator() {
        return set.iterator();
    }

    /**
     * HERE FOR XML JAXB serialisation ..... DO NOT REMOVE
     */
    public Collection<_V07_00_00_DocRef> getDoc() {
        return set;
    }

    /**
     * HERE FOR XML JAXB serialisation ..... DO NOT REMOVE
     */
    public void setDoc(final Collection<_V07_00_00_DocRef> newSet) {
        set = new TreeSet<>(newSet);
    }
}
