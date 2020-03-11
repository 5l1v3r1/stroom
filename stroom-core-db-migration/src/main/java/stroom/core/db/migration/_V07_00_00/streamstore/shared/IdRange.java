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

package stroom.core.db.migration._V07_00_00.streamstore.shared;

import stroom.util.shared.Range;

public class IdRange extends Range<Long> {
    private static final long serialVersionUID = -2823282661622633533L;

    public IdRange(Long start, Long end) {
        super(start, end);
    }

    public IdRange() {
    }

    public static IdRange createIdRangeSpanning(Long point, long size) {
        if (point == null) {
            return new IdRange(null, null);
        } else {
            long start = point / size;
            return new IdRange(start * size, (start + 1) * size);
        }
    }

    public void copyFrom(IdRange other) {
        if (other != null) {
            setFrom(other.getFrom());
            setTo(other.getTo());
        }
    }

}
