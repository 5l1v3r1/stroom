/*
 * Copyright 2019 Crown Copyright
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

package stroom.proxy.repo;


import stroom.data.zip.StroomZipEntry;
import stroom.data.zip.StroomZipOutputStream;
import stroom.util.io.CloseableUtil;

import java.io.IOException;
import java.io.OutputStream;

class StroomZipOutputStreamUtil {
    static void addSimpleEntry(StroomZipOutputStream stroomZipOutputStream, StroomZipEntry entry, byte[] data)
            throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = stroomZipOutputStream.addEntry(entry.getFullName());
            outputStream.write(data);
        } finally {
            CloseableUtil.close(outputStream);
        }
    }
}
