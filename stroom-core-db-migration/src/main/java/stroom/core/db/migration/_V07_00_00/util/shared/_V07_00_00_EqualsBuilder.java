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

package stroom.core.db.migration._V07_00_00.util.shared;

/**
 * A GWT friendly version of commons EqualsBuilder.
 */
public class _V07_00_00_EqualsBuilder {
    private boolean isEquals = true;

    public _V07_00_00_EqualsBuilder appendSuper(final boolean value) {
        if (!isEquals) {
            return this;
        }
        isEquals = value;
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final Object lhs, final Object rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        isEquals = lhs.equals(rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final long lhs, final long rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final long[] lhs, final long[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final int lhs, final int rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(int[] lhs, int[] rhs) {
        if (isEquals == false) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final short lhs, final short rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final short[] lhs, final short[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final char lhs, final char rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final char[] lhs, final char[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final byte lhs, final byte rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final byte[] lhs, final byte[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final double lhs, final double rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final double[] lhs, final double[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final float lhs, final float rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final float[] lhs, final float[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final boolean lhs, final boolean rhs) {
        if (!isEquals) {
            return this;
        }

        isEquals = (lhs == rhs);
        return this;
    }

    public _V07_00_00_EqualsBuilder append(final boolean[] lhs, final boolean[] rhs) {
        if (!isEquals) {
            return this;
        }
        if (lhs == rhs) {
            return this;
        }
        if (lhs == null || rhs == null) {
            isEquals = false;
            return this;
        }
        if (lhs.length != rhs.length) {
            isEquals = false;
            return this;
        }
        for (int i = 0; i < lhs.length && isEquals; ++i) {
            append(lhs[i], rhs[i]);
        }
        return this;
    }

    public boolean isEquals() {
        return isEquals;
    }
}
