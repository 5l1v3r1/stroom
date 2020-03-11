/*
 * Copyright 2016 Crown Copyright
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

package stroom.core.db.migration._V07_00_00.doc.pipeline;

import stroom.core.db.migration._V07_00_00.docref._V07_00_00_HasDisplayValue;
import stroom.core.db.migration._V07_00_00.docref._V07_00_00_SharedObject;
import stroom.core.db.migration._V07_00_00.util.shared._V07_00_00_HasType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _V07_00_00_PipelineElementType
        implements Comparable<_V07_00_00_PipelineElementType>, _V07_00_00_HasType, _V07_00_00_SharedObject {
    public static final String ROLE_SOURCE = "source";
    public static final String ROLE_DESTINATION = "destination";
    public static final String ROLE_TARGET = "target";
    public static final String ROLE_HAS_TARGETS = "hasTargets";
    public static final String ROLE_READER = "reader";
    public static final String ROLE_PARSER = "parser";
    public static final String ROLE_WRITER = "writer";
    /**
     * Pipeline elements that mutate the input provided to them to produce
     * different output, e.g. XSLT filter.
     */
    public static final String ROLE_MUTATOR = "mutator";
    /**
     * Pipeline elements that validate provided input and produce a set of
     * indicators to show where the input is invalid.
     */
    public static final String ROLE_VALIDATOR = "validator";
    /**
     * Pipeline elements that have code associated with them that alters their
     * behaviour, e.g. XSLT filter or various parser types.
     */
    public static final String ROLE_HAS_CODE = "hasCode";
    /**
     * Add this type to elements that we want to appear in the pipeline tree in
     * simple mode.
     */
    public static final String VISABILITY_SIMPLE = "simple";
    /**
     * Add this type to elements that we want to appear in the pipeline tree in
     * stepping mode.
     */
    public static final String VISABILITY_STEPPING = "stepping";
    private static final long serialVersionUID = -5605044940329810364L;
    private String type;
    private Category category;
    private Set<String> roles;
    private String icon;

    public _V07_00_00_PipelineElementType() {
    }

    public _V07_00_00_PipelineElementType(final String type, final Category category, final String[] roles, final String icon) {
        this.type = type;
        this.category = category;

        if (roles == null || roles.length == 0) {
            this.roles = new HashSet<>(0);
        } else {
            this.roles = new HashSet<>(Arrays.asList(roles));
        }

        this.icon = icon;
    }

    @Override
    public String getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getIcon() {
        return icon;
    }

    public boolean hasRole(final String role) {
        return roles.contains(role);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof _V07_00_00_PipelineElementType)) {
            return false;
        }

        final _V07_00_00_PipelineElementType elementType = (_V07_00_00_PipelineElementType) obj;
        return type.equals(elementType.type);
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public int compareTo(final _V07_00_00_PipelineElementType o) {
        return type.compareTo(o.type);
    }

    private enum Category implements _V07_00_00_HasDisplayValue {
        INTERNAL("Internal", -1), READER("Reader", 0), PARSER("Parser", 1), FILTER("Filter", 2), WRITER("Writer",
                3), DESTINATION("Destination", 4);

        private final String displayValue;
        private final int order;

        Category(final String displayValue, final int order) {
            this.displayValue = displayValue;
            this.order = order;
        }

        @Override
        public String getDisplayValue() {
            return displayValue;
        }

        public int getOrder() {
            return order;
        }
    }
}
