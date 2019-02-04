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

package stroom.pipeline.state;

import stroom.meta.shared.Meta;
import stroom.data.store.api.StreamSource;
import stroom.data.store.api.StreamSourceInputStreamProvider;
import stroom.pipeline.scope.PipelineScoped;
import stroom.io.StreamCloser;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@PipelineScoped
public class MetaHolder implements Holder {
    private final Map<String, StreamSourceInputStreamProvider> streamProviders = new HashMap<>();

    private final StreamCloser streamCloser;

    private Meta meta;
    private long streamNo;

    @Inject
    public MetaHolder(final StreamCloser streamCloser) {
        this.streamCloser = streamCloser;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(final Meta meta) {
        this.meta = meta;
    }

    public void addProvider(final StreamSource source, final String streamType) {
        if (source != null) {
            streamCloser.add(source);
            addProvider(source.getInputStreamProvider(), streamType);
        }
    }

    public void addProvider(final StreamSourceInputStreamProvider provider, final String streamType) {
        if (provider != null) {
            streamCloser.add(provider);
            streamProviders.put(streamType, provider);
        }
    }

    public StreamSourceInputStreamProvider getProvider(final String streamType) {
        return streamProviders.get(streamType);
    }

    public long getStreamNo() {
        return streamNo;
    }

    public void setStreamNo(final long streamNo) {
        this.streamNo = streamNo;
    }
}